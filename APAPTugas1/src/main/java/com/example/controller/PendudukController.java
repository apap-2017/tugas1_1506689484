package com.example.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.PendudukService;

@Controller
public class PendudukController
{
    @Autowired
    PendudukService pendudukDAO;

    @RequestMapping("/")
    public String index (Model model)
    {
    	model.addAttribute("title", "Home | SI Kependudukan");
        return "index";
    }
    
    @RequestMapping("/penduduk")
    public String penduduk(Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "nik", required = true) String nik, @ModelAttribute("redirected") String redirected) {
    	PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
    	if (penduduk == null) {
    		return "not-found";
    	}
    	penduduk.setTanggalLahir(dateNormalize(penduduk.getTanggalLahir()));
    	if (redirected.equals("true")) {
    		model.addAttribute("suksesMati", true);
    	}
    	model.addAttribute("penduduk", penduduk);
    	model.addAttribute("title", "Data Penduduk " + nik + " | SI Kependudukan");
    	return "penduduk";
    }
    
    @RequestMapping("/keluarga")
    public String keluarga(Model model, @RequestParam(value = "nkk", required = true) String nkk) {
    	KeluargaModel keluarga = pendudukDAO.getKeluargaFromNKK(nkk);
    	if (keluarga == null) {
    		return "not-found";
    	}
    	model.addAttribute("keluarga", keluarga);
    	model.addAttribute("title", "Data Keluarga " + nkk + " | SI Kependudukan");
    	return "keluarga";
    }
    
    @RequestMapping("/penduduk/tambah")
    public String tambahPenduduk(Model model) {
    	model.addAttribute("title", "Tambah Penduduk | SI Kependudukan");
    	return "tambah-penduduk";
    }
    
    @RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
    public String tambahPendudukPost(Model model,
    		@RequestParam(value = "nama", required = true) String nama,
    		@RequestParam(value = "tempatLahir", required = true) String tempatLahir,
    		@RequestParam(value = "tanggalLahir", required = true) String tanggalLahir,
    		@RequestParam(value = "jenisKelamin", required = true) int jenisKelamin,
    		@RequestParam(value = "golonganDarah", required = true) String golonganDarah,
    		@RequestParam(value = "agama", required = true) String agama,
    		@RequestParam(value = "statusPerkawinan", required = true) String statusPerkawinan,
    		@RequestParam(value = "pekerjaan", required = true) String pekerjaan,
    		@RequestParam(value = "isWni", required = true) int isWni,
    		@RequestParam(value = "isWafat", required = true) int isWafat,
    		@RequestParam(value = "keluarga", required = true) int keluarga,
    		@RequestParam(value = "statusKeluarga", required = true) String statusKeluarga
    	)
    {
    	PendudukModel pendudukBaru = new PendudukModel(0, null, nama, tempatLahir, tanggalLahir, jenisKelamin, isWni, pendudukDAO.getKeluarga(keluarga), agama, pekerjaan, statusPerkawinan, statusKeluarga, golonganDarah, isWafat);
    	String newNIK = generateNIK(keluarga, tanggalLahir, jenisKelamin);
    	pendudukBaru.setNik(newNIK);
    	pendudukDAO.addPenduduk(pendudukBaru);
    	model.addAttribute("title", "Sukses Tambah Penduduk | SI Kependudukan");
    	model.addAttribute("nik", newNIK);
    	return "tambah-penduduk-sukses";
    }
    
    @RequestMapping("/keluarga/tambah")
    public String tambahKeluarga(Model model) {
    	List<KelurahanModel> kelurahan = pendudukDAO.getAllKelurahan();
    	model.addAttribute("listKelurahan", kelurahan);
    	model.addAttribute("title", "Tambah Keluarga | SI Kependudukan");
    	return "tambah-keluarga";
    }
    
    @RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
    public String tambahKeluargaPost(Model model,
    		@RequestParam(value = "alamat") String alamat,
    		@RequestParam(value = "rt") String rt,
    		@RequestParam(value = "rw") String rw,
    		@RequestParam(value = "kelurahan") int kelurahan
    	)
    {
    	KeluargaModel newKeluarga = new KeluargaModel(0, null, alamat, rt, rw, pendudukDAO.getKelurahan(kelurahan), 0, null);
    	String noKK = generateNKK(kelurahan);
    	newKeluarga.setNoKK(noKK);
    	pendudukDAO.addKeluarga(newKeluarga);
    	model.addAttribute("title", "Sukses Tambah Keluarga | SI Kependudukan");
    	model.addAttribute("nkk", noKK);
    	return "tambah-keluarga-sukses";
    }
    
    @RequestMapping("/penduduk/ubah/{nik}")
    public String updatePenduduk(Model model, @PathVariable(value = "nik") String nik) {
    	PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
    	if (penduduk == null) {
    		return "not-found";
    	}
    	model.addAttribute("penduduk", penduduk);
    	model.addAttribute("pendudukNik", nik);
    	model.addAttribute("title", "Ubah Data Penduduk | SI Kependudukan");
    	return "update-penduduk";
    }
    
    @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String updatePendudukPost(Model model, @PathVariable(value = "nik") String nik,
    		@RequestParam(value = "id") int id,
    		@RequestParam(value = "nama", required = true) String nama,
    		@RequestParam(value = "tempatLahir", required = true) String tempatLahir,
    		@RequestParam(value = "tanggalLahir", required = true) String tanggalLahir,
    		@RequestParam(value = "jenisKelamin", required = true) int jenisKelamin,
    		@RequestParam(value = "golonganDarah", required = true) String golonganDarah,
    		@RequestParam(value = "agama", required = true) String agama,
    		@RequestParam(value = "statusPerkawinan", required = true) String statusPerkawinan,
    		@RequestParam(value = "pekerjaan", required = true) String pekerjaan,
    		@RequestParam(value = "isWni", required = true) int isWni,
    		@RequestParam(value = "keluarga", required = true) int keluarga,
    		@RequestParam(value = "statusKeluarga", required = true) String statusKeluarga
    	)
    {
    	PendudukModel oldPenduduk = pendudukDAO.getPenduduk(nik);
    	String newNIK = nik;
    	if (oldPenduduk.getKeluarga().getId() != keluarga) {
    		newNIK = generateNIK(keluarga, tanggalLahir, jenisKelamin);
    	}
    	pendudukDAO.updatePenduduk(id, newNIK, nama, jenisKelamin, isWni, pendudukDAO.getKeluarga(keluarga), agama, pekerjaan, statusPerkawinan, statusKeluarga, golonganDarah);
    	model.addAttribute("title", "Sukses Ubah Penduduk | SI Kependudukan");
    	model.addAttribute("nik", nik);
    	return "update-penduduk-sukses";
    }
    
    @RequestMapping("/keluarga/ubah/{nkk}")
    public String updateKeluarga(Model model, @PathVariable(value = "nkk") String nkk) {
    	KeluargaModel keluarga = pendudukDAO.getKeluargaFromNKK(nkk);
    	if (keluarga == null) {
    		return "not-found";
    	}
    	List<KelurahanModel> kelurahan = pendudukDAO.getAllKelurahan();
    	model.addAttribute("listKelurahan", kelurahan);
    	model.addAttribute("keluargaNKK", nkk);
    	model.addAttribute("keluarga", keluarga);
    	model.addAttribute("title", "Ubah Data Keluarga | SI Kependudukan");
    	return "update-keluarga";
    }
    
    @RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String updateKeluargaPost(Model model, @PathVariable(value = "nkk") String nkk,
    		@RequestParam(value = "id") int id,
    		@RequestParam(value = "alamat") String alamat,
    		@RequestParam(value = "rt") String rt,
    		@RequestParam(value = "rw") String rw,
    		@RequestParam(value = "kelurahan") int kelurahan
    	)
    {
    	KeluargaModel oldKeluarga = pendudukDAO.getKeluargaFromNKK(nkk);
    	String newNKK = nkk;
    	if (oldKeluarga.getKelurahan().getId() != kelurahan) {
    		newNKK = generateNKK(kelurahan);
    	}
    	pendudukDAO.updateKeluarga(id, newNKK, alamat, rt, rw, pendudukDAO.getKelurahan(kelurahan));
    	model.addAttribute("title", "Sukses Ubah Keluarga | SI Kependudukan");
    	model.addAttribute("nkk", nkk);
    	return "update-keluarga-sukses";
    }
    
    @RequestMapping(value = "penduduk/mati", method = RequestMethod.POST)
    public RedirectView nonactivePenduduk(@RequestParam(value = "nik", required = true) String nik, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws IOException {
    	PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
    	List<PendudukModel> anggotaKeluarga = penduduk.getKeluarga().getAnggota();
    	pendudukDAO.setDeath(nik);
    	int countAlive = 0;
    	for (PendudukModel p : anggotaKeluarga) {
    		if (p.getIsWafat() == 0) {
    			countAlive++;
    		}
    	}
    	if (countAlive == 0) {
    		pendudukDAO.setInactive(penduduk.getKeluarga().getId());
    	}
    	redirectAttributes.addFlashAttribute("redirected", "true");
    	return new RedirectView("/penduduk?nik=" + nik, true);
    }
    
    @RequestMapping("/penduduk/cari")
    public String lihatDataPenduduk(Model model, @RequestParam(value = "kt", required = false) String idKota, @RequestParam(value = "kc", required = false) String idKecamatan, @RequestParam(value = "kl", required = false) String idKelurahan) {
    	model.addAttribute("title", "Cari Penduduk | SI Kependudukan");
    	if (idKota == null) {
    		List<KotaModel> kota = pendudukDAO.getAllKota();
    		model.addAttribute("listKota", kota);
    		return "cari-penduduk-kota";
    	}
    	else if (idKecamatan == null && idKelurahan == null) {
    		int id = Integer.parseInt(idKota);
    		KotaModel kota = pendudukDAO.getKota(id);
    		List<KecamatanModel> kecamatan = pendudukDAO.getAllKecamatanFromKota(id);
    		model.addAttribute("kota", kota);
    		model.addAttribute("listKecamatan", kecamatan);
    		return "cari-penduduk-kecamatan";
    	}
    	else if (idKelurahan == null) {
    		int id = Integer.parseInt(idKota);
    		int id2 = Integer.parseInt(idKecamatan);
    		KotaModel kota = pendudukDAO.getKota(id);
    		KecamatanModel kecamatan = pendudukDAO.getKecamatan(id2);
    		List<KelurahanModel> kelurahan = pendudukDAO.getAllKelurahanFromKecamatan(id2);
    		model.addAttribute("kota", kota);
    		model.addAttribute("kecamatan", kecamatan);
    		model.addAttribute("listKelurahan", kelurahan);
    		return "cari-penduduk-kelurahan";
    	}
    	else if (idKota != null && idKecamatan != null && idKelurahan != null) {
    		int id = Integer.parseInt(idKota);
    		int id2 = Integer.parseInt(idKecamatan);
    		int id3 = Integer.parseInt(idKelurahan);
    		KotaModel kota = pendudukDAO.getKota(id);
    		KecamatanModel kecamatan = pendudukDAO.getKecamatan(id2);
    		KelurahanModel kelurahan = pendudukDAO.getKelurahan(id3);
    		List<PendudukModel> penduduk = pendudukDAO.getPendudukInKelurahan(id3);
    		PendudukModel youngest = pendudukDAO.getYoungest(id3);
    		PendudukModel oldest = pendudukDAO.getOldest(id3);
    		model.addAttribute("youngest", youngest);
    		model.addAttribute("oldest", oldest);
    		model.addAttribute("kota", kota);
    		model.addAttribute("kecamatan", kecamatan);
    		model.addAttribute("kelurahan", kelurahan);
    		model.addAttribute("listPenduduk", penduduk);
    		return "data-penduduk";
    	}
    	else {
    		return "not-found";
    	}
    }
    
    /**
     * Helper Methods
     *  
     * Method that handles some operations in controller.
     */
    
    private String dateNormalize(String date) {
    	String[] splitDate = date.split("-");
    	String[] months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    	String month = months[Integer.parseInt(splitDate[1]) - 1];
    	return (splitDate[2] + " " + month + " " + splitDate[0]);
    }
    
    private String generateNIK(int idKeluarga, String tanggalLahir, int gender) {
    	KeluargaModel keluarga = pendudukDAO.getKeluarga(idKeluarga);
    	StringBuilder nik = new StringBuilder();
    	String kodeKecamatan = keluarga.getKelurahan().getKecamatan().getKodeKecamatan();
    	String nikPart1 = kodeKecamatan.substring(0, kodeKecamatan.length() - 1);
    	String[] birthDateSplit = tanggalLahir.split("-");
    	String nikPart2;
    	if (gender == 1) {
    		nikPart2 = Integer.toString(Integer.parseInt(birthDateSplit[2]) + 40) + birthDateSplit[1] + birthDateSplit[0].substring(2, 4);
    	}
    	else {
    		nikPart2 = birthDateSplit[2] + birthDateSplit[1] + birthDateSplit[0].substring(2, 4);
    	}
    	nik.append(nikPart1);
    	nik.append(nikPart2);
    	PendudukModel lastID = pendudukDAO.getLastID(nik.toString());
    	String nikPart3;
    	if (lastID == null) {
    		nikPart3 = "0001";
    	}
    	else {
    		int lastDigit =  Integer.parseInt(lastID.getNik().substring(12, 16));
    		nikPart3 = String.format("%04d", lastDigit + 1);
    	}
    	nik.append(nikPart3);
    	return nik.toString();
    }
    
    private String generateNKK(int idKelurahan) {
    	KelurahanModel kelurahan = pendudukDAO.getKelurahan(idKelurahan);
    	StringBuilder nkk = new StringBuilder();
    	String kodeKecamatan = kelurahan.getKecamatan().getKodeKecamatan();
    	String nkkPart1 = kodeKecamatan.substring(0, kodeKecamatan.length() - 1);
    	DateFormat df = new SimpleDateFormat("dd-MM-yy");
    	Date dateObject = new Date();
    	String currDate = df.format(dateObject);
    	String[] currDateSplit = currDate.split("-");
    	String nkkPart2 = currDateSplit[0] + currDateSplit[1] + currDateSplit[2];
    	nkk.append(nkkPart1);
    	nkk.append(nkkPart2);
    	KeluargaModel lastID = pendudukDAO.getLastKeluargaID(nkk.toString());
    	String nkkPart3;
    	if (lastID == null) {
    		nkkPart3 = "0001";
    	}
    	else {
    		nkkPart3 = String.format("%04d", Integer.parseInt(lastID.getNoKK().substring(12, 16)) + 1);
    	}
    	nkk.append(nkkPart3);
    	return nkk.toString();
    }
}
