package buyingDutchman;

public class EnglishAuction extends Auction {

	public EnglishAuction(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMaxBid() {
		if (maxBid >= 0)
			return Float.toString(maxBid);		
		else
			return BDC.NONESTRING;
	}

}
