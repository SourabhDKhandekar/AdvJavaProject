package com.homework.goi.np.sort;

import java.util.Comparator;

import com.homework.goi.np.entity.NationalParkIndia;

public class NationalParkYearComparator implements Comparator<NationalParkIndia> {

	@Override
	public int compare(NationalParkIndia npi1, NationalParkIndia npi2) {

		if (npi1.getEstablishmentYear() == npi2.getEstablishmentYear()) {
			return 0;
		} else if (npi1.getEstablishmentYear() > npi2.getEstablishmentYear()) {
			return 1;
		} else
			return -1;
	}
}
