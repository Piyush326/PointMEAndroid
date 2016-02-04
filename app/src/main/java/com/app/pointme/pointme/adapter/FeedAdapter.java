package com.app.pointme.pointme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pointme.pointme.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by goparties on 28/1/16.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Integer> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView liPostImage;

        public ViewHolder(View v) {
            super(v);
            liPostImage = (ImageView) v.findViewById(R.id.li_post_image);

        }
    }

    public void add(int position, Integer item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FeedAdapter(ArrayList<Integer> myDataset) {
        mDataset = myDataset;
        context = null;
    }

    public FeedAdapter(Context context) {
        this.context = context;
//        mDataset = myDataset;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
//        vh.setIsRecyclable();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(context)
                .load("http://lorempixel.com/500/300/sports/PointME")
                .centerCrop()
                .crossFade()
                .into(holder.liPostImage);


        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        final String name = mDataset.get(position);
       /* holder.txtHeader.setText(mDataset.get(position));
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(name);
            }
        });

        holder.txtFooter.setText("Footer: " + mDataset.get(position));*/

    }

    @Override
    public int getItemCount() {
        return 20;
    }

}
