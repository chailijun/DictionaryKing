package com.chailijun.dictionaryking.detail;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chailijun.baselib.base.BaseActivity;
import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.dictionaryking.R;

public class DetailActivity extends BaseActivity {

    private static final String INTENT_EXTRA_PARAM_DICTIONARY = "INTENT_EXTRA_PARAM_DICTIONARY";

    private Dictionary dictionary;

    public static Intent getCallingIntent(Activity activity, Dictionary dictionary) {
        Intent callingIntent = new Intent(activity.getApplicationContext(), DetailActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_DICTIONARY, dictionary);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        initializeActivity(savedInstanceState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.dictionary = getIntent().getParcelableExtra(INTENT_EXTRA_PARAM_DICTIONARY);
            addFragment(R.id.fl_fragment, DetailFragment.newInstance(this.dictionary));
        } else {
            this.dictionary = savedInstanceState.getParcelable(INTENT_EXTRA_PARAM_DICTIONARY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(INTENT_EXTRA_PARAM_DICTIONARY, this.dictionary);
        }
        super.onSaveInstanceState(outState);
    }
}
