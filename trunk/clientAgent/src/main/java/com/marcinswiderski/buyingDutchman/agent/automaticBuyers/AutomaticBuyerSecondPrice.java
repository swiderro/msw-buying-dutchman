package com.marcinswiderski.buyingDutchman.agent.automaticBuyers;

import java.math.BigDecimal;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;
import buyingDutchmanClient.BDC.AuctionTypes;
import auctions.Auction;

public class AutomaticBuyerSecondPrice extends AutomaticBuyer {
	private BigDecimal upBid;
	
	public AutomaticBuyerSecondPrice(BuyingDutchmanAgent agent, BigDecimal bid, BigDecimal upBid) {
		super(agent, bid);
		this.type = AuctionTypes.DRUGIEJ_CENY;
		this.upBid = upBid;
	}

	@Override
	public boolean performDuty(Auction a) {
		BigDecimal newBid;
		if (a.getPrice().compareTo(this.bid) > 0) {
			return false;
		} else if (a.getPrice().compareTo(this.bid) == 0) {
			if (a.getBestBidder().equals(BDC.NONESTRING)) {
				newBid = this.bid;
				this.agent.propose(a.getAN(), a.getAuctioneer(), newBid);
				return true;
			} else {
				return false;
			}
		} else if (a.getPrice().compareTo(this.bid) < 0) {
			if (!a.getBestBidder().equals(agent.getLocalName())) {				
				if (a.getBestBidder().equals(BDC.NONESTRING)) {
					newBid = a.getPrice();
				} else {
					newBid = a.getPrice().add(this.upBid);
				}
				if (newBid.compareTo(this.bid) <= 0) {
					this.agent.propose(a.getAN(), a.getAuctioneer(), newBid);
				} else {
					// newBid jest wy�szy ni� zadeklarowana kwota = last shot
					this.agent.propose(a.getAN(), a.getAuctioneer(), this.bid);
				}
				return true;
			} else {
				return true;
			}
		} else {
			//nie powinno si� zdarzy�
			return false;
		}
	}

}
