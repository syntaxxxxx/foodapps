package com.fiqri.gofoodsederhana.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiqri.gofoodsederhana.BuildConfig;
import com.fiqri.gofoodsederhana.R;
import com.fiqri.gofoodsederhana.model.DataMakananItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private onItemClick click;

    Context context;
    List<DataMakananItem> dataMakananItems;

    public FoodAdapter(Context context, List<DataMakananItem> dataMakananItems) {
        this.context = context;
        this.dataMakananItems = dataMakananItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int index) {
        viewHolder.itemDesk.setText(dataMakananItems.get(index).getMakanan());
        viewHolder.itemTime.setText(dataMakananItems.get(index).getInsertTime());
        String images = BuildConfig.BASE_URL + "uploads/" + dataMakananItems.get(index).getFotoMakanan();
        Picasso.get().load(images).into(viewHolder.itemImages);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onItemClick(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataMakananItems == null) return 0;
        return dataMakananItems.size();
    }

    public interface onItemClick {
        void onItemClick(int position);
    }

    public void setOnClickListener(onItemClick onClick) {
        click = onClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_images)
        ImageView itemImages;
        @BindView(R.id.item_desk)
        TextView itemDesk;
        @BindView(R.id.item_time)
        TextView itemTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}