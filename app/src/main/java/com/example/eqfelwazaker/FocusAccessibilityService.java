package com.example.eqfelwazaker;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.content.Intent;
import java.util.*;

public class FocusAccessibilityService extends AccessibilityService {
    @Override public void onAccessibilityEvent(AccessibilityEvent event) {
        if(event==null || event.getEventType()!=AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return;
        CharSequence pkg=event.getPackageName(); if(pkg==null) return;
        String packageName=pkg.toString();
        if(packageName.equals(getPackageName()) || packageName.equals("com.android.systemui")) return;
        long end=getSharedPreferences(MainActivity.PREF,0).getLong(MainActivity.END,0);
        if(end<=System.currentTimeMillis()) return;
        Set<String> blocked=getSharedPreferences(MainActivity.PREF,0).getStringSet(MainActivity.BLOCKED,Collections.emptySet());
        if(blocked.contains(packageName)){
            Intent i=new Intent(this,BlockActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
    @Override public void onInterrupt() {}
}
