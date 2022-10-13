package com.homework.goi.np.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class NationalParkIndia {

	@Id
	@Min(0)
	private int parkId;

	@NotNull(message = "Park name required")
	private String parkName;

	@NotNull(message = "State name required")
	private String state;

	@NotNull(message = "Year required")
	private int establishmentYear;

	@Min(0)
	private double parkAreaInSqkm;

	public NationalParkIndia() {

	}

	public NationalParkIndia(@Min(0)int parkId, @NotNull(message = "Park name required") String parkName,
			@NotNull(message = "State name required") String state,
			@NotNull(message = "Year required") int establishmentYear, @Min(0) double parkAreaInSqkm) {
		super();
		this.parkId = parkId;
		this.parkName = parkName;
		this.state = state;
		this.establishmentYear = establishmentYear;
		this.parkAreaInSqkm = parkAreaInSqkm;
	}

	public int getParkId() {
		return parkId;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getEstablishmentYear() {
		return establishmentYear;
	}

	public void setEstablishmentYear(int establishmentYear) {
		this.establishmentYear = establishmentYear;
	}

	public double getParkAreaInSqkm() {
		return parkAreaInSqkm;
	}

	public void setParkAreaInSqkm(double parkAreaInSqkm) {
		this.parkAreaInSqkm = parkAreaInSqkm;
	}

	@Override
	public String toString() {
		return "NationalParkIndia [parkId=" + parkId + ", parkName=" + parkName + ", state=" + state
				+ ", establishmentYear=" + establishmentYear + ", parkAreaInSqkm=" + parkAreaInSqkm + "]";
	}

}
