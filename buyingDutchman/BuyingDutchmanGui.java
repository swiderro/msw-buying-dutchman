package buyingDutchman;

import jade.gui.GuiEvent;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.JComboBox;


public class BuyingDutchmanGui extends JFrame {

	private BuyingDutchman myAgent;
	
	private static final long serialVersionUID = 1L;
		
	
	private JPanel jContentPane = null;
	private JTabbedPane jTP = null;
	private JPanel jPAuctions = null;	
	private JPanel jPAuction = null;
	private JScrollPane jSPAuctions = null;
	private JTable jTAuctions = null;
	private JPanel jPAuctionCommand = null;
	private JScrollPane jSPAuctionDescription = null;
	private JTextArea jTAAuctionDescription = null;
	private JButton jBBuyNow = null;
	private JLabel jLWaitForPrice = null;
	private JTextField jTFBidPriceInt = null;
	private JLabel jLDecimalPoint = null;
	private JTextField jTFBidPriceDec = null;
	private JButton jBProposition = null;
	private JLabel jLAuctionTitle = null;
	private JTextField jTFAuctionTitle = null;
	private JLabel jLAuctionStartingPrice = null;
	private JPanel jPStartinPrice = null;
	private JLabel jLDecimalPoint1 = null;
	private JTextField jTFStartPriceInt = null;
	private JTextField jTFStartPriceDec = null;
	private JLabel jLMinimumPrice = null;
	private JPanel jPMinimumPrice = null;
	private JTextField jTFEndPriceInt = null;
	private JLabel jLDecimalPoint11 = null;
	private JTextField jTFEndPriceDec = null;
	private JLabel jLAuctionDuration = null;
	private JLabel jLCategory = null;
	private JLabel jLSubCategory = null;
	private JPanel jPAuctionDuration = null;
	private JLabel jLAuctionDurationHours = null;
	private JTextField jTFAuctionDurationHours = null;
	private JLabel jLAuctionDurationMinutes = null;
	private JLabel jLAuctionDurationSeconds = null;
	private JTextField jTFAuctionDurationMinutes = null;
	private JTextField jTFAuctionDurationSeconds = null;
	private JLabel jLAuctionDescription = null;
	private JTextArea jTAAuctionDescriptionInfo = null;
	private JButton jBAuction = null;
	private JLabel jLAuctioner = null;
	private JLabel jLAuctionNr = null;
	private JTextField jTFAuctioneer = null;
	private JTextField jTFAuctionNr = null;
	private JPanel jPFinishedAuctions = null;
	private JScrollPane jSPFinishedAuctions = null;
	private JScrollPane jSPFinishedAuctionDescription = null;
	private JTextArea jTAFinishedAuctionDescription = null;
	private JTable jTFinishedAuctions = null;
	private JComboBox jCBCategory = null;
	private JComboBox jCBSubCategory = null;  //  @jve:decl-index=0:

	private JLabel jLAuctionType = null;

	private JComboBox jCBAutionType;  //  @jve:decl-index=0:

	private JButton jBWaitAndBuy = null;

	/**
	 * This method initializes 
	 * 
	 */
	public BuyingDutchmanGui(BuyingDutchman agent) {
		super();
		myAgent = agent;
		initialize();
		// Make the agent terminate when the user closes 
		// the GUI using the button on the upper right corner	
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				GuiEvent f = new GuiEvent(this, BDC.GUICLOSE);
				myAgent.postGuiEvent(f);
			}
		} );
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(BDC.FRAMEWIDTH, BDC.FRAMEHEIGHT));
        this.setContentPane(getJContentPane());
        this.setTitle(myAgent.getLocalName()+" "+BDC.FRAMETITLE);
			
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJTP(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTP	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTP() {
		if (jTP == null) {
			jTP = new JTabbedPane();
			jTP.addTab(BDC.AUCTIONSTAB, null, getJPAuctions(), null);
			jTP.addTab(BDC.AUCTIONTAB, null, getJPAuction(), null);
			jTP.addTab(BDC.FINISHEDAUCTIONSTAB, null, getJPFinishedAuctions(), null);
		}
		return jTP;
	}

	/**
	 * This method initializes jPAuctions	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPAuctions() {
		if (jPAuctions == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.weighty = 0.0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 0.5;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			jPAuctions = new JPanel();
			jPAuctions.setLayout(new GridBagLayout());
			jPAuctions.add(getJSPAuctions(), gridBagConstraints);
			jPAuctions.add(getJPAuctionCommand(), gridBagConstraints2);
			jPAuctions.add(getJSPAuctionDescription(), gridBagConstraints1);
		}
		return jPAuctions;
	}



	/**
	 * This method initializes jPAuction	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPAuction() {
		if (jPAuction == null) {
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints17.gridy = 7;
			gridBagConstraints17.weightx = 1.0;
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridx = 1;
			GridBagConstraints gridBagConstraints07 = new GridBagConstraints();
			gridBagConstraints07.gridx = 0;
			gridBagConstraints07.gridy = 7;
			jLAuctionType = new JLabel(BDC.AUCTIONTYPELABEL);
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints15.gridy = 5;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.gridx = 1;			
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.fill = GridBagConstraints.WEST;
			gridBagConstraints18.gridy = 9;
			gridBagConstraints18.weighty = 4;
			gridBagConstraints18.anchor = GridBagConstraints.WEST;
			gridBagConstraints18.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.WEST;
			gridBagConstraints14.gridy = 4;
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.weightx = 1;
			GridBagConstraints gridBagConstraints04 = new GridBagConstraints();
			gridBagConstraints04.gridx = 0;
			gridBagConstraints04.gridy = 4;
			GridBagConstraints gridBagConstraints05 = new GridBagConstraints();
			gridBagConstraints05.gridx = 0;
			gridBagConstraints05.gridy = 5;
			jLCategory = new JLabel();
			jLCategory.setText(BDC.CATEGORYLABEL);
			jLSubCategory = new JLabel();
			jLSubCategory.setText(BDC.SUBCATEGORYLABEL);
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.gridx = 1;
			gridBagConstraints24.gridy = 4;
			GridBagConstraints gridBagConstraints08 = new GridBagConstraints();
			gridBagConstraints08.gridx = 0;
			gridBagConstraints08.gridy = 8;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.BOTH;
			gridBagConstraints16.gridy = 6;
			gridBagConstraints16.weighty = 2;
			gridBagConstraints16.gridx = 1;
			GridBagConstraints gridBagConstraints06 = new GridBagConstraints();
			gridBagConstraints06.gridx = 0;
			gridBagConstraints06.gridy = 6;
			jLAuctionDescription = new JLabel();
			jLAuctionDescription.setText(BDC.AUCTIONDESCRIPTIONLABEL);
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 1;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.gridy = 3;
			GridBagConstraints gridBagConstraints03 = new GridBagConstraints();
			gridBagConstraints03.gridx = 0;
			gridBagConstraints03.gridy = 3;
			jLAuctionDuration = new JLabel();
			jLAuctionDuration.setText(BDC.AUCTIONDURATIONLABEL);
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.gridy = 2;
			GridBagConstraints gridBagConstraints02 = new GridBagConstraints();
			gridBagConstraints02.gridx = 0;
			gridBagConstraints02.gridy = 2;
			jLMinimumPrice = new JLabel();
			jLMinimumPrice.setText(BDC.MINIMUMPRICELABEL);
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
			gridBagConstraints01.gridx = 0;
			gridBagConstraints01.gridy = 1;
			jLAuctionStartingPrice = new JLabel();
			jLAuctionStartingPrice.setText(BDC.STARTINGPRICETEXTFIELD);
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 0;
			GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
			gridBagConstraints00.gridx = 0;
			gridBagConstraints00.anchor = GridBagConstraints.CENTER;
			gridBagConstraints00.gridy = 0;
			jLAuctionTitle = new JLabel();
			jLAuctionTitle.setText(BDC.AUCTIONTITLELABEL);
			JPanel jPDummy = new JPanel();
			jPAuction = new JPanel();
			jPAuction.setLayout(new GridBagLayout());
			jPAuction.add(jLAuctionTitle, gridBagConstraints00);
			jPAuction.add(getJTFAuctionTitle(), gridBagConstraints10);
			jPAuction.add(jLAuctionStartingPrice, gridBagConstraints01);
			jPAuction.add(getJPStartinPrice(), gridBagConstraints11);
			jPAuction.add(jLMinimumPrice, gridBagConstraints02);
			jPAuction.add(getJPMinimumPrice(), gridBagConstraints12);
			jPAuction.add(jLAuctionDuration, gridBagConstraints03);
			jPAuction.add(getJPAuctionDuration(), gridBagConstraints13);
			jPAuction.add(jLCategory, gridBagConstraints04);
			jPAuction.add(getJCBCategory(), gridBagConstraints14);
			jPAuction.add(jLSubCategory, gridBagConstraints05);
			jPAuction.add(getJCBSubCategory(), gridBagConstraints15);
			jPAuction.add(jLAuctionDescription, gridBagConstraints06);
			jPAuction.add(getJTAAuctionDescriptionInfo(), gridBagConstraints16);
			jPAuction.add(getJBAuction(), gridBagConstraints08);
			jPAuction.add(jPDummy, gridBagConstraints18);
			jPAuction.add(jLAuctionType, gridBagConstraints07);
			jPAuction.add(getJCBAutionType(), gridBagConstraints17);
		}
		return jPAuction;
	}

	/**
	 * This method initializes jSPAuctions	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJSPAuctions() {
		if (jSPAuctions == null) {
			jSPAuctions = new JScrollPane();
			jSPAuctions.setViewportView(getJTAuctions());
		}
		return jSPAuctions;
	}

	/**
	 * This method initializes jTAuctions	
	 * 	
	 * @return javax.swing.JTable	
	 */
	public JTable getJTAuctions() {
		if (jTAuctions == null) {
			jTAuctions = new JTable(myAgent.getBdtm());
			for (int i = 0; i < BDC.AUCTIONSCOLUMNNAMES.length; i++) {
				jTAuctions.getColumnModel().getColumn(i).setHeaderValue(BDC.AUCTIONSCOLUMNNAMES[i]);
				jTAuctions.getColumnModel().getColumn(i).setPreferredWidth(BDC.AUCTIONSCOLUMNWIDTH[i]);
			}
			jTAuctions.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTAuctions.setColumnSelectionAllowed(false);
			jTAuctions.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);						
			jTAuctions.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					if (!arg0.getValueIsAdjusting()) {
						Auction a = myAgent.getShownAuction(((DefaultListSelectionModel)arg0.getSource()).getMaxSelectionIndex());
						if (a != null) {
							jTFAuctioneer.setText(a.getAuctioneer()); 
							jTFAuctionNr.setText(a.getAN());
							jTAAuctionDescription.setText(a.getAI().getItemDescription());
						} else {
							jTFAuctioneer.setText(null);
							jTFAuctionNr.setText(null);
							jTAAuctionDescription.setText(null);
						}
					}
				}
				
			});
		}
		return jTAuctions;
	}

	/**
	 * This method initializes jPAuctionCommand	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPAuctionCommand() {
		if (jPAuctionCommand == null) {
			jLAuctioner = new JLabel();
			jLAuctioner.setText(BDC.AUCTIONEER);
			jLAuctionNr = new JLabel();
			jLAuctionNr.setText(BDC.AUCTIONNUMBER);
			FlowLayout flowLayout3 = new FlowLayout();
			flowLayout3.setHgap(5);
			flowLayout3.setAlignment(java.awt.FlowLayout.CENTER);
			flowLayout3.setVgap(0);
			jLDecimalPoint = new JLabel();
			jLDecimalPoint.setText(BDC.DECIMALPOINT);
			jLWaitForPrice = new JLabel();
			jLWaitForPrice.setText(BDC.BIDDERPRICE);
			jPAuctionCommand = new JPanel();
			jPAuctionCommand.setLayout(flowLayout3);
			jPAuctionCommand.add(jLAuctioner, null);
			jPAuctionCommand.add(getJTFAuctioneer(), null);
			jPAuctionCommand.add(jLAuctionNr, null);
			jPAuctionCommand.add(getJTFAuctionNr(), null);
			jPAuctionCommand.add(getJBBuyNow(), null);
			jPAuctionCommand.add(jLWaitForPrice, null);
			jPAuctionCommand.add(getJTFBidPriceInt(new JTFIntLength()), null);
			jPAuctionCommand.add(jLDecimalPoint, null);
			jPAuctionCommand.add(getJTFBidPriceDec(new JTFDecLength()), null);
			jPAuctionCommand.add(getJBWaitAndBuy(), null);
			jPAuctionCommand.add(getJBProposition(), null);
		}
		return jPAuctionCommand;
	}

	private JTextField getJTFAuctionNr() {
		if (jTFAuctionNr == null) {
			jTFAuctionNr = new JTextField(BDC.AUCTIONNUMBERCOLUMNS);
			jTFAuctionNr.setEditable(false);
		}
		return jTFAuctionNr;
	}

	/**
	 * This method initializes jSPAuctionDescription	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJSPAuctionDescription() {
		if (jSPAuctionDescription == null) {
			jSPAuctionDescription = new JScrollPane();
			jSPAuctionDescription.setViewportView(getJTAAuctionDescription());
		}
		return jSPAuctionDescription;
	}

	/**
	 * This method initializes jTAAuctionDescription	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTAAuctionDescription() {
		if (jTAAuctionDescription == null) {
			jTAAuctionDescription = new JTextArea();
		}
		return jTAAuctionDescription;
	}

	/**
	 * This method initializes jBBuyNow	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBBuyNow() {
		if (jBBuyNow == null) {
			jBBuyNow = new JButton();
			jBBuyNow.setName(BDC.BUYNOWBUTTON);
			jBBuyNow.setText(BDC.BUYNOWBUTTONLABEL);
			jBBuyNow.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String auctioneer = jTFAuctioneer.getText().trim();
					String auctionNr = jTFAuctionNr.getText().trim();
					GuiEvent e = new GuiEvent(jBBuyNow, BDC.GUIBUYNOW);
					e.addParameter(auctioneer);
					e.addParameter(auctionNr);
					myAgent.postGuiEvent(e);
				}
				
			});
		}
		return jBBuyNow;
	}

	/**
	 * This method initializes jTFPriceIntegerPart	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFBidPriceInt(PlainDocument doc) {
		if (jTFBidPriceInt == null) {
			jTFBidPriceInt = new JTextField(BDC.PRICEINTCOLUMNS);
			jTFBidPriceInt.setDocument(doc);
			jTFBidPriceInt.setText(BDC.PRICESTARTDEF);
		}
		return jTFBidPriceInt;
	}

	/**
	 * This method initializes jTFPriceDecimalPart	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFBidPriceDec(PlainDocument doc) {
		if (jTFBidPriceDec == null) {
			jTFBidPriceDec = new JTextField(BDC.PRICEDECCOLUMNS);			
			jTFBidPriceDec.setDocument(doc);
			jTFBidPriceDec.setText(BDC.PRICESTARTDEF);
		}
		return jTFBidPriceDec;
	}

	/**
	 * This method initializes jBWaitBuy	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBProposition() {
		if (jBProposition == null) {
			jBProposition = new JButton();
			jBProposition.setName(BDC.PROPOSITIONBUTTON);
			jBProposition.setText(BDC.PROPOSITIONBUTTONLABEL);
			jBProposition.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					GuiEvent e = new GuiEvent(jBProposition, BDC.GUIPROPOSITION);
					e.addParameter(jTFAuctionNr.getText().trim());
					e.addParameter(jTFAuctioneer.getText().trim());
					e.addParameter(jTFBidPriceInt.getText().trim()+BDC.FPOINT+jTFBidPriceDec.getText().trim());
					myAgent.postGuiEvent(e);
				}				
			});
		}
		return jBProposition;
	}	

	/**
	 * This method initializes jTFAuctionTitle	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFAuctionTitle() {
		if (jTFAuctionTitle == null) {
			jTFAuctionTitle = new JTextField(BDC.AUCTIONTITLECOLUMNSNUMBER);
			jTFAuctionTitle.setName(BDC.AUCTIONTITLETEXTFIELD);
		}
		return jTFAuctionTitle;
	}

	/**
	 * This method initializes jPStartinPrice	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPStartinPrice() {
		if (jPStartinPrice == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setVgap(0);
			flowLayout.setHgap(0);
			jLDecimalPoint1 = new JLabel();
			jLDecimalPoint1.setText(",");
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints13.gridy = -1;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.gridx = -1;
			jPStartinPrice = new JPanel();
			jPStartinPrice.setLayout(flowLayout);
			jPStartinPrice.add(getJTFStartPriceInt(), null);
			jPStartinPrice.add(jLDecimalPoint1, null);
			jPStartinPrice.add(getJTFStartPriceDec(), null);
		}
		return jPStartinPrice;
	}

	/**
	 * This method initializes jTFPriceIntegerPart1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFStartPriceInt() {
		if (jTFStartPriceInt == null) {
			jTFStartPriceInt = new JTextField(6);
			jTFStartPriceInt.setDocument(new JTFIntLength());
			jTFStartPriceInt.setText(BDC.PRICESTARTDEF);
		}
		return jTFStartPriceInt;
	}

	/**
	 * This method initializes jTFPriceDecimalPart1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFStartPriceDec() {
		if (jTFStartPriceDec == null) {
			jTFStartPriceDec = new JTextField(2);			
			jTFStartPriceDec.setDocument(new JTFDecLength());
			jTFStartPriceDec.setText(BDC.PRICESTARTDEF);
		}
		return jTFStartPriceDec;
	}

	/**
	 * This method initializes jPMinimumPrice	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPMinimumPrice() {
		if (jPMinimumPrice == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout1.setHgap(0);
			flowLayout1.setVgap(0);
			jLDecimalPoint11 = new JLabel();
			jLDecimalPoint11.setText(",");
			jPMinimumPrice = new JPanel();
			jPMinimumPrice.setLayout(flowLayout1);
			jPMinimumPrice.add(getJTFEndPriceInt(), null);
			jPMinimumPrice.add(jLDecimalPoint11, null);
			jPMinimumPrice.add(getJTFEndPriceDec(), null);
		}
		return jPMinimumPrice;
	}

	/**
	 * This method initializes jTFPriceIntegerPart11	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFEndPriceInt() {
		if (jTFEndPriceInt == null) {
			jTFEndPriceInt = new JTextField(6);
			jTFEndPriceInt.setDocument(new JTFIntLength());
			jTFEndPriceInt.setText(BDC.PRICESTARTDEF);
		}
		return jTFEndPriceInt;
	}

	/**
	 * This method initializes jTFPriceDecimalPart11	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFEndPriceDec() {
		if (jTFEndPriceDec == null) {
			jTFEndPriceDec = new JTextField(2);			
			jTFEndPriceDec.setDocument(new JTFDecLength());
			jTFEndPriceDec.setText(BDC.PRICESTARTDEF);
		}
		return jTFEndPriceDec;
	}

	/**
	 * This method initializes jPAuctionDuration	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPAuctionDuration() {
		if (jPAuctionDuration == null) {			
			jLAuctionDurationMinutes = new JLabel();
			jLAuctionDurationMinutes.setText(BDC.MINUTESLABEL);
			jLAuctionDurationHours = new JLabel();
			jLAuctionDurationHours.setText(BDC.HOURSLABEL);
			jPAuctionDuration = new JPanel();
			jLAuctionDurationSeconds = new JLabel();
			jLAuctionDurationSeconds.setText(BDC.SECONDSLABEL);
			jPAuctionDuration.setLayout(new GridBagLayout());
			jPAuctionDuration.add(jLAuctionDurationHours, null);
			jPAuctionDuration.add(getJTFAuctionDurationHours(), null);
			jPAuctionDuration.add(jLAuctionDurationMinutes, null);
			jPAuctionDuration.add(getJTFAuctionDurationMinutes(), null);
			jPAuctionDuration.add(jLAuctionDurationSeconds, null);
			jPAuctionDuration.add(getJTFAuctionDurationSeconds(), null);
		}
		return jPAuctionDuration;
	}

	/**
	 * This method initializes jTFAuctionDurationHours	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFAuctionDurationHours() {
		if (jTFAuctionDurationHours == null) {
			jTFAuctionDurationHours = new JTextField(BDC.DURATIONCOLUMNSNUMBER);
			jTFAuctionDurationHours.setName(BDC.DURATIONHOURSTEXTFIELD);
			jTFAuctionDurationHours.setDocument(new JTFDurationLength());
			jTFAuctionDurationHours.setText(BDC.DURATIONSTARTDEF);
		}
		return jTFAuctionDurationHours;
	}

	/**
	 * This method initializes jTFAuctionDurationMinutes	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFAuctionDurationMinutes() {
		if (jTFAuctionDurationMinutes == null) {
			jTFAuctionDurationMinutes = new JTextField(BDC.DURATIONCOLUMNSNUMBER);
			jTFAuctionDurationMinutes.setName(BDC.DURATIONMINUTESTEXTFIELD);
			jTFAuctionDurationMinutes.setDocument(new JTFDurationLength());
			jTFAuctionDurationMinutes.setText(BDC.DURATIONSTARTDEF);
		}
		return jTFAuctionDurationMinutes;
	}

	private JTextField getJTFAuctionDurationSeconds() {
		if (jTFAuctionDurationSeconds == null) {
			jTFAuctionDurationSeconds = new JTextField(BDC.DURATIONCOLUMNSNUMBER);
			jTFAuctionDurationSeconds.setName(BDC.DURATIONSECONDSTEXTFIELD);
			jTFAuctionDurationSeconds.setDocument(new JTFDurationLength());
			jTFAuctionDurationSeconds.setText(BDC.DURATIONSTARTDEF);
		}
		return jTFAuctionDurationSeconds;
	}

	/**
	 * This method initializes jTAAuctionDescriptionInfo	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTAAuctionDescriptionInfo() {
		if (jTAAuctionDescriptionInfo == null) {
			jTAAuctionDescriptionInfo = new JTextArea();
		}
		return jTAAuctionDescriptionInfo;
	}

	/**
	 * This method initializes jBAuction	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBAuction() {
		if (jBAuction == null) {
			jBAuction = new JButton();
			jBAuction.setText(BDC.BEGINAUCTION);
			jBAuction.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent ev) {
					int ticks = countTicks();
					float priceRange = calculatePriceRange();
					String title = jTFAuctionTitle.getText().trim();
					if (ticks > 0 && priceRange > 0 && title != null && !title.equalsIgnoreCase("")) {
						GuiEvent e = new GuiEvent(jBAuction, BDC.GUIAUCTION);
						AuctionItem ai = new AuctionItem(
							jTAAuctionDescriptionInfo.getText().trim()
							, getJCBCategory().getSelectedItem().toString()
							, getJCBSubCategory().getSelectedItem().toString()
						);
						AuctionDetails ad = new AuctionDetails(
							jTFStartPriceInt.getText().trim()
							, jTFStartPriceDec.getText().trim()
							, jTFEndPriceInt.getText().trim()
							, jTFEndPriceDec.getText().trim()
							, ticks
							, title
							, jCBAutionType.getSelectedItem().toString()
						);
						AuctionFactory af = new AuctionFactory();
						Auction a = af.createAuction(ad, ai, myAgent.getNextAuctionNumber(), myAgent.getLocalName());
						e.addParameter(a);
						myAgent.postGuiEvent(e);
					}
				}								
			});
		}
		return jBAuction;
	}
	
	private float calculatePriceRange() {
		float priceRange = 
			Float.valueOf(jTFStartPriceInt.getText().trim()+BDC.FPOINT+jTFStartPriceDec.getText().trim())
			- Float.valueOf(jTFEndPriceInt.getText().trim()+BDC.FPOINT+jTFEndPriceDec.getText().trim());
		return priceRange;
	}

	private int countTicks() {
		int hours = new Integer(jTFAuctionDurationHours.getText().trim()).intValue();
		int minutes = new Integer(jTFAuctionDurationMinutes.getText().trim()).intValue();
		int seconds = new Integer(jTFAuctionDurationSeconds.getText().trim()).intValue();
		if (hours >= 0 && minutes >= 0 && seconds >= 0)
			return (int) (hours*60*60*BDC.TICK*BDC.TICKSPERSECOND + minutes*60*BDC.TICK*BDC.TICKSPERSECOND + seconds*BDC.TICK*BDC.TICKSPERSECOND);
		else
			return 0;
	}
	
	/**
	 * This method initializes jTFAuctioneer	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFAuctioneer() {
		if (jTFAuctioneer == null) {
			jTFAuctioneer = new JTextField(BDC.AUCTIONEERCOLUMNS);
			jTFAuctioneer.setEditable(false);
		}
		return jTFAuctioneer;
	}
	
	private class JTFNumberOnly extends PlainDocument {
		private static final long serialVersionUID = 1L;

		public void insertString(int offset, String  str, AttributeSet attr) {
			if (str == null) return;
			if (isNumber(str))
				try {
					super.insertString(offset, str, attr);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
		}

		private boolean isNumber(String str) {
			return(!str.matches("\\D"));			
		}
	}
	
	private class JTFDecLength extends JTFNumberOnly {
		private static final long serialVersionUID = 1L;
		private int limit;	  
		
		JTFDecLength() {
			super();
			this.limit = 2;
		}	   	  
		
		public void insertString(int offset, String  str, AttributeSet attr) {
			if (str == null) return;
			if ((getLength() + str.length()) <= limit)				
				super.insertString(offset, str, attr);
		}		
	}
	
	private class JTFIntLength extends JTFNumberOnly {
		private static final long serialVersionUID = 1L;
		private int limit;	  
		
		JTFIntLength() {
			super();
			this.limit = 6;
		}	   	  
		
		public void insertString(int offset, String  str, AttributeSet attr) {
			if (str == null) return;
			if ((getLength() + str.length()) <= limit)				
				super.insertString(offset, str, attr);
		}		
	}
	
	private class JTFDurationLength extends JTFNumberOnly {
		private static final long serialVersionUID = 1L;
		private int limit;	  
		
		JTFDurationLength() {
			super();
			this.limit = 2;
		}	   	  
		
		public void insertString(int offset, String  str, AttributeSet attr) {
			if (str == null) return;
			if ((getLength() + str.length()) <= limit)				
				super.insertString(offset, str, attr);
		}		
	}

	/**
	 * This method initializes jPAuctions1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPFinishedAuctions() {
		if (jPFinishedAuctions == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.weighty = 0.6;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.weighty = 1.0;
			gridBagConstraints4.weightx = 1.0;			
			jPFinishedAuctions = new JPanel();
			jPFinishedAuctions.setLayout(new GridBagLayout());
			jPFinishedAuctions.add(getJSPFinishedAuctions(), gridBagConstraints4);
			jPFinishedAuctions.add(getJSPFinishedAuctionDescription(), gridBagConstraints5);
		}
		return jPFinishedAuctions;
	}

	/**
	 * This method initializes jSPAuctions1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJSPFinishedAuctions() {
		if (jSPFinishedAuctions == null) {
			jSPFinishedAuctions = new JScrollPane();
			jSPFinishedAuctions.setViewportView(getJTFinishedAuctions());
		}
		return jSPFinishedAuctions;
	}

	/**
	 * This method initializes jSPAuctionDescription1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJSPFinishedAuctionDescription() {
		if (jSPFinishedAuctionDescription == null) {
			jSPFinishedAuctionDescription = new JScrollPane();
			jSPFinishedAuctionDescription.setViewportView(getJTAFinishedAuctionDescription());
		}
		return jSPFinishedAuctionDescription;
	}

	/**
	 * This method initializes jTAAuctionDescription1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTAFinishedAuctionDescription() {
		if (jTAFinishedAuctionDescription == null) {
			jTAFinishedAuctionDescription = new JTextArea();
		}
		return jTAFinishedAuctionDescription;
	}

	/**
	 * This method initializes jTFinishedAuctions	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	public JTable getJTFinishedAuctions() {
		if (jTFinishedAuctions == null) {
			jTFinishedAuctions = new JTable(myAgent.getBdftm());
			for (int i = 0; i < BDC.FINISHEDAUCTIONSCOLUMNNAMES.length; i++) {
				jTFinishedAuctions.getColumnModel().getColumn(i).setHeaderValue(BDC.FINISHEDAUCTIONSCOLUMNNAMES[i]);
				jTFinishedAuctions.getColumnModel().getColumn(i).setPreferredWidth(BDC.FINISHEDAUCTIONSCOLUMNWIDTH[i]);
			}
			jTFinishedAuctions.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTFinishedAuctions.setColumnSelectionAllowed(false);
			jTFinishedAuctions.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			jTFinishedAuctions.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					if (!arg0.getValueIsAdjusting()) {
						Auction a = myAgent.getFinishedAuction(((DefaultListSelectionModel)arg0.getSource()).getMaxSelectionIndex());
						if (a != null) {
							jTAFinishedAuctionDescription.setText(a.getAI().getItemDescription());
						} else {
							jTAFinishedAuctionDescription.setText(null);
						}
					}
				}
				
			});
		}
		return jTFinishedAuctions;
	}

	/**
	 * This method initializes jCBCategory	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJCBCategory() {
		if (jCBCategory == null) {
			jCBCategory = new JComboBox(BDC.CategoryList);
			jCBCategory.addItemListener(new SubCategoryItemListener(jCBCategory, getJCBSubCategory()));
		}
		return jCBCategory;
	}
	
	private JComboBox getJCBSubCategory() {
		if (jCBSubCategory == null) {
			jCBSubCategory = new JComboBox((Object [])BDC.getSubCategoryList(jCBCategory.getSelectedItem().toString()));
		}
		return jCBSubCategory;
	}
	
	private JComboBox getJCBAutionType() {
		if (jCBAutionType == null) {
			jCBAutionType = new JComboBox((Object [])BDC.AuctionTypes);
		}
		return jCBAutionType;
	}
	
	private class SubCategoryItemListener implements java.awt.event.ItemListener
	{
		private JComboBox superiorJCB;
		private JComboBox inferiorJCB;
		
		public SubCategoryItemListener(JComboBox superior, JComboBox inferior) {
			superiorJCB = superior;
			inferiorJCB = inferior;
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			if (arg0.getStateChange() == ItemEvent.SELECTED) {
				inferiorJCB.removeAllItems();
				String [] list = BDC.getSubCategoryList(superiorJCB.getSelectedItem().toString());
				for (int i = 0; i<list.length; ++i)
					inferiorJCB.
					addItem(list[i]);
			}			
		}
	}

	
	private JButton getJBWaitAndBuy() {
		if (jBWaitAndBuy == null) {
			jBWaitAndBuy = new JButton();
			jBWaitAndBuy.setName(BDC.WAITBUYBUTTON);
			jBWaitAndBuy.setText(BDC.WAITBUYBUTTONLABEL);
			jBWaitAndBuy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					GuiEvent e = new GuiEvent(jBWaitAndBuy, BDC.GUIWAITBUY);
					e.addParameter(jTFAuctionNr.getText().trim());
					e.addParameter(jTFAuctioneer.getText().trim());
					e.addParameter(jTFBidPriceInt.getText().trim()+BDC.FPOINT+jTFBidPriceDec.getText().trim());
					myAgent.postGuiEvent(e);
				}				
			});
		}
		return jBWaitAndBuy;
	}
	
}
