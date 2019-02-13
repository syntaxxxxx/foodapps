package com.fiqri.gofoodsederhana.model;

import com.google.gson.annotations.SerializedName;

public class DataMakananItem{

	@SerializedName("id_makanan")
	private String idMakanan;

	@SerializedName("id_kategori")
	private String idKategori;

	@SerializedName("foto_makanan")
	private String fotoMakanan;

	@SerializedName("level")
	private String level;

	@SerializedName("insert_time")
	private String insertTime;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("makanan")
	private String makanan;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("password")
	private String password;

	@SerializedName("nama")
	private String nama;

	@SerializedName("jenkel")
	private String jenkel;

	@SerializedName("no_telp")
	private String noTelp;

	@SerializedName("username")
	private String username;

	@SerializedName("nama_kategori")
	private String namaKategori;

	public void setIdMakanan(String idMakanan){
		this.idMakanan = idMakanan;
	}

	public String getIdMakanan(){
		return idMakanan;
	}

	public void setIdKategori(String idKategori){
		this.idKategori = idKategori;
	}

	public String getIdKategori(){
		return idKategori;
	}

	public void setFotoMakanan(String fotoMakanan){
		this.fotoMakanan = fotoMakanan;
	}

	public String getFotoMakanan(){
		return fotoMakanan;
	}

	public void setLevel(String level){
		this.level = level;
	}

	public String getLevel(){
		return level;
	}

	public void setInsertTime(String insertTime){
		this.insertTime = insertTime;
	}

	public String getInsertTime(){
		return insertTime;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setMakanan(String makanan){
		this.makanan = makanan;
	}

	public String getMakanan(){
		return makanan;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setJenkel(String jenkel){
		this.jenkel = jenkel;
	}

	public String getJenkel(){
		return jenkel;
	}

	public void setNoTelp(String noTelp){
		this.noTelp = noTelp;
	}

	public String getNoTelp(){
		return noTelp;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setNamaKategori(String namaKategori){
		this.namaKategori = namaKategori;
	}

	public String getNamaKategori(){
		return namaKategori;
	}
}