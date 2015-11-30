package com.wwd.practise.myprepractise.preglide.api;

import android.content.Context;
import android.util.SparseArray;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.LruCache;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 创建者: wwd
 * 创建日期:15/11/4
 * 类的功能描述:
 */
public class Api {
  private static Api api;
  private static final int MAX_URLS_TO_CACHE = 2000;
  private static final LruCache<UrlCacheKey, String> CACHE_URLS = new LruCache<>(MAX_URLS_TO_CACHE);
  private static final int MAX_ITEMS_PRE_PAGE = 300;
  private static final String PRE_PAGE = "&per_page=" + MAX_ITEMS_PRE_PAGE;
  private static final String SIGNED_API_URL = "";
  private static final String CACHEABLE_PHOTO_URL = "http://farm%s.staticflickr.com/%s/%s_%s_";
  private static final SparseArray<String> EDGE_TO_SIZE_KEY = new SparseArray<String>() {
    {
      put(75, "s");
      put(100, "t");
      put(150, "q");
      put(240, "m");
      put(320, "n");
      put(640, "z");
      put(1024, "b");
    }
  };
  public static final List<Integer> SORTED_SIZE_KEYS = new ArrayList<>(EDGE_TO_SIZE_KEY.size());

  static {
    for (int i = 0; i < EDGE_TO_SIZE_KEY.size(); i++) {
      SORTED_SIZE_KEYS.add(EDGE_TO_SIZE_KEY.keyAt(i));
    }
    Collections.sort(SORTED_SIZE_KEYS);
  }

  public static final int SQUARE_THUMB_SIZE = SORTED_SIZE_KEYS.get(0);

  private static String getSizeKey(int width, int height) {
    final int largestEdge = Math.max(width, height);
    String result = EDGE_TO_SIZE_KEY.get(SORTED_SIZE_KEYS.get(SORTED_SIZE_KEYS.size() - 1));
    for (int edge : SORTED_SIZE_KEYS) {
      if (largestEdge <= edge) {
        result = EDGE_TO_SIZE_KEY.get(edge);
        break;
      }
    }
    return result;
  }

  private static List<String> getLargerSizeKeys(int width, int height) {
    final int largestEdge = Math.max(width, height);
    List<String> result = new ArrayList<>();
    boolean isFirstLargest = true;
    for (int edge : SORTED_SIZE_KEYS) {
      if (largestEdge <= edge) {
        if (isFirstLargest) {
          isFirstLargest = false;
        } else {
          result.add(EDGE_TO_SIZE_KEY.get(edge));
        }
      }
    }
    return result;
  }

  public static String getCacheableUrl(Photo photo) {
    return String.format(CACHEABLE_PHOTO_URL, photo.getFarm(), photo.getServer(), photo.getId(),
        photo.getSecret());
  }

  public static String getPhotoUrl(Photo photo, int width, int height) {
    return getPhotoUrl(photo, getSizeKey(width, height));
  }

  public static String getPhotoUrl(Photo photo, String sizeKey) {
    UrlCacheKey entry = new UrlCacheKey(photo, sizeKey);
    String result = CACHE_URLS.get(entry);
    if (result == null) {
      //添加缓存url
      result = photo.getPartialUrl() + sizeKey + ".jpg";
      CACHE_URLS.put(entry, result);
    }
    return result;
  }

  private static String getUrlForMethod(String method) {
    return String.format(SIGNED_API_URL, method);
  }

  public static List<String> getAlternateUrls(Photo photo, int width, int height) {
    List<String> result = new ArrayList<>();
    for (String sizeKey : getLargerSizeKeys(width, height)) {
      result.add(getPhotoUrl(photo, sizeKey));
    }
    return result;
  }

  static String getSearchUrl(String text) {
    return getUrlForMethod("flickr.photos.search") + "&text=" + text + PRE_PAGE;
  }

  static String getRecentUrl() {
    return getUrlForMethod("flickr.photos.getRecent" + PRE_PAGE);
  }

  public interface QueryListener {
    void onSearchCompleted(Query query, List<Photo> photos);

    void onSearchFailed(Query query, Exception e);
  }

  public static Api get(Context context) {
    if (api == null) {
      api = new Api(context);
    }
    return api;
  }

  private RequestQueue requestQueue;
  private Set<QueryListener> queryListeners = new HashSet<>();
  private QueryResult lastQueryResult;

  protected Api(Context context) {
    this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    QueryListener queryListener = new QueryListener() {
      @Override public void onSearchCompleted(Query query, List<Photo> photos) {
        lastQueryResult = new QueryResult(query, photos);
      }

      @Override public void onSearchFailed(Query query, Exception e) {
        lastQueryResult = null;
      }
    };
    queryListeners.add(queryListener);
  }

  public void registerSearchListener(QueryListener queryListener) {
    queryListeners.add(queryListener);
  }

  public void unregisterSearchListener(QueryListener queryListener) {
    queryListeners.remove(queryListener);
  }

  public void query(Query query) {
    if (lastQueryResult != null && lastQueryResult.query.equals(query)) {
      for (QueryListener listener : queryListeners) {
        listener.onSearchCompleted(lastQueryResult.query, lastQueryResult.results);
      }
      return;
    }
    FlickrQueryResponseListener responseListener =
        new FlickrQueryResponseListener(new PhotoJsonStringParser(), query, queryListeners);
    StringRequest request =
        new StringRequest(Request.Method.GET, query.getUrl(), responseListener, responseListener);
    request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 3,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    requestQueue.add(request);
  }

  private static final class UrlCacheKey {
    private Photo photo;
    private String sizeKey;

    private UrlCacheKey(Photo photo, String size) {
      this.photo = photo;
      this.sizeKey = size;
    }
  }

  private static class QueryResult {
    private Query query;
    private List<Photo> results;

    public QueryResult(Query query, List<Photo> results) {
      this.query = query;
      this.results = results;
    }
  }
}
