package buyingDutchman;

public class SecondPriceAuction extends Auction {

	public SecondPriceAuction(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMaxBid() {
		if (maxBid >=0 && isFinished())
			return Float.toString(maxBid);		
		else
			return BDC.NONESTRING;
	}

}
