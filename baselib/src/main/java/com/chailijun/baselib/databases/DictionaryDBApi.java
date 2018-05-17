package com.chailijun.baselib.databases;

import com.chailijun.baselib.repository.Dictionary;

import io.reactivex.Flowable;

public interface DictionaryDBApi {

    /**
     * 根据汉字查找
     *
     * @param zi
     * @return
     */
    Flowable<Dictionary> getDictionary(String zi);
}
