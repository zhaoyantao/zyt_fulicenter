package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;

import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.activity.BaseActivity;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;

/**
 * Created by sks on 2016/4/21.
 */
public class DownloadCollectListTask extends BaseActivity {
    Context mContext;
    String path;

    public DownloadCollectListTask(Context mContext) {
        this.mContext = mContext;
        initPath();
    }

    private void initPath() {
        try {
            UserBean user = FuLiCenterApplication.getInstance().getUser();
            if (user != null) {
                path = new ApiParams().with(I.User.USER_NAME, user.getUserName())
                        .getRequestUrl(I.REQUEST_FIND_COLLECT_COUNT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(){
        if(path==null||path.isEmpty()){
            return;
        }
        executeRequest(new GsonRequest<MessageBean>(path,MessageBean.class,
                responseDownloadCollectCountListener(),errorListener()));
    }

    private Response.Listener<MessageBean> responseDownloadCollectCountListener() {
        return new Response.Listener<MessageBean>() {
            @Override
            public void onResponse(MessageBean messageBean) {
                if (messageBean.isSuccess()){
                    String count = messageBean.getMsg();
                    FuLiCenterApplication.getInstance().setCollectGood(Integer.parseInt(count));
                }else{
                    FuLiCenterApplication.getInstance().setCollectGood(0);
                }
                Intent intent = new Intent("update_collect_count");
                mContext.sendStickyBroadcast(intent);
            }
        };
    }

}
