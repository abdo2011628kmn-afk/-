package com.example.eqfelwazaker;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class BlockActivity extends Activity {
 @Override protected void onCreate(Bundle b){super.onCreate(b);
  LinearLayout l=new LinearLayout(this);l.setOrientation(1);l.setGravity(Gravity.CENTER);l.setPadding(28,28,28,28);l.setBackgroundColor(Color.rgb(16,24,39));
  TextView t=new TextView(this);t.setText("📚\nوقت المذاكرة!\n\nارجع لهدفك، التطبيق ده مؤجل لحد انتهاء الجلسة.");t.setTextColor(Color.WHITE);t.setTextSize(24);t.setGravity(Gravity.CENTER);l.addView(t);
  Button back=new Button(this);back.setText("العودة للمذاكرة");back.setOnClickListener(v->finish());l.addView(back);
  setContentView(l);
 }
 @Override public void onBackPressed(){finish();}
}
