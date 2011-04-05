package buyingDutchman;

public class NullAuction extends Auction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO Implement according to Null Object Design Pattern
	public NullAuction(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	@Override
	public String getMaxBid() {
		return BDC.NONESTRING;
	}

}
