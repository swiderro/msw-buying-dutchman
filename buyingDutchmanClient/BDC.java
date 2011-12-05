package buyingDutchmanClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public final class BDC {
	public enum AuctionTypes {
		HOLENDERSKA, ANGIELSKA, DRUGIEJ_CENY, PRZETARG, GROSZOWA
	}
	//BuyingDutchman Consts
	public static final String[] AUCTIONSCOLUMNNAMES = {
		"# aukcji", "Typ", "Wystawca", "Kategoria", "Podkategoria", "Pozo. czas", "Cena", "Max oferta", "Oferent", "Tytu³"		
	};
	public static final String[] FINISHEDAUCTIONSCOLUMNNAMES = {
		"# aukcji", "Typ", "Wystawca", "Kategoria", "Podkategoria", "Pozo. czas", "Cena", "Max oferta", "Oferent", "Tytu³"		
	};
	public static final int[] AUCTIONSCOLUMNWIDTH = {
		50, 80, 60, 60, 60, 60, 40, 60, 70, 210
	};
	public static final int[] FINISHEDAUCTIONSCOLUMNWIDTH = {
		50, 80, 60, 60, 60, 60, 40, 60, 70, 200
	};
	public static final String BDONTO = "bd-ontology";
	public static final String SDTYPELISTENER = "bd-agent";
	public static final String SDNAMELISTENER = "Dutch Auction Agent";	
	public static final String SDTYPEAUCTION = "bd-auction";
	public static final String SDNAMEAUCTION = "Agent Dutch Auctions";
	public static final String SDPNAME = "Dutch Auction";
	public static final int TICK = 1000;
	public static final int GUIAUCTION = 0;
	public static final int GUIBUYNOW = 1;
	public static final int GUIWAITBUY = 2;
	public static final int GUICLOSE = 3;
	public static final int GUIPROPOSITION = 4;
	public static final float TICKSPERSECOND = 1000/TICK;
	public static final String PROTOCOLS = "fipa-request fipa-Contract-Net fipa-subscribe fipa-agree fipa-proposal fipa-cfp fipa-accept-proposal";
	public static final String POSTFIX = ":";
	public static final String AUCTIONS = "auctions" + POSTFIX;
	public static final String AUCTION = "auction" + POSTFIX;
	public static final String PRICESTARTDEF = "0";
	public static final String DURATIONSTARTDEF = "0";
	public static final String SEPARATOR = " ";		
	public static final String POINT = ",";
	public static final String FPOINT = ".";
	public static final String FINISHED = "finished" + POSTFIX;
	public static final int POINTPRECISION = 2;
	public static final String REGEXPOINT = ",";
	public static final String REGEXFPOINT = "\\.";
	public static final String PROPOSEBUYNOW = "propose_buy_now" + POSTFIX;	
	public static final int BUYNOWAUCTIONEERPARAMNUM = 0;
	public static final int BUYNOWAUCTIONNRPARAMNUM = 1;
	public static final String NONESTRING = "-";
	public static final int WAITBUYAUCTIONNRPARAMNUM = 0;
	public static final int WAITBUYAUCTIONEERPARAMNUM = 1;
	public static final int WAITBUYBIDPARAMNUM = 2;
	public static final int PROPOSEAUCTIONNRPARAMNUM = 0;
	public static final int PROPOSEAUCTIONEERPARAMNUM = 1;
	public static final int PROPOSEBIDPARAMNUM = 2;
	public static final String BID = "BID"+POSTFIX;
	public static final int AUCTIONPARAMNUM = 0;	
	//BuyingDutchmanGui Consts
	//FRAME CONSTS
	public static final String FRAMETITLE = "Buying Dutchman";  
	public static final int FRAMEWIDTH = 850;
	public static final int FRAMEHEIGHT = 400;
	//TABS CONSTS
	public static final String AUCTIONSTAB = "Aukcje";
	public static final String AUCTIONTAB = "Wystaw";
	//PANELS CONSTS
	public static final String BIDDERPRICE = "Twoja cena";  
	//BUTTONS CONSTS
	public static final String WAITBUYBUTTON = "Oczekuj i kup";
	public static final String WAITBUYBUTTONLABEL = "Oczekuj i kup";
	public static final String BUYNOWBUTTON = "Kup teraz";
	public static final String BUYNOWBUTTONLABEL = "Kup teraz";
	public static final String PROPOSITIONBUTTON = "Z³ó¿ ofertê";  
	public static final String PROPOSITIONBUTTONLABEL = "Z³ó¿ ofertê";  
	//LABELS & TEXTFIELDS CONSTS
	public static final String DECIMALPOINT = ",";  
	public static final int PRICEINTCOLUMNS = 4;
	public static final int PRICEDECCOLUMNS = 2;
	public static final String STARTINGPRICETEXTFIELD = "Cena wywo³awcza";
	public static final int DURATIONCOLUMNSNUMBER = 5;
	public static final String DURATIONHOURSTEXTFIELD = "Godziny";
	public static final String DURATIONMINUTESTEXTFIELD = "Minuty";
	public static final String DURATIONSECONDSTEXTFIELD = "Sekundy";	
	public static final int AUCTIONTITLECOLUMNSNUMBER = 47;
	public static final String AUCTIONTITLELABEL = "Tytu³ aukcji";
	public static final String AUCTIONTITLETEXTFIELD = "Tytul aukcji";
	public static final String MINIMUMPRICELABEL = "Cena minimalna";
	public static final String AUCTIONDURATIONLABEL = "Czas trwania";
	public static final String AUCTIONDESCRIPTIONLABEL = "Opis aukcji";
	public static final String HOURSLABEL = "Godziny";
	public static final String MINUTESLABEL = "Minuty";
	public static final String SECONDSLABEL = "Sekundy";
	public static final String BEGINAUCTION = "Rozpocznij aukcjê";
	public static final String AUCTIONEER = "Wystawca";
	public static final int AUCTIONEERCOLUMNS = 5;
	public static final String AUCTIONNUMBER = "Nr aukcji";
	public static final int AUCTIONNUMBERCOLUMNS = 4;
	public static final String FINISHEDAUCTIONSTAB = "Moje aukcje";
	public static final String CATEGORYLABEL = "Kategoria";
	public static final String AUCTIONTYPELABEL = "Typ aukcji";
	//CATEGORIES
	private static final String ANTIQUES = "Antyki";
	private static final String COLLECTIONS = "Kolekcje";
	private static final String FLOWERS = "Kwiaty";
	private static final String GROCERIES = "Art. spo¿.";
	private static final String TRANSMISION = "Przesy³";
	public static final String[] CategoryList = {
		FLOWERS, GROCERIES, TRANSMISION, ANTIQUES, COLLECTIONS
	};
	public static final String SUBCATEGORYLABEL = "Podkategoria";
	private static HashMap CATEGORY_SUBCATEGORY;
	private static HashMap getCategorySubcategory() {
		if (CATEGORY_SUBCATEGORY == null) {
			CATEGORY_SUBCATEGORY = new HashMap();
			String[] antiques = {"Meble", "Sztuka", "Zastawa"};
			CATEGORY_SUBCATEGORY.put(ANTIQUES, antiques);
			String[] flowers = {"Tulipany", "Ró¿e", "Lilie"};
			CATEGORY_SUBCATEGORY.put(FLOWERS, flowers);
			String[] collectables = {"Monety", "Znaczki"};
			CATEGORY_SUBCATEGORY.put(COLLECTIONS, collectables);
			String[] groceries = {"Ryby", "Owoce morza", "Warzywa"};
			CATEGORY_SUBCATEGORY.put(GROCERIES, groceries);
			String[] transmitables = {"En. elektryczna", "Transfer danych", "Czêstotliwoœæ"};
			CATEGORY_SUBCATEGORY.put(TRANSMISION, transmitables);
		}
		return CATEGORY_SUBCATEGORY;
	}
	public static String[] getSubCategoryList(String category) {
		return (String[]) getCategorySubcategory().get(category);
	}
	//AUCTION TYPES
	public static final String DUTCH = "Holenderska";
	public static final String ENGLISH = "Angielska";
	public static final String SECOND_PRICE = "Drugiej ceny";
	public static final String BIDDING = "Przetarg";
	public static final String PENNY = "Groszowa";
	public static final String[] AuctionTypesStrings = {
		DUTCH, ENGLISH, SECOND_PRICE, BIDDING, PENNY
	};
	public static final double Grosz = 0.01;
	public static final int BigDecimalScale = 16;
	public static final int BigDecimalRounding = BigDecimal.ROUND_HALF_EVEN;
}