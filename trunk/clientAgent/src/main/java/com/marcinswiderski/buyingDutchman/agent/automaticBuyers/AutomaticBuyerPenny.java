package com.marcinswiderski.buyingDutchman.agent.automaticBuyers;

import com.marcinswiderski.buyingDutchman.agent.auctions.Auction;
import com.marcinswiderski.buyingDutchman.agent.client.BDC;
import com.marcinswiderski.buyingDutchman.agent.client.BDC.AuctionTypes;
import com.marcinswiderski.buyingDutchman.agent.client.BuyingDutchmanAgent;
import jade.lang.acl.ACLMessage;

import java.math.BigDecimal;
//TODO sprawdzi�, czy jest u�ywane
public class AutomaticBuyerPenny extends AutomaticBuyer {

	private int rounds;
	
	public AutomaticBuyerPenny(BuyingDutchmanAgent agent, BigDecimal bid,
			int rounds) {
		super(agent, bid);
		this.type = AuctionTypes.GROSZOWA;
		this.rounds = rounds;
	}

	@Override
	public boolean performDuty(Auction auction) {
		// TODO ? Do zmiany. Nale�y wys�a� wiadomo�� do auction.getAuctioneer() z poleceniem dodania AutomaticBid
		ACLMessage request = agent.getRequestMsg();
		ACLMessage [] mes = {request};
		agent.fillUpReceivers(mes, agent.getAgent(auction.getAuctioneer()));
		request.setContent(BDC.AUTOMATIC_BID+BDC.SEPARATOR+auction.getAN()+BDC.SEPARATOR+rounds+BDC.SEPARATOR+bid.toPlainString());
		agent.send(request);
		return false;
	}

}
