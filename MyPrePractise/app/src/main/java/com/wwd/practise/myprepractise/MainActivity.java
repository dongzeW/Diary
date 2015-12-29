package com.wwd.practise.myprepractise;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.wwd.practise.myprepractise.comment.BaseActivity;

public class MainActivity extends BaseActivity {
  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.navigation_view) NavigationView mNavigationView;
  @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
  @Bind(R.id.tab_layout) TabLayout mTabLayout;
  @Bind(R.id.view_pager) ViewPager mViewPager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //可以创建简单的登录错误提示框
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });

    ActionBar actionBar = getSupportActionBar();
    actionBar.setHomeAsUpIndicator(android.R.drawable.ic_dialog_alert);
    actionBar.setDisplayHomeAsUpEnabled(true);

    mNavigationView.setNavigationItemSelectedListener(naviListener);

    mDrawerLayout.openDrawer(mNavigationView);
  }

  private NavigationView.OnNavigationItemSelectedListener naviListener =
      new NavigationView.OnNavigationItemSelectedListener() {
        @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
          switch (menuItem.getItemId()) {

          }
          return false;
        }
      };
}

