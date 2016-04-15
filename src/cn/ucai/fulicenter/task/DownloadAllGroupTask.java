package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;

import java.util.HashMap;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.SuperWeChatApplication;
import cn.ucai.fulicenter.activity.BaseActivity;
import cn.ucai.fulicenter.bean.ContactBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;

/**
 * Created by sks on 2016/4/7.
 */
public class DownloadAllGroupTask extends BaseActivity {
    Context mContext;
    String userName;
    String path;

    public DownloadAllGroupTask(Context mContext, String userName) {
        this.mContext = mContext;
        this.userName = userName;
        initPath();
    }

    private void initPath() {
        try {
            path = new ApiParams()
                    .with(I.User.USER_NAME,userName)
                    .getRequestUrl(I.REQUEST_DOWNLOAD_GROUPS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(){
        executeRequest(new GsonRequest<ContactBean[]>(path,ContactBean[].class,rsponseListener(),errorListener()));
    }

    private Response.Listener<ContactBean[]> rsponseListener() {

        return new Response.Listener<ContactBean[]>() {
            @Override
            public void onResponse(ContactBean[] contacts) {

                HashMap<Integer, ContactBean> map = new HashMap<Integer, ContactBean>();
                for (ContactBean contact : contacts) {
                    map.put(contact.getCuid(), contact);
                }
                SuperWeChatApplication instance = SuperWeChatApplication.getInstance();
                HashMap<Integer,ContactBean> contactMap=instance.getContacts();
                contactMap.putAll(map);
                Intent intent = new Intent("update_group");
                sendBroadcast(intent);
            }
        };
    }
}
