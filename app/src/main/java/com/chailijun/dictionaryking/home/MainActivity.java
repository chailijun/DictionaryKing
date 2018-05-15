package com.chailijun.dictionaryking.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chailijun.baselib.base.BaseActivity;
import com.chailijun.dictionaryking.R;
import com.chailijun.dictionaryking.utils.JumpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tv_input)
    TextView tvInput;
    @BindView(R.id.btn_pinyin)
    Button btnPinyin;
    @BindView(R.id.btn_bushou)
    Button btnBushou;
    @BindView(R.id.btn_bihua)
    Button btnBihua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_input, R.id.btn_pinyin, R.id.btn_bushou, R.id.btn_bihua})
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
            case R.id.btn_bihua:
                break;
        }
    }
}
