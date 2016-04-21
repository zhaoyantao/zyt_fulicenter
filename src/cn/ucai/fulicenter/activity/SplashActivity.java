package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.db.UserDao;
import cn.ucai.fulicenter.task.DownloadContactListTask;
import cn.ucai.fulicenter.task.DownloadContactTask;

/**
 * 开屏页
 */
public class SplashActivity extends BaseActivity {
    private SplashActivity mContext;

    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(Bundle arg0) {
        setContentView(R.layout.activity_splash);
        super.onCreate(arg0);
        mContext = this;

    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            public void run() {
                if (DemoHXSDKHelper.getInstance().isLogined()) {
                    String userName = FuLiCenterApplication.getInstance().getUserName();
                    UserDao dao = new UserDao(mContext);
                    UserBean user = dao.findUserByUserName(userName);
                    FuLiCenterApplication.getInstance().setUser(user);
                    //下载联系人列表//REQUEST_DOWNLOAD_CONTACTS  intent:update_contact_list
                    new DownloadContactTask(mContext, userName, 0, 20).execute();
                    //下载好友列表
                    new DownloadContactListTask(mContext, userName, 0, 20).execute();
                } else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }
                startActivity(new Intent(SplashActivity.this, FuLiCenterMainActivity.class));
                finish();
            }
        }).start();

    }

    /**
     * 获取当前应用程序的版本号
     */
    private String getVersion() {
        String st = getResources().getString(R.string.Version_number_is_wrong);
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
            String version = packinfo.versionName;
            return version;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return st;
        }
    }
}
