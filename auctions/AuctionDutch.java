package auctions;

import java.math.BigDecimal;

import buyingDutchmanClient.BDC;

public class AuctionDutch extends Auction {
	public AuctionDutch(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	private static final long serialVersionUID = 1L;	

	@Override
	protected boolean checkFinishByOffer() {
		//computing new price after thick
		BigDecimal newPrice = computePrice();
		//koñczy, jeœli jest oferta
		if ( getBestBid() != null && getBestBid().compareTo(newPrice) >= 0
			//dodatkowe zabezpieczenie. na razie wy³¹czam
//			|| getEndPrice() > newPrice
		) {
			setFinished(true);
			return true;
		} else		
			return false;
	}
	@Override
	public boolean buyNow(String bestBidder) {
		if (isFinished())
			return false;
		else {
			setBestBidder(bestBidder);
			bestBidInt = getPriceInt();
			bestBidDec = getPriceDec();
			bestBid = new BigDecimal(bestBidInt+BDC.FPOINT+bestBidDec);
			setFinished(true);
			return true;
		}
	}
	@Override
	protected void performAuctionTick() {
		setNewPrice(getPrice().subtract(getReductionStep()));
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {
		if (bid.compareTo(getBestBid()) > 0)
			return true;
		else
			return false;
	}

}
