package com.marcinswiderski.buyingDutchman.agent.client;

import java.awt.*;

/**
 * Klasa startowa dla modu�u agenta systemu Buying Dutchman. 
 * Jej odpowiedzialno�ci� jest utworzenie oddzielnego w�tku i uruchomienie w nim GUI logowania modu�u agenta, zawartego w klasie {@link LoginGui}.
 */
public class AgentStarter {
	/**
	 * Metoda rozruchowa dla modu�u agenta. Uruchamia instancj� klasy {@link LoginGui} w nowym w�tku.
	 * Ta metoda jest wywo�ywana z paczki AgentStarter.jar.
	 * 
	 * @param	args	Tabela argument�w dla modu�u agenta. Nie u�ywana w praktyce.
	 */
	public static void main(final String[] args){		
		EventQueue.invokeLater(
			new Runnable(){
	           public void run(){
	              try{
	            	  new LoginGui(args);
	              }
	              catch (Exception ex){
	                 ex.printStackTrace();
	              }
	           }
			}
		);		
	}
}
