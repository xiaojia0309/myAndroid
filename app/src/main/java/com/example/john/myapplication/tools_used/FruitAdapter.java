package com.example.john.myapplication.tools_used;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.myapplication.R;

import java.util.List;

/**
 * Created by John on 2017/10/17.
 */

public class FruitAdapter extends ArrayAdapter<Fruit> {

    private int resourceId;


    public FruitAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Fruit> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    /**
     *
     * @param position
     * @param convertView 这个参数表示layout的缓存，如果存在之前缓存好的布局，则其将它拿出不再加载，反之则重新加载！
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Fruit fruit =getItem(position);//首先获取到这个item实例！
       /* View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);*/
       //优化后的代码：
        View view;
        ViewHolder viewHolder;//实例化一个viewholder；

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();

            viewHolder.fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            viewHolder.fruitName =  (TextView) view.findViewById(R.id.fruit_name);

            //将viewHolder存储在view中！
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        //重新绑定值！
        viewHolder.fruitImage.setImageResource(fruit.getImgId());
        viewHolder.fruitName.setText(fruit.getName());

        return view;
    }

    /**
     * @see--新建一个viewholder类，用于优化代码！
     */
    class ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
    }
}
