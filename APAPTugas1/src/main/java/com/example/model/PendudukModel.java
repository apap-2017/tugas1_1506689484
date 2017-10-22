package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel
{
	private int id;
    private String nik;
    private String nama;
    private String tempatLahir;
    private String tanggalLahir;
    private int jenisKelamin;
    private int isWni;
    private KeluargaModel keluarga;
    private String agama;
    private String pekerjaan;
    private String statusPerkawinan;
    private String statusKeluarga;
    private String golonganDarah;
    private int isWafat;
}
