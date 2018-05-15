package com.chailijun.dictionaryking.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.chailijun.baselib.base.BaseActivity;
import com.chailijun.baselib.data.DictionaryRepository;
import com.chailijun.baselib.data.source.local.DictionaryLocalDataSource;
import com.chailijun.baselib.utils.schedulers.SchedulerProvider;
import com.chailijun.dictionaryking.R;
import com.chailijun.dictionaryking.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    private SearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


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
}
