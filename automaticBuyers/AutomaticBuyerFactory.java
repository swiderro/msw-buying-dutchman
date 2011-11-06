package automaticBuyers;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;

public class AutomaticBuyerFactory{
	// klasa odpowiada za produkowanie obiekt�w obs�uguj�cych przyci�ni�cie guzika "Oczekuj i kup"
	public static AutomaticBuyer AutomaticBuyerInstance(BuyingDutchmanAgent agent, float bid,
			BDC.AuctionTypes type) {
		// TODO Doda� dla aukcji: groszowej, angielskiej, przetargu, 
		switch (type) {
		case HOLENDERSKA:
			return new AutomaticBuyerDutch(agent, bid);
		}
		return null;		
	}
}
