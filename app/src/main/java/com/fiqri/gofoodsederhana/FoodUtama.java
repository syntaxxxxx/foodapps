package com.fiqri.gofoodsederhana;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.fiqri.gofoodsederhana.helper.SessionManager;
import com.fiqri.gofoodsederhana.ui.adapter.FoodAdapter;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodUtama extends SessionManager implements SwipeRefreshLayout.OnRefreshListener, FoodAdapter.onItemClick {

    @BindView(R.id.spin_kategori_utama)
    Spinner spinKategoriUtama;
    @BindView(R.id.list_food)
    RecyclerView listFood;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    Dialog dialog, dialog2;
    TextInputEditText edtInsertNameFood, edtUpdateId, edtUpdateNameFood;
    Button btnUploadImagesFood, btnUpload, btnCancel, btnUpdate, btnDelete, btnUpdateImages;
    ImageView ivPreviewInsert, ivPreviewUpdate;
    Spinner spinInsert, spinUpdate;
    String idMakanan, idUser, kategori, path, time;
    Target target;
    Uri filepath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_utama);
        ButterKnife.bind(this);

        setRequestPermission();
        insertFoodData();
    }

    private void setRequestPermission() {
    }

    @Override
    public void onRefresh() {

    }

    private void insertFoodData() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(FoodUtama.this);
                dialog.setContentView(R.layout.item_add_food);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);

                edtInsertNameFood = dialog.findViewById(R.id.edt_input_makanan);
                btnUploadImagesFood = dialog.findViewById(R.id.btn_upload_images);
                spinKategoriUtama = dialog.findViewById(R.id.spin_kategori);
                ivPreviewInsert = dialog.findViewById(R.id.image_preview_insert);
                btnUpload = dialog.findViewById(R.id.btn_upload);
                btnCancel = dialog.findViewById(R.id.btn_cancel);

                btnUploadImagesFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        dialog2 = new Dialog(FoodUtama.this);
        dialog2.setContentView(R.layout.item_update_food);
        dialog2.setTitle(getString(R.string.data_food));
        dialog2.setCancelable(true);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.show();

        edtUpdateId = findViewById(R.id.edt_id_makanan);
        edtUpdateNameFood = findViewById(R.id.edt_input_makanan_update);
        btnUpdateImages = findViewById(R.id.btn_update_images);
        spinUpdate = findViewById(R.id.spin_kategori_update);
        ivPreviewUpdate = findViewById(R.id.image_preview_update);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        spinUpdate = findViewById(R.id.spin_kategori_update);

        btnUpdateImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
