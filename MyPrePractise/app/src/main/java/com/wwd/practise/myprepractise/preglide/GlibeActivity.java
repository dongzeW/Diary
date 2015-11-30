package com.wwd.practise.myprepractise.preglide;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.prefill.PreFillType;
import com.bumptech.glide.request.FutureTarget;
import com.facebook.stetho.common.LogUtil;
import com.wwd.practise.myprepractise.R;
import com.wwd.practise.myprepractise.Utils.LogUtils;
import com.wwd.practise.myprepractise.comment.BaseActivity;
import com.wwd.practise.myprepractise.preglide.api.Api;
import com.wwd.practise.myprepractise.preglide.api.Photo;
import com.wwd.practise.myprepractise.preglide.api.Query;
import com.wwd.practise.myprepractise.preglide.api.RecentQuery;
import com.wwd.practise.myprepractise.preglide.api.SearchQuery;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * 创建者: wwd
 * 创建日期:15/11/2
 * 类的功能描述:
 */
public class GlibeActivity extends BaseActivity implements SearchView.OnQueryTextListener {
  private static final String TAG = GlibeActivity.class.getSimpleName();
  private static final String STATE_QUERY = "state_search_str";
  //private static final Api.QueryListener = new Api.QueryListener();
  private Query currentQuery;
  private BackgroundThumbnailFetcher backgroundThumbnailFetcher;
  private Set<PhotoViewer> photoViewers = new HashSet<>();
  private List<Photo> currentPhotos = new ArrayList<>();
  private HandlerThread backgroundThread;
  private Handler backgroundHandler;
  @Bind(R.id.search) View searching;
  View searchLoading;
  TextView searchTerm;
  SearchView searchView;
  private final QueryListener queryListener = new QueryListener();
  @Bind(R.id.view_pager) ViewPager pager;

  private enum Page {
    SMALL, MEDIUM, LIST
  }

  private static final Map<Page, Integer> PAGE_TO_TITLE = new HashMap<Page, Integer>() {
    {
      put(Page.SMALL, R.string.small);
      put(Page.MEDIUM, R.string.medium);
      put(Page.LIST, R.string.list);
    }
  };

  @Override public void onAttachFragment(Fragment fragment) {
    super.onAttachFragment(fragment);
    if (fragment instanceof PhotoViewer) {
      PhotoViewer photoViewer = (PhotoViewer) fragment;
      photoViewer.onPhotesUpdated(currentPhotos);
      if (!photoViewers.contains(photoViewer)) {
        photoViewers.add(photoViewer);
      }
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.search_activity, menu);
    searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
    searchView.setSubmitButtonEnabled(true);
    searchView.setIconified(false);
    searchView.setOnQueryTextListener(this);
    return true;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.flickr_search_activity);
    ButterKnife.bind(this);
    StrictMode.setThreadPolicy(
        new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
    backgroundThread = new HandlerThread("BackgroundThumbnailHandlerThread");
    backgroundThread.start();
    backgroundHandler = new Handler(backgroundThread.getLooper());
    Resources res = getResources();
    pager.setPageMargin(res.getDimensionPixelOffset(R.dimen.page_margin));
    //pager.setAdapter();
    if (savedInstanceState != null) {
      Query savedQuery = savedInstanceState.getParcelable(STATE_QUERY);
      if (savedQuery != null) {
        executeQuery(savedQuery);
      }
    } else {
      executeQuery(RecentQuery.get());
    }
    int smallGridSize = res.getDimensionPixelSize(R.dimen.small_photo_side);
    int mediumGridSize = res.getDimensionPixelSize(R.dimen.medium_photo_side);
    int listHeightSize = res.getDimensionPixelSize(R.dimen.flickr_list_item_height);
    int screenWidth = getScreenWidth();
    if (savedInstanceState == null) {
      Glide.get(this)
          .preFillBitmapPool(new PreFillType.Builder(smallGridSize).setWeight(1),
              new PreFillType.Builder(mediumGridSize).setWeight(1),
              new PreFillType.Builder(screenWidth / 2, listHeightSize).setWeight(6));
    }
  }

  @Override public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
    if (currentQuery != null) {
      outState.putParcelable(STATE_QUERY, currentQuery);
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    Api.get(this).unregisterSearchListener(queryListener);
    if (backgroundThumbnailFetcher != null) {
      backgroundThumbnailFetcher.cancel();
      backgroundThumbnailFetcher = null;
      backgroundThread.quit();
      backgroundThread = null;
    }
  }

  @Override public void onTrimMemory(int level) {
    super.onTrimMemory(level);
    Glide.get(this).trimMemory(level);
  }

  @Override public void onLowMemory() {
    super.onLowMemory();
    Glide.get(this).clearMemory();
  }

  private int getScreenWidth() {
    return getResources().getDisplayMetrics().widthPixels;
  }

  @Override public boolean onQueryTextSubmit(String query) {
    executeSearch(query);
    searchView.setQuery("", false/*submit*/);
    return false;
  }

  private void executeSearch(String searchString) {
    Query query = TextUtils.isEmpty(searchString) ? null : new SearchQuery(searchString);
    executeQuery(query);
  }

  private void executeQuery(Query query) {
    currentQuery = query;
    if (query == null) {
      queryListener.onSearchCompleted(null, Collections.<Photo>emptyList());
      return;
    }
    searching.setVisibility(View.VISIBLE);
    searchLoading.setVisibility(View.VISIBLE);
    searchTerm.setText(getString(R.string.searching_for, currentQuery.getDescription()));
    Api.get(this).query(currentQuery);
  }

  @Override public boolean onQueryTextChange(String newText) {
    return false;
  }

  private class QueryListener implements Api.QueryListener {

    private boolean isCurrentQuery(Query query) {
      return currentQuery != null && currentQuery.equals(query);
    }

    @Override public void onSearchCompleted(Query query, List<Photo> photos) {
      if (!isCurrentQuery(query)) {
        return;
      }
      LogUtils.i("search completed ,got" + photos.size());
      searching.setVisibility(View.INVISIBLE);
      for (PhotoViewer viewer : photoViewers) {
        viewer.onPhotesUpdated(photos);
      }
      if (backgroundThumbnailFetcher != null) {
        backgroundThumbnailFetcher.cancel();
      }
      backgroundThumbnailFetcher = new BackgroundThumbnailFetcher(GlibeActivity.this, photos);
      backgroundHandler.post(backgroundThumbnailFetcher);
      currentPhotos = photos;
    }

    @Override public void onSearchFailed(Query query, Exception e) {
      if (!isCurrentQuery(query)) {
        return;
      }
      LogUtil.i("search failed");
      searching.setVisibility(View.VISIBLE);
      searchLoading.setVisibility(View.INVISIBLE);
      searchTerm.setText(getString(R.string.search_failed, currentQuery.getDescription()));
    }
  }

  private class FlickrPagerAdapter extends FragmentPagerAdapter {

    private int mLastPosition = -1;
    private Fragment mLastFragment;

    public FlickrPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      return pageToFragment(position);
    }

    @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
      super.setPrimaryItem(container, position, object);
      if (position != mLastPosition) {
        if (mLastPosition >= 0) {
          Glide.with(mLastFragment).pauseRequests();
        }
        Fragment current = (Fragment) object;
        mLastPosition = position;
        mLastFragment = current;
        if (current.isAdded()) {
          Glide.with(current).resumeRequests();
        }
      }
    }

    public CharSequence getPageTitle(int position) {
      Page page = Page.values()[position];
      int titleId = PAGE_TO_TITLE.get(page);
      return getString(titleId);
    }

    private Fragment pageToFragment(int position) {
      Page page = Page.values()[position];
      if (page == Page.SMALL) {
        int pageSize = getPageSize(R.dimen.small_photo_side);
        return FlickrPhotoGrid.newInstance(pageSize, 15, false);
      } else if (page == Page.MEDIUM) {
        int pageSize = getPageSize(R.dimen.medium_photo_side);
        return FlickrPhotoGrid.newInstance(pageSize, 10, true);
      } else if (page == Page.LIST) {
        return FlickrPhotoList.newInstance();
      } else {
        throw new IllegalArgumentException("error ... frag");
      }
    }

    private int getPageSize(int id) {
      return getResources().getDimensionPixelSize(id);
    }

    @Override public int getCount() {
      return Page.values().length;
    }
  }

  private static class BackgroundThumbnailFetcher implements Runnable {
    private boolean isCancelled;
    private Context context;
    private List<Photo> photos;

    public BackgroundThumbnailFetcher(Context context, List<Photo> photos) {
      this.context = context;
      this.photos = photos;
    }

    public void cancel() {
      isCancelled = true;
    }

    @Override public void run() {

      //盲点，学习线程设置有限级的作用。
      android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_LOWEST);
      for (Photo photo : photos) {
        if (photo == null) {

        }
        if (isCancelled) {
          return;
        }
        FutureTarget<File> futureTarget = Glide.with(context)
            .load(photo)
            .downloadOnly(Api.SQUARE_THUMB_SIZE, Api.SQUARE_THUMB_SIZE);
        try {
          futureTarget.get();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
        Glide.clear(futureTarget);
      }
    }
  }
}
