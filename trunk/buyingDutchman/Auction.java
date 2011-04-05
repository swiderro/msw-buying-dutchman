package buyingDutchman;

import java.io.Serializable;

public abstract class Auction implements Serializable {	
	private static final long serialVersionUID = 1L;
	private final AuctionDetails ad;
	private final AuctionItem ai;
	private final String an;
	private final String auctioneer;
	private String maxBidInt;
	private String maxBidDec;
	private String maxBidder;
	private boolean finished;
	private float reductionStep;
	private float price;
	private float endPrice;
	protected float maxBid;
	/**
	 * @param ad
	 * @param ai
	 * @param an
	 */
	public Auction(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		ad = AD;
		ai = AI;
		an = AN;
		finished = false;
		auctioneer = Auctioneer;
		maxBidInt = null;
		maxBidDec = null;
		maxBidder = null;
		price = computePrice();			
		endPrice = computeEndPrice();
		int time = ((AD.getStartTicks())/BDC.TICK);		
		reductionStep = (price - endPrice)/((float) time);
		maxBid = -1;
	}
	private float computeEndPrice() {		
		return Float.valueOf(ad.getEndPriceInt()+BDC.FPOINT+ad.getEndPriceDec());
	}
	private float computePrice() {
		return Float.valueOf(ad.getPriceInt()+BDC.FPOINT+ad.getPriceDec());
	}
	public AuctionDetails getAD() {
		return ad;
	}
	public AuctionItem getAI() {
		return ai;
	}
	public String getAN() {
		return an;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}	
	public String getPriceInt() {		
		return ad.getPriceInt();
	}
	private void setPriceInt(String i) {
		ad.setPriceInt(i);
	}
	public String getPriceDec() {
		return ad.getPriceDec();		
	}
	private void setPriceDec(String i) {
		ad.setPriceDec(i);
	}
	public int getTicksLeft() {
		return ad.getTicksLeft();
	}
	private void setTicksLeft(int i) {
		ad.setTicksLeft(i);
	}
	public void onTick() {
		if (isFinished())
			return;
		price -= reductionStep;
		ad.setTicksLeft(ad.getTicksLeft()-BDC.TICK);
		String ii = null;
		if (maxBid >= price) {			
			setFinished(true);
			price = maxBid;
			ii = Float.toString(price);		
		} else if (price < endPrice || ad.getTicksLeft() <= 0) {
			price = endPrice;
			ii = Float.toString(endPrice);
			ad.setTicksLeft(0);
			setFinished(true);
		} else {
			ii = Float.toString(price);			
		}		
		String [] i = ii.split(BDC.REGEXFPOINT);
		int j = i[1].length();
		if (j >= 2) {
			ad.setPriceDec(i[1].substring(0, 2));
			ad.setPriceInt(i[0]);
		} else {
			ad.setPriceDec(i[1]+"0");
			ad.setPriceInt(i[0]);
		}
	}
	public void onTick(String priceInt, String priceDec, int ticksLeft, String MaxBid) {	
		if (isFinished())
			return;
		setPriceInt(priceInt);
		setPriceDec(priceDec);
		setTicksLeft(ticksLeft);
		if (!MaxBid.equalsIgnoreCase(BDC.NONESTRING))				
			setMaxBid(MaxBid);
		float newPrice = computePrice();
		if (ad.getTicksLeft() <= 0
			|| maxBid >= newPrice
			|| endPrice > newPrice
		) {
			setFinished(true);
		}
	}	
	private void setMaxBid(String MaxBid) {		
		String ii = MaxBid;			
		String [] i = ii.split(BDC.REGEXFPOINT);
		int j = i[1].length();
		if (j >= 2) {
			maxBidDec = i[1].substring(0, 2);
			maxBidInt = i[0];
		} else {
			maxBidDec = i[1]+"0";
			maxBidInt = i[0];
		}
		maxBid = Float.valueOf(maxBidInt+BDC.FPOINT+maxBidDec);
	}
	public String getTitle() {		
		return ad.getTitle();
	}
	public String getAuctioneer() {
		return auctioneer;
	}
	public String getAuctioneerAN() {
		return auctioneer+BDC.POSTFIX+an;
	}
	public abstract String getMaxBid();
	public float getMaxBidFloat() {
		return maxBid;
	}
	public String getMaxBidder() {	
		if (maxBidder != null && !maxBidder.equalsIgnoreCase(""))
			return maxBidder;
		else
			return BDC.NONESTRING;
	}
	public Object getColumnValue(int col) {
		switch (col) {
		case 0: return getAN();
		case 1: return getType();
		case 2: return getAuctioneer();
		case 3: return getCategory();
		case 4: return getSubCategory();
		case 5: return getTicksLeft()/BDC.TICK;
		case 6: return getPriceInt()+BDC.POINT+getPriceDec();
		case 7: return getMaxBid();
		case 8: return getTitle();
		}
		return null;
	}
	protected String getType() {
		return ad.getType();
	}
	private String getSubCategory() {
		return ai.getSubCategory().toString();
	}
	private String getCategory() {		
		return ai.getCategory().toString();
	}
	public Object getFinishedColumnValue(int col) {
		switch (col) {
		case 0: return getAN();
		case 1: return getType();
		case 2: return getAuctioneer();
		case 3: return getCategory();
		case 4: return getSubCategory();
		case 5: return getTicksLeft()/BDC.TICK;
		case 6: return getPriceInt()+BDC.POINT+getPriceDec();
		case 7: return getMaxBid();
		case 8: return getMaxBidder();
		case 9: return getTitle();
		}
		return null;
	}
	public boolean buyNow(String MaxBidder) {
		if (isFinished())
			return false;
		else {
			maxBidder = MaxBidder;
			maxBidInt = getPriceInt();
			maxBidDec = getPriceDec();
			maxBid = Float.valueOf(maxBidInt+BDC.FPOINT+maxBidDec).floatValue();
			setFinished(true);
			return true;
		}
	}
	public void buyNow(String MaxBidder, String MaxBidInt, String MaxBidDec) {
		maxBidder = MaxBidder;
		maxBidInt = MaxBidInt;
		maxBidDec = MaxBidDec;
		maxBid = Float.valueOf(maxBidInt+BDC.FPOINT+maxBidDec).floatValue();
		return;
	}
	public boolean waitAndBuy(String bidder, float bid) {
		if (isFinished())
			return false;
		else {
			if (bid <= maxBid)
				return false;
			maxBid = bid;
			maxBidder = bidder;
			if (maxBid>=Float.valueOf(ad.getPriceInt()+BDC.FPOINT+ad.getPriceDec()).floatValue()) {
				maxBid=Float.valueOf(ad.getPriceInt()+BDC.FPOINT+ad.getPriceDec()).floatValue();
				price = Float.valueOf(ad.getPriceInt()+BDC.FPOINT+ad.getPriceDec()).floatValue();
				setFinished(true);
			}
			return true;
		}
	}
	public float getPrice() {
		return Float.valueOf(ad.getPriceInt()+BDC.FPOINT+ad.getPriceDec()).floatValue();
	}
}
