package cn.ucai.fulicenter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.android.volley.Response;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.FuLiCenterMainActivity;
import cn.ucai.fulicenter.adapter.CategoryAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;

public class CategoryFragment extends Fragment {

    FuLiCenterMainActivity mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    ExpandableListView melvCategory;

    CategoryAdapter mAdapter;
    ImageView mivGroupIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        mContext = (FuLiCenterMainActivity) getActivity();
        View layout = View.inflate(getContext(),R.layout.fragment_category, null);
        initView(layout);
        initData();
        setListener();
        return layout;
    }

    private void initView(View layout) {
        melvCategory = (ExpandableListView) layout.findViewById(R.id.elvCategory);
        melvCategory.setGroupIndicator(null);
        mGroupList = new ArrayList<CategoryGroupBean>();
        mChildList = new ArrayList<ArrayList<CategoryChildBean>>();
        mAdapter = new CategoryAdapter(mContext,mGroupList,mChildList);
        melvCategory.setAdapter(mAdapter);
    }

    private void initData() {
        mGroupList = new ArrayList<CategoryGroupBean>();
        mChildList = new ArrayList<ArrayList<CategoryChildBean>>();
        try {
            String path = new ApiParams().getRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP);
            mContext.executeRequest(new GsonRequest<CategoryGroupBean[]>(path,CategoryGroupBean[].class,responseDLCategoryListener(),mContext.errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<CategoryGroupBean[]> responseDLCategoryListener() {
        return new Response.Listener<CategoryGroupBean[]>() {
            @Override
            public void onResponse(CategoryGroupBean[] categoryGroupBean) {
                try {
                    if(categoryGroupBean != null){
                        mGroupList = Utils.array2List(categoryGroupBean);
                        for(CategoryGroupBean group : mGroupList){
                            String path = new ApiParams().with(I.CategoryChild.PARENT_ID,group.getId()+"")
                                    .with(I.PAGE_ID,"0").with(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                                    .getRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN);
                            mContext.executeRequest(new GsonRequest<CategoryChildBean[]>(
                                    path,CategoryChildBean[].class,responseCCBListener(),mContext.errorListener()
                            ));
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
    }

    private Response.Listener<CategoryChildBean[]> responseCCBListener() {
        return new Response.Listener<CategoryChildBean[]>() {
            @Override
            public void onResponse(CategoryChildBean[] categoryChildBeen) {
                if(categoryChildBeen!=null){
                    ArrayList<CategoryChildBean> childList = Utils.array2List(categoryChildBeen);
                    if(childList!=null){
                        mChildList.add(childList);
                    }
                }
                if(mGroupList.size()==mChildList.size()){
                    mAdapter.addItems(mGroupList,mChildList);
                }
            }
        };
    }

    private void setListener() {
        setCategoryGroupExpandOnListener();
        setCategoryGroupExpandOffListener();
        setCategoryGroupClickListener();
    }

    private void setCategoryGroupExpandOnListener() {
        melvCategory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                mivGroupIndicator.setImageResource(R.drawable.expand_off);
            }
        });
    }

    private void setCategoryGroupExpandOffListener() {
        melvCategory.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                mivGroupIndicator.setImageResource(R.drawable.expand_on);
            }
        });
    }

    private void setCategoryGroupClickListener() {
        melvCategory.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                mivGroupIndicator = (ImageView) v.findViewById(R.id.iv_category_group_icon);
                return false;
            }
        });
    }

}
