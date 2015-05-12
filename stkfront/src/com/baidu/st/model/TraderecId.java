package com.baidu.st.model;

import java.util.Date;

/**
 * TraderecId entity. @author MyEclipse Persistence Tools
 */

public class TraderecId implements java.io.Serializable {

	// Fields

	private Date time;
	private String code;
	private Integer inner;
	private Integer outter;
	private float curprice;

	// Constructors

	/** default constructor */
	public TraderecId() {
	}

	/** full constructor */
	public TraderecId(Date time, String code, Integer inner, Integer outter,
			float curprice) {
		this.time = time;
		this.code = code;
		this.inner = inner;
		this.outter = outter;
		this.curprice = curprice;
	}

	// Property accessors

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getInner() {
		return this.inner;
	}

	public void setInner(Integer inner) {
		this.inner = inner;
	}

	public Integer getOutter() {
		return this.outter;
	}

	public void setOutter(Integer outter) {
		this.outter = outter;
	}

	public float getCurprice() {
		return this.curprice;
	}

	public void setCurprice(float curprice) {
		this.curprice = curprice;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TraderecId))
			return false;
		TraderecId castOther = (TraderecId) other;

		return ((this.getTime() == castOther.getTime()) || (this.getTime() != null
				&& castOther.getTime() != null && this.getTime().equals(
				castOther.getTime())))
				&& ((this.getCode() == castOther.getCode()) || (this.getCode() != null
						&& castOther.getCode() != null && this.getCode()
						.equals(castOther.getCode())))
				&& ((this.getInner() == castOther.getInner()) || (this
						.getInner() != null
						&& castOther.getInner() != null && this.getInner()
						.equals(castOther.getInner())))
				&& ((this.getOutter() == castOther.getOutter()) || (this
						.getOutter() != null
						&& castOther.getOutter() != null && this.getOutter()
						.equals(castOther.getOutter())))
				&& (this.getCurprice() == castOther.getCurprice());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTime() == null ? 0 : this.getTime().hashCode());
		result = 37 * result
				+ (getCode() == null ? 0 : this.getCode().hashCode());
		result = 37 * result
				+ (getInner() == null ? 0 : this.getInner().hashCode());
		result = 37 * result
				+ (getOutter() == null ? 0 : this.getOutter().hashCode());
		result = 37 * result + (int) this.getCurprice();
		return result;
	}

}