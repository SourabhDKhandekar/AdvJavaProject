package com.homework.goi.np.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.homework.goi.np.entity.NationalParkIndia;

public interface NationalParkIndiaService {
	
	public boolean saveNationalPark(NationalParkIndia nationalParkIndia);
	
	public boolean updateNationalPark(NationalParkIndia nationalParkIndia);
	
	public List<NationalParkIndia> getAllNationalPark();
	
	public boolean deleteNationalPark(int parkId);
	
	public List<NationalParkIndia> getNationalParkByState(String state);
	
	public List<NationalParkIndia> getNationalParkByYear(int establishmentYear);
	
	public List<NationalParkIndia> sortNationalPark(String sortBy);
	
	public Map<String, String> uploadSheet(CommonsMultipartFile file, HttpSession session);
	
	public List<NationalParkIndia> readExcel(String filepath);
	
	public String exportToExcel(HttpSession session);

}
