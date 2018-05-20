package com.chailijun.dictionaryking.detail;

import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chailijun.baselib.base.BaseFragment;
import com.chailijun.baselib.net.ApiConstant;
import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.baselib.utils.C;
import com.chailijun.baselib.utils.NetworkUtils;
import com.chailijun.baselib.utils.StringUtils;
import com.chailijun.dictionaryking.R;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author： Chailijun
 * date  ： 2018/5/19 13:50
 * e-mail： 1499505466@qq.com
 */

public class DetailFragment extends BaseFragment {

    private static final String INTENT_EXTRA_PARAM_DICTIONARY = "INTENT_EXTRA_PARAM_DICTIONARY";

    private Unbinder unbinder;

    @BindView(R.id.tv_hanzi)
    TextView tv_hanzi;
//    @BindView(R.id.iv_speek1)
//    ImageView iv_speek1;
//    @BindView(R.id.iv_speek2)
//    ImageView iv_speek2;

    @BindView(R.id.rv_pinyin)
    RecyclerView rv_pinyin;
    @BindView(R.id.tv_bushou)
    TextView tv_bushou;

    private Dictionary dictionary;

    private PinyinAdapter pinyinAdapter;
    private MediaPlayer mediaPlayer;

    public static DetailFragment newInstance(Dictionary dictionary) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putParcelable(INTENT_EXTRA_PARAM_DICTIONARY, dictionary);
        detailFragment.setArguments(argumentsBundle);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dictionary = currentDictionary();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setUpUI();
        return rootView;
    }

    private void setUpUI() {

        rv_pinyin.setHasFixedSize(true);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_pinyin.setLayoutManager(layoutManager);
        rv_pinyin.setAdapter(pinyinAdapter = new PinyinAdapter(null));

        pinyinAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!NetworkUtils.isNetworkAvailable(C.get())){
                    showToast("播放语音需要连接网络");
                    return;
                }

                if (view.getId() == R.id.iv_voice){
                    String pinyin = (String) adapter.getData().get(position);

                    String transformPinyin = StringUtils.transformPinyin(pinyin);
//                    showToast(transformPinyin);


                    if (mediaPlayer == null){
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    }
                    Uri uri = Uri.parse(String.format(Locale.CHINA, ApiConstant.PIN_YIN, transformPinyin));
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(C.get(),uri);
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mp, int what, int extra) {
                                mp.reset();
                                return false;
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDetail();
    }

    private void setDetail() {
        if (dictionary != null) {
            tv_hanzi.setText(dictionary.getZi());
            //设置字体
            Typeface typeface=Typeface.createFromAsset(C.get().getAssets(),"HuaWenKaiTi.ttf");
            tv_hanzi.setTypeface(typeface);

            //设置拼音
            String pinyin = dictionary.getPinyin();
            if (pinyin != null && !TextUtils.isEmpty(pinyin)) {
                String[] split = pinyin.split(",");
                if (split.length != 0 && pinyinAdapter != null){
                    pinyinAdapter.setNewData(Arrays.asList(split));
                }
            }

            tv_bushou.setText("部首 "+dictionary.getBushou());

        }
    }

    @Override
    public void onDestroyView() {
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }



    private Dictionary currentDictionary() {
        final Bundle arguments = getArguments();
        Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
        return arguments.getParcelable(INTENT_EXTRA_PARAM_DICTIONARY);
    }
}
