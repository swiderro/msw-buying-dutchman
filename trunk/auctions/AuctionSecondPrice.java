package auctions;

import buyingDutchmanClient.BDC;

public class AuctionSecondPrice extends Auction {

	public AuctionSecondPrice(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
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

//	@Override
//	public void onTick() {
//		if (isFinished())
//			return;
//		
//		
//	}

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

}
