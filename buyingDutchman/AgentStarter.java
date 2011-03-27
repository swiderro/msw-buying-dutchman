package buyingDutchman;

import java.awt.EventQueue;

public class AgentStarter {
	public static void main(final String[] args){		
		EventQueue.invokeLater(
			new Runnable(){
	           public void run(){
	              try{
	            	  new LoginGui();
	              }
	              catch (Exception ex){
	                 ex.printStackTrace();
	              }
	           }
			}
		);		
	}
}
