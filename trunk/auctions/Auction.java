package auctions;

import java.io.Serializable;
import java.math.BigDecimal;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BDC.AuctionTypes;

public abstract class Auction implements Serializable {
	private static final long serialVersionUID = 1L;
	private final AuctionDetails ad;
	private final AuctionItem ai;
	private final String an;
	private final String auctioneer;
	protected String maxBidInt;
	protected String maxBidDec;
	protected String maxBidder;
	private boolean finished;
	private BigDecimal reductionStep;
	private BigDecimal endPrice;
	protected BigDecimal price;
	protected BigDecimal maxBid;
	/**
	 * @param ad
	 * @param ai
	 * @param an
	 */
	public Auction(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		this.ad = AD;
		this.ai = AI;
		this.an = AN;
		this.finished = false;
		this.auctioneer = Auctioneer;
		this.maxBid = null;
		this.maxBidInt = null;
		this.maxBidDec = null;
		setMaxBidder(null);
		setPrice(computePrice());
		setEndPrice(computeEndPrice());
		int time = ((AD.getStartTicks())/BDC.TICK);		
		setReductionStep(getPrice().subtract(getEndPrice()).divide(new BigDecimal(time), BDC.BigDecimalScale, BDC.BigDecimalRounding));
	}
	private BigDecimal computeEndPrice() {		
		return new BigDecimal(getAd().getEndPriceInt()+BDC.FPOINT+getAd().getEndPriceDec());
	}
	protected BigDecimal computePrice() {
		return new BigDecimal(getAd().getPriceInt()+BDC.FPOINT+getAd().getPriceDec());
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
	public String getPriceDec() {
		return getAd().getPriceDec();		
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
	// TODO Czy powy¿sze jest prawd¹? Tak naprawdê, to tylko aukcja holenderska manipuluje cen¹ w czasie z automatu.
	// TODO Zobaczyæ, jak to jest zaimplementowane w aukcjach innych ni¿ holenderska.
	protected abstract void performAuctionTick();
	public final void performFinish(){
		setFinished(true);
		String [] i = getPrice().toPlainString().split(BDC.REGEXFPOINT);
		if (i[1].length() >= 2) {
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
		setPrice(new BigDecimal(priceInt+BDC.FPOINT+priceDec));
		setTicksLeft(ticksLeft);
		if (!MaxBid.equalsIgnoreCase(BDC.NONESTRING))
			setMaxBid(MaxBid);
		if (!MaxBidder.equalsIgnoreCase(BDC.NONESTRING))
			setMaxBidder(MaxBidder);
	}
	private void setMaxBid(String MaxBid) {
		maxBid = new BigDecimal(maxBidInt+BDC.FPOINT+maxBidDec);
		String [] i = maxBid.toPlainString().split(BDC.REGEXFPOINT);
		if (i[1].length() >= 2) {
			maxBidDec = i[1].substring(0, 2);
			maxBidInt = i[0];
		} else {
			maxBidDec = i[1]+"0";
			maxBidInt = i[0];
		}
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
	public final String getMaxBidString() {
		if (maxBid != null && isFinished())
			return maxBid.toString();		
		else
			return BDC.NONESTRING;
	}
	public final BigDecimal getMaxBid() {
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
		case 7: return getMaxBidString();
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
		case 7: return getMaxBidString();
		case 8: return getMaxBidder();
		case 9: return getTitle();
		}
		return null;
	}
	// Mo¿na by daæ to jako abstract, ale tak naprawdê tylko w aukcji holenderskiej jest opcja kup teraz.
	// Dlatego te¿ zostawiam tu domyœlne zachowanie, a przes³aniam w holenderskiej.
	public boolean buyNow(String MaxBidder) {
		return false;
	}
	public void buyNow(String MaxBidder, String MaxBidInt, String MaxBidDec) {
		setMaxBidder(MaxBidder);
		setMaxBidInt(MaxBidInt);
		setMaxBidDec(MaxBidDec);
		setMaxBid(maxBidInt+BDC.FPOINT+maxBidDec);
		return;
	}
	private void setMaxBidDec(String maxBidDec2) {
		maxBidDec = maxBidDec2;
	}
	private void setMaxBidInt(String maxBidInt2) {
		maxBidInt = maxBidInt2;
	}
	// Inaczej obs³ugiwane w aukcji drugiej ceny
	// Inaczej obs³ugiwane w przetargu
	// Nie wystêpuje w aukcji groszowej
	// Nie wystêpuje w aukcji holenderskiej
	// Jak poni¿ej dla aukcji angielskiej
	// TODO Przerobiæ na abstract i zaimplementowaæ w poszczególnych klasach aukcji.
	public final boolean propose(String bidder, BigDecimal bid) {
		if (isFinished())
			return false;
		else {
			if (isBestBid(bid)){
				// TODO Tutaj setPrice MUSI byæ przed setMaxBid
				// inaczej wypierdzieli siê aukcja drugiej ceny
				// Kurwa, a mo¿e to zmieniæ ?
				setPrice(bid);
				setMaxBid(bid);
				setMaxBidder(bidder);
				return true;
			} else
				return false;
		}
	}
	// metoda okreœla, czy z³o¿ona oferta jest lepsza od obowi¹zuj¹cej
	protected abstract boolean isBestBid(BigDecimal bid);
	
	protected void setMaxBidder(String bidder) {
		maxBidder = bidder;		
	}
	public BigDecimal getPrice() {
		//return Float.valueOf(getAd().getPriceInt()+BDC.FPOINT+getAd().getPriceDec()).floatValue();
		return this.price;
	}
	protected void setPrice(BigDecimal bid) {
		 this.price = bid;
		 String [] p = this.price.toPlainString().split(BDC.REGEXFPOINT);
		 if (p[1].length() >= 2) {
			 getAd().setPriceDec(p[1].substring(0, 2));
			 getAd().setPriceInt(p[0]);
		 } else {
			 getAd().setPriceDec(p[1]+"0");
			 getAd().setPriceInt(p[0]);
		 }		 
	}
	public void setReductionStep(BigDecimal reductionStep) {
		this.reductionStep = reductionStep;
	}
	public BigDecimal getReductionStep() {
		return reductionStep;
	}
	public void setEndPrice(BigDecimal bigDecimal) {
		this.endPrice = bigDecimal;
	}
	public BigDecimal getEndPrice() {
		return endPrice;
	}
	public void setMaxBid(BigDecimal maxBid) {
		this.maxBid = maxBid;
	}
}
