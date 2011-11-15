package automaticBuyers;

import java.math.BigDecimal;

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
	public void performDuty(Auction a) {
		// TODO Do przetestowania.
		// TODO Tu pojawia siê problem. Poniewa¿ propagacja ceny nastêpuje tylko po post¹pieniu zegara,
		// cena zwiêkszy siê jedynie o: (iloœæ post¹pieñ)*(upBid). Trzeba wiêc umo¿liwiæ dowolne wybieranie upBid.
		// To z kolei skomplikuje gui jeszcze bardziej.
		// TODO Trzeba wiêc gui licytowania przerobiæ tak, by by³y pokazywane oddzielne paski licytowania
		// dla ka¿dego typu aukcji.
		// TODO Nale¿y te¿ zastanowiæ siê, czy nie propagowaæ zmiany ceny oddzielnie od post¹pieñ zegara.
		// propagacja ceny zwi¹zana z post¹pieniem mia³a g³êboki sens dla aukcji holenderskiej.
		// Dla innych typów aukcji takiego sensu ju¿ nie ma. 
		BigDecimal newBid;
		if (a.getMaxBidder() != agent.getName())
			if (a.getPrice().compareTo(this.bid) <= 0) {
				newBid = a.getPrice().add(this.upBid);
				this.agent.propose(a.getAN(), agent.getName(), newBid);
			}
	}

}
