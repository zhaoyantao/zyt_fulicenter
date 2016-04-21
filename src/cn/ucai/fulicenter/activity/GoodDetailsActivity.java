package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.utils.Utils;
import cn.ucai.fulicenter.view.DisplayUtils;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

/**
 * Created by ucai on 2016/4/18.
 */
public class GoodDetailsActivity extends BaseActivity{
    GoodDetailsActivity mContext;
    GoodDetailsBean mGoodDetails;
    int mGoodsId;
    NewGoodBean mGood;
//    ViewHolder mHolder;

    SlideAutoLoopView mSlideAutoLoopView;
    FlowIndicator mFlowIndicator;
    LinearLayout mLayoutColors;
    ImageView mivCollect;
    ImageView mivAddCart;
    ImageView mivShare;
    TextView mtvCartCount;

    TextView tvGoodName;
    TextView tvGoodEngishName;
    TextView tvShopPrice;
    TextView tvCurrencyPrice;
    WebView wvGoodBrief;

    int mCurrentColor;

    boolean isCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_details);
        mContext=this;
        initView();
        getIsCollect();
        initData();
        setListener();
    }

    private void setListener() {
        setOnClickListener();
    }

    private void setOnClickListener() {
        mivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("abc","进入点击事件了");
                UserBean user = FuLiCenterApplication.getInstance().getUser();
                if(isCollect){
                    deleteCollect();
                    return;
                }else{
                    Log.e("abc","进入一次判断了");
                    if(user==null){
                        startActivity(new Intent(GoodDetailsActivity.this,LoginActivity.class));
                    }else{
                        Log.e("abc","进入二次判断了");
                        addCollect(user);
                    }
                }
            }
        });
    }



    private void addCollect(UserBean user){
        Log.e("abc","进入添加方法了");
        try {
            String path = new ApiParams().with(I.Collect.ADD_TIME,mGoodDetails.getAddTime()+"")
                    .with(I.Collect.GOODS_IMG, mGoodDetails.getGoodsImg())
                    .with(I.Collect.GOODS_THUMB, mGoodDetails.getGoodsThumb())
                    .with(I.Collect.GOODS_ENGLISH_NAME, tvGoodEngishName.getText().toString())
                    .with(I.Collect.GOODS_NAME, tvGoodName.getText().toString())
                    .with(I.Collect.GOODS_ID, mGoodsId+"")
                    .with(I.User.USER_NAME, user.getUserName())
                    .getRequestUrl(I.REQUEST_ADD_COLLECT);
            Log.e("abc","add:"+path.toString());
            executeRequest(new GsonRequest<MessageBean>(path,MessageBean.class,
                    responseAddCollectListener(),errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<MessageBean> responseAddCollectListener() {
        return new Response.Listener<MessageBean>() {
            @Override
            public void onResponse(MessageBean messageBean) {
                if (messageBean!=null){
                    if(messageBean.isSuccess()){
                        mivCollect.setImageResource(R.drawable.bg_collect_out);
                        isCollect = true;
                    }
                }
            }
        };
    }

    private void deleteCollect(){
        String name = FuLiCenterApplication.getInstance().getUserName();
        try {
            String path = new ApiParams().with(I.CategoryGood.GOODS_ID,mGoodsId+"")
                    .with(I.User.USER_NAME, name)
                    .getRequestUrl(I.REQUEST_DELETE_COLLECT);
            Log.e("abc","delete:"+path.toString());
            executeRequest(new GsonRequest<MessageBean>(path,MessageBean.class,
                    responseDeleteCollectListener(),errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<MessageBean> responseDeleteCollectListener() {
        return new Response.Listener<MessageBean>(){

            @Override
            public void onResponse(MessageBean messageBean) {
                if (messageBean!=null){
                    if(messageBean.isSuccess()){
                        mivCollect.setImageResource(R.drawable.bg_collect_in);
                        isCollect = false;
                    }
                }
            }
        };
    }

    private void getIsCollect() {
        String name = FuLiCenterApplication.getInstance().getUserName();
        mivCollect.setImageResource(R.drawable.bg_collect_in);
        isCollect =false;
        if(name!=null) {
            try {
                String path = new ApiParams()
                        .with(I.CategoryGood.GOODS_ID, mGoodsId + "")
                        .with(I.User.USER_NAME, name)
                        .getRequestUrl(I.REQUEST_IS_COLLECT);
                Log.e("abc","findis:"+path.toString());
                executeRequest(new GsonRequest<MessageBean>(path,MessageBean.class,responseDownLoadCollectListener(),errorListener()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Response.Listener<MessageBean> responseDownLoadCollectListener() {
        return new Response.Listener<MessageBean>() {
            @Override
            public void onResponse(MessageBean messageBean) {
                if (messageBean!=null){
                    if(messageBean.isSuccess()){
                        mivCollect.setImageResource(R.drawable.bg_collect_out);
                        isCollect = true;
                    }
                }
            }
        };
    }


    private void initData() {
        mGoodsId=getIntent().getIntExtra(D.GoodDetails.KEY_GOODS_ID, 0);
        try {
            String path = new ApiParams().with(I.CategoryGood.GOODS_ID, mGoodsId+"")
                    .getRequestUrl(I.REQUEST_FIND_GOOD_DETAILS);
            executeRequest(new GsonRequest<GoodDetailsBean>(path,GoodDetailsBean.class,
                    responseDownloadGoodDetailsListener(),errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<GoodDetailsBean> responseDownloadGoodDetailsListener() {
        return new Response.Listener<GoodDetailsBean>() {
            @Override
            public void onResponse(GoodDetailsBean goodDetailsBean) {
                if(goodDetailsBean!=null){
                    mGoodDetails = goodDetailsBean;
//                    DisplayUtils.initBackwithTitle(mContext,"商品详情");
                    tvCurrencyPrice.setText(mGoodDetails.getCurrencyPrice());
                    tvGoodEngishName.setText(mGoodDetails.getGoodsEnglishName());
                    tvGoodName.setText(mGoodDetails.getGoodsName());
                    wvGoodBrief.loadDataWithBaseURL(null, mGoodDetails.getGoodsBrief().trim(), D.TEXT_HTML, D.UTF_8, null);

                    //初始化颜色面板
                    initColorsBanner();
                }else {
                    Utils.showToast(mContext, "商品详情下载失败", Toast.LENGTH_LONG);
                }
            }
        };
    }

    private void initColorsBanner() {
        //设置第一个颜色的图片轮播
        updateColor(0);
        for(int i=0;i<mGoodDetails.getProperties().length;i++){
            mCurrentColor=i;
            View layout=View.inflate(mContext, R.layout.layout_property_color, null);
            final NetworkImageView ivColor=(NetworkImageView) layout.findViewById(R.id.ivColorItem);
            String colorImg = mGoodDetails.getProperties()[i].getColorImg();
            if(colorImg.isEmpty()){
                continue;
            }
            ImageUtils.setGoodDetailThumb(colorImg,ivColor);
            mLayoutColors.addView(layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateColor(mCurrentColor);
                }
            });
        }
    }
    /**
     * 设置指定属性的图片轮播
     * @param i
     */
    private void updateColor(int i) {
        AlbumsBean[] albums = mGoodDetails.getProperties()[i].getAlbums();
        String[] albumImgUrl=new String[albums.length];
        for(int j=0;j<albumImgUrl.length;j++){
            albumImgUrl[j]=albums[j].getImgUrl();
        }
        mSlideAutoLoopView.startPlayLoop(mFlowIndicator, albumImgUrl, albumImgUrl.length);
    }

    private void initView() {
        mivCollect= (ImageView) findViewById(R.id.ivCollect);
        mivAddCart=(ImageView) findViewById(R.id.ivAddCart);
        mivShare = (ImageView) findViewById(R.id.ivShare);
        mtvCartCount=(TextView) findViewById(R.id.tvCartCount);

        mSlideAutoLoopView= (SlideAutoLoopView) findViewById(R.id.salv);
        mFlowIndicator= (FlowIndicator) findViewById(R.id.indicator);
        mLayoutColors= (LinearLayout) findViewById(R.id.layoutColorSelector);
        tvCurrencyPrice= (TextView) findViewById(R.id.tvCurrencyPrice);
        tvGoodEngishName=(TextView) findViewById(R.id.tvGoodEnglishName);
        tvGoodName=(TextView) findViewById(R.id.tvGoodName);
        tvShopPrice=(TextView) findViewById(R.id.tvShopPrice);
        wvGoodBrief= (WebView) findViewById(R.id.wvGoodBrief);
        WebSettings settings = wvGoodBrief.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true);
    }


}
