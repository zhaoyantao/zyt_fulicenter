package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.CategoryChildActivity;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.ImageUtils;


public class CategoryAdapter extends BaseExpandableListAdapter {

    Context mContext;
    ArrayList<CategoryGroupBean> categoryList;
    ArrayList<ArrayList<CategoryChildBean>> childList;

    public CategoryAdapter(Context mContext, ArrayList<CategoryGroupBean> categoryList, ArrayList<ArrayList<CategoryChildBean>> childList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return categoryList==null?0:categoryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList==null||childList.get(groupPosition)==null?0:childList.get(groupPosition).size();
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return categoryList.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View layout, ViewGroup parent) {
        ViewGroupHolder holder = null;
        if(layout==null){
            layout = View.inflate(mContext,R.layout.item_category_group,null);
            holder = new ViewGroupHolder(layout);
            layout.setTag(holder);
        }else{
            holder = (ViewGroupHolder) layout.getTag();
        }
        CategoryGroupBean group = getGroup(groupPosition);
        holder.mCategoryGroupName.setText(group.getName());
        String url = I.DOWNLOAD_DOWNLOAD_CATEGORY_GROUP_IMAGE_URL+group.getImageUrl();
        ImageUtils.setThumb(url,holder.mCategoryGroupImage);

        if(isExpanded){
            holder.mCategoryGroupIcon.setImageResource(R.drawable.expand_off);
        }else{
            holder.mCategoryGroupIcon.setImageResource(R.drawable.expand_on);
        }
        return layout;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View layout, ViewGroup parent) {
        ViewChildHolder holder = null;
        if(layout==null){
            layout = View.inflate(mContext,R.layout.item_category_child,null);
            holder = new ViewChildHolder(layout);
            layout.setTag(holder);
        }else{
            holder = (ViewChildHolder) layout.getTag();
        }
        final CategoryChildBean child = getChild(groupPosition,childPosition);
        String name = child.getName();
        holder.mCategoryChildName.setText(name);
        String url = I.DOWNLOAD_DOWNLOAD_CATEGORY_CHILD_IMAGE_URL+child.getImageUrl();
        ImageUtils.setThumb(url,holder.mCategoryChildImage);
        holder.childLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CategoryChildActivity.class);
                intent.putExtra(I.CategoryChild.CAT_ID,child.getId());
                ArrayList<CategoryChildBean> children = childList.get(groupPosition);
                intent.putExtra("children",children);
                intent.putExtra(I.CategoryGroup.NAME,getGroup(groupPosition).getName());
                mContext.startActivity(intent);
            }
        });
        return layout;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    class ViewGroupHolder extends RecyclerView.ViewHolder{
        RelativeLayout groupLayout;
        NetworkImageView mCategoryGroupImage;
        TextView mCategoryGroupName;
        ImageView mCategoryGroupIcon;

        public ViewGroupHolder(View itemView) {
            super(itemView);
            groupLayout = (RelativeLayout) itemView.findViewById(R.id.layout_category_group);
            mCategoryGroupImage = (NetworkImageView) itemView.findViewById(R.id.iv_category_group);
            mCategoryGroupName = (TextView) itemView.findViewById(R.id.tv_category);
            mCategoryGroupIcon = (ImageView) itemView.findViewById(R.id.iv_category_group_icon);
        }
    }
    class ViewChildHolder extends RecyclerView.ViewHolder{
        RelativeLayout childLayout;
        NetworkImageView mCategoryChildImage;
        TextView mCategoryChildName;

        public ViewChildHolder(View itemView) {
            super(itemView);
            childLayout = (RelativeLayout) itemView.findViewById(R.id.layout_category_child);
            mCategoryChildImage = (NetworkImageView) itemView.findViewById(R.id.iv_category_child);
            mCategoryChildName = (TextView) itemView.findViewById(R.id.tv_category_child);
        }
    }

    public void addItems(ArrayList<CategoryGroupBean> groupList,
            ArrayList<ArrayList<CategoryChildBean>> childList){
        this.categoryList.addAll(groupList);
        this.childList.addAll(childList);
        notifyDataSetChanged();
    }
}
