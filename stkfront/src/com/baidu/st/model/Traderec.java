package com.baidu.st.model;

/**
 * Traderec entity. @author MyEclipse Persistence Tools
 */

public class Traderec implements java.io.Serializable {

	// Fields

	private TraderecId id;

	// Constructors

	/** default constructor */
	public Traderec() {
	}

	/** full constructor */
	public Traderec(TraderecId id) {
		this.id = id;
	}

	// Property accessors

	public TraderecId getId() {
		return this.id;
	}

	public void setId(TraderecId id) {
		this.id = id;
	}

}