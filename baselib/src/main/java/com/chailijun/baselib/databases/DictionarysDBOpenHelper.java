package com.chailijun.baselib.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.chailijun.baselib.repository.Dictionary;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
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
    public Flowable<Dictionary> getDictionary(String hanzi) {
        return Flowable.create(e -> {
            e.onNext(getDictionaryFromDB(hanzi));
            e.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    private Dictionary getDictionaryFromDB(String hanzi) {
        String SQL_BOOK_TYPES = "select * from xhzd_surnfu where zi = '" + hanzi+"'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_BOOK_TYPES, null);

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
                return bookEntityList.get(0);
            }
            return null;
        }
    }
}
