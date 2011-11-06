package auctions;

import java.io.Serializable;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BDC.AuctionTypes;

public abstract class Auction implements Serializable {
	// TODO Trzeba wyœwietlaæ najwy¿szego oferenta: potrzebne do przetargów, aukcji angielskiej, drugiej ceny. Do groszowej niekoniecznie.
	// TODO W zwi¹zku z tym trzeba zmieniæ klasy od tabelek: 
	private static final long serialVersionUID = 1L;
	private final AuctionDetails ad;
	private final AuctionItem ai;
	private final String an;
	private final String auctioneer;
	protected String maxBidInt;
	protected String maxBidDec;
	protected String maxBidder;
	private boolean finished;
	private float reductionStep;
	private float endPrice;
	private float price;
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
		setMaxBidder(null);
		setPrice(computePrice());			
		setEndPrice(computeEndPrice());
		int time = ((AD.getStartTicks())/BDC.TICK);		
		setReductionStep((getPrice() - getEndPrice())/((float) time));
		maxBid = -1;
	}
	private float computeEndPrice() {		
		return Float.valueOf(getAd().getEndPriceInt()+BDC.FPOINT+getAd().getEndPriceDec());
	}
	protected float computePrice() {
		return Float.valueOf(getAd().getPriceInt()+BDC.FPOINT+getAd().getPriceDec());
	}
	public AuctionDetails getAd() {
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
		return getAd().getPriceInt();
	}
	private void setPriceInt(String i) {
		getAd().setPriceInt(i);
	}
	public String getPriceDec() {
		return getAd().getPriceDec();		
	}
	private void setPriceDec(String i) {
		getAd().setPriceDec(i);
	}
	public int getTicksLeft() {
		return getAd().getTicksLeft();
	}
	protected void setTicksLeft(int i) {
		getAd().setTicksLeft(i);
	}
	//Sprawdza warunek koñca, spowodowany z³o¿eniem oferty.
	//Dla ka¿dego typu aukcji mo¿e byæ inny, wiêc jest zadeklarowany jako abstract
	protected abstract boolean checkFinishByOffer();
	//Wykonuje czynnoœci aukcji zwi¹zan¹ z zegarem, takie jak obni¿enie ceny.
	//Dla ka¿dego typu aukcji mo¿e byæ inny, wiêc jest zadeklarowany jako abstract
	protected abstract void performAuctionTick();
	public final void performFinish(){
		String ii = Float.toString(getPrice());
		setFinished(true);
		String [] i = ii.split(BDC.REGEXFPOINT);
		int j = i[1].length();
		if (j >= 2) {
			getAd().setPriceDec(i[1].substring(0, 2));
			getAd().setPriceInt(i[0]);
		} else {
			getAd().setPriceDec(i[1]+"0");
			getAd().setPriceInt(i[0]);
		}		
	};
	//
	public final void onTick() {
		if (isFinished())
			return;
		setTicksLeft(getTicksLeft()-BDC.TICK);
		if (getTicksLeft() > 0) {
			performAuctionTick();
			if(checkFinishByOffer())
				performFinish();
		} else
			performFinish();		
	}
	//odpalane na aukcji po stronie agenta obserwuj¹cego
	public void onTick(String priceInt, String priceDec, int ticksLeft, String MaxBid, String MaxBidder) {	
		if (isFinished())
			return;
		setPriceInt(priceInt);
		setPriceDec(priceDec);
		setPrice(Float.parseFloat(priceInt+BDC.FPOINT+priceDec));
		setTicksLeft(ticksLeft);
		if (!MaxBid.equalsIgnoreCase(BDC.NONESTRING))
			setMaxBid(MaxBid);
		if (!MaxBidder.equalsIgnoreCase(BDC.NONESTRING))
			setMaxBidder(MaxBidder);
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
		return getAd().getTitle();
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
		case 8: return getMaxBidder();
		case 9: return getTitle();
		}
		return null;
	}
	public AuctionTypes getType() {
		return getAd().getType();
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
		return false;
	}
	public void buyNow(String MaxBidder, String MaxBidInt, String MaxBidDec) {
		setMaxBidder(MaxBidder);
		maxBidInt = MaxBidInt;
		maxBidDec = MaxBidDec;
		maxBid = Float.valueOf(maxBidInt+BDC.FPOINT+maxBidDec).floatValue();
		return;
	}
	public boolean propose(String bidder, float bid) {
		if (isFinished())
			return false;
		else {
			if (bid <= getMaxBidFloat())
				return false;
			// maxBid przyda siê do auckji drugiej ceny
			setMaxBid(bid);
			setPrice(bid);
			setMaxBidder(bidder);
			return true;
		}
	}
	protected void setMaxBidder(String bidder) {
		maxBidder = bidder;		
	}
	public float getPrice() {
		//return Float.valueOf(getAd().getPriceInt()+BDC.FPOINT+getAd().getPriceDec()).floatValue();
		return this.price;
	}
	protected void setPrice(float price) {
		 this.price = price;
		 String pp = Float.toString(this.price);			
			String [] p = pp.split(BDC.REGEXFPOINT);
			int j = p[1].length();
			if (j >= 2) {
				getAd().setPriceDec(p[1].substring(0, 2));
				getAd().setPriceInt(p[0]);
			} else {
				getAd().setPriceDec(p[1]+"0");
				getAd().setPriceInt(p[0]);
			}		 
	}
	public void setReductionStep(float reductionStep) {
		this.reductionStep = reductionStep;
	}
	public float getReductionStep() {
		return reductionStep;
	}
	public void setEndPrice(float endPrice) {
		this.endPrice = endPrice;
	}
	public float getEndPrice() {
		return endPrice;
	}
	public void setMaxBid(float maxBid) {
		this.maxBid = maxBid;
	}
}
