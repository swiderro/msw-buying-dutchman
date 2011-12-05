package automaticBuyers;

import java.math.BigDecimal;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;
import buyingDutchmanClient.BDC.AuctionTypes;
import auctions.Auction;

public class AutomaticBuyerEnglish extends AutomaticBuyer {
	private BigDecimal upBid =  BigDecimal.valueOf(0.5);
	public AutomaticBuyerEnglish(BuyingDutchmanAgent agent, BigDecimal bid) {
		super(agent, bid);
		this.type = AuctionTypes.ANGIELSKA;
	}

	@Override
	public boolean performDuty(Auction a) {
		// TODO Trzeba umo¿liwiæ dowolne wybieranie upBid. To z kolei skomplikuje gui jeszcze bardziej.
		// TODO Trzeba wiêc gui licytowania przerobiæ tak, by by³y pokazywane oddzielne paski licytowania dla ka¿dego typu aukcji.
		if (!a.getBestBidder().equals(agent.getLocalName()))
			if (a.getPrice().compareTo(this.bid) <= 0) {
				BigDecimal newBid;
				if (a.getBestBidder().equals(BDC.NONESTRING))
					newBid = a.getPrice();
				else {
					newBid = a.getPrice().add(this.upBid);
					if (newBid.compareTo(this.bid) > 0)
						newBid = this.bid;
				}
				this.agent.propose(a.getAN(), a.getAuctioneer(), newBid);
			} else {
				return false;
			}
		return true;
	}
}
