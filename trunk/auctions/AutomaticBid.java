package auctions;

import java.math.BigDecimal;

public class AutomaticBid {
	
	private short bidCounter;
	private BigDecimal startPrice;
	private short bidsUsed;
	
	public AutomaticBid(short bidCounter, BigDecimal startPrice) {
		this.bidCounter = bidCounter;
		this.startPrice = startPrice;
		this.bidsUsed = 0;
	}
	
	public short getBidCounts() {
		return bidCounter;
	}
	public BigDecimal getStartPrice() {
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
