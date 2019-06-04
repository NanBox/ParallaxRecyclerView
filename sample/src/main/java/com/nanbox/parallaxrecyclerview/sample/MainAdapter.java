package com.nanbox.parallaxrecyclerview.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 主列表适配器
 * Created by NanBox on 2017/2/4.
 */
class MainAdapter extends RecyclerView.Adapter {

    private Context context;

    MainAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    private class Holder extends RecyclerView.ViewHolder {

        Holder(View itemView) {
            super(itemView);
        }

    }
}
