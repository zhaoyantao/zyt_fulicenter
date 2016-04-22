package cn.ucai.fulicenter.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.CartAdapter;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;


public class CartFragment extends Fragment {

    Context mContext;
    CartAdapter mAdapter;
    ArrayList<CartBean> mCartList;
    RecyclerView mrvCart;

    TextView mtvSumPrice;
    TextView mtvsavePrive;
    TextView mtvNothing;
    CartChangedReceiver mCartChangedReceiver;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = View.inflate(getActivity(),R.layout.fragment_cart, null);
        mContext = getActivity();
        mCartList = new ArrayList<CartBean>();
        initView(layout);
        registerCartChangedReceiver();
        refresh();
        return layout;
    }

    private void refresh() {
        ArrayList<CartBean> list = FuLiCenterApplication.getInstance().getCartList();
        mCartList.clear();
        mCartList.addAll(list);
        mAdapter.notifyDataSetChanged();
        sumPrice();
        if(mCartList!=null&&mCartList.size()>0){
            mtvNothing.setVisibility(View.GONE);
        }else{
            mtvNothing.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void sumPrice() {
        ArrayList<CartBean> cartList = FuLiCenterApplication.getInstance().getCartList();
        int sumRankPrice = 0;
        int sumCurrentPrice = 0;
        for (int i=0;i<cartList.size();i++){
            CartBean cart = cartList.get(i);
            GoodDetailsBean goods = cart.getGoods();
            if(cart.isChecked()){
                for(int j=0;j<cart.getCount();j++){
                    if(goods!=null){
                        int rankPrice = convertPrice(goods.getRankPrice());
                        int currentPrice = convertPrice(goods.getCurrencyPrice());
                        sumRankPrice += rankPrice;
                        sumCurrentPrice += currentPrice;
                    }
                }
            }
        }
        int sumSavePrice = sumCurrentPrice-sumRankPrice;
        mtvSumPrice.setText("合计：￥"+sumRankPrice);
        mtvsavePrive.setText("节省：￥"+sumSavePrice);
    }

    private int convertPrice(String rankPrice) {
        Log.e("abc",rankPrice);
        rankPrice = rankPrice.substring(rankPrice.indexOf("￥")+1);
        Log.e("abc",rankPrice);
        int price = Integer.parseInt(rankPrice);
        return price;
    }

    private void registerCartChangedReceiver() {
        mCartChangedReceiver = new CartChangedReceiver();
        IntentFilter filter = new IntentFilter("update_cart");
        getActivity().registerReceiver(mCartChangedReceiver,filter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mCartChangedReceiver!=null){
            getActivity().unregisterReceiver(mCartChangedReceiver);
        }
    }

    private void initView(View layout) {
        mtvSumPrice = (TextView) layout.findViewById(R.id.tvSumPrice);
        mtvsavePrive = (TextView) layout.findViewById(R.id.tvSavePrice);
        mtvNothing = (TextView) layout.findViewById(R.id.tv_nothing);
        mrvCart = (RecyclerView) layout.findViewById(R.id.rv_cart);
        mrvCart.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CartAdapter(mContext,mCartList);
        mrvCart.setAdapter(mAdapter);
        mtvNothing.setVisibility(View.VISIBLE);
    }

    class CartChangedReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
        }
    }

}
