package com.marcinswiderski.buyingDutchman.agent.client;

import java.math.BigDecimal;
import java.util.HashMap;
/** 
 * Klasa zawieraj�ca warto�ci sta�e, u�ywane w systemie. Warto�ci u�ywane g��wnie w klasach {@link BuyingDutchmanGui} i {@link BuyingDutchmanAgent}
 */
public final class BDC {
	/** Typ wyliczeniowy reprezentuj�cy rodzaje aukcji obs�ugiwane przez system.*/
	public enum AuctionTypes {
		HOLENDERSKA, ANGIELSKA, DRUGIEJ_CENY, PRZETARG, GROSZOWA
	}
	//BuyingDutchman Consts
	/** Nazwy kolumn dla tabeli trwaj�cych aukcji */
	public static final String[] AUCTIONSCOLUMNNAMES = {
		"# aukcji", "Typ", "Wystawca", "Kategoria", "Podkategoria", "Pozo. czas", "Cena", "Max oferta", "Oferent", "Tytu�"		
	};
	/** Szeroko�ci kolumn dla tabeli trwaj�cych aukcji */
	public static final int[] AUCTIONSCOLUMNWIDTH = {
		50, 80, 60, 60, 60, 60, 40, 60, 70, 210
	};
	/** Nazwy kolumn dla tabeli zako�czonych aukcji */
	public static final String[] FINISHEDAUCTIONSCOLUMNNAMES = {
		"# aukcji", "Typ", "Wystawca", "Kategoria", "Podkategoria", "Pozo. czas", "Cena", "Max oferta", "Oferent", "Tytu�"		
	};
	/** Szeroko�ci kolumn dla tabeli zako�czonych aukcji */
	public static final int[] FINISHEDAUCTIONSCOLUMNWIDTH = {
		50, 80, 60, 60, 60, 60, 40, 60, 70, 210
	};
	/** Nazwy kolumn dla tabeli historii ofert */
	public static final String [] AUCTIONBIDSCOLUMNNAMES = {
		"Oferent", "Cena"
	};
	/** Szeroko�ci kolumn dla tabeli historii ofert */
	public static final int[] AUCTIONBIDSCOLUMNWIDTH = {
		50, 50
	};
	/** Nazwa ontologii u�ywanej w wysy�anych wiadomo�ciach */
	public static final String BDONTO = "bd-ontology";
	public static final String SDTYPELISTENER = "bd-agent";
	public static final String SDNAMELISTENER = "Dutch Auction Agent";	
	public static final String SDTYPEAUCTION = "bd-auction";
	public static final String SDNAMEAUCTION = "Agent Dutch Auctions";
	public static final String SDPNAME = "Dutch Auction";
	/** D�ugo�� okresu zegara wyra�ona w milisekundach */
	public static final double ONE_SECOND = 1000.0;
	public static final int TICK = 1000;
	public static final int GUIAUCTION = 0;
	public static final int GUIBUYNOW = 1;
	public static final int GUIWAITBUY = 2;
	public static final int GUICLOSE = 3;
	public static final int GUIPROPOSITION = 4;
	//public static final float TICKSPERSECOND = 1000/TICK;
	/** Ilo�� okres�w zegara na sekund�. Wykorzystywane w {@link BuyingDutchmanGui#countMiliSeconds}*/
	public static final double TICKSPERSECOND = ONE_SECOND/TICK;
	public static final String PROTOCOLS = "fipa-request fipa-Contract-Net fipa-subscribe fipa-agree fipa-proposal fipa-cfp fipa-accept-proposal";
	public static final String POSTFIX = ":";
	public static final String SEPARATOR = ";";
	public static final String HISTORY_SEPARATOR = "#";
	public static final String AUCTIONS = "auctions" + POSTFIX;
	public static final String AUCTION = "auction" + POSTFIX;
	public static final String AUCTION_INFO = "auction info";
	public static final String AUTOMATIC_BID = "automatic bid";
	public static final String PRICESTARTDEF = "0";
	public static final String DURATIONSTARTDEF = "0";
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
	public static final int WAITBUYUPBIDPARAMNUM = 3;
	public static final int WAITBUYROUNDSPARAMNUM = 4;	
	public static final int PROPOSEAUCTIONNRPARAMNUM = 0;
	public static final int PROPOSEAUCTIONEERPARAMNUM = 1;
	public static final int PROPOSEBIDPARAMNUM = 2;
	public static final String BID = "BID"+POSTFIX;
	public static final int AUCTIONPARAMNUM = 0;	
	//BuyingDutchmanGui Consts
	//FRAME CONSTS
	public static final String FRAMETITLE = "Buying Dutchman";  
	public static final int FRAMEWIDTH = 900;
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
	public static final String PROPOSITIONBUTTON = "Z�� ofert�";  
	public static final String PROPOSITIONBUTTONLABEL = "Z�� ofert�";  
	//LABELS & TEXTFIELDS CONSTS
	public static final String DECIMALPOINT = ",";  
	public static final int PRICEINTCOLUMNS = 4;
	public static final int PRICEDECCOLUMNS = 2;
	public static final String STARTINGPRICETEXTFIELD = "Cena wywo�awcza";
	public static final int DURATIONCOLUMNSNUMBER = 5;
	public static final String DURATIONHOURSTEXTFIELD = "Godziny";
	public static final String DURATIONMINUTESTEXTFIELD = "Minuty";
	public static final String DURATIONSECONDSTEXTFIELD = "Sekundy";	
	public static final int AUCTIONTITLECOLUMNSNUMBER = 47;
	public static final String AUCTIONTITLELABEL = "Tytu� aukcji";
	public static final String AUCTIONTITLETEXTFIELD = "Tytul aukcji";
	public static final String MINIMUMPRICELABEL = "Cena minimalna";
	public static final String AUCTIONDURATIONLABEL = "Czas trwania";
	public static final String AUCTIONDESCRIPTIONLABEL = "Opis aukcji";
	public static final String HOURSLABEL = "Godziny";
	public static final String MINUTESLABEL = "Minuty";
	public static final String SECONDSLABEL = "Sekundy";
	public static final String BEGINAUCTION = "Rozpocznij aukcj�";
	public static final String AUCTIONEER = "Wystawca";
	public static final int AUCTIONEERCOLUMNS = 5;
	public static final String AUCTIONNUMBER = "Nr aukcji";
	public static final int AUCTIONNUMBERCOLUMNS = 4;
	public static final String FINISHEDAUCTIONSTAB = "Zako�czone aukcje";
	public static final String CATEGORYLABEL = "Kategoria";
	public static final String AUCTIONTYPELABEL = "Typ aukcji";
	public static final String UPBID = "Podbijaj o:";
	//CATEGORIES
	private static final String ANTIQUES = "Antyki";
	private static final String COLLECTIONS = "Kolekcje";
	private static final String FLOWERS = "Kwiaty";
	private static final String GROCERIES = "Art. spo�.";
	private static final String TRANSMISION = "Przesy�";
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
			String[] flowers = {"Tulipany", "R�e", "Lilie"};
			CATEGORY_SUBCATEGORY.put(FLOWERS, flowers);
			String[] collectables = {"Monety", "Znaczki"};
			CATEGORY_SUBCATEGORY.put(COLLECTIONS, collectables);
			String[] groceries = {"Ryby", "Owoce morza", "Warzywa"};
			CATEGORY_SUBCATEGORY.put(GROCERIES, groceries);
			String[] transmitables = {"En. elektryczna", "Transfer danych", "Cz�stotliwo��"};
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
	public static final String EMPTYCOMMANDPANELTEXT = "Wybierz aukcj� z powy�szej listy.";
	public static final String STAYINAUCTION = "Ilo�� rund w aukcji";
	public static final int ROUNDSQUANTITYCOLUMNS = 6;
}