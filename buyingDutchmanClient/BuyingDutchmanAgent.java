package buyingDutchmanClient;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
import java.util.Hashtable;
import javax.swing.table.AbstractTableModel;

import auctions.Auction;
import automaticBuyers.AutomaticBuyer;
import automaticBuyers.AutomaticBuyerFactory;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

/**
 * @author Marcin Lucjan Swiderski FEIT WUT
 */
public class BuyingDutchmanAgent extends GuiAgent {	

	/** Default serialization const */
	private static final long serialVersionUID = 1L;
	/** Agent name, not fully qualified */
	private String nickName;
	/** Agent's GUI */
	private BuyingDutchmanGui myGui;
	/** Next auction ID */
	private int nextAuctionNumber;
	/** Active auctions auctioned by this agent */
	private Hashtable ownActiveAuctions;
	/** Auctions auctioned by this agent that has just finished */
	private Hashtable ownFreshlyFinishedAuctions;
	/** Active auctions displayed by the agent */
	private Hashtable shownAuctions;
	/** Row numbers of active auctions displayed by the agent */
	private Vector shownAuctionsRowNumber;
	/** A flag used for refreshing displayed active auctions table*/
	private boolean refreshAuctionsTable;
	/** Table model for active auctions display */
	private AbstractTableModel bdtm;
	/** Used for keeping selection after active auctions table has resized */
	private String selection;
	/** Row numbers of finished auctions displayed by the agent */
	private Vector finishedAuctionsRowNumber;
	/** Finished auctions displayed by the agent */
	private Hashtable finishedAuctions;
	/** Table model for finished auction display */
	private AbstractTableModel bdftm;
	/** A flag used for refreshing displayed finished auctions table*/
	private boolean refreshFinishedAuctionTable;
	/** Auctions the agent is going to bid*/
	private Hashtable automaticBuyers;
	
	protected void setup() {
		//Field initialization
		nickName=getAID().getLocalName();
		ownActiveAuctions = new Hashtable();
		ownFreshlyFinishedAuctions = new Hashtable();
		shownAuctions = new Hashtable();
		shownAuctionsRowNumber = new Vector();
		finishedAuctions = new Hashtable();
		automaticBuyers = new Hashtable();
		finishedAuctionsRowNumber = new Vector();
		nextAuctionNumber = 0;
		refreshAuctionsTable = false;
		refreshFinishedAuctionTable = false;
		bdtm = new BDTableModel();
		bdftm = new BDFinishedTableModel();
		selection = null;
		//Agent registration
		try {
			DFService.register(this, getDFAgentDescription());
		} catch (jade.domain.FIPAAgentManagement.FailureException e) {
			//Already registered, need to deregister
			try {
				DFService.deregister(this);
			} catch (FIPAException e1) {
				//Deregistration failed: maybe it took place in the meanwhile 
				e1.printStackTrace();
				doDelete();
			}
			try {
				//Registration after deregistration
				DFService.register(this, getDFAgentDescription());
			} catch (FIPAException e1) {
				e1.printStackTrace();
				doDelete();				
			}
		} catch (FIPAException e) {
			e.printStackTrace();
			doDelete();
		}
		//GUI initialization
		myGui = new BuyingDutchmanGui(this);
	    myGui.setVisible(true);
	    //Clock behaviour
	    addBehaviour(new AuctionTickBehaviour(this, BDC.TICK));
	    //Messaging service behaviour
	    addBehaviour(new MessageReciever());
	}

	public void doDelete(){
		//GUI disposal
		myGui.setVisible(false);
		myGui.dispose();
		//deregistration
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		super.doDelete();
	}
	
	public void onGuiEvent(GuiEvent ev) {
		if (ev.getType() == BDC.GUIAUCTION) {
			auction((Auction)ev.getParameter(BDC.AUCTIONPARAMNUM));			
		}
		if (ev.getType() == BDC.GUIBUYNOW) {
			buyNow(
				(String)ev.getParameter(BDC.BUYNOWAUCTIONEERPARAMNUM)
				, (String)ev.getParameter(BDC.BUYNOWAUCTIONNRPARAMNUM)
			);			
		}
		if (ev.getType() == BDC.GUIPROPOSITION) {			
			propose(
				(String) ev.getParameter(BDC.PROPOSEAUCTIONNRPARAMNUM)
				, (String) ev.getParameter(BDC.PROPOSEAUCTIONEERPARAMNUM)
				, Float.valueOf((String)ev.getParameter(BDC.PROPOSEBIDPARAMNUM)).floatValue()
			);			
		}
		if (ev.getType() == BDC.GUIWAITBUY) {
			waitAndBuy(
				(String) ev.getParameter(BDC.WAITBUYAUCTIONNRPARAMNUM)
				, (String) ev.getParameter(BDC.WAITBUYAUCTIONEERPARAMNUM)
				, Float.valueOf((String)ev.getParameter(BDC.WAITBUYBIDPARAMNUM)).floatValue()
			);
		}
		if (ev.getType() == BDC.GUICLOSE) {
			doDelete();
		}
	}	

	private void waitAndBuy(String auctionNr, String auctioneer, float bid) {
		if (fraudBid(auctioneer))
			return;
		Auction a = (Auction) shownAuctions.get(auctioneer+BDC.POSTFIX+auctionNr);
		if (a != null) {
			AutomaticBuyer ab = AutomaticBuyerFactory.AutomaticBuyerInstance(this, bid, a.getType());
			if (ab != null) {
				automaticBuyers.put(auctioneer+BDC.POSTFIX+auctionNr,ab);
				ab.performDuty(a);
			}
		} else
			return;
	}

	/**
	 * @return A description of this agent to be registered with the DF
	 **/
	private DFAgentDescription getDFAgentDescription() {
	    ServiceDescription sd = new ServiceDescription();
	    sd.setName(BDC.SDNAMELISTENER);
		sd.setType(BDC.SDTYPELISTENER);
		sd.addOntologies(BDC.BDONTO);
		sd.setOwnership(nickName);
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		dfd.addServices(sd);
		dfd.addOntologies(BDC.BDONTO);
		dfd.addProtocols(BDC.PROTOCOLS);
	    return dfd;
	}

	/**
	 * @return A description of BD agent, to search for when sending an auction details
	 **/
	private DFAgentDescription getLightDFAgentDescription() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.addOntologies(BDC.BDONTO);
	    return dfd;
	}
	
	private DFAgentDescription[] getAgents() {
	    try {
	    	DFAgentDescription[] l = DFService.search(this, getLightDFAgentDescription());
	    	return l;
	    } catch (FIPAException fe) {
	      fe.printStackTrace();	      
	    }
		return null;
	}
	private DFAgentDescription[] getAgent(String name) {
	    try {
	    	DFAgentDescription dfad = getLightDFAgentDescription();
	    	AID aid = new AID(name, false);
	    	dfad.setName(aid);
	    	DFAgentDescription[] l = DFService.search(this, dfad);
	    	return l;
	    } catch (FIPAException fe) {
	      fe.printStackTrace();	      
	    }
		return null;
	}
	public String getNextAuctionNumber(){
		return Integer.toString(nextAuctionNumber++);
	}

	private void auction(Auction a) {
		addBehaviour(new AuctionStartBehaviour(a));
	}
	private boolean fraudBid(String auctioneer) {
		return auctioneer.equalsIgnoreCase(getLocalName());
	}	
	private void propose(String auctionNr, String auctioneer, float bid) {
		if (fraudBid(auctioneer))
			return;
		Auction a = (Auction) shownAuctions.get(auctioneer+BDC.POSTFIX+auctionNr);
		if (a != null) {
			String maxBid = a.getMaxBid();
			if (maxBid.equalsIgnoreCase(BDC.NONESTRING))
				maxBid = "-1";
			if (bid <= Float.valueOf(maxBid).floatValue())
				return;
			//There's still an auction and proposed bid is high enough
			ACLMessage proposition = getProposeMsg();
			ACLMessage [] mes = {proposition};
			fillUpReceivers(mes, getAgent(auctioneer));
			proposition.setContent(BDC.BID+BDC.SEPARATOR+auctionNr+BDC.SEPARATOR+Float.toString(bid));
			send(proposition);
		} else
			return;
	}

	public void buyNow(String auctioneer, String auctionNr) {		
		if (fraudBid(auctioneer))
			return;
		ACLMessage msg = getProposeMsg();
		ACLMessage [] mes = {msg};
		fillUpReceivers(mes, getAgent(auctioneer));
		String content = BDC.PROPOSEBUYNOW + BDC.SEPARATOR	+ auctionNr;
		msg.setContent(content);
		send(msg);
	}

	
	
	private String reducePrices(String content) {
		Iterator i = ownActiveAuctions.values().iterator();
		Hashtable h = new Hashtable();
		while(i.hasNext()) {
			Auction a = (Auction) i.next();			
			if (!a.isFinished()) {
				a.onTick();
				content += BDC.SEPARATOR + a.getAN() 
					+ BDC.SEPARATOR + a.getPriceInt() 
					+ BDC.SEPARATOR + a.getPriceDec() 
					+ BDC.SEPARATOR + a.getTicksLeft()
					+ BDC.SEPARATOR + a.getMaxBid()
					+ BDC.SEPARATOR + a.getMaxBidder()
					;
			} else {
				// There was a bidding
				if (a.getMaxBidFloat() >= 0 && a.getMaxBidFloat() >= a.getPrice()) {
					ACLMessage reply = getAcceptProposalMsg(a);
					ACLMessage [] mes = {reply};
					reply.addReceiver(getAID());
					fillUpReceivers(mes, getAgent(a.getMaxBidder()));
					//reply.setContent();
					send(reply);
				}
				ownFreshlyFinishedAuctions.put(a.getAN(), a);
				h.put(a.getAN(), a);
			}			
		}
		//Has to be this way cause to Hashtable fail-fast
		i = h.values().iterator();
		while (i.hasNext()){
			Auction a = (Auction)i.next();
			ownActiveAuctions.remove(a.getAN());			
		}
		return content;
	}

	// Creates empty cfp message, with sender as this and BDOntology
	private ACLMessage getCfpMsg() {
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		msg.setSender(getAID());
		msg.setOntology(BDC.BDONTO);				
		return msg;
	}
	
	// Creates empty request message, with sender as this and BDOntology
	private ACLMessage getRequestMsg() {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setSender(getAID());
		msg.setOntology(BDC.BDONTO);				
		return msg;
	}
	
	// Creates empty proposal message, with sender as this and BDOntology
	private ACLMessage getProposeMsg() {
		ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
		msg.setSender(getAID());
		msg.setOntology(BDC.BDONTO);				
		return msg;
	}
	
	// Fills up message with known BD Agents
	private void fillUpReceivers(ACLMessage[] messages, DFAgentDescription[] r) {
		if (r != null) {
			for (int j = 0; j < messages.length; j++) {
				for (int i = 0; i < r.length; i++) {
					messages[j].addReceiver(r[i].getName());
				}
			}
		}
		//return messages;
	}
	
	// Behaviour which serves auction start
	private class AuctionStartBehaviour extends OneShotBehaviour {
		private static final long serialVersionUID = 1L;
		private Auction a;
		public AuctionStartBehaviour(Auction au) {
			this.a = au;
		}
		
		@Override
		public void action() {
			ownActiveAuctions.put(a.getAN(), a);
			ACLMessage cfp = getCfpMsg();
			ACLMessage informRef = getInformRefMsg();
			ACLMessage mes [] = {cfp, informRef};
			fillUpReceivers(mes, getAgents());
			try {
				informRef.setContentObject(a);
			} catch (IOException e) {
				e.printStackTrace();
			}						
			cfp.setContent(getSingleCFPContent(a));			
			send(informRef);
			send(cfp);
		}
	}
	
	// Behaviour which serves message recieving
	private class MessageReciever extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;
		public void action() {
			ACLMessage msg = myAgent.receive();
		    if (msg != null) {
		    	parseMessage(msg);
		    	refreshAuctionsTable();
		    	refreshFinishedAuctionsTable();
		    }
			else {
				block();
			}
		}
		private void parseMessage(ACLMessage msg){
			// INFORM_REF Message received. Process it
	    	if (msg.getPerformative() == ACLMessage.INFORM_REF) {
	    		parseInformRefMessage(msg);	    		
	    	}
	    	// INFORM Message received. Process it
	    	if (msg.getPerformative() == ACLMessage.INFORM) {
	    		parseInformMessage(msg);	    		
	    	}
	    	// REQUEST Message recieved. Process it
	    	else if (msg.getPerformative() == ACLMessage.REQUEST) {
	    		parseRequestMessage(msg);
    		}
			// CFP Message received. Process it
	    	else if (msg.getPerformative() == ACLMessage.CFP) {
	    		parseCallForProposalMessage(msg);
	    	}
	    	// PROPOSE Message received. Process it
	    	else if (msg.getPerformative() == ACLMessage.PROPOSE) {
	    		parseProposeMessage(msg);
	    	}
	    	// ACCEPT_PROPOSAL Message received. Process it
	    	else if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
	    		parseAcceptProposalMessage(msg);
	    	}
	    	// REJECT_PROPOSAL Message received. Process it
	    	else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
	    		parseRejectProposalMessage(msg);
	    	}
		}
		private void parseRejectProposalMessage(ACLMessage msg) {
			// So far, so good
		}
		private void parseAcceptProposalMessage(ACLMessage msg) {			
			String content [] = msg.getContent().split(BDC.SEPARATOR);
			String sender = msg.getSender().getLocalName();
			//Updating after successful bidding
			if (content[0].equals(BDC.AUCTION)) {
				Auction a = (Auction) shownAuctions.get(sender + BDC.POSTFIX + content[1]);
				if ( a!=null ) {
						a.buyNow(content[4], content[2], content[3]);
		    			setRefreshAuctionsTable(true);
	    			}
				else {
					a = (Auction) finishedAuctions.get(sender + BDC.POSTFIX + content[1]);
					if ( a!=null ) {
						a.buyNow(content[4], content[2], content[3]);
		    			setRefreshFinishedAuctionsTable(true);
    				} else {
    					//Shouldn't happen
    					return;
    				}
				}
			}
		}
		private void parseProposeMessage(ACLMessage msg) {
			String content [] = msg.getContent().split(BDC.SEPARATOR);
			String maxBidder = msg.getSender().getLocalName();
			AID sender = msg.getSender();
			ACLMessage reply = null;
			if (content[0].equalsIgnoreCase(BDC.PROPOSEBUYNOW)){
				Auction a = (Auction) ownActiveAuctions.get(content[1]);
				if (a == null)
					return;
				if (a.buyNow(maxBidder)) {
					// Accepted offer to buy immediately
					ownActiveAuctions.put(a.getAN(), a);
					//Sending Accept_proposal message to both bidder and auctioneer
					//to obey the rule: "Auctioneer shows the same informations as other agents"
					reply = getAcceptProposalMsg(a);
					reply.addReceiver(getAID());
					reply.addReceiver(sender);					
				} else {
					reply = getRejectProposalMsg();
					reply.addReceiver(sender);
				}
				send(reply);
			} else if (content[0].equalsIgnoreCase(BDC.BID)) {
				Auction a = (Auction) ownActiveAuctions.get(content[1]);
				if (a!=null) {
					if (!a.propose(sender.getLocalName(), Float.valueOf(content[2]).floatValue())) {
						reply = getRejectProposalMsg();
						reply.addReceiver(sender);
						send(reply);
					}
				} else {
					reply = getRejectProposalMsg();
					reply.addReceiver(sender);
					send(reply);
				}					
			}
			//Be carefull with this else block!
			//Be sure that it's never accidently executed 
			else {
				reply = getNotUnderstoodMsg();
				reply.addReceiver(sender);
				send(reply);
			}
		}
		private void parseCallForProposalMessage(ACLMessage msg) {
			String content [] = msg.getContent().split(BDC.SEPARATOR);
			String sender = msg.getSender().getLocalName();
			// single auction after AuctionStartBehaviour
			if (content[0].equals(BDC.AUCTION)) {
				Auction aa = (Auction) shownAuctions.get(sender + BDC.POSTFIX + content[1]);
				if ( aa != null) {
					aa.onTick(
						content[2]
						, content[3]
						, new Integer(content[4]).intValue()
						, content[5]
						, content[6]
					);
					setRefreshAuctionsTable(true);
				} else {
					ACLMessage req = getRequestMsg();
					req.addReceiver(msg.getSender());
					req.setContent(content[1]);
					send(req);
				}
			}
			// multiple auctions after AuctionTickBehaviour
			else if (content[0].equals(BDC.AUCTIONS)) {
				for (int j = 1; j < content.length; j+=6) {
					Auction aa = (Auction) shownAuctions.get(sender + BDC.POSTFIX + content[j]);
					if ( aa != null) {
						aa.onTick(
							content[j+1]
							, content[j+2]
							, new Integer(content[j+3]).intValue()
							, content[j+4]
							, content[j+5]          
						);
						// If AutomaticBidder exists for this exact auction, run it
						AutomaticBuyer ab = (AutomaticBuyer) automaticBuyers.get(sender + BDC.POSTFIX + content[j]);
						if (ab!=null)
							ab.performDuty(aa);
						setRefreshAuctionsTable(true);
					} else {
						// Auction is running, but it isn't shown yet.
						ACLMessage req = getRequestMsg();
						req.addReceiver(msg.getSender());
						req.setContent(content[j]);
						send(req);
					}					
				}
			}
		}
		private void parseRequestMessage(ACLMessage msg) {
			String content [] = msg.getContent().split(BDC.SEPARATOR);
			ACLMessage inform = getInformRefMsg();
			inform.addReceiver(msg.getSender());
			Auction a = (Auction) ownActiveAuctions.get(content[0]);
			if (a != null)
				try {
					inform.setContentObject(a);
					send(inform); 
				} catch (IOException e) {
					e.printStackTrace();
				}									   	
		}
		private void parseInformMessage(ACLMessage msg) {
			String content [] = msg.getContent().split(BDC.SEPARATOR);
			String sender = msg.getSender().getLocalName();
			//Auction are going to be finished, need to save selection
			if (content[0].equals(BDC.FINISHED)) {
				int i = myGui.getJTAuctions().getSelectionModel().getMaxSelectionIndex();
				if (i >= 0)
					selection = (String) shownAuctionsRowNumber.get(i);
				for (int j = 1; j < content.length; j+=1) {
					Auction a = (Auction) shownAuctions.remove(sender + BDC.POSTFIX + content[j]);
					automaticBuyers.remove(sender + BDC.POSTFIX + content[j]);
					if ( a!=null ) {
						if (
							a.getAuctioneer().equalsIgnoreCase(getLocalName())
							|| a.getMaxBidder().equalsIgnoreCase(getLocalName())
						) {
							finishedAuctions.put(a.getAuctioneerAN(), a);
							finishedAuctionsRowNumber.add(a.getAuctioneerAN());
							setRefreshFinishedAuctionsTable(true);
						}
						shownAuctionsRowNumber.remove(a.getAuctioneerAN());
					}				
				}
				setRefreshAuctionsTable(true);				
			}			
		}
		private void parseInformRefMessage(ACLMessage msg) {
			try {
    			Auction a = (Auction) msg.getContentObject();
    			if (a.isFinished())
    				//What you gonna do about it?
					return;
    			else {
    				shownAuctions.put(a.getAuctioneerAN(), a);
	    			shownAuctionsRowNumber.add(a.getAuctioneerAN());
	    			setRefreshAuctionsTable(true);
    			}
    		} catch (UnreadableException e) {
    			e.printStackTrace();
    		}			
		}
	}
	
	// Behaviour which serves dutch auctioning
	private class AuctionTickBehaviour extends TickerBehaviour {		
    	public AuctionTickBehaviour(Agent a, long period) {
			super(a, period);
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected void onTick() {
			if (!ownActiveAuctions.isEmpty()) {
				String content_cfp = BDC.AUCTIONS;
				content_cfp = reducePrices(content_cfp);			
				ACLMessage cfp = getCfpMsg();
				ACLMessage mes [] = {cfp};
				fillUpReceivers(mes, getAgents());
				cfp.setContent(content_cfp);				
				send(cfp);
				//some auctions auctioned by the agent has finished
				if (!ownFreshlyFinishedAuctions.isEmpty()) {
					String content_finished = BDC.FINISHED;
					Iterator i = ownFreshlyFinishedAuctions.values().iterator();
					while (i.hasNext()) {
						Auction a = (Auction)i.next();
						content_finished += BDC.SEPARATOR + a.getAN();						
					}
					ownFreshlyFinishedAuctions.clear();
					ACLMessage inf = getInformMsg();
					ACLMessage mes1 [] = {inf};
					fillUpReceivers(mes1, getAgents());
					inf.setContent(content_finished);
					send(inf);
				}
			}
    	}
    }

	// TableModel for JTAuctions
	private class BDTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		@Override
		public int getColumnCount() {			
			return BDC.AUCTIONSCOLUMNNAMES.length;
		}

		@Override
		public int getRowCount() {
			if (shownAuctionsRowNumber.isEmpty())
				return 0;
			else
				return shownAuctions.keySet().size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			if (row < shownAuctionsRowNumber.size()) {
				Auction  a = (Auction) shownAuctions.get(shownAuctionsRowNumber.get(row));
				if (a != null){
					return a.getColumnValue(col);				
				}			
			}
			return null;
		}
		
	}
	
	// TableModel for JTFinishedAuctions
	private class BDFinishedTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		@Override
		public int getColumnCount() {			
			return BDC.FINISHEDAUCTIONSCOLUMNNAMES.length;
		}

		@Override
		public int getRowCount() {
			if (finishedAuctionsRowNumber.isEmpty())
				return 0;
			else
				return finishedAuctions.keySet().size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			if (row < finishedAuctionsRowNumber.size()) {
				Auction  a = (Auction) finishedAuctions.get(finishedAuctionsRowNumber.get(row));
				if (a != null){
					return a.getFinishedColumnValue(col);				
				}			
			}
			return null;
		}
		
	}
	
	private ACLMessage getNotUnderstoodMsg() {
		ACLMessage msg = new ACLMessage(ACLMessage.NOT_UNDERSTOOD);
		msg.setSender(getAID());
		msg.setOntology(BDC.BDONTO);				
		return msg;
	}

	private ACLMessage getRejectProposalMsg() {
		ACLMessage msg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
		msg.setSender(getAID());
		msg.setOntology(BDC.BDONTO);				
		return msg;
	}

	private ACLMessage getAcceptProposalMsg(Auction a) {
		ACLMessage msg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
		msg.setSender(getAID());
		msg.setOntology(BDC.BDONTO);
		// why and what for? wouldn't be easier to send an object?
		msg.setContent(BDC.AUCTION + BDC.SEPARATOR + a.getAN() + BDC.SEPARATOR + a.getPriceInt() + BDC.SEPARATOR + a.getPriceDec() + BDC.SEPARATOR + a.getMaxBidder());
		return msg;
	}

	private ACLMessage getInformRefMsg() {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM_REF);
		msg.setSender(getAID());
		msg.setOntology(BDC.BDONTO);				
		return msg;
	}
	
	private ACLMessage getInformMsg() {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setSender(getAID());
		msg.setOntology(BDC.BDONTO);				
		return msg;
	}
	
	private void setRefreshFinishedAuctionsTable(boolean b) {
		refreshFinishedAuctionTable = b;
	}

	private void setRefreshAuctionsTable(boolean b) {
		refreshAuctionsTable = b;		
	}

	private void refreshAuctionsTable() {
		if (refreshAuctionsTable) {
			int row = myGui.getJTAuctions().getSelectedRow();
			String AuctionId = null;
			if (row >= 0 && row < shownAuctionsRowNumber.size())
				AuctionId = (String) shownAuctionsRowNumber.get(row);
			bdtm.fireTableDataChanged();
			if (selection != null) {
				row = shownAuctionsRowNumber.lastIndexOf(selection);
				selection = null;
			}
			else if (AuctionId != null)
				row = shownAuctionsRowNumber.lastIndexOf(AuctionId);
			if (row >= 0 && row < bdtm.getRowCount())
				myGui.getJTAuctions().setRowSelectionInterval(row, row);
		}		
		setRefreshAuctionsTable(false);
	}

	private void refreshFinishedAuctionsTable() {
		if (refreshFinishedAuctionTable) {
			int row = myGui.getJTFinishedAuctions().getSelectedRow();
			String AuctionId = null;
			if (row >= 0 && row < finishedAuctionsRowNumber.size())
				AuctionId = (String) finishedAuctionsRowNumber.get(row);
			bdftm.fireTableDataChanged();
			if (AuctionId != null)
				row = finishedAuctionsRowNumber.lastIndexOf(AuctionId);
			if (row >= 0 && row < bdtm.getRowCount())
				myGui.getJTFinishedAuctions().setRowSelectionInterval(row, row);
		}		
		setRefreshFinishedAuctionsTable(false);
	}
	
	private String getSingleCFPContent(Auction a) {
		return 
			BDC.AUCTION 
			+ BDC.SEPARATOR + a.getAN() 
			+ BDC.SEPARATOR + a.getPriceInt() 
			+ BDC.SEPARATOR + a.getPriceDec() 
			+ BDC.SEPARATOR + a.getTicksLeft()
			+ BDC.SEPARATOR + a.getMaxBid()
			+ BDC.SEPARATOR + a.getMaxBidder()
			;
	}

	public AbstractTableModel getBdtm() {
		if (bdtm == null)
			bdtm = new BDTableModel();
		return bdtm;
	}

	public AbstractTableModel getBdftm() {
		if (bdftm == null)
			bdftm = new BDFinishedTableModel();
		return bdftm;
	}
	
	public Auction getShownAuction(int row) {
		if (row < shownAuctionsRowNumber.size() && row >= 0) {
			return (Auction) shownAuctions.get(shownAuctionsRowNumber.get(row));
		}
		return null;
	}

	public Auction getFinishedAuction(int row) {
		if (row < finishedAuctionsRowNumber.size() && row >= 0) {
			return (Auction) finishedAuctions.get(finishedAuctionsRowNumber.get(row));
		}
		return null;
	}
}

