package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Arrays;

import cn.ucai.fulicenter.R;


/**
 * Created by sks on 2016/4/16.
 */
public class FuliCenterMainActivity extends BaseActivity {

    TextView mtvCartHint;
    private RadioButton newGood;
    private RadioButton boutique;
    private RadioButton category;
    private RadioButton cart;
    private RadioButton personal_center;

    private RadioButton[] mRedio = new RadioButton[5];
    int index;
    int currentIndex = -1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_fulicenter_main);
        initView();
        setRadioDefaultChecked(currentIndex);
    }

    private void setRadioDefaultChecked(int index) {
        if(index==-1){
            index=0;
        }
        for(int i=0;i<mRedio.length;i++){
            if(i==index){
                mRedio[i].setChecked(true);
            }else{
                mRedio[i].setChecked(false);
            }
        }
    }

    private void initView() {
        mtvCartHint = (TextView) findViewById(R.id.tvCartHint);
        newGood = (RadioButton) findViewById(R.id.layout_new_good);
        boutique = (RadioButton) findViewById(R.id.layout_boutique);
        category = (RadioButton) findViewById(R.id.layout_category);
        cart = (RadioButton) findViewById(R.id.layout_cart);
        personal_center = (RadioButton) findViewById(R.id.layout_personal_center);

        mRedio[0] = newGood;
        mRedio[1] = boutique;
        mRedio[2] = category;
        mRedio[3] = cart;
        mRedio[4] = personal_center;
    }

    public void onCheckedChange(View view){
        switch (view.getId()){
            case R.id.layout_new_good:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_category:
                index = 2;
                break;
            case R.id.layout_cart:
                index = 3;
                break;
            case R.id.layout_personal_center:
                index = 4;
                break;
        }
        if(currentIndex!=index){
            currentIndex = index;
            setRadioDefaultChecked(index);
        }
    }

}
