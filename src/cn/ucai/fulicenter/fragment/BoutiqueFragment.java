package cn.ucai.fulicenter.fragment;


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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.FuLiCenterMainActivity;
import cn.ucai.fulicenter.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;

public class BoutiqueFragment extends Fragment {


    private FuLiCenterMainActivity mContext;
    ArrayList<BoutiqueBean> mGoodList;
    BoutiqueAdapter mAdaptar;
    String path;
    private  int pageId = 0;
    private int action = I.ACTION_DOWNLOAD;

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    TextView mtvHint;
    LinearLayoutManager mLinearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        mContext = (FuLiCenterMainActivity) getActivity();
        View layout = View.inflate(mContext,R.layout.fragment_boutique, null);
        mGoodList = new ArrayList<BoutiqueBean>();
        initView(layout);
        setListener();
        initData();
        return layout;
    }

    private void setListener() {
        setPullDownRefreshListener();
        setPullUpRefreshListener();
    }

    /**
     * 上拉刷新事件监听
     */
    private void setPullUpRefreshListener() {
        mRecyclerView.setOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    int lastItemPosition;
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                                lastItemPosition == mAdaptar.getItemCount()-1){
                            if(mAdaptar.isMore()){
                                mSwipeRefreshLayout.setRefreshing(true);
                                action = I.ACTION_PULL_UP;
                                mContext.executeRequest(new GsonRequest<BoutiqueBean[]>(path,
                                        BoutiqueBean[].class,responseDownloadBoutiqueGoodListener(),
                                        mContext.errorListener()));
                            }
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        //获取最后列表项的下标
                        lastItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                        //解决RecyclerView和SwipeRefreshLayout共用存在的bug
                        mSwipeRefreshLayout.setEnabled(mLinearLayoutManager
                                .findFirstCompletelyVisibleItemPosition() == 0);
                    }
                }
        );
    }

    /**
     * 下拉刷新事件监听
     */
    private void setPullDownRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener(){
                    @Override
                    public void onRefresh() {
                        mtvHint.setVisibility(View.VISIBLE);
                        pageId = 0;
                        action = I.ACTION_PULL_DOWN;
                        getPath();
                        mContext.executeRequest(new GsonRequest<BoutiqueBean[]>(path,
                                BoutiqueBean[].class,responseDownloadBoutiqueGoodListener(),
                                mContext.errorListener()));
                    }
                }
        );
    }


    private void initData() {
        getPath();
        mContext.executeRequest(new GsonRequest<BoutiqueBean[]>(path,
                BoutiqueBean[].class,responseDownloadBoutiqueGoodListener(),
                mContext.errorListener()));
    }

    private Response.Listener<BoutiqueBean[]> responseDownloadBoutiqueGoodListener() {
        return new Response.Listener<BoutiqueBean[]>() {
            @Override
            public void onResponse(BoutiqueBean[] boutiqueBeen) {
                if(boutiqueBeen!=null){
                    mAdaptar.setMore(true);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mtvHint.setVisibility(View.GONE);
                    mAdaptar.setFooterText(getResources().getString(R.string.load_more));
                    ArrayList<BoutiqueBean> list = Utils.array2List(boutiqueBeen);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdaptar.initItems(list);
                    } else if (action == I.ACTION_PULL_UP) {
                        mAdaptar.addItems(list);
                    }
                    if(boutiqueBeen.length<I.PAGE_SIZE_DEFAULT){
                        mAdaptar.setMore(false);
                        mAdaptar.setFooterText(getResources().getString(R.string.no_more));
                    }
                }
            }
        };
    }

    private String getPath(){
        try {
            path = new ApiParams().getRequestUrl(I.REQUEST_FIND_BOUTIQUES);
            Log.e("abc","path:"+path);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.sfl_boutiquegood);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        mtvHint = (TextView) layout.findViewById(R.id.tv_refresh_hint);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_boutiquegood);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdaptar = new BoutiqueAdapter(mContext,mGoodList);
        mRecyclerView.setAdapter(mAdaptar);
    }


}
