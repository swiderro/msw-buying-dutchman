package auctions;

import buyingDutchmanClient.BDC;

public class AuctionDutch extends Auction {

	public AuctionDutch(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getMaxBid() {
		if (maxBid >=0 && isFinished())
			return Float.toString(maxBid);		
		else
			return BDC.NONESTRING;
	}

	@Override
	public boolean propose(String bidder, float bid) {
		return false;
	}

	@Override
	protected boolean checkFinishByOffer() {
		//computing new price after thick
		float newPrice = computePrice();
		//koñczy, jeœli jest oferta
		if (maxBid >= newPrice
			//dodatkowe zabezpieczenie. na razie wy³¹czam
//			|| getEndPrice() > newPrice
		) {
			setFinished(true);
			return true;
		} else		
			return false;
	}
	@Override
	public boolean buyNow(String MaxBidder) {
		if (isFinished())
			return false;
		else {
			setMaxBidder(MaxBidder);
			maxBidInt = getPriceInt();
			maxBidDec = getPriceDec();
			maxBid = Float.valueOf(maxBidInt+BDC.FPOINT+maxBidDec).floatValue();
			setFinished(true);
			return true;
		}
	}
	@Override
	protected void performAuctionTick() {
		setPrice(getPrice() - getReductionStep());
		//zapobiega b³êdom zaokr¹gleñ, gdy zamiast zera wychodzi np. 4.76231E-7
		if (getPrice() < 0.01)
			setPrice(0);
	}

}
