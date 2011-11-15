package auctions;

import java.math.BigDecimal;

public class AuctionEnglish extends Auction {

	public AuctionEnglish(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkFinishByOffer() {
		//No offer can finish English auction. Only time finishes it.
		return false;
	}

	@Override
	protected void performAuctionTick() {
		//English auction does not involve price reduction.
		return;
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {
		if (bid.compareTo(getMaxBid()) > 0)
			return true;
		else
			return false;
	}
}
