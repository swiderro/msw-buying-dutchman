package auctions;

import java.math.BigDecimal;

import buyingDutchmanClient.BDC;

public class AuctionSecondPrice extends Auction {
	//TODO nie dzia³a poprawnie sk³adanie oferty dla tego typu aukcji. Do poprawy.
	public AuctionSecondPrice(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		super(AD, AI, AN, Auctioneer);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkFinishByOffer() {
		return false;		
	}

	@Override
	protected void performAuctionTick() {
		return;
	}

	@Override
	protected boolean isBestBid(BigDecimal bid) {
		if (bid.compareTo(getMaxBid()) > 0)
			return true;
		else
			return false;
	}

	@Override
	protected void setPrice(BigDecimal price) {
		 this.price = getMaxBid().add(BigDecimal.valueOf(BDC.Grosz));
		 String [] p = this.price.toPlainString().split(BDC.REGEXFPOINT);
		 if (p[1].length() >= 2) {
			 getAd().setPriceDec(p[1].substring(0, 2));
			 getAd().setPriceInt(p[0]);
		 } else {
			 getAd().setPriceDec(p[1]+"0");
			 getAd().setPriceInt(p[0]);
		 }		 
	}
}
