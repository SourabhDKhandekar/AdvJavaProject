package com.homework.goi.np.sort;

import java.util.Comparator;

import com.homework.goi.np.entity.NationalParkIndia;

public class NationalParkStateComparator implements Comparator<NationalParkIndia> {

	@Override
	public int compare(NationalParkIndia npi1, NationalParkIndia npi2) {
		
		return npi1.getState().compareTo(npi2.getState());
	}

}
