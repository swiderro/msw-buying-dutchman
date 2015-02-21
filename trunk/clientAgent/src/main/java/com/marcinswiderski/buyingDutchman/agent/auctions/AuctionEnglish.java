package com.marcinswiderski.buyingDutchman.agent.auctions;

import java.math.BigDecimal;

import buyingDutchmanClient.BDC;

public class AuctionEnglish extends Auction {

	public AuctionEnglish(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkFinishByOffer() {
		//No offer can finish English auction. Only time finishes it.
		return false;
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {		
		if (bid.compareTo(getPrice()) >= 0)
			if (getBestBidder().equals(BDC.NONESTRING))
				return true;
			else if (bid.compareTo(getPrice()) > 0)
				return true;
			return false;
	}

	@Override
	public boolean propose(String bidder, BigDecimal bid) {
		if (isFinished())
			return false;
		else {
			if (isBestBid(bid)){
				setNewPrice(bid, bidder);
				setBestBid(bid, bidder);
				return true;
			} else
				return false;
		}
	}
}
