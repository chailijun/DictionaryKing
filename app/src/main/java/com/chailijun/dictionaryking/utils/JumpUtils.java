package com.chailijun.dictionaryking.utils;

import android.app.Activity;
import android.content.Intent;

import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.dictionaryking.detail.DetailActivity;
import com.chailijun.dictionaryking.search.SearchActivity;

public class JumpUtils {

    /**
     * 跳转到汉字详细页面
     * @param activity
     * @param dictionary
     */
    public static void goToDetailActivity(Activity activity, Dictionary dictionary) {
        if (activity != null) {
            Intent intent = DetailActivity.getCallingIntent(activity, dictionary);
            activity.startActivity(intent);
        }
    }


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
