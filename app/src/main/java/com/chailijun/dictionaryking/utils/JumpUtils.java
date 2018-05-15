package com.chailijun.dictionaryking.utils;

import android.app.Activity;
import android.content.Intent;

import com.chailijun.dictionaryking.search.SearchActivity;

public class JumpUtils {


    /**
     * 跳转到搜索页面
     * @param activity
     */
    public static void goToSearch(Activity activity) {
        if (activity != null) {
            Intent intent = new Intent(activity, SearchActivity.class);
            activity.startActivity(intent);
        }
    }
}
