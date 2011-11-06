package auctions;

import java.util.HashMap;
import buyingDutchmanClient.BDC;

public class AuctionPenny extends Auction {

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
	public String getMaxBid() {
		if (maxBid >= 0)
			return Float.toString(maxBid);		
		else
			return BDC.NONESTRING;
	}

	@Override
	protected void performAuctionTick() {
		if (getAd().getTicksLeft() <= 0) {
			if (!noAutomaticBidders()) {
				for ( String bidder : automaticBidders.keySet() ) {
					AutomaticBid a = automaticBidders.get(bidder);
					if (a.useBid()) {
						// Increases auction price by currency rate corresponding to number of automatic bidders that still can provide
						setPrice(getPrice()+(float)0.01);
						// Prolongs auction time by number of thicks equal to number of automatic bidders that still can provide
						setTicksLeft(getTicksLeft()+1);
					} else
						// Removing bidder, that can't provide
						automaticBidders.remove(bidder);
				}
			}
		}
	}

}
