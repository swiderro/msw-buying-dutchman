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
		// TODO Tu pojawia si� problem. Poniewa� propagacja ceny nast�puje tylko po post�pieniu zegara,
		// cena zwi�kszy si� jedynie o: (ilo�� post�pie�)*(upBid). Trzeba wi�c umo�liwi� dowolne wybieranie upBid.
		// To z kolei skomplikuje gui jeszcze bardziej.
		// TODO Trzeba wi�c gui licytowania przerobi� tak, by by�y pokazywane oddzielne paski licytowania
		// dla ka�dego typu aukcji.
		// TODO Nale�y te� zastanowi� si�, czy nie propagowa� zmiany ceny oddzielnie od post�pie� zegara.
		// propagacja ceny zwi�zana z post�pieniem mia�a g��boki sens dla aukcji holenderskiej.
		// Dla innych typ�w aukcji takiego sensu ju� nie ma. 
		BigDecimal newBid;
		if (a.getMaxBidder() != agent.getName())
			if (a.getPrice().compareTo(this.bid) <= 0) {
				newBid = a.getPrice().add(this.upBid);
				this.agent.propose(a.getAN(), agent.getName(), newBid);
			}
	}

}
