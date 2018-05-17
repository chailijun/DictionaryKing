package com.chailijun.dictionaryking.search;


import com.chailijun.baselib.base.BasePresenter;
import com.chailijun.baselib.base.BaseView;
import com.chailijun.baselib.repository.Dictionary;


public interface SearchContract {

    interface View extends BaseView<Presenter> {

        void showError(String errorMsg);

        void showHanzi(Dictionary dictionary);
    }

    interface Presenter extends BasePresenter {

        void search(String hanzi);

    }
}
