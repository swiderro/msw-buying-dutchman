package automaticBuyers;

import java.math.BigDecimal;

import auctions.Auction;
import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;

public abstract class AutomaticBuyer implements AutomaticBuyerInterface {

	protected BigDecimal bid;
	protected BuyingDutchmanAgent agent;
	protected BDC.AuctionTypes type;
	
	public AutomaticBuyer(BuyingDutchmanAgent agent, BigDecimal bid) {
		this.agent = agent;
		this.bid = bid;
	}
	public abstract void performDuty(Auction auction);
}
