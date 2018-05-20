package com.chailijun.dictionaryking.home;

import android.support.annotation.NonNull;

import com.chailijun.baselib.base.BasePresenter;
import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.baselib.repository.DictionaryRepository;
import com.chailijun.baselib.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;


public class MainPresenter implements BasePresenter {


    @NonNull
    private final DictionaryRepository mBooksRepository;

    @NonNull
    private MainView mView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    public MainPresenter(@NonNull DictionaryRepository dictionaryRepository,
//                         @NonNull MainContract.View booksView,
                         @NonNull BaseSchedulerProvider schedulerProvider) {
        mBooksRepository = checkNotNull(dictionaryRepository, "dictionaryRepository cannot be null");
//        mView = checkNotNull(booksView, "mView cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mCompositeDisposable = new CompositeDisposable();
//        mView.setPresenter(this);
    }

    public void setPresenter(MainView view){
        this.mView = view;
    }

    @Override
    public void subscribe() {
//        loadBooks(false);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }


    public void getDictionary(String hanzi) {

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

    private void processDatas(Dictionary dictionary) {
        mView.showHanzi(dictionary);
    }


}
