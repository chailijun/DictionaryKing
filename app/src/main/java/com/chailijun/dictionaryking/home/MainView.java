package com.chailijun.dictionaryking.home;

import com.chailijun.baselib.repository.Dictionary;

/**
 * author： Chailijun
 * date  ： 2018/5/20 20:08
 * e-mail： 1499505466@qq.com
 */

public interface MainView {

    void showError(String errorMsg);

    void showHanzi(Dictionary dictionary);
}
