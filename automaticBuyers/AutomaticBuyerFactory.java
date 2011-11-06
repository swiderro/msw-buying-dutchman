package automaticBuyers;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;

public class AutomaticBuyerFactory{
	// klasa odpowiada za produkowanie obiektów obs³uguj¹cych przyciœniêcie guzika "Oczekuj i kup"
	public static AutomaticBuyer AutomaticBuyerInstance(BuyingDutchmanAgent agent, float bid,
			BDC.AuctionTypes type) {
		// TODO Dodaæ dla aukcji: groszowej, angielskiej, przetargu, 
		switch (type) {
		case HOLENDERSKA:
			return new AutomaticBuyerDutch(agent, bid);
		}
		return null;		
	}
}
