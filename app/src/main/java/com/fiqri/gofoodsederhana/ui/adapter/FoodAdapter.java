package com.fiqri.gofoodsederhana.ui.adapter;

public class FoodAdapter {

    private onItemClick click;

    public interface onItemClick {
        void onItemClick(int position);
    }

    public void setOnClickListener(onItemClick onClick) {
        click = onClick;
    }
}
