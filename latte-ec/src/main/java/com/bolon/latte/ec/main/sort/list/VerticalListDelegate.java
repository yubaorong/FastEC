package com.bolon.latte.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bolon.latte.ec.R;
import com.bolon.latte.delegates.LatteDelegate;
import com.bolon.latte.ec.main.sort.SortDelegate;
import com.bolon.latte.net.RestClient;
import com.bolon.latte.net.callback.ISuccess;
import com.bolon.latte.ui.recycler.MultipleItemEntity;

import java.util.List;


/**
 * Created by Administrator
 */

public class VerticalListDelegate extends LatteDelegate {

    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        //屏蔽动画效果
        mRecyclerView.setItemAnimator(null);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRecyclerView = $(R.id.rv_vertical_menu_list);
        initRecyclerView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("sort_list.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final List<MultipleItemEntity> data =
                                new VerticalListDataConverter().setJsonData(response).convert();
                        final SortDelegate delegate = getParentDelegate();
                        final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data, delegate);
                        mRecyclerView.setAdapter(adapter);

                    }
                })
                .build()
                .get();
    }
}
