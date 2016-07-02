package com.tencent.qqlive.listviewdeleteanimationdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by earljwang on 2016/6/17.
 */
public class MyListAdapter extends BaseAdapter{
    private static final String TAG = "MyListAdapter";
    private List<String> mStringList;
    private LayoutInflater mLayoutInflater;
    private int currentDelteOrder = 0;
    private float currentScale = 0;
    private int originHeight;
    private boolean hadGetOriginHeight;
    private boolean isNeedDelete = false;
    private boolean isDeleting = false;

    public MyListAdapter(List<String> stringList, Context context) {
        mStringList = stringList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Log.i(TAG,"getView position -> " + position);
        if(convertView == null || ((ViewHolder)convertView.getTag()).isNeedInflate){
            convertView = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.isNeedInflate = false;
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.tvContent.setText(mStringList.get(position));

        return convertView;
    }


    public void collapseDeleteView(ListView listView, int position){
        final View view;
        if(listView == null){
            throw new RuntimeException("listView can not be null");
        }
        if(isDeleting)
            return;
        int first = listView.getFirstVisiblePosition();
        view = listView.getChildAt(position - first);
        isDeleting = true;
        isNeedDelete = true;
        hadGetOriginHeight = false;
        currentDelteOrder = position;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentScale = animation.getAnimatedFraction();
                if(isNeedDelete){
                    doCollapse(view);
                }
            }
        });
        valueAnimator.setDuration(100);
        valueAnimator.start();
    }


    private void doCollapse(View view){
        if(!hadGetOriginHeight){
            originHeight = view.getHeight();
            hadGetOriginHeight = true;
        }
        view.getLayoutParams().height = (int) (originHeight - originHeight * currentScale);
        view.requestLayout();
        if(view.getLayoutParams().height == 0){
            isNeedDelete = false;
            mStringList.remove(currentDelteOrder);
            ((ViewHolder)view.getTag()).isNeedInflate = true;
            notifyDataSetChanged();
            isDeleting = false;
        }
    }


    private class ViewHolder{
        public TextView tvContent;
        public boolean isNeedInflate;
    }

}
