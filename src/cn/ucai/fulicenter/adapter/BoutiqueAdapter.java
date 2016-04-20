package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Arrays;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.GoodDetailsActivity;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by sks on 2016/4/19.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<BoutiqueBean> mGoodList;
    BoutiqueViewHolder mBoutiqueViewHolder;
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

    public BoutiqueAdapter(Context mContext, ArrayList<BoutiqueBean> mGoodList) {
        this.mContext = mContext;
        this.mGoodList = mGoodList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case I.TYPE_ITEM:
                holder = new BoutiqueViewHolder(inflater.inflate(R.layout.item_boutique,parent,false));
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
        if(holder instanceof BoutiqueViewHolder){
            mBoutiqueViewHolder = (BoutiqueViewHolder) holder;
            BoutiqueBean good = mGoodList.get(position);
            String path = I.DOWNLOAD_BOUTIQUE_IMG_URL+good.getImageurl();
            mBoutiqueViewHolder.tvGoodName1.setText(good.getTitle());
            mBoutiqueViewHolder.tvGoodName2.setText(good.getDescription());
            mBoutiqueViewHolder.tvGoodName3.setText(good.getName());
            ImageUtils.setThumb(path,mBoutiqueViewHolder.nivBoutique);
        }
    }

    @Override
    public int getItemCount() {
        return mGoodList==null?1:mGoodList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("main","getItemViewType,position="+position);
        if(position==getItemCount()-1){
            return I.TYPE_FOOTER;
        }else{
            return I.TYPE_ITEM;
        }
    }

    class BoutiqueViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutBoutiqueGood;
        NetworkImageView nivBoutique;
        TextView tvGoodName1;
        TextView tvGoodName2;
        TextView tvGoodName3;

        public BoutiqueViewHolder(View itemView) {
            super(itemView);
            layoutBoutiqueGood = (LinearLayout) itemView.findViewById(R.id.layout_boutique);
            nivBoutique = (NetworkImageView) itemView.findViewById(R.id.niv_boutique);
            tvGoodName1 = (TextView) itemView.findViewById(R.id.boutique_text1);
            tvGoodName2 = (TextView) itemView.findViewById(R.id.boutique_text2);
            tvGoodName3 = (TextView) itemView.findViewById(R.id.boutique_text3);
        }
    }

    public void initItems(ArrayList<BoutiqueBean> list) {
        if(mGoodList!=null && !mGoodList.isEmpty()){
            mGoodList.clear();
        }
        if(mGoodList==null){
            Log.e("abc","initItems.mGoodList:空的");
        }
        mGoodList.addAll(list);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<BoutiqueBean> list) {
        mGoodList.addAll(list);
        notifyDataSetChanged();
    }
}
