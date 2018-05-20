package com.chailijun.dictionaryking;

import android.graphics.Typeface;

import com.chailijun.baselib.base.BaseApplication;

import java.lang.reflect.Field;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends BaseApplication{
    @Override
    protected void init() {

//        //默认使用的高度是设备的可用高度，也就是不包括状态栏和底部的操作栏的，如果你希望拿设备的物理高度进行百分比化：
//        //可以在Application的onCreate方法中进行设置:
//        AutoLayoutConifg.getInstance().useDeviceSize();

//        initTypeface();

        //修改系统默认字体
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/HuaWenKaiTi.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    /**
     * 修改系统默认字体
     */
    private void initTypeface() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/HuaWenKaiTi.ttf");

        try {
            Field field = Typeface.class.getDeclaredField("MONOSPACE");
            field.setAccessible(true);
            try {
                field.set(null,typeface);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
