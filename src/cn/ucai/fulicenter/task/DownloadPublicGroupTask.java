package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.SuperWeChatApplication;
import cn.ucai.fulicenter.activity.BaseActivity;
import cn.ucai.fulicenter.bean.GroupBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;

/**
 * Created by sks on 2016/4/13.
 */
public class DownloadPublicGroupTask extends BaseActivity{
    Context mContext;
    String userName;
    String path;
    int pageId;
    int pageSize;

    public DownloadPublicGroupTask(Context mContext ,String userName, int pageId, int pageSize) {
        this.mContext = mContext;
        this.pageSize = pageSize;
        this.userName = userName;
        this.pageId = pageId;
        initPath();
    }

    private void initPath() {
        try {
            path = new ApiParams()
                    .with(I.User.USER_NAME,userName)
                    .with(I.PAGE_ID,pageId+"")
                    .with(I.PAGE_SIZE,pageSize+"")
                    .getRequestUrl(I.REQUEST_FIND_PUBLIC_GROUPS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(){
        executeRequest(new GsonRequest<GroupBean[]>(path,GroupBean[].class,rsponseListener(),errorListener()));
    }

    private Response.Listener<GroupBean[]> rsponseListener() {
        return new Response.Listener<GroupBean[]>() {
            @Override
            public void onResponse(GroupBean[] groups) {
                if (groups == null) {
                    return;
                }
                ArrayList<GroupBean> list = SuperWeChatApplication.getInstance().getPublicGroupList();
                ArrayList<GroupBean> group = Utils.array2List(groups);
                for(GroupBean g :group){
                    if(!list.contains(g)){
                        list.add(g);
                    }
                }
                Intent intent = new Intent("update_public_group");
                mContext.sendStickyBroadcast(intent);
            }
        };
    }

}
