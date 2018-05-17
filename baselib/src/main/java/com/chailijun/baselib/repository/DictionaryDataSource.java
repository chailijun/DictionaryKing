package com.chailijun.baselib.repository;


import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by PVer on 2018/3/28.
 */

public interface DictionaryDataSource {

    Flowable<List<Dictionary>> getDictionary(String hanzi);


}
