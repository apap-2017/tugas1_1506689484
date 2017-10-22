package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {
	private int id;
	private KecamatanModel kecamatan;
	private String kodeKelurahan;
	private String namaKelurahan;
	private String kodePos;
}
