package com.marcinswiderski.buyingDutchman.agent.client;
//TODO Po zaakceptowaniu oferty rozsy�ka info do wszystkich, nie tylko do oferenta
//TODO Po zako�czeniu aukcji poprawi� od�wie�anie jPAuctionCommand
//TODO error.txt w katalogu magisterska
//TODO brak historii bid�w dla aukcji drugiej ceny, dla holenderskiej. Dla angielskiej i przetarg�w jest ok.
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.Hashtable;
import javax.swing.table.AbstractTableModel;

import auctions.Auction;
import auctions.AuctionPenny;
import auctions.AutomaticBid;
import auctions.Bid;
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
 * Klasa implementuj�ca i umiejscawiaj�ca agenta w �rodowisku platformy
 * agentowej. Jej odpowiedzialno�ci� s�:
 * <ol>
 * <li>Stworzenie agenta dzia�aj�cego na platformie agentowej</li>
 * <li>kt�ry b�dzie m�g� komunikowa� si� us�ugami platformy</li>
 * <li>oraz z innymi agentami systemu Buying Dutchman</li>
 * <li>Zapewnienie prawid�owego prowadzenia aukcji</li>
 * <li>Zapewnienie prawid�owego uczestnictwa w aukcji</li>
 * <li>przechowywanie danych o aukcjach zako�czonych, prowadzonych i wygranych</li>
 * </ol>
 */
public class BuyingDutchmanAgent extends GuiAgent {

	/** Default serialization constant */
	private static final long serialVersionUID = 1L;
	/** Skr�cona nazwa agenta (nie w pe�ni kwalifikowana) */
	private String nickName;
	/** GUI agenta */
	private BuyingDutchmanGui myGui;
	/** Id kolejnej aukcji */
	private int nextAuctionNumber;
	/** Aukcje prowadzone przez agenta */
	private Hashtable ownActiveAuctions;
	/** �wie�o zako�czone aukcje (zmienna pomocnicza) */
	private Hashtable ownFreshlyFinishedAuctions;
	/** Trwaj�ce aukcje wy�wietlane przez agenta */
	private Hashtable shownAuctions;
	/** Trwaj�ce aukcje, o kt�rych obiekt agent poprosi� */
	private Vector requestedAuctions;
	/** Wektor okre�laj�cy kolejno�� wy�wietlania trwaj�cych aukcji */
	private Vector shownAuctionsRowNumber;
	/** Flaga u�ywana w celu od�wie�ania tabeli trwaj�cych aukcji */
	private boolean refreshAuctionsTable;
	/** Zmienna pomocnicza, s�u��ca do przechowania zaznaczonej aukcji */
	private String selection;
	/** Wektor okre�laj�cy kolejno�� wy�wietlania zako�czonych aukcji */
	private Vector finishedAuctionsRowNumber;
	/** Zako�czone aukcje wy�wietlane przez agenta */
	private Hashtable finishedAuctions;
	/** Obiekt TableModel dla tabeli trwaj�cych aukcji */
	private AbstractTableModel bdatm;
	/** Obiekt TableModel dla tabeli zako�czonych aukcji */
	private AbstractTableModel bdfatm;
	/** Obiekt TableModel dla tabeli historii ofert na aukcjach trwaj�cych */
	private AbstractTableModel bdabtm;
	/** Obiekt TableModel dla tabeli historii ofert na aukcjach zako�czonych */
	private AbstractTableModel bdfabtm;
	/** Flaga u�ywana w celu od�wie�ania tabeli zako�czonych aukcji */
	private boolean refreshFinishedAuctionTable;
	/** Zbi�r obiekt�w AutomaticBid, kt�re agent b�dzie uruchamia� */
	private Hashtable automaticBuyers;

	/**
	 * Metoda inicjalizuj�ca obiekt Agenta.
	 * <ol>
	 * <li>inicjuje zmienne obiektu</li>
	 * <li>rejestruje agenta na platformie agentowej</li>
	 * <li>inicjuje GUI agenta</li>
	 * <li>dodaje zachowanie zegara</li>
	 * <li>dodaje zachowanie odbierania wiadomo�ci</li>
	 * </ol>
	 */
	protected void setup() {
		// Field initialization
		nickName = getAID().getLocalName();
		ownActiveAuctions = new Hashtable();
		ownFreshlyFinishedAuctions = new Hashtable();
		shownAuctions = new Hashtable();
		requestedAuctions = new Vector();
		shownAuctionsRowNumber = new Vector();
		finishedAuctions = new Hashtable();
		automaticBuyers = new Hashtable();
		finishedAuctionsRowNumber = new Vector();
		nextAuctionNumber = 0;
		refreshAuctionsTable = false;
		refreshFinishedAuctionTable = false;
		bdatm = new BDAuctionsTableModel();
		bdfatm = new BDFinishedAuctionsTableModel();
		bdabtm = new BDAuctionBidsTableModel();
		bdfabtm = new BDFinishedAuctionBidsTableModel();
		selection = null;
		// Agent registration
		try {
			DFService.register(this, getDFAgentDescription());
		} catch (jade.domain.FIPAAgentManagement.FailureException e) {
			// Already registered, need to deregister
			try {
				DFService.deregister(this);
			} catch (FIPAException e1) {
				// Deregistration failed: maybe it took place in the meanwhile
				e1.printStackTrace();
				doDelete();
			}
			try {
				// Registration after deregistration
				DFService.register(this, getDFAgentDescription());
			} catch (FIPAException e1) {
				e1.printStackTrace();
				doDelete();
			}
		} catch (FIPAException e) {
			e.printStackTrace();
			doDelete();
			return;
		}
		// GUI initialization
		myGui = new BuyingDutchmanGui(this);
		myGui.setVisible(true);
		// Clock behaviour
		addBehaviour(new AuctionTickBehaviour(this, BDC.TICK));
		// Messaging service behaviour
		addBehaviour(new MessageReciever());
	}

	/**
	 * Metoda s�u��ca do poprawnego usuwania agenta.
	 * <ol>
	 * <li>usuwa GUI agenta</li>
	 * <li>wyrejestrowuje agenta z platformy agentowej</li>
	 * </ol>
	 */
	public void doDelete() {
		// GUI disposal
		myGui.setVisible(false);
		myGui.dispose();
		// deregistration
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		super.doDelete();
	}

	/**
	 * Metoda obs�uguj�ca zda�enia GUI. Uruchamia odpowiednie metody, w
	 * zale�no�ci od typu zdarzenia ev.getType()
	 * 
	 * @param ev
	 *            obiekt typu GuiEvent, przekazanay ze zdarzenia.
	 * @see BuyingDutchmanGui
	 */
	public void onGuiEvent(GuiEvent ev) {
		if (ev.getType() == BDC.GUIAUCTION) {
			auction((Auction) ev.getParameter(BDC.AUCTIONPARAMNUM));
		}
		if (ev.getType() == BDC.GUIBUYNOW) {
			buyNow((String) ev.getParameter(BDC.BUYNOWAUCTIONEERPARAMNUM),
					(String) ev.getParameter(BDC.BUYNOWAUCTIONNRPARAMNUM));
		}
		if (ev.getType() == BDC.GUIPROPOSITION) {
			propose((String) ev.getParameter(BDC.PROPOSEAUCTIONNRPARAMNUM),
					(String) ev.getParameter(BDC.PROPOSEAUCTIONEERPARAMNUM),
					new BigDecimal((String) ev
							.getParameter(BDC.PROPOSEBIDPARAMNUM)));
		}
		if (ev.getType() == BDC.GUIWAITBUY) {
			waitAndBuy(
				(String) ev.getParameter(BDC.WAITBUYAUCTIONNRPARAMNUM),
				(String) ev.getParameter(BDC.WAITBUYAUCTIONEERPARAMNUM),
				new BigDecimal((String) ev.getParameter(BDC.WAITBUYBIDPARAMNUM)),
				new BigDecimal((String) ev.getParameter(BDC.WAITBUYUPBIDPARAMNUM)),
				Integer.parseInt(((String) ev.getParameter(BDC.WAITBUYROUNDSPARAMNUM)))
			);
		}
		if (ev.getType() == BDC.GUICLOSE) {
			doDelete();
		}
	}

	/**
	 * Metoda obs�uguje funkcjonalno�� automatycznego licytowania. Dodaje obiekt
	 * typu AutomaticBuyer do zbioru automaticBuyers agenta lokalnego. Metoda
	 * uruchamiana po stronie agenta lokalnego.
	 * 
	 * @param auctionNr
	 *            numer aukcji, kt�ra zostanie automatycznie licytowana.
	 * @param auctioneer
	 *            agent, b�d�cy sprzedawc� na danej aukcji.
	 * @param bid
	 *            kwota graniczna, od/do kt�rej agent ma licytowa�.
	 * @param upBid
	 *            kwota, o kt�r� agent b�dzie podbija� cen� aukcji.
	 * @param rounds 
	 */
	private void waitAndBuy(String auctionNr, String auctioneer,
			BigDecimal bid, BigDecimal upBid, int rounds) {
		if (fraudBid(auctioneer))
			return;
		Auction a = (Auction) shownAuctions.get(auctioneer + BDC.POSTFIX
				+ auctionNr);
		if (a != null) {
			AutomaticBuyer ab = AutomaticBuyerFactory.AutomaticBuyerInstance(
					this, bid, upBid, rounds, a.getType());
			if (ab != null) {
				automaticBuyers.put(auctioneer + BDC.POSTFIX + auctionNr, ab);
				if (!ab.performDuty(a))
					automaticBuyers
							.remove(auctioneer + BDC.POSTFIX + auctionNr);
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
	 * @return A description of BD agent, to search for when sending an auction
	 *         details
	 **/
	private DFAgentDescription getLightDFAgentDescription() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.addOntologies(BDC.BDONTO);
		return dfd;
	}

	private DFAgentDescription[] getAgents() {
		try {
			DFAgentDescription[] l = DFService.search(this,
					getLightDFAgentDescription());
			return l;
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		return null;
	}

	public DFAgentDescription[] getAgent(String name) {
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

	public String getNextAuctionNumber() {
		return Integer.toString(nextAuctionNumber++);
	}

	private void auction(Auction a) {
		addBehaviour(new AuctionStartBehaviour(a));
	}

	private boolean fraudBid(String auctioneer) {
		return auctioneer.equalsIgnoreCase(getLocalName());
	}

	// TODO Udokumentowa� JavaDoc'iem dzia�anie i moment wywo�ania
	// Po klikni�ciu z�� ofert�, na agencie kupca

	public void propose(String auctionNr, String auctioneer, BigDecimal newBid) {
		if (fraudBid(auctioneer))
			return;
		Auction a = (Auction) shownAuctions.get(auctioneer + BDC.POSTFIX
				+ auctionNr);
		if (a != null) {
			// There's still an auction
			ACLMessage proposition = getProposeMsg();
			ACLMessage[] mes = { proposition };
			fillUpReceivers(mes, getAgent(auctioneer));
			proposition.setContent(BDC.BID + BDC.SEPARATOR + auctionNr
					+ BDC.SEPARATOR + newBid.toPlainString());
			send(proposition);
		} else
			return;
	}

	public void buyNow(String auctioneer, String auctionNr) {
		if (fraudBid(auctioneer))
			return;
		ACLMessage msg = getProposeMsg();
		ACLMessage[] mes = { msg };
		fillUpReceivers(mes, getAgent(auctioneer));
		String content = BDC.PROPOSEBUYNOW + BDC.SEPARATOR + auctionNr;
		msg.setContent(content);
		send(msg);
	}

	private String reducePrices() {
		String content = new String();
		Iterator i = ownActiveAuctions.values().iterator();
		Hashtable h = new Hashtable();
		while (i.hasNext()) {
			Auction a = (Auction) i.next();
			a.onTick();
			content += BDC.SEPARATOR + a.getCfpContent();
			if (a.isFinished()) {
				// Auction is finished due to offer or time
				// ocenianie ofert nie nale�y do tej cz�ci programu, poni�sze
				// wyci�te z warunku
				if (a.getBestBid() != null) {
					ACLMessage reply = getAcceptProposalMsg(a);
					ACLMessage[] mes = { reply };
					reply.addReceiver(getAID());
					fillUpReceivers(mes, getAgent(a.getBestBidder()));
					send(reply);
				}
				ownFreshlyFinishedAuctions.put(a.getAN(), a);
				h.put(a.getAN(), a);
			}
		}
		// Has to be this way cause to Hashtable fail-fast
		i = h.values().iterator();
		while (i.hasNext()) {
			Auction a = (Auction) i.next();
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
	public ACLMessage getRequestMsg() {
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
	public void fillUpReceivers(ACLMessage[] messages, DFAgentDescription[] r) {
		if (r != null) {
			for (int j = 0; j < messages.length; j++) {
				for (int i = 0; i < r.length; i++) {
					messages[j].addReceiver(r[i].getName());
				}
			}
		}
		// return messages;
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
			ACLMessage mes[] = { cfp, informRef };
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
			} else {
				block();
			}
		}

		private void parseMessage(ACLMessage msg) {
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
			String content[] = msg.getContent().split(BDC.SEPARATOR);
			String sender = msg.getSender().getLocalName();
			// Updating after successful bidding
			if (content[0].equals(BDC.AUCTION)) {
				Auction a = (Auction) shownAuctions.get(sender + BDC.POSTFIX
						+ content[1]);
				if (a != null) {
					a.buyNow(content[4], content[2], content[3]);
					setRefreshAuctionsTable(true);
				} else {
					a = (Auction) finishedAuctions.get(sender + BDC.POSTFIX
							+ content[1]);
					if (a != null) {
						a.buyNow(content[4], content[2], content[3]);
						setRefreshFinishedAuctionsTable(true);
					} else {
						// Shouldn't happen
						return;
					}
				}
			}
		}

		private void parseProposeMessage(ACLMessage msg) {
			String content[] = msg.getContent().split(BDC.SEPARATOR);
			String bestBidder = msg.getSender().getLocalName();
			AID sender = msg.getSender();
			ACLMessage reply = null;
			if (content[0].equalsIgnoreCase(BDC.PROPOSEBUYNOW)) {
				Auction a = (Auction) ownActiveAuctions.get(content[1]);
				if (a == null)
					return;
				if (a.buyNow(bestBidder)) {
					// Accepted offer to buy immediately
					ownActiveAuctions.put(a.getAN(), a);
					// Sending Accept_proposal message to both bidder and
					// auctioneer
					// to obey the rule:
					// "Auctioneer shows the same informations as other agents"
					
					//MSW 2012-08-11
					//nie wysy�a wiadomo�ci o zaakceptowaniu oferty. b�dzie wysy�a�, gdy aukcja si� zako�czy
					//reply = getAcceptProposalMsg(a);
					//reply.addReceiver(getAID());
					//reply.addReceiver(sender);
				} else {
					reply = getRejectProposalMsg();
					reply.addReceiver(sender);
					send(reply);
				}
			} else if (content[0].equalsIgnoreCase(BDC.BID)) {
				Auction a = (Auction) ownActiveAuctions.get(content[1]);
				if (a != null) {
					boolean accepted = a.propose(sender.getLocalName(),
							new BigDecimal(content[2]));
					if (!accepted) {
						reply = getRejectProposalMsg();
						reply.addReceiver(sender);
						send(reply);
					}
					String content_cfp = BDC.AUCTION + BDC.SEPARATOR
							+ a.getCfpContent();
					ACLMessage cfp = prepareCfpMessage(content_cfp);
					send(cfp);
				} else {
					reply = getRejectProposalMsg();
					reply.addReceiver(sender);
					send(reply);
				}
			}
			// Be careful with this else block!
			// Be sure that it's never accidently executed
			else {
				reply = getNotUnderstoodMsg();
				reply.addReceiver(sender);
				send(reply);
			}
		}

		private void parseCallForProposalMessage(ACLMessage msg) {
			String content[] = msg.getContent().split(BDC.SEPARATOR);
			String sender = msg.getSender().getLocalName();
			if (content[0].equals(BDC.AUCTIONS)
					|| content[0].equals(BDC.AUCTION)) {
				for (int j = 1; j < content.length; j+=8) {
					Auction aa = (Auction) shownAuctions.get(sender + BDC.POSTFIX + content[j]);
					if (aa != null) {
						aa.onTick(
							content[j+1]
					        , content[j+2]
					        , new Double(content[j+3]).doubleValue()
					        , content[j+4]
					        , content[j+5]
				        );
						int historySize = Integer.parseInt(content[j+6]);
						if (historySize > 0) {
							//TODO nale�y umieszcza� histori� w ka�dej aukcji. wtedy j++ b�dzie mo�na zast�pi� j+x i doda� j+=xx do p�tli for
							// i prawid�owo obs�u�y� lini� 594, z u�yciem content[j] zamiast content[1]
							aa.setBidsHistory(content[j+7]);
						}
						// If AutomaticBidder exists for this exact auction, run
						// it
						AutomaticBuyer ab = (AutomaticBuyer) automaticBuyers
								.get(sender + BDC.POSTFIX + aa.getAN());
						if (ab != null)
							if (!ab.performDuty(aa))
								automaticBuyers.remove(sender + BDC.POSTFIX
										+ aa.getAN());
						setRefreshAuctionsTable(true);
					} else {
						// Auction is running, but it isn't shown yet.
						if (!requestedAuctions.contains(sender + BDC.POSTFIX + content[j])) {
							requestedAuctions.add(sender + BDC.POSTFIX + content[j]);
							ACLMessage req = getRequestMsg();
							req.addReceiver(msg.getSender());
							req.setContent(BDC.AUCTION_INFO + BDC.SEPARATOR + content[j]);
							send(req);
						}
					}
				}
			}
		}

		private void parseRequestMessage(ACLMessage msg) {
			String content[] = msg.getContent().split(BDC.SEPARATOR);
			if (content[0].equals(BDC.AUCTION_INFO)) {
				ACLMessage inform = getInformRefMsg();
				inform.addReceiver(msg.getSender());
				Auction a = (Auction) ownActiveAuctions.get(content[1]);
				if (a != null)
					try {
						inform.setContentObject(a);
						send(inform);
					} catch (IOException e) {
						e.printStackTrace();
					}
			} else if (content[0].equals(BDC.AUTOMATIC_BID)) {
				AuctionPenny a = (AuctionPenny) ownActiveAuctions.get(content[1]);
				if (a != null) {
					AutomaticBid ab = new AutomaticBid(Integer.valueOf(
						content[2]).intValue()
						, new BigDecimal(content[3])
					);
					a.addAutomaticBid(msg.getSender().getLocalName(), ab);
				}
			}
		}

		private void parseInformMessage(ACLMessage msg) {
			String content[] = msg.getContent().split(BDC.SEPARATOR);
			String sender = msg.getSender().getLocalName();
			// Auction are going to be finished, need to save selection
			if (content[0].equals(BDC.FINISHED)) {
				int i = myGui.getJTAuctions().getSelectionModel().getMaxSelectionIndex();
				try {
					if (i >= 0)
						selection = (String) shownAuctionsRowNumber.get(i);
				}
				catch (IndexOutOfBoundsException e) {
					selection = null;
				}
				for (int j = 1; j < content.length; j++) {
					Auction a = (Auction) shownAuctions.remove(sender + BDC.POSTFIX + content[j]);
					if (a != null) {
						shownAuctionsRowNumber.remove(a.getAuctioneerAN());
						automaticBuyers.remove(sender + BDC.POSTFIX + content[j]);
						// ZAKO�CZONE AUKCJE WIDOCZNE DLA WSZYSTKICH AGENT�W
						/*if (a.getAuctioneer().equalsIgnoreCase(getLocalName())
								|| a.getBestBidder().equalsIgnoreCase(
										getLocalName())) {*/
							finishedAuctions.put(a.getAuctioneerAN(), a);
							finishedAuctionsRowNumber.add(a.getAuctioneerAN());
							setRefreshFinishedAuctionsTable(true);
						/*}*/
					}
				}
				setRefreshAuctionsTable(true);
			}
		}

		private void parseInformRefMessage(ACLMessage msg) {
			try {
				Auction a = (Auction) msg.getContentObject();
				if (a.isFinished())
					// What you gonna do about it?
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

	// Behaviour which serves as clock
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
				String content_cfp = BDC.AUCTIONS + reducePrices();
				ACLMessage cfp = prepareCfpMessage(content_cfp);
				send(cfp);
				// some auctions auctioned by the agent has finished
				if (!ownFreshlyFinishedAuctions.isEmpty()) {
					String content_finished = BDC.FINISHED;
					Iterator i = ownFreshlyFinishedAuctions.values().iterator();
					while (i.hasNext()) {
						Auction a = (Auction) i.next();
						content_finished += BDC.SEPARATOR + a.getAN();
					}
					ownFreshlyFinishedAuctions.clear();
					ACLMessage inf = prepareInfMessage(content_finished);
					send(inf);
				}
			}
		}
	}

	// TableModel for JTAuctionBids
	private class BDAuctionBidsTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		@Override
		public int getColumnCount() {
			return BDC.AUCTIONBIDSCOLUMNNAMES.length;
		}

		@SuppressWarnings("unchecked")
		@Override
		public int getRowCount() {
			int row = myGui.getJTAuctions().getSelectedRow();
			if (row < 0)
				return 0;
			else {
				Auction a1 = ((Auction) shownAuctions
						.get(shownAuctionsRowNumber.get(row)));
				if (a1 != null) {
					ArrayList a = a1.getBidsHistory();
					if (a != null)
						return a.size();
					else
						return 0;
				} else
					return 0;
			}
		}

		@Override
		public Object getValueAt(int row, int col) {
			int r = myGui.getJTAuctions().getSelectedRow();
			if (r < 0)
				return null;
			try {
				Auction a = ((Auction) shownAuctions.get(shownAuctionsRowNumber.get(r)));
				if (a != null && row < a.getBidsHistory().size()) {
					return ((Bid) a.getBidsHistory().get(row)).getColumnValue(col);
				}
			} 
			catch (ArrayIndexOutOfBoundsException e) {}
			catch (NullPointerException e) {}
			return null;
		}

	}

	// TableModel for JTFinishedAuctionBids
	private class BDFinishedAuctionBidsTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		@Override
		public int getColumnCount() {
			return BDC.AUCTIONBIDSCOLUMNNAMES.length;
		}

		@SuppressWarnings("unchecked")
		@Override
		public int getRowCount() {
			int row = myGui.getJTFinishedAuctions().getSelectedRow();
			if (row < 0)
				return 0;
			else {
				Auction a1 = ((Auction) finishedAuctions.get(finishedAuctionsRowNumber.get(row)));
				if (a1 != null) {
					ArrayList a = a1.getBidsHistory();
					if (a != null)
						return a.size();
					else
						return 0;
				} else
					return 0;
			}
		}

		@Override
		public Object getValueAt(int row, int col) {
			int r = myGui.getJTFinishedAuctions().getSelectedRow();
			if (r < 0)
				return null;
			try {
				Auction a = ((Auction) finishedAuctions.get(finishedAuctionsRowNumber.get(r)));
				if (a != null && row < a.getBidsHistory().size()) {
					return ((Bid) a.getBidsHistory().get(row)).getColumnValue(col);
				}
			}
			catch (IndexOutOfBoundsException e) {}
			return null;
		}

	}

	// TableModel for JTAuctions
	private class BDAuctionsTableModel extends AbstractTableModel {

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
				Auction a = (Auction) shownAuctions.get(shownAuctionsRowNumber.get(row));
				if (a != null) {
					return a.getColumnValue(col);
				}
			}
			return null;
		}

	}

	// TableModel for JTFinishedAuctions
	private class BDFinishedAuctionsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		@Override
		public int getColumnCount() {
			return BDC.FINISHEDAUCTIONSCOLUMNNAMES.length;
		}

		// TODO 001 Nie jest wo�ane. Sprawdzi�, co jest zepsute w GUI. Mo�e
		// viewport?
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
				Auction a = (Auction) finishedAuctions.get(finishedAuctionsRowNumber.get(row));
				if (a != null) {
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

	public ACLMessage prepareInfMessage(String content_finished) {
		ACLMessage inf = getInformMsg();
		ACLMessage mes1[] = { inf };
		fillUpReceivers(mes1, getAgents());
		inf.setContent(content_finished);
		return inf;
	}

	public ACLMessage prepareCfpMessage(String content_cfp) {
		ACLMessage cfp = getCfpMsg();
		ACLMessage mes[] = { cfp };
		fillUpReceivers(mes, getAgents());
		cfp.setContent(content_cfp);
		return cfp;
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
		msg.setContent(BDC.AUCTION + BDC.SEPARATOR + a.getAN() + BDC.SEPARATOR
				+ a.getPriceInt() + BDC.SEPARATOR + a.getPriceDec()
				+ BDC.SEPARATOR + a.getBestBidder());
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
	//wo�ane dopiero po zako�czeniu aukcji
	private void refreshAuctionsTable() {
		if (refreshAuctionsTable) {
			int row = myGui.getJTAuctions().getSelectedRow();
			String AuctionId = null;
			if (row >= 0 && row < shownAuctionsRowNumber.size())
				AuctionId = (String) shownAuctionsRowNumber.get(row);
			getBdatm().fireTableDataChanged();
			if (selection != null) {
				row = shownAuctionsRowNumber.lastIndexOf(selection);
				selection = null;
			} else if (AuctionId != null)
				row = shownAuctionsRowNumber.lastIndexOf(AuctionId);
			if (row >= 0 && row < bdatm.getRowCount())
				myGui.getJTAuctions().setRowSelectionInterval(row, row);
			getBdabtm().fireTableDataChanged();
		}
		setRefreshAuctionsTable(false);
	}
	//TODO 001 SPRAWDZI� CZY DZIA�A POPRAWNIE
	private void refreshFinishedAuctionsTable() {
		if (refreshFinishedAuctionTable) {
			int row = myGui.getJTFinishedAuctions().getSelectedRow();
			String AuctionId = null;
			if (row >= 0 && row < finishedAuctionsRowNumber.size())
				AuctionId = (String) finishedAuctionsRowNumber.get(row);
			getBdfatm().fireTableDataChanged();
			if (AuctionId != null)
				row = finishedAuctionsRowNumber.lastIndexOf(AuctionId);
			if (row >= 0 && row < bdatm.getRowCount())
				myGui.getJTFinishedAuctions().setRowSelectionInterval(row, row);
			getBdfabtm().fireTableDataChanged();
		}
		setRefreshFinishedAuctionsTable(false);
	}

	private String getSingleCFPContent(Auction a) {
		return BDC.AUCTION + BDC.SEPARATOR + a.getCfpContent();
	}

	public AbstractTableModel getBdatm() {
		if (bdatm == null)
			bdatm = new BDAuctionsTableModel();
		return bdatm;
	}

	public AbstractTableModel getBdfatm() {
		if (bdfatm == null)
			bdfatm = new BDFinishedAuctionsTableModel();
		return bdfatm;
	}

	public AbstractTableModel getBdabtm() {
		if (bdabtm == null)
			bdabtm = new BDAuctionBidsTableModel();
		return bdabtm;
	}

	public AbstractTableModel getBdfabtm() {
		if (bdfabtm == null)
			bdfabtm = new BDFinishedAuctionBidsTableModel();
		return bdfabtm;
	}

	public Auction getShownAuction(int row) {
		if (row < shownAuctionsRowNumber.size() && row >= 0) {
			return (Auction) shownAuctions.get(shownAuctionsRowNumber.get(row));
		}
		return null;
	}

	public Auction getFinishedAuction(int row) {
		if (row < finishedAuctionsRowNumber.size() && row >= 0) {
			return (Auction) finishedAuctions.get(finishedAuctionsRowNumber
					.get(row));
		}
		return null;
	}
	// TODO MUST historia ofert na zako�czonych aukcjach
}
