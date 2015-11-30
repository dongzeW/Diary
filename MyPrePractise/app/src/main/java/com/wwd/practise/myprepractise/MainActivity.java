package com.wwd.practise.myprepractise;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wwd.practise.myprepractise.comment.BaseActivity;
import com.wwd.practise.myprepractise.customerview.DataItem;
import com.wwd.practise.myprepractise.customerview.DataSource;
import com.wwd.practise.myprepractise.preglide.GlibeActivity;
import com.wwd.practise.myprepractise.rx.RxActivity;
import java.io.IOException;

public class MainActivity extends BaseActivity {

  @Bind(R.id.lv_practise) ListView mListView;
  private DataSource mDataSource;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //可以创建简单的登录错误提示框
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //    .setAction("Action", null)
        //    .show();
        Intent intent = new Intent(MainActivity.this, RxActivity.class);
        startActivity(intent);
      }
    });
    ButterKnife.bind(this);
    init();
    testBaidu();
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent glideIntent = new Intent(MainActivity.this, GlibeActivity.class);
        if (position == 0) {
          startActivity(glideIntent);
        }
      }
    });
  }

  private void testBaidu() {
    OkHttpClient ok = new OkHttpClient();
    ok.networkInterceptors().add(new StethoInterceptor());
    Request request = new Request.Builder().url("https://www.baidu.com/").build();
    Call call = ok.newCall(request);
    call.enqueue(new Callback() {
      @Override public void onFailure(Request request, IOException e) {

      }

      @Override public void onResponse(Response response) throws IOException {
        Logger.d("测试百度地址");
        Logger.e("this is error test");
      }
    });
  }

  private void init() {
    mDataSource = new DataSource(this);
    mListView.setAdapter(new SampleAdapter());
  }

  private class SampleAdapter extends BaseAdapter {

    public SampleAdapter() {
    }

    @Override public int getCount() {
      return mDataSource.getCount();
    }

    @Override public DataItem getItem(int position) {
      return mDataSource.getItem(position);
    }

    @Override public long getItemId(int position) {
      return position;
    }

    @Override public View getView(int position, android.view.View convertView, ViewGroup parent) {
      ViewHolder holder;
      if (convertView == null) {
        convertView =
            LayoutInflater.from(MainActivity.this).inflate(R.layout.item_list_textdrawable, null);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }
      DataItem item = getItem(position);
      final Drawable drawable = item.getDrawable();
      holder.imageView.setImageDrawable(drawable);
      holder.textView.setText(item.getLabel());
      if (item.getNavigationInfo() != DataSource.NO_NAVIGATION) {
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
            getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_24dp), null);
      } else {
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
      }
      if (drawable instanceof AnimationDrawable) {
        holder.imageView.post(new Runnable() {
          @Override public void run() {
            ((AnimationDrawable) drawable).stop();
            ((AnimationDrawable) drawable).start();
          }
        });
      }

      return convertView;
    }
  }

  static class ViewHolder {
    @Bind(R.id.imageView) ImageView imageView;
    @Bind(R.id.textView) TextView textView;

    public ViewHolder(View view) {
      ButterKnife.bind(this, view);
    }
  }
}

