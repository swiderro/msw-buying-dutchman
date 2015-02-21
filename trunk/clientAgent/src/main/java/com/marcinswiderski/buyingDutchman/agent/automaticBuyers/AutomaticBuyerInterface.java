package com.marcinswiderski.buyingDutchman.agent.automaticBuyers;

import com.marcinswiderski.buyingDutchman.agent.auctions.Auction;

public interface AutomaticBuyerInterface {

	public boolean performDuty(Auction auction);
	
}
