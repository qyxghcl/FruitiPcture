package com.example.fruitpictures.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fruitpictures.R;
import com.example.fruitpictures.bean.Fruit;
import com.example.fruitpictures.view.FruitActivity;

import java.util.ArrayList;


/**
 * Created by hwj on 2017/1/28.
 */

public class FruitAdapter extends RecyclerView.Adapter {


    private final ArrayList<Fruit> fruits;
    private Context mContext;

    public FruitAdapter(ArrayList<Fruit> fruits) {
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
                Fruit fruit = fruits.get(position);
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
        Fruit fruit = fruits.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getIamgerId()).into(viewHolder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return fruits == null ? 0 : fruits.size();
    }
}
