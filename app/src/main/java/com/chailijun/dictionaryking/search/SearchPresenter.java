package com.chailijun.dictionaryking.search;

import android.support.annotation.NonNull;


import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.baselib.repository.DictionaryRepository;
import com.chailijun.baselib.utils.StringUtils;
import com.chailijun.baselib.utils.schedulers.BaseSchedulerProvider;


import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.google.common.base.Preconditions.checkNotNull;


public class SearchPresenter implements SearchContract.Presenter {


    @NonNull
    private final DictionaryRepository mBooksRepository;

    @NonNull
    private final SearchContract.View mView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    public SearchPresenter(@NonNull DictionaryRepository dictionaryRepository,
                           @NonNull SearchContract.View booksView,
                           @NonNull BaseSchedulerProvider schedulerProvider) {
        mBooksRepository = checkNotNull(dictionaryRepository, "dictionaryRepository cannot be null");
        mView = checkNotNull(booksView, "mView cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mCompositeDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
//        loadBooks(false);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }


    @Override
    public void search(String hanzi) {

        mCompositeDisposable.clear();
        Disposable disposable = mBooksRepository
                .getDictionary(hanzi)
                .flatMap(new Function<List<Dictionary>, Publisher<Dictionary>>() {
                    @Override
                    public Publisher<Dictionary> apply(List<Dictionary> dictionaries) throws Exception {
                        return Flowable.fromIterable(dictionaries)
                                .doOnNext(new Consumer<Dictionary>() {
                                    @Override
                                    public void accept(Dictionary dictionary) throws Exception {
                                        //处理原数据，防止原数据不规范
                                        String pinyin = dictionary.getPinyin();
                                        String pinyin2 = StringUtils.toHalfAngleString(pinyin.trim());

                                        //去掉开头的","
                                        boolean startsWith = pinyin2.startsWith(",");
                                        if (startsWith) {
                                            pinyin2 = pinyin2.substring(1);
                                        }

                                        //去掉结尾的","
                                        boolean endsWith = pinyin2.endsWith(",");
                                        if (endsWith) {
                                            pinyin2 = pinyin2.substring(0,pinyin2.length() - 1);
                                        }
                                    }
                                });
                    }
                }).toList().toFlowable()

                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        // onNext
                        dictionary -> {
                            processDatas(dictionary);
                        },
                        // onError
                        throwable -> {

                            mView.showError(throwable.getMessage());
                        });

        mCompositeDisposable.add(disposable);
    }

    private void processDatas(List<Dictionary> dictionarys) {
        mView.showHanzi(dictionarys);
    }


    /*private void processDatas(@NonNull Dictionary dictionary) {
        mView.showHanzi(dictionary);

    }*/

}
