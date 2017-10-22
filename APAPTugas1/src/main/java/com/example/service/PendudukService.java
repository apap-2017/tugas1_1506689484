package com.example.service;

import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

public interface PendudukService
{
	PendudukModel getPenduduk(String nik);
	KeluargaModel getKeluarga(int id);
	KeluargaModel getKeluargaFromNKK(String nkk);
	List<PendudukModel> getAnggotaKeluarga(int id);
	KelurahanModel getKelurahan(int id);
	List<KelurahanModel> getAllKelurahan();
	KecamatanModel getKecamatan(int id);
	KotaModel getKota(int id);
	PendudukModel getLastID(String nik);
	KeluargaModel getLastKeluargaID(String nkk);
	void addPenduduk(PendudukModel penduduk);
	void addKeluarga(KeluargaModel keluarga);
	List<KotaModel> getAllKota();
	void updatePenduduk(int id, String nik, String nama, int jenisKelamin, int isWni, KeluargaModel keluarga, String agama, String pekerjaan, String statusPerkawinan, String statusKeluarga, String golonganDarah);
	void updateKeluarga(int id, String nkk, String alamat, String rt, String rw, KelurahanModel kelurahan);
	void setDeath(String nik);
	void setInactive(int id);
	List<KelurahanModel> getAllKelurahanFromKecamatan(int idKecamatan);
	List<KecamatanModel> getAllKecamatanFromKota(int idKota);
	List<PendudukModel> getPendudukInKelurahan(int idKelurahan);
	PendudukModel getYoungest(int idKelurahan);
	PendudukModel getOldest(int idKelurahan);
}