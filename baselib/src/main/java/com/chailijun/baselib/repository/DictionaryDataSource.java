package com.chailijun.baselib.repository;


import io.reactivex.Flowable;

/**
 * Created by PVer on 2018/3/28.
 */

public interface DictionaryDataSource {

    Flowable<Dictionary> getDictionary(String hanzi);


}
