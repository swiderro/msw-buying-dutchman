package auctions;

public class AutomaticBid {
	
	private short bidCounter;
	private float startPrice;
	private short bidsUsed;
	
	public AutomaticBid(short bidCounter, float startPrice) {
		this.bidCounter = bidCounter;
		this.startPrice = startPrice;
		this.bidsUsed = 0;
	}
	
	public short getBidCounts() {
		return bidCounter;
	}
	public float getStartPrice() {
		return startPrice;
	}

	public boolean useBid() {
		if (this.bidCounter > 0) {
			this.bidsUsed++;
			this.bidCounter--;
			return true;
		} else
			return false;
	}

	public short getBidsUsed() {
		return bidsUsed;
	}
	
}
