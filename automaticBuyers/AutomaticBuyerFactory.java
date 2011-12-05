package automaticBuyers;

import java.math.BigDecimal;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;

public class AutomaticBuyerFactory{
	// klasa odpowiada za produkowanie obiekt�w obs�uguj�cych przyci�ni�cie guzika "Oczekuj i kup"
	public static AutomaticBuyer AutomaticBuyerInstance(BuyingDutchmanAgent agent, BigDecimal bid, BDC.AuctionTypes type) {
		// TODO Doda� dla aukcji: groszowej, angielskiej, przetargu. Dla aukcji drugiej ceny zmiana aktualnej ceny musi by� obslugiwana przez sam� aukcj�.
		switch (type) {
		case HOLENDERSKA:
			return new AutomaticBuyerDutch(agent, bid);
		case ANGIELSKA:
			return new AutomaticBuyerEnglish(agent, bid);
		case DRUGIEJ_CENY:
			return new AutomaticBuyerSecondPrice(agent, bid);
		case PRZETARG:
			return new AutomaticBuyerBidding(agent, bid);
		}	
		return null;		
	}
}