package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService
{
    @Autowired
    private PendudukMapper pendudukMapper;


    @Override
    public PendudukModel getPenduduk (String nik)
    {
        log.info ("select penduduk with nik {}", nik);
        return pendudukMapper.getPenduduk (nik);
    }
    
    @Override
    public KeluargaModel getKeluarga (int id)
    {
        log.info ("select keluarga with id {}", id);
        return pendudukMapper.getKeluarga (id);
    }
    
    @Override
    public KeluargaModel getKeluargaFromNKK (String nkk)
    {
        log.info ("select keluarga with nkk {}", nkk);
        return pendudukMapper.getKeluargaFromNKK (nkk);
    }
    
    @Override
    public List<PendudukModel> getAnggotaKeluarga (int id)
    {
        log.info ("select keluarga with id {}", id);
        return pendudukMapper.getAnggotaKeluarga (id);
    }
    
    @Override
    public KelurahanModel getKelurahan (int id)
    {
        log.info ("select kelurahan with id {}", id);
        return pendudukMapper.getKelurahan (id);
    }
    
    @Override
    public List<KelurahanModel> getAllKelurahan ()
    {
        log.info ("select all kelurahan");
        return pendudukMapper.getAllKelurahan ();
    }
    
    @Override
    public KecamatanModel getKecamatan (int id)
    {
        log.info ("select kecamatan with id {}", id);
        return pendudukMapper.getKecamatan (id);
    }
    
    @Override
    public KotaModel getKota (int id)
    {
        log.info ("select kota with id {}", id);
        return pendudukMapper.getKota (id);
    }
    
    @Override
    public PendudukModel getLastID (String nik)
    {
        log.info ("select penduduk with nik like {}", nik);
        return pendudukMapper.getLastID (nik);
    }
    
    @Override
    public KeluargaModel getLastKeluargaID (String nkk)
    {
        log.info ("select penduduk with nkk like {}", nkk);
        return pendudukMapper.getLastKeluargaID (nkk);
    }
    
    @Override
    public void addPenduduk (PendudukModel penduduk)
    {
        log.info ("insert new penduduk");
        pendudukMapper.addPenduduk(penduduk);
    }
    
    @Override
    public void addKeluarga (KeluargaModel keluarga)
    {
        log.info ("insert new keluarga");
        pendudukMapper.addKeluarga(keluarga);
    }
    
    @Override
    public List<KotaModel> getAllKota ()
    {
        log.info ("get all kota");
        return pendudukMapper.getAllKota();
    }
    
   @Override
   public void updatePenduduk (int id, String nik, String nama, int jenisKelamin, int isWni, KeluargaModel keluarga, String agama, String pekerjaan, String statusPerkawinan, String statusKeluarga, String golonganDarah)
   {
    	log.info("update penduduk with id {}", id);
    	pendudukMapper.updatePenduduk(id, nik, nama, jenisKelamin, isWni, keluarga, agama, pekerjaan, statusPerkawinan, statusKeluarga, golonganDarah);
   }
   
   @Override
   public void updateKeluarga(int id, String nkk, String alamat, String rt, String rw, KelurahanModel kelurahan) {
	   log.info("update keluarga with id {}", id);
	   pendudukMapper.updateKeluarga(id, nkk, alamat, rt, rw, kelurahan);
   }
   
   @Override
   public void setDeath(String nik) {
	   log.info("set penduduk with nik {} to death", nik);
	   pendudukMapper.setDeath(nik);
   }
   
   @Override
   public void setInactive(int id) {
	   log.info("set keluarga with id {} to inactive", id);
	   pendudukMapper.setInactive(id);
   }
   
   @Override
   public List<KelurahanModel> getAllKelurahanFromKecamatan(int idKecamatan) {
	   log.info("get kelurahan from kecamatan with id {}", idKecamatan);
	   return pendudukMapper.getAllKelurahanFromKecamatan(idKecamatan);
   }
   
   @Override
   public List<KecamatanModel> getAllKecamatanFromKota(int idKota) {
	   log.info("get kecamatan from kota with id {}", idKota);
	   return pendudukMapper.getAllKecamatanFromKota(idKota);
   }
   
   @Override
   public List<PendudukModel> getPendudukInKelurahan(int idKelurahan) {
	   log.info("get all penduduk from kelurahan with id {}", idKelurahan);
	   return pendudukMapper.getPendudukInKelurahan(idKelurahan);
   }
   
   @Override
   public PendudukModel getYoungest(int idKelurahan) {
	   log.info("get youngest from kelurahan with id {}", idKelurahan);
	   return pendudukMapper.getYoungest(idKelurahan);
   }
   
   @Override
   public PendudukModel getOldest(int idKelurahan) {
	   log.info("get oldest from kelurahan with id {}", idKelurahan);
	   return pendudukMapper.getOldest(idKelurahan);
   }
}
