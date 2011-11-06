package auctions;

import buyingDutchmanClient.BDC;

public class AuctionNull extends Auction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuctionNull(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	@Override
	public String getMaxBid() {
		return BDC.NONESTRING;
	}

	@Override
	protected boolean checkFinishByOffer() {
		return true;
	}

	@Override
	protected void performAuctionTick() {		
	}

}
