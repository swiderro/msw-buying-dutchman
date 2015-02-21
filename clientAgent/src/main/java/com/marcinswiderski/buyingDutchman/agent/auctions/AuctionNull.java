package com.marcinswiderski.buyingDutchman.agent.auctions;

import java.math.BigDecimal;

public class AuctionNull extends Auction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuctionNull(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	@Override
	protected boolean checkFinishByOffer() {
		return true;
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {
		return false;
	}

	@Override
	public boolean propose(String bidder, BigDecimal bid) {
		return false;
	}

}
