package cn.ucai.fulicenter.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.Arrays;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.FuliCenterMainActivity;
import cn.ucai.fulicenter.adapter.NewGoodAdapter;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;


public class NewGoodFragment extends Fragment {
    FuliCenterMainActivity mContext ;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mtvHint;
    GridLayoutManager mGridLayoutManager;
    RecyclerView mRecyclerView;
    NewGoodAdapter mAdapter;
    ArrayList<NewGoodBean> mGoodList;
    int action;
    int pageId = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = (FuliCenterMainActivity)getActivity();
        View layout = View.inflate(mContext,R.layout.fragment_new_good,null);
        mGoodList = new ArrayList<NewGoodBean>();
        initView(layout);
        setListener();
        initDate(pageId);
        return layout;
    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mtvHint.setVisibility(View.VISIBLE);
                pageId = 0;
                action = I.ACTION_PULL_DOWN;
                initDate(pageId);
            }
        });
    }

    private void setPullUpListener() {
       mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
           int lastItem;
           @Override
           public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
               if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastItem==mAdapter.getItemCount()-1){
                   if(mAdapter.isMore()){
                       mSwipeRefreshLayout.setRefreshing(true);
                       action = I.ACTION_PULL_UP;
                       pageId+=I.PAGE_SIZE_DEFAULT;
                       initDate(pageId);
                   }
               }
           }

           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
               lastItem = mGridLayoutManager.findLastVisibleItemPosition();
               mSwipeRefreshLayout.setEnabled(mGridLayoutManager.findFirstCompletelyVisibleItemPosition()==0);
           }
       });
    }

    private void initDate(int pageid){
        try {
            String path = new ApiParams()
                    .with(I.NewAndBoutiqueGood.CAT_ID,I.CAT_ID+"")
                    .with(I.PAGE_ID,pageid+"")
                    .with(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                    .getRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS);
            Log.e("abc","path:"+path);
            mContext.executeRequest(new GsonRequest<NewGoodBean[]>(path,NewGoodBean[].class
                    ,responseDownLoadNewGoodListener(),mContext.errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<NewGoodBean[]> responseDownLoadNewGoodListener() {
        return new Response.Listener<NewGoodBean[]>() {
            @Override
            public void onResponse(NewGoodBean[] newGoodBean) {
                if(newGoodBean != null){
                    Log.e("abc","bean:"+Arrays.toString(newGoodBean));
                    mAdapter.setMore(true);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mtvHint.setVisibility(View.GONE);
                    mAdapter.setFooterText("load more");
                    ArrayList<NewGoodBean> list = Utils.array2List(newGoodBean);
                    if(action==I.ACTION_DOWNLOAD||action==I.ACTION_PULL_DOWN){
                        mAdapter.initItem(list);
                    }else if(action==I.ACTION_PULL_UP){
                        mAdapter.addItem(list);
                    }
                    if(newGoodBean.length<I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                        mAdapter.setFooterText("no more data");
                    }
                }
            }
        };
    }

    private void initView(View layout){
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.sfl_newGood);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,R.color.google_green,
                R.color.google_red,R.color.google_yellow
        );
        mtvHint = (TextView) layout.findViewById(R.id.tv_refresh_hint);
        mGridLayoutManager = new GridLayoutManager(mContext,I.COLUM_NUM);
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_newGood);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new NewGoodAdapter(mContext,I.SORT_BY_ADDTIME_DESC,mGoodList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
