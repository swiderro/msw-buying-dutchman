package automaticBuyers;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;

public abstract class AutomaticBuyer implements AutomaticBuyerInterface {

	protected float bid;
	protected BuyingDutchmanAgent agent;
	protected BDC.AuctionTypes type;
	
	public AutomaticBuyer(BuyingDutchmanAgent agent, float bid) {
		this.agent = agent;
		this.bid = bid;
	}
	
}
