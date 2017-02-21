package com.example.fruitpictures.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fruitpictures.R;
import com.example.fruitpictures.view.FruitActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hwj on 2017/1/28.
 */

public class FruitAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private  ArrayList<String> urls = new ArrayList<>();

    public FruitAdapter(ArrayList<String> urls) {
        this.urls = urls;
    }

    static class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView im;
        ImageView mIvAvatar;
        TextView mTvNickname;
        public ViewHolder(View itemView) {
            super(itemView);
            this.im = (ImageView) itemView.findViewById(R.id.iv_image);
            mIvAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            mTvNickname = (TextView) itemView.findViewById(R.id.tv_nickname);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null){
            mContext = parent.getContext();
        }
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(inflate);
        holder.im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(mContext,FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,urls.get(position));
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       String uri = urls.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        Glide.with(mContext).load(uri).into(viewHolder.im);

        Picasso.with(holder.itemView.getContext())
                .load(getAvatarResId(position))
                .centerInside()
                .fit()
                .into(viewHolder.mIvAvatar);

        viewHolder.mTvNickname.setText("Taeyeon " + position);
    }
    private int getAvatarResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }


    @Override
    public int getItemCount() {
        return urls == null? 0:urls.size();
    }


   /* private final ArrayList<Images> fruits;
    private Context mContext;

    public FruitAdapter(ArrayList<Images> fruits) {
        this.fruits = fruits;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView fruitName;
        CardView cardView;
        ImageView fruitImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView;
            this.fruitName = (TextView) itemView.findViewById(R.id.fruit_name);
            this.fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Images fruit = fruits.get(position);
                Intent intent = new Intent(mContext,FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getIamgerId());
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Images fruit = fruits.get(position);
        ViewHolder ViewHolder = (ViewHolder) holder;
        ViewHolder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getIamgerId()).into(ViewHolder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return fruits == null ? 0 : fruits.size();
    }*/
}
