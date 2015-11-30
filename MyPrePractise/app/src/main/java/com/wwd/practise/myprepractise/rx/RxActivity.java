package com.wwd.practise.myprepractise.rx;

import android.os.Bundle;
import com.wwd.practise.myprepractise.R;
import com.wwd.practise.myprepractise.Utils.LogUtils;
import com.wwd.practise.myprepractise.comment.BaseActivity;
import rx.Observable;
import rx.functions.Action1;

public class RxActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rx);
    printStr();
  }

  private void printStr() {
    String[] names = { "a", "b", "c", "d" };
    Observable.from(names).subscribe(myLog);
  }

  Action1 myLog = new Action1<String>() {
    @Override public void call(String s) {
      LogUtils.i(s);
    }
  };
}
