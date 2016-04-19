package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.AlbumBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.DisplayUtils;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.utils.Utils;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

public class GoodDetailActivity extends BaseActivity {

    private GoodDetailActivity mContext;
    private GoodDetailsBean mGoodDetails;
    int mGoodsId;
    NewGoodBean mGood;

    SlideAutoLoopView mSlideAutoLoopView;
    FlowIndicator mFlowIndicator;
    LinearLayout mLayoutColors;
    ImageView mivCollect;
    ImageView mivAddCart;
    ImageView mivShare;
    TextView mtvCartCount;

    TextView tvGoodName;
    TextView tvGoodEnglishName;
    TextView tvShopPrice;
    TextView tvCurrencyPrice;
    WebView wvGoodBrief;

    private int mCurrentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        mContext = this;
        initView();
        initData();
        initColorsBanner();
    }

    private void initData() {
        mGoodsId = getIntent().getIntExtra(D.GoodDetails.KEY_GOODS_ID,0);
        try {
            String path = new ApiParams().with(I.CategoryGood.GOODS_ID,mGoodsId+"")
                    .getRequestUrl(I.REQUEST_FIND_GOOD_DETAILS);
            executeRequest(new GsonRequest<GoodDetailsBean>(path,GoodDetailsBean.class,
                    responseDLGoodDetailsListener(),errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<GoodDetailsBean> responseDLGoodDetailsListener() {
        return new Response.Listener<GoodDetailsBean>() {
            @Override
            public void onResponse(GoodDetailsBean goodDetailsBean) {
                if(goodDetailsBean!=null){
                    mGoodDetails = goodDetailsBean;
                    DisplayUtils.initBackWithTitle(mContext,getResources().getString(R.string.title_activity_good_details));
                    tvCurrencyPrice.setText(mGoodDetails.getCurrencyPrice());
                    tvGoodEnglishName.setText(mGoodDetails.getGoodsEnglishName());
                    tvGoodName.setText(mGoodDetails.getGoodsName());
                    wvGoodBrief.loadDataWithBaseURL(null,mGoodDetails.getGoodsBrief().trim(),D.TEXT_HTML,D.UTF_8,null);

                    initColorsBanner();
                }else{
                    Utils.showToast(mContext,"详情下载失败", Toast.LENGTH_SHORT);
                    finish();
                }
            }
        };
    }

    private void initView() {
        mivCollect = (ImageView) findViewById(R.id.ivCollect);
        mivAddCart = (ImageView) findViewById(R.id.ivAddCart);
        mivShare = (ImageView) findViewById(R.id.ivShare);
        mtvCartCount = (TextView) findViewById(R.id.tvCartCount);
        mSlideAutoLoopView = (SlideAutoLoopView) findViewById(R.id.salv);
        mFlowIndicator = (FlowIndicator) findViewById(R.id.indicator);
        mLayoutColors = (LinearLayout) findViewById(R.id.layoutColorSelector);
        tvGoodName = (TextView) findViewById(R.id.tvGoodName);
        tvGoodEnglishName = (TextView) findViewById(R.id.tvGoodEnglistName);
        tvShopPrice = (TextView) findViewById(R.id.tvShopPrice);
        tvCurrencyPrice = (TextView) findViewById(R.id.tvCurrencyPrice);
        wvGoodBrief = (WebView) findViewById(R.id.wvGoodBrief);
        WebSettings settings = wvGoodBrief.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true);
    }

    private void initColorsBanner(){
        updateColor(0);

        for(int i = 0; i<mGoodDetails.getProperties().length; i++){
            mCurrentColor=i;
            View layout = View.inflate(mContext,R.layout.layout_property_color,null);
            final NetworkImageView ivColor = (NetworkImageView) findViewById(R.id.ivColorItem);
            String colorImg = mGoodDetails.getProperties()[i].getColorImg();
            if(colorImg.isEmpty()){
                continue;
            }
            ImageUtils.setNewGoodThumb(colorImg,ivColor);
            mLayoutColors.addView(layout);
            layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    updateColor(mCurrentColor);
                }
            });
        }
    }

    private void updateColor(int i) {
        AlbumBean[] album = mGoodDetails.getProperties()[i].getAlbumBean();
        String[] albumBean = new String[album.length];
        for(int j=0;j<albumBean.length;j++){
            albumBean[j]=album[j].getImgUrl();
        }
        mSlideAutoLoopView.startPlayLoop(mFlowIndicator,albumBean,albumBean.length);
    }
}
