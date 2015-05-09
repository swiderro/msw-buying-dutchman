package mainContainer;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Klasa rozruchowa do uruchomienia g��wnego kontenera platformy agentowej JADE na ho�cie. 
 * Jest g��wn� klas� pakietu {@link mainContainer} systemu Buying Dutchman.
 */
public class MainContainerStarter {
	/**
	 * Domy�lny, pusty konstruktor. W klasie u�ywane s� jedynie metody statyczne {@link #getBootArguments(String[])} oraz {@link #main(String[])}
	 */
	private MainContainerStarter(){}
	
	/**
	 * Metoda zmieniaj�ca argumenty wywo�ania w�asnej metody main(String [] args)
	 * na odpowiednie do u�ycia w jade.Boot.main(String [] args)
	 * 
	 * @param	args	Tabela argument�w do przetworzenia pod k�tem u�ycia w wywo�aniu metody jade.Boot.main(String [] args)
	 * @return			Tabela argument�w w formie, w jakiej potrafi je przyj�� metoda jade.Bootmain(String [] args)
	 */
	private static String[] getBootArguments(String[] args) {
		String hostname = null;
		try {
			//Pobiera nazw� hosta, na kt�rym jest uruchomiona
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
	 * Metoda rozruchowa dla modu�u serwera centralnego. Uruchamia metod� jade.Boot.main(String [] args), 
	 * tworz�c instancj� platformy agentowej JADE. Ta metoda jest wywo�ywana z paczki MainContainer.jar.
	 * Argumenty wej�ciowe s� przekazywane do obr�bki do metody {@link #getBootArguments(String[])}.
	 * Metoda jade.Boot.main(String [] args) jest uruchamiana z argumentami zwr�conymi przez metod� {@link #getBootArguments(String[])}.
	 * 
	 * @param	args	Tabela argument�w dla modu�u centralnego.
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
