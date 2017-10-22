package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {
	private int id;
	private KotaModel kota;
	private String kodeKecamatan;
	private String namaKecamatan;
	private List<KelurahanModel> kelurahan;
}
