package automaticBuyers;

import java.math.BigDecimal;
import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;

public class AutomaticBuyerFactory{
	// klasa odpowiada za produkowanie obiektów obs³uguj¹cych przyciœniêcie guzika "Oczekuj i kup"
	public static AutomaticBuyer AutomaticBuyerInstance(
		BuyingDutchmanAgent agent
		, BigDecimal bid
		, BigDecimal upBid
		, int rounds
		, BDC.AuctionTypes type
	) {
		switch (type) {
		case HOLENDERSKA:
			return new AutomaticBuyerDutch(agent, bid);
		case ANGIELSKA:
			return new AutomaticBuyerEnglish(agent, bid, upBid);
		case DRUGIEJ_CENY:
			return new AutomaticBuyerSecondPrice(agent, bid, upBid);
		case PRZETARG:
			return new AutomaticBuyerBidding(agent, bid, upBid);
		case GROSZOWA:
			return new AutomaticBuyerPenny(agent, bid, rounds);
		}	
		return null;		
	}
}