package automaticBuyers;

import java.math.BigDecimal;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;

public class AutomaticBuyerFactory{
	// klasa odpowiada za produkowanie obiektów obs³uguj¹cych przyciœniêcie guzika "Oczekuj i kup"
	public static AutomaticBuyer AutomaticBuyerInstance(BuyingDutchmanAgent agent, BigDecimal bid, BigDecimal upBid, BDC.AuctionTypes type) {
		// TODO Dodaæ dla aukcji: groszowej, angielskiej, przetargu. Dla aukcji drugiej ceny zmiana aktualnej ceny musi byæ obslugiwana przez sam¹ aukcjê.
		switch (type) {
		case HOLENDERSKA:
			return new AutomaticBuyerDutch(agent, bid);
		case ANGIELSKA:
			return new AutomaticBuyerEnglish(agent, bid, upBid);
		case DRUGIEJ_CENY:
			return new AutomaticBuyerSecondPrice(agent, bid, upBid);
		case PRZETARG:
			return new AutomaticBuyerBidding(agent, bid, upBid);
		}	
		return null;		
	}
}