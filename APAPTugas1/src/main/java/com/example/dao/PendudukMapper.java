package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper
{
    @Select("SELECT * FROM penduduk WHERE nik = #{nik}")
    @Results(value = {
    	@Result(id = true, property = "id", column = "id"),
    	@Result(property = "nik", column = "nik", javaType = String.class),
    	@Result(property = "nama", column = "nama"),
    	@Result(property = "tempatLahir", column = "tempat_lahir"),
    	@Result(property = "tanggalLahir", column = "tanggal_lahir"),
    	@Result(property = "jenisKelamin", column = "jenis_kelamin"),
    	@Result(property = "isWni", column = "is_wni"),
    	@Result(property = "keluarga", column = "id_keluarga", one = @One(select = "getKeluarga")),
    	@Result(property = "agama", column = "agama"),
    	@Result(property = "pekerjaan", column = "pekerjaan"),
    	@Result(property = "statusPerkawinan", column = "status_perkawinan"),
    	@Result(property = "statusKeluarga", column = "status_dalam_keluarga"),
    	@Result(property = "golonganDarah", column = "golongan_darah"),
    	@Result(property = "isWafat", column = "is_wafat")
    })
    PendudukModel getPenduduk(@Param(value = "nik") String nik);
    
    @Select("SELECT * FROM keluarga WHERE id = #{id}")
    @Results(value = {
    	@Result(id = true, property = "id", column = "id"),
    	@Result(property = "noKK", column = "nomor_kk"),
    	@Result(property = "alamat", column = "alamat"),
    	@Result(property = "rt", column = "RT"),
    	@Result(property = "rw", column = "RW"),
    	@Result(property = "kelurahan", column = "id_kelurahan", one = @One(select = "getKelurahan")),
    	@Result(property = "isTidakBerlaku", column = "is_tidak_berlaku"),
    	@Result(property = "anggota", column = "id", javaType = List.class, many = @Many(select = "getAnggotaKeluarga"))
    })
    KeluargaModel getKeluarga(@Param(value = "id") int id);
    
    @Select("SELECT * FROM keluarga WHERE nomor_kk = #{nkk}")
    @Results(value = {
    	@Result(id = true, property = "id", column = "id"),
    	@Result(property = "noKK", column = "nomor_kk"),
    	@Result(property = "alamat", column = "alamat"),
    	@Result(property = "rt", column = "RT"),
    	@Result(property = "rw", column = "RW"),
    	@Result(property = "kelurahan", column = "id_kelurahan", one = @One(select = "getKelurahan")),
    	@Result(property = "isTidakBerlaku", column = "is_tidak_berlaku"),
    	@Result(property = "anggota", column = "id", javaType = List.class, many = @Many(select = "getAnggotaKeluarga"))
    })
    KeluargaModel getKeluargaFromNKK(@Param(value = "nkk") String nkk);
    
    @Select("SELECT p.id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat FROM penduduk as p, keluarga as k WHERE p.id_keluarga = k.id AND k.id = #{id}")
    @Results(value = {
        	@Result(id = true, property = "id", column = "id"),
        	@Result(property = "nik", column = "nik", javaType = String.class),
        	@Result(property = "nama", column = "nama"),
        	@Result(property = "tempatLahir", column = "tempat_lahir"),
        	@Result(property = "tanggalLahir", column = "tanggal_lahir"),
        	@Result(property = "jenisKelamin", column = "jenis_kelamin"),
        	@Result(property = "isWni", column = "is_wni"),
        	//@Result(property = "keluarga", column = "id_keluarga", one = @One(select = "getKeluarga")),
        	@Result(property = "agama", column = "agama"),
        	@Result(property = "pekerjaan", column = "pekerjaan"),
        	@Result(property = "statusPerkawinan", column = "status_perkawinan"),
        	@Result(property = "statusKeluarga", column = "status_dalam_keluarga"),
        	@Result(property = "golonganDarah", column = "golongan_darah"),
        	@Result(property = "isWafat", column = "is_wafat")
    })
    List<PendudukModel> getAnggotaKeluarga(@Param(value = "id") int id);
    
    @Select("SELECT * FROM kelurahan WHERE id = #{id}")
    @Results(value = {
    	@Result(id = true, property = "id", column = "id"),
    	@Result(property = "kecamatan", column = "id_kecamatan", one = @One(select = "getKecamatan")),
    	@Result(property = "kodeKelurahan", column = "kode_kelurahan"),
    	@Result(property = "namaKelurahan", column = "nama_kelurahan"),
    	@Result(property = "kodePos", column = "kode_pos")
    })
    KelurahanModel getKelurahan(@Param(value = "id") int id);
    
    @Select("SELECT * FROM kecamatan WHERE id = #{id}")
    @Results(value = {
    	@Result(id = true, property = "id", column = "id"),
    	@Result(property = "kota", column = "id_kota", one = @One(select = "getKota")),
    	@Result(property = "kodeKecamatan", column = "kode_kecamatan"),
    	@Result(property = "namaKecamatan", column = "nama_kecamatan")
    })
    KecamatanModel getKecamatan(@Param(value = "id") int id);
    
    @Select("SELECT * FROM kota WHERE id = #{id}")
    @Results(value = {
        	@Result(id = true, property = "id", column = "id"),
        	@Result(property = "kodeKota", column = "kode_kota"),
        	@Result(property = "namaKota", column = "nama_kota")
        })
    KotaModel getKota(@Param(value = "id") int id);
    
    @Select("SELECT * FROM kelurahan ORDER BY nama_kelurahan ASC")
    @Results(value = {
    		@Result(id = true, property = "id", column = "id"),
        	@Result(property = "kecamatan", column = "id_kecamatan", one = @One(select = "getKecamatan")),
        	@Result(property = "kodeKelurahan", column = "kode_kelurahan"),
        	@Result(property = "namaKelurahan", column = "nama_kelurahan"),
        	@Result(property = "kodePos", column = "kode_pos")
        })
    List<KelurahanModel> getAllKelurahan();
    
    @Select("SELECT * FROM kelurahan WHERE id_kecamatan = #{idKecamatan} ORDER BY nama_kelurahan ASC")
    @Results(value = {
    		@Result(id = true, property = "id", column = "id"),
        	@Result(property = "kecamatan", column = "id_kecamatan", one = @One(select = "getKecamatan")),
        	@Result(property = "kodeKelurahan", column = "kode_kelurahan"),
        	@Result(property = "namaKelurahan", column = "nama_kelurahan"),
        	@Result(property = "kodePos", column = "kode_pos")
        })
    List<KelurahanModel> getAllKelurahanFromKecamatan(@Param(value = "idKecamatan") int idKecamatan);
    
    @Select("SELECT * FROM kecamatan WHERE id_kota = #{idKota} ORDER BY nama_kecamatan ASC")
    @Results(value = {
        	@Result(id = true, property = "id", column = "id"),
        	@Result(property = "kota", column = "id_kota", one = @One(select = "getKota")),
        	@Result(property = "kodeKecamatan", column = "kode_kecamatan"),
        	@Result(property = "namaKecamatan", column = "nama_kecamatan")
        })
    List<KecamatanModel> getAllKecamatanFromKota(@Param(value = "idKota") int idKota);
    
    @Select("SELECT * FROM kota")
    @Results(value = {
        	@Result(id = true, property = "id", column = "id"),
        	@Result(property = "kodeKota", column = "kode_kota"),
        	@Result(property = "namaKota", column = "nama_kota")
        })
    List<KotaModel> getAllKota();
    
    @Select("SELECT * FROM penduduk WHERE nik LIKE '" + "${nik}" + "%' ORDER BY nik DESC LIMIT 1")
    @Results(value = {
    	@Result(id = true, property = "id", column = "id"),
    	@Result(property = "nik", column = "nik", javaType = String.class),
    	@Result(property = "nama", column = "nama"),
    	@Result(property = "tempatLahir", column = "tempat_lahir"),
    	@Result(property = "tanggalLahir", column = "tanggal_lahir"),
    	@Result(property = "jenisKelamin", column = "jenis_kelamin"),
    	@Result(property = "isWni", column = "is_wni"),
    	@Result(property = "keluarga", column = "id_keluarga", one = @One(select = "getKeluarga")),
    	@Result(property = "agama", column = "agama"),
    	@Result(property = "pekerjaan", column = "pekerjaan"),
    	@Result(property = "statusPerkawinan", column = "status_perkawinan"),
    	@Result(property = "statusKeluarga", column = "status_dalam_keluarga"),
    	@Result(property = "golonganDarah", column = "golongan_darah"),
    	@Result(property = "isWafat", column = "is_wafat")
    })
    PendudukModel getLastID(@Param(value = "nik") String nik);
    
    @Select("SELECT * FROM keluarga WHERE nomor_kk LIKE '" + "${nkk}" + "%' ORDER BY nomor_kk DESC LIMIT 1")
    @Results(value = {
        	@Result(id = true, property = "id", column = "id"),
        	@Result(property = "noKK", column = "nomor_kk"),
        	@Result(property = "alamat", column = "alamat"),
        	@Result(property = "rt", column = "RT"),
        	@Result(property = "rw", column = "RW"),
        	@Result(property = "kelurahan", column = "id_kelurahan", one = @One(select = "getKelurahan")),
        	@Result(property = "isTidakBerlaku", column = "is_tidak_berlaku")
        })
    KeluargaModel getLastKeluargaID(@Param(value = "nkk") String nkk);
    
    @Insert("INSERT INTO penduduk(nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) VALUES (#{nik}, #{nama}, #{tempatLahir}, #{tanggalLahir}, #{jenisKelamin}, #{isWni}, #{keluarga.id}, #{agama}, #{pekerjaan}, #{statusPerkawinan}, #{statusKeluarga}, #{golonganDarah}, #{isWafat})")
    void addPenduduk(PendudukModel penduduk);
    
    @Insert("INSERT INTO keluarga(nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) VALUES (#{noKK}, #{alamat}, #{rt}, #{rw}, #{kelurahan.id}, #{isTidakBerlaku})")
    void addKeluarga(KeluargaModel keluarga);
    
    @Update("UPDATE penduduk SET nik = #{nik}, nama = #{nama}, jenis_kelamin = #{jenisKelamin}, is_wni = #{isWni}, id_keluarga = #{keluarga.id}, agama = #{agama}, pekerjaan = #{pekerjaan}, status_perkawinan = #{statusPerkawinan}, status_dalam_keluarga = #{statusKeluarga}, golongan_darah = #{golonganDarah} WHERE id = #{id}")
    void updatePenduduk(@Param(value = "id") int id, @Param(value = "nik") String nik, @Param(value = "nama") String nama, @Param(value = "jenisKelamin") int jenisKelamin, @Param(value = "isWni") int isWni, @Param(value = "keluarga") KeluargaModel keluarga, @Param(value = "agama") String agama, @Param(value = "pekerjaan") String pekerjaan, @Param("statusPerkawinan") String statusPerkawinan, @Param("statusKeluarga") String statusKeluarga, @Param(value = "golonganDarah") String golonganDarah);
    
    @Update("UPDATE keluarga SET nomor_kk = #{nkk}, alamat = #{alamat}, RT = #{rt}, RW = #{rw}, id_kelurahan = #{kelurahan.id} WHERE id = #{id}")
    void updateKeluarga(@Param(value = "id") int id, @Param(value = "nkk") String nkk, @Param(value = "alamat") String alamat, @Param(value = "rt") String rt, @Param(value = "rw") String rw, @Param(value = "kelurahan") KelurahanModel kelurahan);
    
    @Update("UPDATE penduduk SET is_wafat = 1 WHERE nik = #{nik}")
    void setDeath(@Param(value = "nik") String nik);
    
    @Update("UPDATE keluarga SET is_tidak_berlaku = 1 WHERE id = #{id}")
    void setInactive(@Param(value = "id") int id);
    
    @Select("SELECT p.nik, p.nama, p.jenis_kelamin FROM penduduk as p, keluarga as k, kelurahan as kl WHERE p.id_keluarga = k.id AND k.id_kelurahan = kl.id AND kl.id = #{idKelurahan}")
    @Results(value = {
        	@Result(property = "nik", column = "nik", javaType = String.class),
        	@Result(property = "nama", column = "nama"),
        	@Result(property = "jenisKelamin", column = "jenis_kelamin")
        })
    List<PendudukModel> getPendudukInKelurahan(@Param(value = "idKelurahan") int idKelurahan);
    
    @Select("SELECT p.nik, p.nama, p.tanggal_lahir FROM penduduk as p, keluarga as k, kelurahan as kl WHERE p.id_keluarga = k.id AND k.id_kelurahan = kl.id AND kl.id = #{idKelurahan} ORDER BY p.tanggal_lahir DESC LIMIT 1")
    @Results(value = {
        	@Result(property = "nik", column = "nik", javaType = String.class),
        	@Result(property = "nama", column = "nama"),
        	@Result(property = "tanggalLahir", column = "tanggal_lahir")
        })
    PendudukModel getYoungest(@Param(value = "idKelurahan") int idKelurahan);
    
    @Select("SELECT p.nik, p.nama, p.tanggal_lahir FROM penduduk as p, keluarga as k, kelurahan as kl WHERE p.id_keluarga = k.id AND k.id_kelurahan = kl.id AND kl.id = #{idKelurahan} ORDER BY p.tanggal_lahir ASC LIMIT 1")
    @Results(value = {
        	@Result(property = "nik", column = "nik", javaType = String.class),
        	@Result(property = "nama", column = "nama"),
        	@Result(property = "tanggalLahir", column = "tanggal_lahir")
        })
    PendudukModel getOldest(@Param(value = "idKelurahan") int idKelurahan);
}
