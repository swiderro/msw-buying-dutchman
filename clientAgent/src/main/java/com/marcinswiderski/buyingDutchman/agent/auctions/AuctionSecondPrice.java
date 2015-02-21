package com.marcinswiderski.buyingDutchman.agent.auctions;

import com.marcinswiderski.buyingDutchman.agent.client.BDC;

import java.math.BigDecimal;

public class AuctionSecondPrice extends Auction {
	public AuctionSecondPrice(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkFinishByOffer() {
		return false;		
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {
		if (getBestBid() == null) {
			return true;
		} else {
			if (bid.compareTo(getBestBid()) > 0)
				return true;
			else
				return false;
		}
	}

	protected void setNewPrice(BigDecimal price) {
		if ( getBestBid() != null) {
			if (getBestBid().compareTo(price) < 0) {
				this.price = getBestBid().add(BigDecimal.valueOf(BDC.Grosz));
			} else if (getBestBid().compareTo(price) == 0) {
				this.price = getBestBid();
			} else {
				this.price = price.add(BigDecimal.valueOf(BDC.Grosz));
			}
		}
		String [] p = this.price.toPlainString().split(BDC.REGEXFPOINT);
		if (p[1].length() >= 2) {
			getAd().setPriceDec(p[1].substring(0, 2));
			getAd().setPriceInt(p[0]);
		} else {
			getAd().setPriceDec(p[1]+"0");
			getAd().setPriceInt(p[0]);
		}		 
	}

	@Override
	public boolean propose(String bidder, BigDecimal bid) {
		if (isFinished())
			return false;
		else if (this.price.compareTo(bid) <= 0) {
			setNewPrice(bid);
			if (isBestBid(bid)){
				setBestBid(bid, bidder);
				return true;
			} else
				return false;
			}
		else
			return false;
	}
}
