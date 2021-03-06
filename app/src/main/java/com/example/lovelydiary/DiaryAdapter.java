package com.example.lovelydiary;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DiaryAdapter extends ArrayAdapter<Diary> {
    private int resourceId;

    public DiaryAdapter( Context context, int textViewResourceId, List<Diary> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
//      获取当前实例
        Diary diary=getItem(position);
        View view;
        ViewHolder viewHolder;
//       缓存实例
//       convertView用于缓存布局，提高listView的效率
        if(convertView==null){
            //缓存为空时才加载布局
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            //缓存空时，创建一个用于缓存的实例
            viewHolder=new ViewHolder();
            //提示信息
            viewHolder.item=(TextView)view.findViewById(R.id.item);
            //将viewHolder保存在view中
            view.setTag(viewHolder);
        }
        else {
            //否则重用convertView，以此达到了不会重复加载布局
            view=convertView;
            //重用viewHolder,重新获取viewHolder
            viewHolder=(ViewHolder)view.getTag();
        }
        //提示信息
        //日记内容
        viewHolder.item.setText("标题："+diary.getTitle()+"\u3000"+"\u3000"+"日期："+diary.getDate());
        return view;
    }

//创建一个内部类ViewHolder,用于对控件的实例进行缓存
    public class ViewHolder{
        TextView item;
    }
}

