package buyingDutchmanClient;

import java.awt.EventQueue;

/**
 * Klasa startowa dla modu³u agenta systemu Buying Dutchman. 
 * Jej odpowiedzialnoœci¹ jest utworzenie oddzielnego w¹tku i uruchomienie w nim GUI logowania modu³u agenta, zawartego w klasie {@link LoginGui}.
 */
public class AgentStarter {
	/**
	 * Metoda rozruchowa dla modu³u agenta. Uruchamia instancjê klasy {@link LoginGui} w nowym w¹tku.
	 * Ta metoda jest wywo³ywana z paczki AgentStarter.jar.
	 * 
	 * @param	args	Tabela argumentów dla modu³u agenta. Nie u¿ywana w praktyce.
	 */
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
