package buyingDutchman;

public class Bidding extends Auction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bidding(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	@Override
	public String getMaxBid() {
		if (maxBid >=0)
			return Float.toString(maxBid);		
		else
			return BDC.NONESTRING;
	}

}
