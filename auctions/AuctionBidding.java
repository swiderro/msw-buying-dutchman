package auctions;

import java.math.BigDecimal;

public class AuctionBidding extends Auction {
	//TODO nie dzia�a poprawnie sk�adanie oferty dla tego typu aukcji. Do poprawy.
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuctionBidding(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	@Override
	protected boolean checkFinishByOffer() {
		// TODO A co je�li cena zejdzie do 0?
		return false;
	}

	@Override
	protected void performAuctionTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {
		if (bid.compareTo(getMaxBid()) < 0)
			return true;
		else
			return false;
	}


}
