package com.marcinswiderski.buyingDutchman.agent.automaticBuyers;

import com.marcinswiderski.buyingDutchman.agent.auctions.Auction;
import com.marcinswiderski.buyingDutchman.agent.client.BDC;
import com.marcinswiderski.buyingDutchman.agent.client.BuyingDutchmanAgent;

import java.math.BigDecimal;

public class AutomaticBuyerDutch extends AutomaticBuyer {

	public AutomaticBuyerDutch(BuyingDutchmanAgent agent, BigDecimal bid) {
		super(agent, bid);
		this.type = BDC.AuctionTypes.HOLENDERSKA;
	}

	@Override
	public boolean performDuty(Auction auction) {
		if (auction.getPrice().compareTo(this.bid) <= 0) {
			this.agent.buyNow(auction.getAuctioneer(), auction.getAN());
		}
		return true;
	}

}
