package com.chailijun.baselib.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.baselib.utils.StringUtils;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class DictionarysDBOpenHelper extends SQLiteAssetHelper implements DictionaryDBApi {

    private static final String TAG = DictionarysDBOpenHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "xinhuazidian.sqlite";


    public final Context mContext;

    private static DictionarysDBOpenHelper sInstance;

    public static synchronized DictionarysDBOpenHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DictionarysDBOpenHelper.class) {
                if (sInstance == null) {
                    sInstance = new DictionarysDBOpenHelper(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private DictionarysDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context.getApplicationContext();
    }

    @Override
    public Flowable<List<Dictionary>> getDictionary(String hanzi) {
        return Flowable.create(e -> {
            e.onNext(getDictionaryFromDB(hanzi));
            e.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    private List<Dictionary> getDictionaryFromDB(String hanzi) {
        if (hanzi != null && !TextUtils.isEmpty(hanzi)) {

            StringBuilder SQL = new StringBuilder();
            SQL.append("select * from xhzd_surnfu where ");

            if (!StringUtils.isContainEnglish(hanzi)) {//1.如果搜索全是汉字
                int length = hanzi.length();
                for (int i = 0; i < length; i++) {
                    SQL.append("zi = ");
                    SQL.append("'");
                    SQL.append(hanzi.charAt(i));
                    SQL.append("'");

                    if (i < length - 1) {
                        SQL.append(" or ");
                    }
                }
            } else if (!StringUtils.isContainChinese(hanzi)) {

            }
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(SQL.toString(), null);

            List<Dictionary> bookEntityList = new ArrayList<>();
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Dictionary dictionary = new Dictionary();
                        float id = cursor.getFloat(cursor.getColumnIndex("id"));
                        String zi = cursor.getString(cursor.getColumnIndex("zi"));
                        String py = cursor.getString(cursor.getColumnIndex("py"));
                        String wubi = cursor.getString(cursor.getColumnIndex("wubi"));
                        String bushou = cursor.getString(cursor.getColumnIndex("bushou"));
                        float bihua = cursor.getFloat(cursor.getColumnIndex("bihua"));
                        String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                        String jijie = cursor.getString(cursor.getColumnIndex("jijie"));
                        String xiangjie = cursor.getString(cursor.getColumnIndex("xiangjie"));

                        dictionary.setId(id);
                        dictionary.setZi(zi);
                        dictionary.setPy(py);
                        dictionary.setWubi(wubi);
                        dictionary.setBushou(bushou);
                        dictionary.setBihua(bihua);
                        dictionary.setPinyin(pinyin);
                        dictionary.setJijie(jijie);
                        dictionary.setXiangjie(xiangjie);

                        bookEntityList.add(dictionary);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.d(TAG, "Error while trying to get lesson from database");
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }

                if (!bookEntityList.isEmpty()) {

                    if (!StringUtils.isContainEnglish(hanzi)) {//1.如果搜索全是汉字
                        //搜索结果的顺序与输入的汉字顺序保持一致
                        int length = hanzi.length();
                        for (int i = 0; i < length; i++) {
                            for (Dictionary dic:bookEntityList) {
                                char c = hanzi.charAt(i);
                                char c1 = dic.getZi().charAt(0);
                                if (c == c1){
                                    dic.setSort(i);
                                    break;
                                }
                            }
                        }
                        Collections.sort(bookEntityList, new Comparator<Dictionary>() {
                            @Override
                            public int compare(Dictionary o1, Dictionary o2) {
                                return o1.getSort()-o2.getSort();
                            }
                        });
                    }

                    return bookEntityList;
                }
                return Collections.emptyList();
            }

        }
        return Collections.emptyList();

    }
}
