package com.marcinswiderski.buyingDutchman.agent.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Klasa odpowiedzialna za GUI logowania do systemu.
 * Tworzy okno logowania, w kt�rym u�ytkownik wprowadza nazw� agenta do utworzenia oraz adres hosta.
 * Adres hosta identyfikuje platform� agentow�, do kt�rej agent ma za zadanie po��czy� si�.
 * 
 * Dziedziczy po javax.swing.JFrame, korzysta z bibliotek Swing i AWT.
 */
public class LoginGui extends JFrame{

	private static final long serialVersionUID = 1L;
	//FRAME CONSTS
	/**
	 * Szeroko�� okna logowania.
	 */
	private static final int FRAMEWIDTH = 310;
	/**
	 * Wysoko�� okna logowania.
	 */
	private static final int FRAMEHEIGHT = 114;
	/**
	 * Tytu� okna logowania.
	 */
	private static final String FRAMETITLE = "Buying Dutchman Login";  
	
	//ELEMENTS
	private JPanel jContentPane = null;
	private JPanel jPAccount = null;
	private JPanel jPAccountDetails = null;
	private JPanel jPLogin = null;
	private JLabel jLLogin = null;
	/** Nazwa agenta, kt�ry ma zosta� utworzony. */
	private JTextField jTFLogin = null;
	private JLabel jLHost = null;
	/** Nazwa hosta, na kt�rej jest uruchomiona platforma agentowa. */
	private JTextField jTFHost = null;
	private JButton jBLogin = null;
	//ELEMENTS CONSTS
	private static final String HOSTLABEL = "Host";
	private static final String LOGINLABEL = "Login";  //  @jve:decl-index=0:
	private static final int LOGINCOLUMNSNUMBER = 15;
	private static final String LOGINTEXTFIELD = "Login";  
	private static final String LOGINBUTTON = "Zaloguj";  
	private static final String LOGINTEXTFIELDINFO = "Login info";  
	//AGENT CONSTS
	/**
	 * Nazwa klasy agenta. U�ywana w celu utworzenia agenta.
	 */
	private static final String AGENTNAME = "BuyingDutchmanAgent";
	private static final String SEPARATOR_1 = ":";
	/**
	 * Nazwa pakietu, zawieraj�cego klas� agenta. U�ywana w celu utworzenia agenta.
	 */
	private static final String PACKAGENAME = "client";
	private static final String SEPARATOR_2 = ".";
	 	
	/**
	 * Konstruktor domy�lny. Wywo�uje super-konstruktor, nast�pnie wywo�uje metod� {@link #initialize()}.
	 */
	public LoginGui() {
		super();
		initialize();
	}
	
	public LoginGui(String[] args) {
		super();
		initialize();
		//no agents names nor host name
		if (args.length < 1)
			return;
		//there is at least host name
		String host = args[args.length-1];
		setHost(host);
		//checking for agent names
		if (args.length > 1) {
			for (int i = 0; i<args.length-1; i++) {
				//run agent on the host
				setAgentName(args[i]);
				connect();
			}
		}
	}

	private void setAgentName(String agentName) {
		getJTFLogin().setText(agentName);
	}

	private void setHost(String host) {
		getJTFHost().setText(host);
	}

	/**
	 * Metoda inicializuj�ca okno logowania. 
	 */
	private void initialize() {
        this.setSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
        this.setContentPane(getJContentPane());
        this.setTitle(FRAMETITLE);        
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	  	this.setVisible(true);
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPAccount(), BorderLayout.CENTER);
		}
		return jContentPane;
	}
	
	private JPanel getJPAccount() {
		if (jPAccount == null) {
			jPAccount = new JPanel();
			jPAccount.setLayout(new BorderLayout());
			jPAccount.add(getJPAccountDetails(), BorderLayout.CENTER);
			jPAccount.add(getJPLogin(), BorderLayout.NORTH);
		}
		return jPAccount;
	}
	
	private JPanel getJPAccountDetails() {
		if (jPAccountDetails == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			jLHost = new JLabel();
			jLHost.setText(HOSTLABEL);
			jPAccountDetails = new JPanel();
			jPAccountDetails.setLayout(flowLayout1);
			jPAccountDetails.add(jLHost, null);
			jPAccountDetails.add(getJTFHost(), null);
		}
		return jPAccountDetails;
	}
	
	private JPanel getJPLogin() {
		if (jPLogin == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			jLLogin = new JLabel();
			jLLogin.setText(LOGINLABEL);
			jPLogin = new JPanel();
			jPLogin.setLayout(flowLayout);
			jPLogin.add(jLLogin, null);
			jPLogin.add(getJTFLogin(), null);
			jPLogin.add(getJBLogin(), null);
		}
		return jPLogin;
	}
	
	private JTextField getJTFLogin() {
		if (jTFLogin == null) {
			jTFLogin = new JTextField(LOGINCOLUMNSNUMBER);
			jTFLogin.setName(LOGINTEXTFIELD);
			jTFLogin.addKeyListener(
	        	new KeyListener() {

					@Override
					public void keyPressed(KeyEvent arg0) {
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
							connect();
						}
					}

					@Override
					public void keyTyped(KeyEvent arg0) {
					}
	        		
	        	}
	        );
		}
		return jTFLogin;
	}

	private JTextField getJTFHost() {
		if (jTFHost == null) {
			jTFHost = new JTextField(LOGINCOLUMNSNUMBER);
			jTFHost.setName(LOGINTEXTFIELDINFO);
		}
		return jTFHost;
	}

	private JButton getJBLogin() {
		if (jBLogin == null) {
			jBLogin = new JButton(LOGINBUTTON);
			jBLogin.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					connect();
				}
			} );
		}
		return jBLogin;
	}	

	/**
	 * Metoda tworz�ca argumenty odpowiednie do u�ycia w jade.Boot.main(String [] args) w celu utworzenia agenta systemu.
	 * 
	 * @return			Tabela argument�w w formie, w jakiej potrafi je przyj�� metoda jade.Boot.main(String [] args)
	 * @see				#connect()
	 */
	private String[] getBootArguments() {
		String AgentFQN = getAgentFQN();
		String [] a = new String[] {
			"-container"
			, "-host"
			, getJTFHost().getText().trim()
			, AgentFQN
		};
		return a;
	}

	/**
	 * Metoda s�u�y do utworzenia i po��czenia agenta z platform� agentow�. U�ywa w tym celu jade.Boot.main(String [] args).
	 * Do jade.Boot.main s� przekazywane argumenty przygotowane za pomoc� metody {@link #getBootArguments()}
	 */
	protected void connect() {
		try {			
			String login = jTFLogin.getText().trim();
			if (login != null && login.length() > 0) {
				try {
	            	jade.Boot.main(getBootArguments());
				}
				catch (Exception ex){
					ex.printStackTrace();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	/**
	 * Zwraca ci�g reprezentuj�cy klas� agenta. Wykorzystywany jako parametr wywo�ania jade.Boot.main(String [] args)
	 * 
	 * @return Ci�g reprezentuj�cy klas� agenta.
	 * @see #getBootArguments()
	 * @see #PACKAGENAME
	 * @see #AGENTNAME
	 */
	private String getAgentFQN() {
		return jTFLogin.getText().trim()+SEPARATOR_1+PACKAGENAME+SEPARATOR_2+AGENTNAME;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
