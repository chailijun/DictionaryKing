package com.chailijun.dictionaryking.detail;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.dictionaryking.R;

import java.util.List;

public class PinyinAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public PinyinAdapter(@Nullable List<String> data) {
        super(R.layout.item_pinyin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_pinyin,item);

        helper.addOnClickListener(R.id.iv_voice);

    }
}
