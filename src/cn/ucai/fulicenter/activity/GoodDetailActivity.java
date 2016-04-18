package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.toolbox.NetworkImageView;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.utils.ImageUtils;

public class GoodDetailActivity extends BaseActivity {

    private GoodDetailActivity mContext;
    private GoodDetailsBean mGoodDetails;
    private int mCurrentColor;
    private NetworkImageView ivColor;
    private Button layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        initView();
        initData();
        initColorsBanner();
    }

    private void initData() {
    }

    private void initView() {
        mContext = this;
    }

    private void initColorsBanner(){
        updateColor(0);

        for(int i = 0; i<mGoodDetails.getProperties().length; i++){
            mCurrentColor=i;
            View view = View.inflate(mContext,R.layout.layout_property_color,null);
            final NetworkImageView icColor = (NetworkImageView) findViewById(R.id.ivColorItem);
            String colorImg = mGoodDetails.getProperties()[i].getColorImg();
            if(colorImg.isEmpty()){
                continue;
            }
            ImageUtils.setNewGoodThumb(colorImg,ivColor);
//            mLayoutColors.addView(layout);
            layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    updateColor(mCurrentColor);
                }
            });
        }
    }

    private void updateColor(int i) {
    }
}
