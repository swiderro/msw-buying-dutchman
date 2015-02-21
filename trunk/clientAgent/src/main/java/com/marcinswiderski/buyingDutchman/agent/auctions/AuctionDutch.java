package com.marcinswiderski.buyingDutchman.agent.auctions;

import com.marcinswiderski.buyingDutchman.agent.client.BDC;

import java.math.BigDecimal;

public class AuctionDutch extends Auction {
	public AuctionDutch(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	private static final long serialVersionUID = 1L;	

	@Override
	protected boolean checkFinishByOffer() {
		//computing new price after thick
		BigDecimal newPrice = computePrice();
		//ko�czy, je�li jest oferta
		if ( getBestBid() != null && getBestBid().compareTo(newPrice) >= 0
			//dodatkowe zabezpieczenie. na razie wy��czam
//			|| getEndPrice() > newPrice
		) {
			setFinished(true);
			return true;
		} else		
			return false;
	}
	@Override
	public boolean buyNow(String bestBidder) {
		if (isFinished())
			return false;
		else {
			bestBidInt = getPriceInt();
			bestBidDec = getPriceDec();
			setBestBid(new BigDecimal(bestBidInt+BDC.FPOINT+bestBidDec), bestBidder);
			setFinished(true);
			return true;
		}
	}
	@Override
	protected void performAuctionTick() {
		lowerPrice(getPrice().subtract(getReductionStep()));
	}

	private void lowerPrice(BigDecimal bid) {
		if (getPrice().compareTo(bid) > 0)
			setNewPrice(bid);		
	}
	@Override
	protected boolean isBestBid(BigDecimal bid) {
		if (bid.compareTo(getBestBid()) > 0)
			return true;
		else
			return false;
	}

}
