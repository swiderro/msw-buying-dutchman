package automaticBuyers;

import jade.lang.acl.ACLMessage;

import java.math.BigDecimal;

import buyingDutchmanClient.BDC;
import buyingDutchmanClient.BuyingDutchmanAgent;
import buyingDutchmanClient.BDC.AuctionTypes;
import auctions.Auction;

public class AutomaticBuyerPenny extends AutomaticBuyer {

	private int upBids;
	
	public AutomaticBuyerPenny(BuyingDutchmanAgent agent, BigDecimal bid,
			BigDecimal upBid) {
		super(agent, bid);
		this.type = AuctionTypes.GROSZOWA;
		upBids = upBid.intValue();
	}

	@Override
	public boolean performDuty(Auction auction) {
		// TODO Do zmiany. Nale¿y wys³aæ wiadomoœæ do auction.getAuctioneer() z poleceniem dodania AutomaticBid
		ACLMessage request = agent.getRequestMsg();
		ACLMessage [] mes = {request};
		agent.fillUpReceivers(mes, agent.getAgent(auction.getAuctioneer()));
		request.setContent(BDC.AUTOMATIC_BID+BDC.SEPARATOR+auction.getAN()+BDC.SEPARATOR+upBids+BDC.SEPARATOR+bid.toPlainString());
		agent.send(request);
		return false;
	}

}
