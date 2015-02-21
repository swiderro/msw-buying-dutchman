package com.marcinswiderski.buyingDutchman.agent.auctions;

import com.marcinswiderski.buyingDutchman.agent.client.BDC.AuctionTypes;

public class AuctionFactory implements AuctionFactoryInterface {
	public Auction createAuction(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		AuctionTypes type = AD.getType();
		Auction a;
		switch(type) {
		case HOLENDERSKA:
			a = new AuctionDutch(AD, AI, AN, Auctioneer);
			break;
		case ANGIELSKA:
			a = new AuctionEnglish(AD, AI, AN, Auctioneer);
			break;
		case DRUGIEJ_CENY:
			a = new AuctionSecondPrice(AD, AI, AN, Auctioneer);
			break;
		case PRZETARG:
			a = new AuctionBidding(AD, AI, AN, Auctioneer);
			break;
		case GROSZOWA:
			a = new AuctionPenny(AD, AI, AN, Auctioneer);
			break;
		default:
			a= new AuctionNull(AD, AI, AN, Auctioneer);
		}
		return a;
	}
}
