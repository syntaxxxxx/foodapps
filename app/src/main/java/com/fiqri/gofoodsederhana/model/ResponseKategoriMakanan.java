package com.fiqri.gofoodsederhana.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseKategoriMakanan{

	@SerializedName("DataKategori")
	private List<DataKategoriItem> dataKategori;

	public void setDataKategori(List<DataKategoriItem> dataKategori){
		this.dataKategori = dataKategori;
	}

	public List<DataKategoriItem> getDataKategori(){
		return dataKategori;
	}
}