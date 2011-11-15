package auctions;

import java.math.BigDecimal;
import java.util.HashMap;
import buyingDutchmanClient.BDC;

public class AuctionPenny extends Auction {
	//TODO nie dzia�a poprawnie sk�adanie oferty dla tego typu aukcji. Do poprawy.
	private HashMap<String, AutomaticBid> automaticBidders;

	public AuctionPenny(AuctionDetails AD, AuctionItem AI, String AN,
			String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkFinishByOffer() {
		if (getAd().getTicksLeft() <= 0) {
			if (noAutomaticBidders()){
				setFinished(true);
				return true;
			} else
				return false;
		} else
			return false;
	}

	private boolean noAutomaticBidders() {
		if (automaticBidders == null)
			return true;
		else
			return automaticBidders.isEmpty();
	}

	@Override
	protected void performAuctionTick() {
		if (getAd().getTicksLeft() <= 0) {
			if (!noAutomaticBidders()) {
				for ( String bidder : automaticBidders.keySet() ) {
					AutomaticBid a = automaticBidders.get(bidder);
					if (a.useBid()) {
						// Increases auction price by currency rate corresponding to number of automatic bidders that still can provide
						setPrice(getPrice().add(BigDecimal.valueOf(BDC.Grosz)));
						// Prolongs auction time by number of thicks equal to number of automatic bidders that still can provide
						setTicksLeft(getTicksLeft()+1);
					} else
						// Removing bidder, that can't provide
						automaticBidders.remove(bidder);
				}
			}
		}
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {
		// TODO Zastanowi� si�, jak to zaimplementwa�. Jak w og�le do cholery ja zaimplementowa�em te automaty? Pod oczekuj i kup?
		// TODO Czy powinna by� mo�liwo�� r�cznego podbijania? Je�li tak, to pod kt�rym przyciskiem?
		return false;
	}

}
