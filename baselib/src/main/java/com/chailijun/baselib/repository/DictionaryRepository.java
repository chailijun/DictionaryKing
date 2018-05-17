package com.chailijun.baselib.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by PVer on 2018/3/28.
 */

public class DictionaryRepository implements DictionaryDataSource {

    @Nullable
    private static DictionaryRepository INSTANCE = null;

//    @NonNull
//    private final DictionaryDataSource mBooksRemoteDataSource;

    @NonNull
    private final DictionaryDataSource mDictionaryLocalDataSource;


    // Prevent direct instantiation.
    private DictionaryRepository(/*@NonNull DictionaryDataSource booksRemoteDataSource,*/
                                 @NonNull DictionaryDataSource dictionaryLocalDataSource) {
//        mBooksRemoteDataSource = checkNotNull(booksRemoteDataSource);
        mDictionaryLocalDataSource = checkNotNull(dictionaryLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param booksLocalDataSource  the device storage data source
     * @return the {@link DictionaryRepository} instance
     */
    public static DictionaryRepository getInstance(/*@NonNull DictionaryDataSource booksRemoteDataSource,*/
                                                   @NonNull DictionaryDataSource booksLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (DictionaryRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DictionaryRepository(/*booksRemoteDataSource, */booksLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Flowable<Dictionary> getDictionary(String hanzi) {
        return mDictionaryLocalDataSource.getDictionary(hanzi);
    }
}
