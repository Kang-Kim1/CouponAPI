package com.restapi.couponapi.dto;

import java.util.Date;

public class CouponDTO {
	private int c_UID;
	private String c_couponCode;
	private boolean c_isUsed;
	private String c_assignedTo;
	private Date c_expDate;
	private Date c_regiDate;
	
	public int getC_UID() {
		return c_UID;
	}
	public void setC_UID(int c_UID) {
		this.c_UID = c_UID;
	}
	public String getC_couponCode() {
		return c_couponCode;
	}
	public void setC_couponCode(String c_couponCode) {
		this.c_couponCode = c_couponCode;
	}
	public boolean isC_isUsed() {
		return c_isUsed;
	}
	public void setC_isUsed(boolean c_isUsed) {
		this.c_isUsed = c_isUsed;
	}
	public String getC_assignedTo() {
		return c_assignedTo;
	}
	public void setC_assignedTo(String c_assignedTo) {
		this.c_assignedTo = c_assignedTo;
	}
	public Date getC_expDate() {
		return c_expDate;
	}
	public void setC_expDate(Date c_expDate) {
		this.c_expDate = c_expDate;
	}
	public Date getC_regiDate() {
		return c_regiDate;
	}
	public void setC_regiDate(Date c_regiDate) {
		this.c_regiDate = c_regiDate;
	}
}
