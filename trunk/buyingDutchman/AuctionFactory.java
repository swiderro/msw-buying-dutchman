package buyingDutchman;

public class AuctionFactory implements AuctionFactoryInterface {
	public Auction createAuction(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer) {
		String type = AD.getType();
		Auction a;
		if (type.equalsIgnoreCase(BDC.DUTCH))
			a = new DutchAuction(AD, AI, AN, Auctioneer);
		else if (type.equalsIgnoreCase(BDC.ENGLISH))
			a = new EnglishAuction(AD, AI, AN, Auctioneer);
		else if (type.equalsIgnoreCase(BDC.SECOND_PRICE))
			a = new SecondPriceAuction(AD, AI, AN, Auctioneer);
		else if (type.equalsIgnoreCase(BDC.BIDDING))
			a = new Bidding(AD, AI, AN, Auctioneer);
		else
			a= new NullAuction(AD, AI, AN, Auctioneer);
		return a;
	}
}
