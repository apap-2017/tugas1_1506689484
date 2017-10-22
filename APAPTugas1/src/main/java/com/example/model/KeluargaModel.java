package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private int id;
	private String noKK;
	private String alamat;
	private String rt;
	private String rw;
	private KelurahanModel kelurahan;
	private int isTidakBerlaku;
	private List<PendudukModel> anggota;
}
