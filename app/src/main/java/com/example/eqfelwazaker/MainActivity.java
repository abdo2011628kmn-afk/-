package com.example.eqfelwazaker;

import android.app.*;
import android.os.*;
import android.content.*;
import android.provider.Settings;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    public static final String PREF="focus_prefs";
    public static final String END="end_time";
    public static final String BLOCKED="blocked";
    TextView timer, status;
    Handler handler=new Handler();
    Runnable ticker;
    String[] names={"YouTube","TikTok","Instagram","Facebook","PUBG Mobile","Free Fire"};
    String[] pkgs={"com.google.android.youtube","com.zhiliaoapp.musically","com.instagram.android","com.facebook.katana","com.tencent.ig","com.dts.freefireth"};
    @Override public void onCreate(Bundle b){super.onCreate(b); buildUi();}
    void buildUi(){
        LinearLayout page=new LinearLayout(this); page.setOrientation(1); page.setPadding(32,36,32,24);
        page.setBackgroundColor(0xff101827);
        TextView title=new TextView(this); title.setText("📚 اقفل وذاكر"); title.setTextSize(27); title.setTextColor(-1); title.setGravity(17); page.addView(title);
        TextView intro=new TextView(this); intro.setText("جلسة تركيز قوية — 90 دقيقة"); intro.setTextColor(0xff9fb0c5); intro.setGravity(17); intro.setPadding(0,12,0,18); page.addView(intro);
        timer=new TextView(this); timer.setTextSize(48); timer.setTextColor(0xff55d6a2); timer.setGravity(17); page.addView(timer);
        status=new TextView(this); status.setTextColor(0xffdce6f2); status.setGravity(17); status.setPadding(0,8,0,18); page.addView(status);
        Button start=btn("ابدأ جلسة 90 دقيقة"); start.setOnClickListener(v->{getPrefs().edit().putLong(END,System.currentTimeMillis()+90*60*1000L).apply(); status.setText("الجلسة تعمل"); updateTimer();}); page.addView(start);
        Button stop=btn("إنهاء الجلسة"); stop.setOnClickListener(v->{getPrefs().edit().remove(END).apply(); status.setText("تم إنهاء الجلسة"); updateTimer();}); page.addView(stop);
        TextView choose=new TextView(this); choose.setText("اختر التطبيقات التي تريد حجبها:"); choose.setTextColor(-1); choose.setTextSize(18); choose.setPadding(0,22,0,8); page.addView(choose);
        Set<String> selected=new HashSet<>(getPrefs().getStringSet(BLOCKED,new HashSet<>(Arrays.asList(pkgs))));
        for(int i=0;i<names.length;i++){ final String p=pkgs[i]; CheckBox c=new CheckBox(this); c.setText(names[i]); c.setTextColor(-1); c.setButtonTintList(android.content.res.ColorStateList.valueOf(0xff55d6a2)); c.setChecked(selected.contains(p)); c.setOnCheckedChangeListener((x,checked)->{Set<String>s=new HashSet<>(getPrefs().getStringSet(BLOCKED,new HashSet<>()));if(checked)s.add(p);else s.remove(p);getPrefs().edit().putStringSet(BLOCKED,s).apply();});page.addView(c);}
        Button access=btn("تفعيل صلاحية حجب التطبيقات"); access.setOnClickListener(v->startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))); page.addView(access);
        TextView note=new TextView(this); note.setText("يجب تفعيل خدمة «اقفل وذاكر - حجب التطبيقات» يدويًا من إعدادات إمكانية الوصول. الحجب تذكيري ويمكن إنهاء الجلسة من التطبيق أو إعدادات الهاتف. لا يحجب المكالمات والطوارئ."); note.setTextColor(0xff9fb0c5); note.setTextSize(12); note.setPadding(0,18,0,0); page.addView(note);
        ScrollView sc=new ScrollView(this);sc.addView(page);setContentView(sc);updateTimer();
    }
    Button btn(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextSize(15);return b;}
    android.content.SharedPreferences getPrefs(){return getSharedPreferences(PREF,0);}
    void updateTimer(){
        long end=getPrefs().getLong(END,0), left=Math.max(0,end-System.currentTimeMillis());
        timer.setText(String.format(java.util.Locale.US,"%02d:%02d",left/60000,(left/1000)%60));
        if(end>0 && left==0){getPrefs().edit().remove(END).apply();status.setText("انتهت الجلسة! أحسنت.");}
        else if(end==0) status.setText("جاهز للمذاكرة");
        if(ticker!=null)handler.removeCallbacks(ticker);
        ticker=()->updateTimer(); if(end>0)handler.postDelayed(ticker,1000);
    }
    @Override protected void onResume(){super.onResume();updateTimer();}
}
