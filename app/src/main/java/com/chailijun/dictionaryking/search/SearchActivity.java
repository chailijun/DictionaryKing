package com.chailijun.dictionaryking.search;

import android.os.Bundle;

import com.chailijun.baselib.base.BaseActivity;
import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.baselib.repository.DictionaryRepository;
import com.chailijun.baselib.repository.source.local.DictionaryLocalDataSource;
import com.chailijun.baselib.utils.schedulers.SchedulerProvider;
import com.chailijun.dictionaryking.R;
import com.chailijun.dictionaryking.utils.ActivityUtils;
import com.chailijun.dictionaryking.utils.JumpUtils;

public class SearchActivity extends BaseActivity implements SearchFragment.SearchFragmentListener{

    private SearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);


        SearchFragment searchFragment =
                (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.fl_fragment);
        if (searchFragment == null) {
            // Create the fragment
            searchFragment = new SearchFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), searchFragment, R.id.fl_fragment);
        }

        // Create the presenter
        mPresenter = new SearchPresenter(
                DictionaryRepository.getInstance(
                        DictionaryLocalDataSource.getInstance(this, SchedulerProvider.getInstance())),
                searchFragment,
                SchedulerProvider.getInstance()
        );
    }

    @Override
    public void gotoDetail(Dictionary dictionary) {
        JumpUtils.goToDetailActivity(this,dictionary);
    }
}
