package com.homework.goi.np.validation;

import com.homework.goi.np.entity.NationalParkIndia;

public class ValidateObject {

	public static boolean validateNationalPark(NationalParkIndia npi) {
		boolean isValid;

	 
		if (0 <= npi.getParkId() || 0 <= npi.getEstablishmentYear() || 0 <= npi.getParkAreaInSqkm()) {
			isValid = true;
		} else if (null == npi.getParkName() || null == npi.getState() || npi.getParkName().equals("")
				|| npi.getState().equals("")) {
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
	}
}
