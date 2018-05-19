package com.chailijun.dictionaryking.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chailijun.baselib.base.BaseFragment;
import com.chailijun.baselib.repository.Dictionary;
import com.chailijun.dictionaryking.R;
import com.chailijun.dictionaryking.utils.KeyboardUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchFragment extends BaseFragment implements SearchContract.View {


    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.rv_result)
    RecyclerView rvResult;

    private Unbinder unbinder;
    private SearchContract.Presenter mPresenter;

    private SearchResultAdapter mAdapter;

    private SearchFragmentListener searchFragmentListener;

    interface SearchFragmentListener{

        void gotoDetail(Dictionary dictionary);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentListener){
            this.searchFragmentListener = ((SearchFragmentListener) context);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setupUI(rootView);
        return rootView;
    }

    private void setupUI(View rootView) {

        rvResult.setHasFixedSize(true);
        rvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvResult.setAdapter(mAdapter = new SearchResultAdapter(null));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (searchFragmentListener != null){
                    Dictionary dictionary = (Dictionary) adapter.getData().get(position);
                    searchFragmentListener.gotoDetail(dictionary);
                }

            }
        });

        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    if (getActivity() != null) {
                        KeyboardUtil.hideKeyboard(getActivity());
                    }

                    if (mPresenter != null) {
                        String searchText = etInput.getText().toString();
                        mPresenter.search(searchText);
                    }
                }
                return false;
            }
        });

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mPresenter != null) {
                    String searchText = s.toString();
                    mPresenter.search(searchText);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void showError(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void showHanzi(List<Dictionary> dictionaryList) {
        if (mAdapter != null){
            mAdapter.setNewData(dictionaryList);
        }
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
