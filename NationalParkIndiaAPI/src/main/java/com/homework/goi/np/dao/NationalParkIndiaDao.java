package com.homework.goi.np.dao;


import java.util.List;

import com.homework.goi.np.entity.NationalParkIndia;

public interface NationalParkIndiaDao {
	
	public boolean saveNationalPark(NationalParkIndia nationalParkIndia);
	
	public boolean updateNationalPark(NationalParkIndia nationalParkIndia);
	
	public List<NationalParkIndia> getAllNationalPark();
	
	public boolean deleteNationalPark(int parkId);
	
	public List<NationalParkIndia> getNationalParkByState(String state);
	
	public List<NationalParkIndia> getNationalParkByYear(int establishmentYear);
	
	public int updateNationalParkList(List<NationalParkIndia> list);

}
