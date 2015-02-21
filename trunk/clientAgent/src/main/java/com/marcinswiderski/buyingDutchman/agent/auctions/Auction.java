package com.marcinswiderski.buyingDutchman.agent.auctions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BDC.AuctionTypes;

public abstract class Auction implements Serializable {
	private static final long serialVersionUID = 1L;
	private final AuctionDetails ad;
	private final AuctionItem ai;
	private final String an;
	/** wystawca */
	private final String auctioneer;
	protected String bestBidInt;
	protected String bestBidDec;
	protected String bestBidder;
	private boolean finished;
	private BigDecimal reductionStep;
	private BigDecimal endPrice;
	protected BigDecimal price;
	protected BigDecimal bestBid;
	private ArrayList bidsHistory;
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
		this.bestBid = null;
		this.bestBidInt = null;
		this.bestBidDec = null;
		setBestBidder(null);
		setPrice(computePrice());
		setEndPrice(computeEndPrice());
		double ticks = ((AD.getStartMiliSeconds())/BDC.TICK);
		setReductionStep(getPrice().subtract(getEndPrice()).divide(new BigDecimal(ticks), BDC.BigDecimalScale, BDC.BigDecimalRounding));
		bidsHistory = new ArrayList();
	}
	private void setPrice(BigDecimal computePrice) {
		this.price = computePrice;
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
	public double getMiliSecondsLeft() {
		return getAd().getMiliSecondsLeft();
	}
	protected void setMiliSecondsLeft(double d) {
		getAd().setMiliSecondsLeft(d);
	}
	//Sprawdza warunek ko�ca, spowodowany z�o�eniem oferty.
	//Dla ka�dego typu aukcji mo�e by� inny, wi�c jest zadeklarowany jako abstract
	protected abstract boolean checkFinishByOffer();
	//Wykonuje czynno�ci aukcji zwi�zan� z zegarem, takie jak obni�enie ceny.
	//U�ywana w aukcjach manipuluj�cych cen� wg wskaza� zegara. W tej aplikacji w holenderskiej i groszowej.
	protected void performAuctionTick(){};
	public void performFinish(){
		setFinished(true);
		setMiliSecondsLeft(0);
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
		setMiliSecondsLeft(getMiliSecondsLeft()-BDC.TICK);
		if (getMiliSecondsLeft() >= 0) {
			performAuctionTick();
			if(checkFinishByOffer())
				performFinish();
		} else {
			performFinish();
		}
	}
	//odpalane na aukcji po stronie agenta obserwuj�cego
	public void onTick(String priceInt, String priceDec, double miliSecondsLeft, String bestBid, String bestBidder) {	
		if (isFinished())
			return;
		getAd().setPriceDec(priceDec);
		getAd().setPriceInt(priceInt);
		setPrice(new BigDecimal(priceInt+BDC.FPOINT+priceDec));
		setMiliSecondsLeft(miliSecondsLeft);
		if (!bestBid.equalsIgnoreCase(BDC.NONESTRING))
			setBestBid(bestBid);
		if (!bestBidder.equalsIgnoreCase(BDC.NONESTRING))
			setBestBidder(bestBidder);
	}
	private void setBestBid(String bestBid) {
		this.bestBid = new BigDecimal(bestBid);
		String [] i = this.bestBid.toPlainString().split(BDC.REGEXFPOINT);
		if (i[1].length() >= 2) {
			bestBidDec = i[1].substring(0, 2);
			bestBidInt = i[0];
		} else {
			bestBidDec = i[1]+"0";
			bestBidInt = i[0];
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
	public final String getBestBidString() {
		if (bestBid != null)
			return bestBid.toString();		
		else
			return BDC.NONESTRING;
	}
	public final BigDecimal getBestBid() {
		return bestBid;
	}
	public String getBestBidder() {	
		if (bestBidder != null && !bestBidder.equalsIgnoreCase(""))
			return bestBidder;
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
		case 5: return getMiliSecondsLeft()/BDC.ONE_SECOND;
		case 6: return getPriceInt()+BDC.POINT+getPriceDec();
		case 7: return BDC.NONESTRING;// 2012-08-15 Jak sobie przypomn�, po co ma by�, to b�d� pokazywa� MSW// getBestBidString();
		case 8: return getBestBidder();
		case 9: return getTitle();
		}
		return null;
	}
	public AuctionTypes getType() {
		return getAd().getType();
	}
	protected String getSubCategory() {
		return ai.getSubCategory().toString();
	}
	protected String getCategory() {		
		return ai.getCategory().toString();
	}
	public Object getFinishedColumnValue(int col) {
		switch (col) {
		case 0: return getAN();
		case 1: return getType();
		case 2: return getAuctioneer();
		case 3: return getCategory();
		case 4: return getSubCategory();
		case 5: return getMiliSecondsLeft()/BDC.ONE_SECOND;
		case 6: return getPriceInt()+BDC.POINT+getPriceDec();
		case 7: return BDC.NONESTRING;// 2012-08-15 Jak sobie przypomn�, po co ma by�, to b�d� pokazywa� MSW// getBestBidString();
		case 8: return getBestBidder();
		case 9: return getTitle();
		}
		return null;
	}
	// Mo�na by da� to jako abstract, ale tak naprawd� tylko w aukcji holenderskiej jest opcja kup teraz.
	// Dlatego te� zostawiam tu domy�lne zachowanie, a przes�aniam w holenderskiej.
	public boolean buyNow(String bestBidder) {
		return false;
	}
	public void buyNow(String bestBidder, String bestBidInt, String bestBidDec) {
		setBestBidder(bestBidder);
		setBestBidInt(bestBidInt);
		setBestBidDec(bestBidDec);
		setBestBid(bestBidInt+BDC.FPOINT+bestBidDec);
		return;
	}
	private void setBestBidDec(String bestBidDec2) {
		bestBidDec = bestBidDec2;
	}
	private void setBestBidInt(String bestBidInt2) {
		bestBidInt = bestBidInt2;
	}
	public boolean propose(String bidder, BigDecimal bid) {return false;};
	
	// metoda okre�la, czy z�o�ona oferta jest lepsza od obowi�zuj�cej
	protected abstract boolean isBestBid(BigDecimal bid);
	
	private final void setBestBidder(String bidder) {
		bestBidder = bidder;		
	}
	public BigDecimal getPrice() {
		return this.price;
	}
	protected final void setNewPrice(BigDecimal bid, String bidder) {
		setBestBidder(bidder);
		setNewPrice(bid);
		//Dodaje bidy do aukcji lokalnej, ale nie dodaje do aukcji pokazywanych.
		bidsHistory.add(new Bid(bidder, bid));
	}
	protected void setNewPrice(BigDecimal bid) {
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
	protected void setBestBid(BigDecimal bestBid, String bidder) {
		setBestBid(bestBid);
		setBestBidder(bidder);
	}
	private void setBestBid(BigDecimal bestBid) {
		this.bestBid = bestBid;
	}
	public String getCfpContent() {		
		String tmp = getAN()
		+ BDC.SEPARATOR + getPriceInt() 
		+ BDC.SEPARATOR + getPriceDec() 
		+ BDC.SEPARATOR + getMiliSecondsLeft()
		+ BDC.SEPARATOR + getBestBidString()
		+ BDC.SEPARATOR + getBestBidder()
		+ BDC.SEPARATOR + getBidsHistory().size()
		+ BDC.SEPARATOR 
		;
		/*int s = getBidsHistory().size();*/
		if ( getBidsHistory().size() < 1 )
			tmp += BDC.NONESTRING;
		for (int i = 0; i < getBidsHistory().size(); i++ )
		{
			tmp += getBidsHistory().get(i).toString();
		}
		return tmp;
	}
	public ArrayList getBidsHistory() {
		return bidsHistory;
	}
	@SuppressWarnings("unchecked")
	public void setBidsHistory(String a) {
		String [] bids = a.split(BDC.HISTORY_SEPARATOR);
		bidsHistory.clear();
		for (int i = 1 ; i < bids.length; ) {
			bidsHistory.add(new Bid(bids[i++], new BigDecimal(bids[i++])));
		}
	}
}
