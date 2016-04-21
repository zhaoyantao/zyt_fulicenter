package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.GoodDetailsActivity;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by sks on 2016/4/21.
 */
public class CollectListAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CollectBean> mGoodList;

    GoodItemViewHolder goodHolder;
    FooterViewHolder footerHolder;

    private String footerText;
    private boolean isMore;


    public void setFooterText(String footerText) {
        this.footerText = footerText;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public CollectListAdapter(Context mContext, ArrayList<CollectBean> mGoodList) {
        this.mContext = mContext;
        this.mGoodList = mGoodList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case I.TYPE_ITEM:
                holder = new GoodItemViewHolder(inflater.inflate(R.layout.item_collect,parent,false));
                break;
            case I.TYPE_FOOTER:
                holder = new FooterViewHolder(inflater.inflate(R.layout.item_footer,parent,false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FooterViewHolder){
            footerHolder = (FooterViewHolder) holder;
            footerHolder.tvFooter.setText(footerText);
            footerHolder.tvFooter.setVisibility(View.VISIBLE);
        }

        if(holder instanceof GoodItemViewHolder){
            goodHolder = (GoodItemViewHolder) holder;
            final CollectBean good = mGoodList.get(position);
            goodHolder.tvGoodName.setText(good.getGoodsName());
            ImageUtils.setNewGoodThumb(good.getGoodsThumb(),goodHolder.nivThumb);
            goodHolder.layoutGood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, GoodDetailsActivity.class)
                            .putExtra(D.NewGood.KEY_GOODS_ID, good.getGoodsId()));
                }
            });

            goodHolder.ivDeleteCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = FuLiCenterApplication.getInstance().getUserName();
                    try {
                        String path = new ApiParams().with(I.Collect.GOODS_ID,"")
                                .with(I.User.USER_NAME, name)
                                .getRequestUrl(I.REQUEST_DELETE_COLLECT);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mGoodList==null?1:mGoodList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1){
            return I.TYPE_FOOTER;
        }else{
            return I.TYPE_ITEM;
        }
    }

    public void initItems(ArrayList<CollectBean> list) {
        if(mGoodList!=null && !mGoodList.isEmpty()){
            mGoodList.clear();
        }
        mGoodList.addAll(list);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<CollectBean> list) {
        mGoodList.addAll(list);
        notifyDataSetChanged();
    }

    class GoodItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutGood;
        NetworkImageView nivThumb;
        TextView tvGoodName;
        TextView tvGoodPrice;
        ImageView ivDeleteCollect;

        public GoodItemViewHolder(View itemView) {
            super(itemView);
            layoutGood = (LinearLayout) itemView.findViewById(R.id.layout_good);
            nivThumb = (NetworkImageView) itemView.findViewById(R.id.niv_good_thumb);
            tvGoodName = (TextView) itemView.findViewById(R.id.tv_good_name);
            tvGoodPrice = (TextView) itemView.findViewById(R.id.tv_good_price);
            ivDeleteCollect = (ImageView) itemView.findViewById(R.id.iv_delete_collect);
        }

    }
}
