package com.chailijun.dictionaryking.search;

import android.support.annotation.NonNull;


import com.chailijun.baselib.data.Dictionary;
import com.chailijun.baselib.data.DictionaryRepository;
import com.chailijun.baselib.utils.schedulers.BaseSchedulerProvider;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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


    private void processDatas(@NonNull Dictionary dictionary) {
        mView.showHanzi(dictionary);

    }

}
