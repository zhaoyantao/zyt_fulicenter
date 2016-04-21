package cn.ucai.fulicenter.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.CollectActivity;
import cn.ucai.fulicenter.activity.FuLiCenterMainActivity;
import cn.ucai.fulicenter.activity.SettingsActivity;
import cn.ucai.fulicenter.task.DownloadCollectListTask;
import cn.ucai.fulicenter.utils.UserUtils;


/**
 * Created by ucai on 2016/4/20.
 */
public class PersionalCenterFragment extends Fragment {
    FuLiCenterMainActivity mContext;
    NetworkImageView mUserAvatar;
    TextView mUserName;
    String mCurrentUserName;
    Button mbtnSetting;
    TextView mtvCollectCount;
    LinearLayout CollectList;

//    CollectCountChangerReceiver mCollectCountChangerReceiver;
    private int mCollectCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = (FuLiCenterMainActivity) getActivity();
        View layout = View.inflate(mContext, R.layout.fragment_persional_center, null);
//        CollectCountChangerReceiverRegister();
        initview(layout);
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        setSettingOnClickListener();
        setCollectListOnClickListener();
    }

    private void setCollectListOnClickListener() {
        CollectList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CollectActivity.class));
            }
        });
    }

    private void setSettingOnClickListener() {
        mbtnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SettingsActivity.class));
            }
        });
    }

    private void initData() {
        if (mCurrentUserName != null) {
            mUserName.setText(mCurrentUserName);
            UserUtils.setCurrentUserBeanAvatar(mUserAvatar);
        }
    }

    private void initview(View layout) {
        mCurrentUserName = FuLiCenterApplication.getInstance().getUserName();
        mUserAvatar = (NetworkImageView) layout.findViewById(R.id.nivUserAvatar);
        mUserName = (TextView) layout.findViewById(R.id.tvUserName);
        mbtnSetting = (Button) layout.findViewById(R.id.btnSetting);
        mtvCollectCount = (TextView) layout.findViewById(R.id.ll_Collect_Baby_Count);
        CollectList = (LinearLayout) layout.findViewById(R.id.ll_Collect_Baby);
    }

//    class CollectCountChangerReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String count = intent.getStringExtra("count");
//            Log.i("main", "intent.getStringExtra(count)" + count);
//            mtvCollectCount.setText(count);
//        }
//    }
//
//    private void CollectCountChangerReceiverRegister() {
//        mCollectCountChangerReceiver = new CollectCountChangerReceiver();
//        IntentFilter filter = new IntentFilter("countChanger");
//        mContext.registerReceiver(mCollectCountChangerReceiver, filter);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCollectCountChangedReceiver != null) {
            mContext.unregisterReceiver(mCollectCountChangedReceiver);
        }
    }
    private void refresh() {
        mtvCollectCount.setText(""+mCollectCount);
        if(FuLiCenterApplication.getInstance().getUser()!=null){
            UserUtils.setCurrentUserBeanAvatar(mUserAvatar);
            UserUtils.setCurrentUserBeanNick(mUserName);
        }
    }


    class UpdateUserChangedReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            new DownloadCollectListTask(mContext).execute();
            refresh();
        }
    }

    UpdateUserChangedReceiver mUpdateUserChangedReceiver;

    private void registerUpdateUserChangedReceiver(){
        mUpdateUserChangedReceiver = new UpdateUserChangedReceiver();
        IntentFilter filter = new IntentFilter("update_user");
        mContext.registerReceiver(mUpdateUserChangedReceiver,filter);

    }


    class CollectCountChangedReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            mCollectCount = FuLiCenterApplication.getInstance().getCollectGood();
            refresh();
        }
    }

    CollectCountChangedReceiver mCollectCountChangedReceiver;

    private void registerCollectCountReceiver(){
        mCollectCountChangedReceiver = new CollectCountChangedReceiver();
        IntentFilter filter = new IntentFilter("update_collect_count");
        mContext.registerReceiver(mCollectCountChangedReceiver,filter);

    }
}
