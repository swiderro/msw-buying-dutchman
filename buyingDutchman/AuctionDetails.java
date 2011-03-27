package buyingDutchman;

import java.io.Serializable;

public class AuctionDetails implements Serializable{
	private static final long serialVersionUID = 1L;
	private final String StartPriceInt;
	private final String StartPriceDec;
	private final String EndPriceInt;
	private final String EndPriceDec;	
	private String PriceInt;
	private String PriceDec;
	private final String Title;
	private int TicksLeft;
	private final int StartTicks;
	
	public AuctionDetails(String startPriceInt, String startPriceDec, String endPriceInt,
			String endPriceDec, int ticks, String title) {
		String i = endPriceDec;
		if (i == null || i.equalsIgnoreCase("") || i.equalsIgnoreCase("0") || i.equalsIgnoreCase("00"))
			i = "00";
		else if (i.equalsIgnoreCase("1"))
			i = "10";
		else if (i.equalsIgnoreCase("2"))
			i = "20";
		else if (i.equalsIgnoreCase("3"))
			i = "30";
		else if (i.equalsIgnoreCase("4"))
			i = "40";
		else if (i.equalsIgnoreCase("5"))
			i = "50";
		else if (i.equalsIgnoreCase("6"))
			i = "60";
		else if (i.equalsIgnoreCase("7"))
			i = "70";
		else if (i.equalsIgnoreCase("8"))
			i = "80";
		else if (i.equalsIgnoreCase("9"))
			i = "90";
		EndPriceDec = i;
		i = endPriceInt;
		if (i == null || i.equalsIgnoreCase(""))
			i = "0";
		EndPriceInt = i;
		i = startPriceDec;
		if (i == null || i.equalsIgnoreCase("") || i.equalsIgnoreCase("0") || i.equalsIgnoreCase("00"))
			i = "00";
		else if (i.equalsIgnoreCase("1"))
			i = "10";
		else if (i.equalsIgnoreCase("2"))
			i = "20";
		else if (i.equalsIgnoreCase("3"))
			i = "30";
		else if (i.equalsIgnoreCase("4"))
			i = "40";
		else if (i.equalsIgnoreCase("5"))
			i = "50";
		else if (i.equalsIgnoreCase("6"))
			i = "60";
		else if (i.equalsIgnoreCase("7"))
			i = "70";
		else if (i.equalsIgnoreCase("8"))
			i = "80";
		else if (i.equalsIgnoreCase("9"))
			i = "90";
		StartPriceDec = i;
		i = startPriceInt;
		if (i == null || i.equalsIgnoreCase(""))
			i = "0";
		StartPriceInt = i;
		PriceInt = StartPriceInt;
		PriceDec = StartPriceDec;
		Title = title;
		StartTicks = ticks;
		TicksLeft = ticks;		
	}
	public String getStartPriceInt() {
		return StartPriceInt;
	}
	public String getStartPriceDec() {
		return StartPriceDec;
	}
	public String getEndPriceInt() {
		return EndPriceInt;
	}
	public String getEndPriceDec() {
		return EndPriceDec;
	}
	public String getPriceInt() {
		return PriceInt;
	}
	public void setPriceInt(String priceInt) {
		PriceInt = priceInt;
	}
	public String getPriceDec() {
		return PriceDec;
	}
	public void setPriceDec(String priceDec) {
		PriceDec = priceDec;
	}
	public String getTitle() {
		return Title;
	}
	public int getTicksLeft() {
		return TicksLeft;
	}
	public void setTicksLeft(int ticksLeft) {
		TicksLeft = ticksLeft;
	}
	public int getStartTicks() {
		return StartTicks;
	}
}
