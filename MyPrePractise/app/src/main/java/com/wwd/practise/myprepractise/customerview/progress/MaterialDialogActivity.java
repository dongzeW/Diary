package com.wwd.practise.myprepractise.customerview.progress;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.wwd.practise.myprepractise.R;
import com.wwd.practise.myprepractise.comment.BaseActivity;

/**
 * 创建者: wwd
 * 创建日期:15/10/23
 * 类的功能描述:Material风格的progress
 */
public class MaterialDialogActivity extends BaseActivity {
  int progress = 0;
  @Bind(R.id.progress1) CircleProgressBar progress1;
  @Bind(R.id.progress2) CircleProgressBar progress2;
  @Bind(R.id.progressWithArrow) CircleProgressBar progressWithArrow;
  @Bind(R.id.progressWithoutBg) CircleProgressBar progressWithoutBg;
  private Handler handler;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.material_dialog);
    ButterKnife.bind(this);
    //progress1 = (CircleProgressBar) findViewById(R.id.progress1);
    //progress2 = (CircleProgressBar) findViewById(R.id.progress2);
    //progressWithArrow = (CircleProgressBar) findViewById(R.id.progressWithArrow);
    //progressWithoutBg = (CircleProgressBar) findViewById(R.id.progressWithoutBg);
    progress2.setColorSchemeResources(android.R.color.holo_green_light,
        android.R.color.holo_orange_light, android.R.color.holo_red_light);
    progressWithArrow.setColorSchemeResources(android.R.color.holo_orange_light);
    handler = new Handler();
    for (int i = 0; i < 10; i++) {
      final int finalI = i;
      handler.postDelayed(new Runnable() {
        @Override public void run() {
          if (finalI * 10 >= 90) {
            progress1.setVisibility(View.VISIBLE);
            progress2.setVisibility(View.INVISIBLE);
          } else {
            progress2.setProgress(finalI * 10);
          }
        }
      }, 1000 * (i + 1));
    }
  }
}
