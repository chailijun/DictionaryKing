package com.chailijun.dictionaryking.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chailijun.baselib.base.BaseActivity;
import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.baselib.repository.DictionaryRepository;
import com.chailijun.baselib.repository.source.local.DictionaryLocalDataSource;
import com.chailijun.baselib.utils.C;
import com.chailijun.baselib.utils.Constants;
import com.chailijun.baselib.utils.schedulers.SchedulerProvider;
import com.chailijun.dictionaryking.R;
import com.chailijun.dictionaryking.utils.JumpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {


    @BindView(R.id.tv_logo)
    TextView tv_logo;
    @BindView(R.id.tv_input)
    TextView tvInput;
    @BindView(R.id.btn_pinyin)
    Button btnPinyin;
    @BindView(R.id.btn_bushou)
    Button btnBushou;
    @BindView(R.id.btn_bihua)
    Button btnBihua;

    @BindView(R.id.tv_daily_hanzi)
    TextView tv_daily_hanzi;
    @BindView(R.id.tv_daily_jieshi)
    TextView tv_daily_jieshi;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initToolbar();

        mPresenter = new MainPresenter(DictionaryRepository.getInstance(
                DictionaryLocalDataSource.getInstance(this, SchedulerProvider.getInstance())),
                SchedulerProvider.getInstance());
        mPresenter.setPresenter(this);

        long dailyWord = C.getPreference(Constants.DAILY_WORD_TIME, 0L);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - dailyWord > 24 * 60 * 60 * 1000) {
            C.setPreference(Constants.DAILY_WORD_TIME, currentTimeMillis);
            if (dailyWord == 0){
                mPresenter.getDictionary("福");
            }else {
                mPresenter.getDictionary("");
            }
        } else {
            String word = C.getPreference(Constants.DAILY_WORD, "福");
            mPresenter.getDictionary(word);
        }
    }

    private void initView() {
        //设置字体
        Typeface typeface = Typeface.createFromAsset(C.get().getAssets(), "HuaWenKaiTi.ttf");
        tv_logo.setTypeface(typeface);
        tv_daily_hanzi.setTypeface(typeface);
        tv_daily_jieshi.setTypeface(typeface);
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && actionBar.isShowing()) {
            actionBar.hide();
        }
    }


    @OnClick({R.id.tv_input, R.id.btn_pinyin, R.id.btn_bushou, R.id.btn_bihua, R.id.iv_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_input:
                JumpUtils.goToSearch(this);
                break;
            case R.id.btn_pinyin:
                showToast("拼音");
                break;
            case R.id.btn_bushou:
                break;

            case R.id.iv_update:
                if (mPresenter != null) {
                    mPresenter.getDictionary("");
                }
                break;

            case R.id.btn_bihua:
                break;
        }
    }

    @Override
    public void showError(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void showHanzi(Dictionary dictionary) {
        if (dictionary != null) {
            String hanzi = dictionary.getZi();
            C.setPreference(Constants.DAILY_WORD, hanzi);

            tv_daily_hanzi.setText(hanzi);
            tv_daily_jieshi.setText(Html.fromHtml(dictionary.getJijie()));
        }
    }
}
