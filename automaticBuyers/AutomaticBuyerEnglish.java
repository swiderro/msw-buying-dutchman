package automaticBuyers;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;
import buyingDutchmanClient.BDC.AuctionTypes;
import auctions.Auction;

public class AutomaticBuyerEnglish extends AutomaticBuyer {

	public AutomaticBuyerEnglish(BuyingDutchmanAgent agent, float bid) {
		super(agent, bid);
		this.type = AuctionTypes.ANGIELSKA;
	}

	@Override
	public void performDuty(Auction auction) {
		// TODO By poprawnie zaimplementowaæ podbijanie, trzeba wyœwietlaæ najwy¿szego oferenta

	}

}
