package com.marcinswiderski.buyingDutchman.agent.auctions;

import com.marcinswiderski.buyingDutchman.agent.client.BDC;

import java.io.Serializable;
import java.math.BigDecimal;


public class Bid  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String bidder;
	private final BigDecimal price;
	
	public Bid(String bidder, BigDecimal price) {
		this.bidder = bidder;
		this.price = price;
	}

	public String getBidder() {
		return bidder;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Object getColumnValue(int col) {
		switch (col) {
		case 0:
			return bidder;
		case 1:
			return price;
		default:
			return null;
		}
	}
	public String toString() {
		return BDC.HISTORY_SEPARATOR + bidder + BDC.HISTORY_SEPARATOR + price.toString();
	}
}