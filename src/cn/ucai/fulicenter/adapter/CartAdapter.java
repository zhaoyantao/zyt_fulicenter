package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.task.UpdateCartTask;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.utils.Utils;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by sks on 2016/4/22.
 */
public class CartAdapter extends RecyclerView.Adapter {

    Context mContext;
    ArrayList<CartBean> cartList;
    CartListHolder cartHolder;
    boolean isUpdate;
    FooterViewHolder footerHolder;
    ViewGroup parent;
    String footerText;

    public CartAdapter(Context mContext, ArrayList<CartBean> cartList) {
        this.mContext = mContext;
        this.cartList = cartList;
        isUpdate = true;
        ArrayList<CartBean> mCartList = FuLiCenterApplication.getInstance().getCartList();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case I.TYPE_ITEM:
                holder = new CartListHolder(inflater.inflate(R.layout.item_new_good,parent,false));
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
        if(holder instanceof CartListHolder){
            cartHolder = (CartListHolder) holder;
            final CartBean cart = cartList.get(position);
            GoodDetailsBean goods = cart.getGoods();
            if(goods==null){
                return;
            }
            cartHolder.cartName.setText(goods.getGoodsName());
            cartHolder.cartNumber.setText("("+cart.getCount()+")");
            cartHolder.cartPrice.setText(goods.getCurrencyPrice());
            String path = I.DOWNLOAD_GOODS_THUMB_URL+cart.getGoods().getGoodsThumb();
            ImageUtils.setThumb(path,cartHolder.cartAvatar);
            AddDelCartListener listener = new AddDelCartListener(cart);
            cartHolder.ivAddCart.setOnClickListener(listener);
            cartHolder.ivDelCart.setOnClickListener(listener);
            cartHolder.chkChecked.setChecked(cart.isChecked());
            cartHolder.chkChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    cart.setChecked(isChecked);
                    new UpdateCartTask(mContext,cart).execute();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cartList==null?1:cartList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1){
            return I.TYPE_FOOTER;
        }else{
            return I.TYPE_ITEM;
        }
    }

    class CartListHolder extends RecyclerView.ViewHolder{
        TextView cartName;
        TextView cartPrice;
        NetworkImageView cartAvatar;
        TextView cartNumber;
        ImageView ivAddCart;
        ImageView ivDelCart;
        CheckBox chkChecked;

        public CartListHolder(View itemView) {
            super(itemView);
            cartName = (TextView) itemView.findViewById(R.id.tv_cart_name);
            cartPrice = (TextView) itemView.findViewById(R.id.tv_cart_price);
            cartAvatar = (NetworkImageView) itemView.findViewById(R.id.ivGoodsThumb);
            cartNumber = (TextView) itemView.findViewById(R.id.tv_cart_number);
            ivAddCart = (ImageView) itemView.findViewById(R.id.iv_cart_add);
            ivDelCart = (ImageView) itemView.findViewById(R.id.iv_cart_del);
            chkChecked = (CheckBox) itemView.findViewById(R.id.chkSelece);
        }
    }

    class AddDelCartListener implements View.OnClickListener{

        CartBean cart;

        public AddDelCartListener(CartBean cart) {
            this.cart = cart;
        }

        @Override
        public void onClick(View v) {
            cart.setChecked(true);
            switch (v.getId()){
                case R.id.iv_cart_add:
                    Utils.addCart(mContext,cart.getGoods());
                    break;
                case R.id.iv_cart_del:
                    Utils.delCart(mContext,cart.getGoods());
                    break;
            }
        }
    }
}
