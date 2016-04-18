package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by sks on 2016/4/16.
 */
public class NewGoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    ArrayList<NewGoodBean> mGoodList;

    GoodItemViewHolder goodHolder;
    FooterViewHolder footerHolder;

    private String footerText;
    private boolean isMore;

    private int sortBy;


    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }


    public void setFooterText(String footerText) {
        this.footerText = footerText;
        notifyDataSetChanged();
    }


    public NewGoodAdapter(Context mContext, int sortBy, ArrayList<NewGoodBean> mGoodList) {
        this.mContext = mContext;
        this.sortBy = sortBy;
        this.mGoodList = mGoodList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case I.TYPE_ITEM:
                holder = new GoodItemViewHolder(inflater.inflate(R.layout.item_new_good,parent,false));
                break;
            case I.TYPE_FOOTER:
                holder = new GoodItemViewHolder(inflater.inflate(R.layout.item_footer,parent,false));
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FooterViewHolder){
            footerHolder = (FooterViewHolder) holder;
            footerHolder.tvFooter.setText(footerText);
            footerHolder.tvFooter.setVisibility(View.VISIBLE);
        }
        if(position == mGoodList.size()){
            return;
        }
        if(holder instanceof GoodItemViewHolder){
            goodHolder = (GoodItemViewHolder) holder;
            NewGoodBean good = mGoodList.get(position);
            goodHolder.tvGoodName.setText(good.getColorName());
            goodHolder.tvGoodPrice.setText(good.getCurrencyPrice());
            ImageUtils.setNewGoodThumb(good.getGoodsThumb(),goodHolder.nivThumb);
        }
    }

    @Override
    public int getItemCount() {
        return mGoodList==null?0:mGoodList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()){
            return I.TYPE_FOOTER;
        }else{
            return I.TYPE_ITEM;
        }
    }

    class GoodItemViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutGood;
        NetworkImageView nivThumb;
        TextView tvGoodName;
        TextView tvGoodPrice;

        public GoodItemViewHolder(View itemView) {
            super(itemView);
            layoutGood = (LinearLayout) itemView.findViewById(R.id.layout_good);
            nivThumb = (NetworkImageView) itemView.findViewById(R.id.niv_good_thumb);
            tvGoodName = (TextView) itemView.findViewById(R.id.tv_good_name);
            tvGoodPrice = (TextView) itemView.findViewById(R.id.tv_good_price);
        }
    }

    public void addItem(ArrayList<NewGoodBean> list){
        mGoodList.addAll(list);
        notifyDataSetChanged();
    }

    public void initItem(ArrayList<NewGoodBean> list){
        if(mGoodList!=null&&!mGoodList.isEmpty()){
            mGoodList.clear();
        }
        mGoodList.addAll(list);
        notifyDataSetChanged();
    }
}
