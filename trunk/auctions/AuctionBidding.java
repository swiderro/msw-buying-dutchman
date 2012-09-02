package auctions;

import java.math.BigDecimal;

public class AuctionBidding extends Auction {
	private static final long serialVersionUID = 1L;

	public AuctionBidding(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	@Override
	protected boolean checkFinishByOffer() {
		return false;
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {
		if (bid.compareTo(price) < 0)
			return true;
		else if (bid.compareTo(price) == 0) {
			if (getBestBid() == null)
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public boolean propose(String bidder, BigDecimal bid) {
		if (isFinished())
			return false;
		else {
			if (isBestBid(bid)){
				setNewPrice(bid, bidder);
				setBestBid(bid, bidder);
				return true;
			} else
				return false;
		}
	}
}
