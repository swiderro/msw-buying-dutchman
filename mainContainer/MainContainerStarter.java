package mainContainer;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Klasa rozruchowa do uruchomienia g³ównego kontenera platformy agentowej JADE na hoœcie. 
 * Jest g³ówn¹ klas¹ pakietu {@link mainContainer} systemu Buying Dutchman.
 */
public class MainContainerStarter {
	/**
	 * Domyœlny, pusty konstruktor. W klasie u¿ywane s¹ jedynie metody statyczne {@link #getBootArguments(String[])} oraz {@link #main(String[])}
	 */
	private MainContainerStarter(){}
	
	/**
	 * Metoda zmieniaj¹ca argumenty wywo³ania w³asnej metody main(String [] args)
	 * na odpowiednie do u¿ycia w jade.Boot.main(String [] args)
	 * 
	 * @param	args	Tabela argumentów do przetworzenia pod k¹tem u¿ycia w wywo³aniu metody jade.Boot.main(String [] args)
	 * @return			Tabela argumentów w formie, w jakiej potrafi je przyj¹æ metoda jade.Bootmain(String [] args)
	 */
	private static String[] getBootArguments(String[] args) {
		String hostname = null;
		try {
			//Pobiera nazwê hosta, na którym jest uruchomiona
		    InetAddress addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		} catch (UnknownHostException e) {
			return null;
		}
		//Argumenty dla jade.Boot.main(String [] args)
		String [] a = new String[] {
			"-gui"
			, "-host"
			, hostname
			, "-nomtp"
		};
		return a;
	}
	
	/**
	 * Metoda rozruchowa dla modu³u serwera centralnego. Uruchamia metodê jade.Boot.main(String [] args), 
	 * tworz¹c instancjê platformy agentowej JADE. Ta metoda jest wywo³ywana z paczki MainContainer.jar.
	 * Argumenty wejœciowe s¹ przekazywane do obróbki do metody {@link #getBootArguments(String[])}.
	 * Metoda jade.Boot.main(String [] args) jest uruchamiana z argumentami zwróconymi przez metodê {@link #getBootArguments(String[])}.
	 * 
	 * @param	args	Tabela argumentów dla modu³u centralnego.
	 */
	public static void main(String [] args) {
//		For future development
//		Profile p = new ProfileImpl();
//		p.setParameter(Profile.MAIN, Boolean.toString(true));
//		jade.core.Runtime.instance().createMainContainer(p);
			try {
				jade.Boot.main(getBootArguments(args));
			}
			catch (Exception ex){
				ex.printStackTrace();
			}
	}
}
