package com.marcinswiderski.buyingDutchman.agent.automaticBuyers;

import com.marcinswiderski.buyingDutchman.agent.auctions.Auction;
import com.marcinswiderski.buyingDutchman.agent.client.BDC;
import com.marcinswiderski.buyingDutchman.agent.client.BuyingDutchmanAgent;

import java.math.BigDecimal;

public abstract class AutomaticBuyer implements AutomaticBuyerInterface {

	protected BigDecimal bid;
	protected BuyingDutchmanAgent agent;
	protected BDC.AuctionTypes type;
	
	public AutomaticBuyer(BuyingDutchmanAgent agent, BigDecimal bid) {
		this.agent = agent;
		this.bid = bid;
	}
	public abstract boolean performDuty(Auction auction);
}
