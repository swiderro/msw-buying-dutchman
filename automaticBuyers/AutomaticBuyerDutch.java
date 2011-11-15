package automaticBuyers;

import java.math.BigDecimal;

import auctions.Auction;
import buyingDutchmanClient.BuyingDutchmanAgent;
import buyingDutchmanClient.BDC.AuctionTypes;

public class AutomaticBuyerDutch extends AutomaticBuyer {

	public AutomaticBuyerDutch(BuyingDutchmanAgent agent, BigDecimal bid) {
		super(agent, bid);
		this.type = AuctionTypes.HOLENDERSKA;
	}

	@Override
	public void performDuty(Auction auction) {		
		if (auction.getPrice().compareTo(this.bid) <= 0)
			this.agent.buyNow(auction.getAuctioneer(), auction.getAN());
	}

}
