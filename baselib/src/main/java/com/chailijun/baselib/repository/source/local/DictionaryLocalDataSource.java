package com.chailijun.baselib.repository.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chailijun.baselib.databases.DictionarysDBOpenHelper;
import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.baselib.repository.DictionaryDataSource;
import com.chailijun.baselib.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;



public class DictionaryLocalDataSource implements DictionaryDataSource {

    private static DictionaryLocalDataSource INSTANCE;
//    private BookManager bookManager;
    private DictionarysDBOpenHelper dbOpenHelper;

    // Prevent direct instantiation.
    private DictionaryLocalDataSource(@NonNull Context context,
                                      @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
//        DaoUtils.init(context);
//        bookManager = DaoUtils.getBookManager();
        dbOpenHelper = DictionarysDBOpenHelper.getInstance(context);
    }

    public static DictionaryLocalDataSource getInstance(@NonNull Context context,
                                                   @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            synchronized (DictionaryLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DictionaryLocalDataSource(context.getApplicationContext(), schedulerProvider);
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public Flowable<List<Dictionary>> getDictionaryList(String hanzi) {
        return dbOpenHelper.getDictionaryList(hanzi);
    }

    @Override
    public Flowable<Dictionary> getDictionary(String hanzi) {
        return dbOpenHelper.getDictionary(hanzi);
    }
}
