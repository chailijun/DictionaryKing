package com.chailijun.dictionaryking.search;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.dictionaryking.R;

import java.util.List;

public class SearchResultAdapter extends BaseQuickAdapter<Dictionary,BaseViewHolder>{

    public SearchResultAdapter(@Nullable List<Dictionary> data) {
        super(R.layout.item_search_result, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Dictionary item) {
        helper.setText(R.id.tv_hanzi,item.getZi());
        helper.setText(R.id.tv_pinyin,item.getPinyin());

    }
}
