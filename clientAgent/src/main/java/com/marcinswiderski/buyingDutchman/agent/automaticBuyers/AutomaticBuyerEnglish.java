package com.marcinswiderski.buyingDutchman.agent.automaticBuyers;

import com.marcinswiderski.buyingDutchman.agent.auctions.Auction;
import com.marcinswiderski.buyingDutchman.agent.client.BDC;
import com.marcinswiderski.buyingDutchman.agent.client.BDC.AuctionTypes;
import com.marcinswiderski.buyingDutchman.agent.client.BuyingDutchmanAgent;

import java.math.BigDecimal;

public class AutomaticBuyerEnglish extends AutomaticBuyer {
	private BigDecimal upBid;
	public AutomaticBuyerEnglish(BuyingDutchmanAgent agent, BigDecimal bid, BigDecimal upBid) {
		super(agent, bid);
		this.type = AuctionTypes.ANGIELSKA;
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
