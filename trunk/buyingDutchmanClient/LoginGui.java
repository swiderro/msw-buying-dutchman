package buyingDutchmanClient;

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

public class LoginGui extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4882352228364389156L;
	//FRAME CONSTS
	private static final int FRAMEWIDTH = 310;
	private static final int FRAMEHEIGHT = 114;
	private static final String FRAMETITLE = "Buying Dutchman Login";  
	
	//ELEMENTS
	private JPanel jContentPane = null;
	private JPanel jPAccount = null;
	private JPanel jPAccountDetails = null;
	private JPanel jPLogin = null;
	private JLabel jLLogin = null;
	private JTextField jTFLogin = null;
	private JLabel jLHost = null;
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
	private static final String AGENTNAME = "BuyingDutchmanAgent";
	private static final String SEPARATOR_1 = ":";	
	private static final String PACKAGENAME = "buyingDutchmanClient";
	private static final String SEPARATOR_2 = ".";
	 	
	/**
	 * Default constructor 
	 * 
	 */
	public LoginGui() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
        this.setContentPane(getJContentPane());
        this.setTitle(FRAMETITLE);        
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	  	this.setVisible(true);
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
			jContentPane.add(getJPAccount(), BorderLayout.CENTER);
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes jPAccount	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	
	private JPanel getJPAccount() {
		if (jPAccount == null) {
			jPAccount = new JPanel();
			jPAccount.setLayout(new BorderLayout());
			jPAccount.add(getJPAccountDetails(), BorderLayout.CENTER);
			jPAccount.add(getJPLogin(), BorderLayout.NORTH);
		}
		return jPAccount;
	}
	
	/**
	 * This method initializes jPAccountDetails	
	 * 	
	 * @return javax.swing.JPanel	
	 */
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
	
	/**
	 * This method initializes jPLogin	
	 * 	
	 * @return javax.swing.JPanel	
	 */
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
	
	/**
	 * This method initializes jTFLogin	
	 * 	
	 * @return javax.swing.JTextField	
	 */
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

	/**
	 * This method initializes jTFLoginInfo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFHost() {
		if (jTFHost == null) {
			jTFHost = new JTextField(LOGINCOLUMNSNUMBER);
			jTFHost.setName(LOGINTEXTFIELDINFO);
			//TODO Delete after testing
			jTFHost.setText("Marcin-PC");
		}
		return jTFHost;
	}

	/**
	 * This method initializes jBLogin	
	 * 	
	 * @return javax.swing.JButton	
	 */
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
	
	private String getAgentFQN() {
		return jTFLogin.getText().trim()+SEPARATOR_1+PACKAGENAME+SEPARATOR_2+AGENTNAME;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
