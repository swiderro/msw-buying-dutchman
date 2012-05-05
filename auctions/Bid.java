package auctions;

import java.io.Serializable;
import java.math.BigDecimal;

public class Bid  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String bidder;
	private final BigDecimal price;
	
	public Bid(String bidder, BigDecimal price) {
		this.bidder = bidder;
		this.price = price;
	}

	public String getBidder() {
		return bidder;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Object getColumnValue(int col) {
		switch (col) {
		case 0:
			return bidder;
		case 1:
			return price;
		default:
			return null;
		}
	}
}