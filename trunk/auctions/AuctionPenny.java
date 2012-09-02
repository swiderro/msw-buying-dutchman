package auctions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import buyingDutchmanClient.BDC;

public class AuctionPenny extends Auction {
	//TODO ? nie dzia³a poprawnie sk³adanie oferty dla tego typu aukcji. Do poprawy.
	private HashMap<String, Vector<AutomaticBid>> automaticBids;

	public AuctionPenny(AuctionDetails AD, AuctionItem AI, String AN,
			String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
		automaticBids = new HashMap<String, Vector<AutomaticBid>>();
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkFinishByOffer() {
		return false;
	}
	
	@Override
	protected boolean isBestBid(BigDecimal bid) {
		return false;
	}
	
	public void addAutomaticBid(String bidder, AutomaticBid ab) {
		if (!automaticBids.containsKey(bidder))
			automaticBids.put(bidder, new Vector<AutomaticBid>());
		automaticBids.get(bidder).add(ab);
	}
	
	public final void performFinish()
	{
			HashMap<String, Vector<AutomaticBid>> removedAutomaticBids = new HashMap<String, Vector<AutomaticBid>>();
			int usedBids = 0;
			if ( !automaticBids.isEmpty()) {
				for ( String bidder : automaticBids.keySet() ) {
					for (AutomaticBid a : automaticBids.get(bidder)) {
						if (a.getStartPrice().compareTo(getPrice()) <= 0) {
							if (a.useBid()) {
								usedBids++;
								// Increases auction price by currency rate corresponding to number of automatic bidders that still can provide
								setNewPrice(
									getPrice().add(BigDecimal.valueOf(BDC.Grosz))
									, bidder
								);
								// Prolongs auction time by number of seconds equal to number of automatic bidders that still can provide
								setMiliSecondsLeft(getMiliSecondsLeft()+BDC.ONE_SECOND);
								setBestBid(getPrice(), bidder);
							} else {
								// Removing bidder, that can't provide
								if (!removedAutomaticBids.containsKey(bidder))
									removedAutomaticBids.put(bidder, new Vector<AutomaticBid>());
								removedAutomaticBids.get(bidder).add(a);
							}
						}
					}
				}
				setMiliSecondsLeft(getMiliSecondsLeft()+BDC.ONE_SECOND);
				for ( String bidder : removedAutomaticBids.keySet() ) {
					for (AutomaticBid a : removedAutomaticBids.get(bidder)) {
						automaticBids.get(bidder).remove(a);
					}
				}
			}
			//Okreœla, ile automatów w danym wykonaniu przd³u¿a³o aukcje. Jeœli nie ma konkurencji, koñczy aukcjê
			if (usedBids<2)
				setFinished(true);
	}
}
