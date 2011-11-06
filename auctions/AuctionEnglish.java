package auctions;

import buyingDutchmanClient.BDC;

public class AuctionEnglish extends Auction {

	public AuctionEnglish(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
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

//	@Override
//	public void onTick() {
//		if (isFinished())
//			return;
//		getAd().setTicksLeft(getAd().getTicksLeft()-BDC.TICK);
//		String ii = null;
//		if (getAd().getTicksLeft() <= 0) {
//			ii = Float.toString(getPrice());
//			getAd().setTicksLeft(0);
//			setFinished(true);
//		} else {
//			ii = Float.toString(getPrice());			
//		}		
//		String [] i = ii.split(BDC.REGEXFPOINT);
//		int j = i[1].length();
//		if (j >= 2) {
//			getAd().setPriceDec(i[1].substring(0, 2));
//			getAd().setPriceInt(i[0]);
//		} else {
//			getAd().setPriceDec(i[1]+"0");
//			getAd().setPriceInt(i[0]);
//		}
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
