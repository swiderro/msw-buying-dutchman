package buyingDutchman;

public interface AuctionFactoryInterface {
	public Auction createAuction(AuctionDetails AD, AuctionItem AI, String AN, String Auctioneer);
}
