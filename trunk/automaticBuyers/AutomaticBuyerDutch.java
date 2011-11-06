package automaticBuyers;

import auctions.Auction;
import buyingDutchmanClient.BuyingDutchmanAgent;
import buyingDutchmanClient.BDC.AuctionTypes;

public class AutomaticBuyerDutch extends AutomaticBuyer {

	public AutomaticBuyerDutch(BuyingDutchmanAgent agent, float bid) {
		super(agent, bid);
		this.type = AuctionTypes.HOLENDERSKA;
	}

	@Override
	public void performDuty(Auction auction) {		
		if (auction.getPrice() <= this.bid)
			this.agent.buyNow(auction.getAuctioneer(), auction.getAN());
	}

}
