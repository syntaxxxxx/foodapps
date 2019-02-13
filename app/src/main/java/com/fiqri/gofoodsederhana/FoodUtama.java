package com.fiqri.gofoodsederhana;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fiqri.gofoodsederhana.helper.SessionManager;
import com.fiqri.gofoodsederhana.model.DataKategoriItem;
import com.fiqri.gofoodsederhana.model.DataMakananItem;
import com.fiqri.gofoodsederhana.model.ResponseDataMakanan;
import com.fiqri.gofoodsederhana.model.ResponseKategori;
import com.fiqri.gofoodsederhana.network.ConfigRetrofit;
import com.fiqri.gofoodsederhana.ui.adapter.FoodAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodUtama extends SessionManager implements SwipeRefreshLayout.OnRefreshListener, FoodAdapter.onItemClick {

    private static final int STORAGE_PERMISSION_CODE = 1;
    private static final int REQ_FILE_CODE = 2;

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
    String idMakanan, strIdUser, kategori, path, time, namaMakanan;
    Target target;
    Uri filepath;
    Bitmap bitmap;

    List<DataKategoriItem> dataKategori = new ArrayList<>();
    List<DataMakananItem> dataMakananItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_utama);
        ButterKnife.bind(this);
        refresh.setOnRefreshListener(this);

        requestPermission();
        insertFoodData();
        fetchDataKategori(spinKategoriUtama);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    private void fetchDataKategori(final Spinner spin) {
        ConfigRetrofit.getInstancee().getDataKategori().enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {

                if (response.isSuccessful()) {
                    dataKategori = response.body().getDataKategori();
                    String id[] = new String[dataKategori.size()];
                    String nameKategori[] = new String[dataKategori.size()];

                    for (int index = 0; index < dataKategori.size(); index++) {
                        id[index] = dataKategori.get(index).getIdKategori();
                        nameKategori[index] = dataKategori.get(index).getNamaKategori();
                    }

                    ArrayAdapter adapter = new ArrayAdapter(
                            FoodUtama.this, android.R.layout.simple_spinner_item, nameKategori);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin.setAdapter(adapter);
                    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            kategori = parent.getItemAtPosition(position).toString();
                            fetchDataMakanan(kategori);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
            }
        });
    }

    private void fetchDataMakanan(String kategori) {
        String idUser = sessionManager.getIdUser();
        ConfigRetrofit.getInstancee().getDataMakanan(idUser, kategori).enqueue(new Callback<ResponseDataMakanan>() {
            @Override
            public void onResponse(Call<ResponseDataMakanan> call, Response<ResponseDataMakanan> response) {

                if (response.isSuccessful()) {
                    dataMakananItems = response.body().getDataMakanan();
                    String id[] = new String[dataMakananItems.size()];
                    String name[] = new String[dataMakananItems.size()];
                    String time[] = new String[dataMakananItems.size()];
                    String images[] = new String[dataMakananItems.size()];

                    for (int index = 0; index < dataMakananItems.size(); index++) {
                        id[index] = dataMakananItems.get(index).getIdMakanan();
                        name[index] = dataMakananItems.get(index).getMakanan();
                        time[index] = dataMakananItems.get(index).getInsertTime();
                        images[index] = dataMakananItems.get(index).getFotoMakanan();
                        strIdUser = id[index];
                    }
                    FoodAdapter adapter = new FoodAdapter(FoodUtama.this, dataMakananItems);
                    listFood.setLayoutManager(new LinearLayoutManager(FoodUtama.this));
                    listFood.setHasFixedSize(true);
                    listFood.setAdapter(adapter);
                    adapter.setOnClickListener(FoodUtama.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseDataMakanan> call, Throwable t) {

            }
        });
    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(false);
        fetchDataMakanan(kategori);
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
                spinInsert = dialog.findViewById(R.id.spin_kategori);
                ivPreviewInsert = dialog.findViewById(R.id.image_preview_insert);
                btnUpload = dialog.findViewById(R.id.btn_upload);
                btnCancel = dialog.findViewById(R.id.btn_cancel);

                btnUploadImagesFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showfilechooser(REQ_FILE_CODE);
                    }
                });

                fetchDataKategori(spinInsert);

                btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        namaMakanan = edtInsertNameFood.getText().toString().trim();

                        if (TextUtils.isEmpty(namaMakanan)) {
                            edtInsertNameFood.setError(getString(R.string.isEmpyField));

                        } else if (ivPreviewInsert.getDrawable() == null) {
                            shortToast("Images Harus Ada");

                        } else {
                            fetchInsertData(kategori);
                            dialog.dismiss();
                        }
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

    private void fetchInsertData(String kategori) {

        try {
            path = getPath(filepath);
            strIdUser = sessionManager.getIdUser();

        } catch (Exception e) {
            longToast("gambar terlalu besar \n silahkan pilih gambar yang lebih kecil");
            e.printStackTrace();
        }
        /**
         * Sets the maximum time to wait in milliseconds between two upload attempts.
         * This is useful because every time an upload fails, the wait time gets multiplied by
         * {@link UploadService#BACKOFF_MULTIPLIER} and it's not convenient that the value grows
         * indefinitely.
         */
        time = getCurentDate();
        try {
            new MultipartUploadRequest(c, BuildConfig.BASE_UPLOAD)
                    .addFileToUpload(path, "image")
                    .addParameter("vsiduser", strIdUser)
                    .addParameter("vsnamamakanan", namaMakanan)
                    .addParameter("vstimeinsert", time)
                    .addParameter("vskategori", kategori)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();

            fetchDataMakanan(kategori);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            shortToast(e.getMessage());

        } catch (FileNotFoundException e) {
            shortToast(e.getMessage());
            e.printStackTrace();
        }
    }

    private void showfilechooser(int reqFileChoose) {
        Intent intentgalery = new Intent(Intent.ACTION_PICK);
        intentgalery.setType("image/*");
        intentgalery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentgalery, "Select Pictures"), reqFileChoose);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_FILE_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                ivPreviewInsert.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private String getPath(Uri filepath) {
        Cursor cursor = getContentResolver().query(filepath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    @Override
    public void onItemClick(int position) {
        dialog2 = new Dialog(FoodUtama.this);
        dialog2.setContentView(R.layout.item_update_food);
        dialog2.setTitle(getString(R.string.data_food));
        dialog2.setCancelable(true);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.show();

        edtUpdateId = dialog2.findViewById(R.id.edt_id_makanan);
        edtUpdateNameFood = dialog2.findViewById(R.id.edt_input_makanan_update);
        btnUpdateImages = dialog2.findViewById(R.id.btn_update_images);
        spinUpdate = dialog2.findViewById(R.id.spin_kategori_update);
        ivPreviewInsert = dialog2.findViewById(R.id.image_preview_update);
        btnUpdate = dialog2.findViewById(R.id.btn_update);
        btnDelete = dialog2.findViewById(R.id.btn_delete);
        spinUpdate = dialog2.findViewById(R.id.spin_kategori_update);

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                ivPreviewInsert.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                longToast(e.getMessage());
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.get()
                .load(BuildConfig.BASE_URL + "uploads/" + dataMakananItems.get(position).getFotoMakanan())
                .into(target);

        fetchDataKategori(spinUpdate);
        edtUpdateNameFood.setText(dataMakananItems.get(position).getMakanan());
        edtUpdateId.setText(dataMakananItems.get(position).getIdMakanan());

        btnUpdateImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfilechooser(REQ_FILE_CODE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idMakanan = edtUpdateId.getText().toString().trim();
                fetchUpdate();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void fetchUpdate() {
        try {
            path = getPath(filepath);
            strIdUser = sessionManager.getIdUser();

        } catch (Exception e) {
            e.printStackTrace();
        }

        namaMakanan = edtUpdateNameFood.getText().toString().trim();
        idMakanan = edtUpdateId.getText().toString().trim();

        if (TextUtils.isEmpty(namaMakanan)) {
            edtUpdateNameFood.setError("nama makanan tidak boleh kosong");
            edtUpdateNameFood.requestFocus();

        } else if (ivPreviewInsert.getDrawable() == null) {
            shortToast("gambar harus dipilih");

        } else if (path == null) {
            shortToast("gambar harus diganti");

        } else {

            /**
             * Sets the maximum time to wait in milliseconds between two upload attempts.
             * This is useful because every time an upload fails, the wait time gets multiplied by
             * {@link UploadService#BACKOFF_MULTIPLIER} and it's not convenient that the value grows
             * indefinitely.
             */

            try {
                new MultipartUploadRequest(c, BuildConfig.BASE_UPDATE)
                        .addFileToUpload(path, "image")
                        .addParameter("vsidmakanan", idMakanan)
                        .addParameter("vsnamamakanan", namaMakanan)
                        .addParameter("vsidkategori", kategori)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                longToast(e.getMessage());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                longToast(e.getMessage());
            }
            dialog2.dismiss();
        }
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
