package auctions;

import java.io.Serializable;
import java.math.BigDecimal;

public class AutomaticBid implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bidCounter;
	private BigDecimal startPrice;
	private int bidsUsed;
	
	public AutomaticBid(int bidCounter, BigDecimal startPrice) {
		this.bidCounter = bidCounter;
		this.startPrice = startPrice;
		this.bidsUsed = 0;
	}
	
	public int getBidCounts() {
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

	public int getBidsUsed() {
		return bidsUsed;
	}
	
}
