package com.chailijun.dictionaryking.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chailijun.baselib.base.BaseFragment;
import com.chailijun.baselib.data.Dictionary;
import com.chailijun.dictionaryking.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchFragment extends BaseFragment implements SearchContract.View {


    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_result)
    TextView tvResult;

    private Unbinder unbinder;
    private SearchContract.Presenter mPresenter;

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
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    if (mPresenter != null) {
                        mPresenter.search(s.toString());
                    }
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
    public void showHanzi(Dictionary dictionary) {

        if (tvResult != null && dictionary != null) {
            tvResult.setText(dictionary.getHanzi());
        }
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
