package com.chailijun.dictionaryking.search;


import com.chailijun.baselib.base.BasePresenter;
import com.chailijun.baselib.base.BaseView;
import com.chailijun.baselib.repository.Dictionary;

import java.util.List;


public interface SearchContract {

    interface View extends BaseView<Presenter> {

        void showError(String errorMsg);

        void showHanzi(List<Dictionary> dictionaryList);
    }

    interface Presenter extends BasePresenter {

        void search(String hanzi);

    }
}
