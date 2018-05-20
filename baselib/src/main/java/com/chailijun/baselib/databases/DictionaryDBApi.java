package com.chailijun.baselib.databases;

import com.chailijun.baselib.repository.Dictionary;

import java.util.List;

import io.reactivex.Flowable;

public interface DictionaryDBApi {

    /**
     * 根据汉字查找
     *
     * @param keyWord
     * @return
     */
    Flowable<List<Dictionary>> getDictionaryList(String keyWord);

    Flowable<Dictionary> getDictionary(String keyWord);
}
