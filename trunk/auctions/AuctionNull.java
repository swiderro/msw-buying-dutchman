package auctions;

import java.math.BigDecimal;

public class AuctionNull extends Auction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuctionNull(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	@Override
	protected boolean checkFinishByOffer() {
		return true;
	}

	@Override
	protected void performAuctionTick() {		
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {
		return false;
	}

}
