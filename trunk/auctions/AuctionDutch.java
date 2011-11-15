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
		if ( getMaxBid() != null && getMaxBid().compareTo(newPrice) >= 0
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
			maxBid = new BigDecimal(maxBidInt+BDC.FPOINT+maxBidDec);
			setFinished(true);
			return true;
		}
	}
	@Override
	protected void performAuctionTick() {
		setPrice(getPrice().subtract(getReductionStep()));
		//zapobiega b³êdom zaokr¹gleñ, gdy zamiast zera wychodzi np. 4.76231E-7
		// TODO Zmieniæ, gdy bêdzie problem z wartoœci¹ blisk¹ 0
		//if (getPrice().compareTo(BigDecimal.valueOf(0.01)) < 0)
			//setPrice(BigDecimal.valueOf(0));
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {
		if (bid.compareTo(getMaxBid()) > 0)
			return true;
		else
			return false;
	}

}
