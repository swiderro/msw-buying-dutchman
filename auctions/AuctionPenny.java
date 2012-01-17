package auctions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

import buyingDutchmanClient.BDC;

public class AuctionPenny extends Auction {
	//TODO nie dzia³a poprawnie sk³adanie oferty dla tego typu aukcji. Do poprawy.
	private HashMap<String, AutomaticBid> automaticBids;

	public AuctionPenny(AuctionDetails AD, AuctionItem AI, String AN,
			String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
		automaticBids = new HashMap<String, AutomaticBid>();
	}

	private static final long serialVersionUID = 1L;

	/*

	@Override
	protected void performAuctionTick() {
		if (getAd().getTicksLeft() <= 0) {
			HashMap<String, AutomaticBid> removedAutomaticBids = new HashMap<String, AutomaticBid>();
			boolean priceChanged = false;
			if ( !automaticBids.isEmpty()) {
				for ( String ab : automaticBids.keySet() ) {
					AutomaticBid a = automaticBids.get(ab);
					if (a.getStartPrice().compareTo(getPrice()) <= 0) {
						if (a.useBid()) {
							priceChanged = true;
							// Increases auction price by currency rate corresponding to number of automatic bidders that still can provide
							setNewPrice(getPrice().add(BigDecimal.valueOf(BDC.Grosz)));
							// Prolongs auction time by number of thicks equal to number of automatic bidders that still can provide
							setTicksLeft(getTicksLeft()+1);
							setBestBidder(ab);
							setBestBid(getPrice());
						} else {
							// Removing bidder, that can't provide
							// Not sure if won't cause problems because of hashtable easy fail
							removedAutomaticBids.put(ab, a);
						}
					}
				}
				Iterator<String> i = removedAutomaticBids.keySet().iterator();
				while (i.hasNext()){
					String ab = i.next();
					automaticBids.remove(ab);
				}
			}
			if (!priceChanged)
				setFinished(true);
		}
	}

	

	public final void performFinish() {
		if (automaticBids.isEmpty())
			super.performFinish();
		else
			performAuctionTick();
	}*/
	@Override
	protected boolean checkFinishByOffer() {
		return false;
	}
	
	@Override
	protected boolean isBestBid(BigDecimal bid) {
		return false;
	}
	
	public void addAutomaticBid(String bidder, AutomaticBid ab) {
		automaticBids.put(bidder, ab);
	}
	
	public final void performFinish()
	{
			HashMap<String, AutomaticBid> removedAutomaticBids = new HashMap<String, AutomaticBid>();
			int usedBids = 0;
			if ( !automaticBids.isEmpty()) {
				for ( String bidder : automaticBids.keySet() ) {
					AutomaticBid a = automaticBids.get(bidder);
					if (a.getStartPrice().compareTo(getPrice()) <= 0) {
						if (a.useBid()) {
							usedBids++;
							// Increases auction price by currency rate corresponding to number of automatic bidders that still can provide
							setNewPrice(
								getPrice().add(BigDecimal.valueOf(BDC.Grosz))
								, bidder
							);
							// Prolongs auction time by number of thicks equal to number of automatic bidders that still can provide
							setTicksLeft(getTicksLeft()+BDC.TICK);
							setBestBid(getPrice(), bidder);
						} else {
							// Removing bidder, that can't provide
							// Not sure if won't cause problems because of hashtable easy fail
							removedAutomaticBids.put(bidder, a);
						}
					}
				}
				Iterator<String> i = removedAutomaticBids.keySet().iterator();
				while (i.hasNext()){
					String ab = i.next();
					automaticBids.remove(ab);
				}
			}
			if (usedBids<2)
				setFinished(true);
	}
}
