package com.alexilyin.android.a41_assetsandgson;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 05.04.16.
 */
public class MyCardAdapter extends CursorRecyclerViewAdapter<MyCardAdapter.MyCardHolder> {


    public MyCardAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(MyCardHolder viewHolder, Cursor cursor) {
        viewHolder.title.setText(CacheDBContract.Asset.getTitle(cursor));
        viewHolder.image.setImageBitmap(CacheDBContract.Asset.getImage(cursor));
    }

    @Override
    public MyCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_view_item,
                parent,
                false
        );

        return new MyCardHolder(view);
    }

    public static class MyCardHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        public MyCardHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.card_view_title);
            image = (ImageView) itemView.findViewById(R.id.card_view_image);
        }
    }

}
