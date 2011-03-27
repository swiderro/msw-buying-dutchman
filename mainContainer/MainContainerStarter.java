package mainContainer;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainContainerStarter {
	
	private static String[] getBootArguments(String[] args) {
		String hostname = null;
		try {
			// Get hostname
		    InetAddress addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		} catch (UnknownHostException e) {
			return null;
		}

		String [] a = new String[] {
			"-gui"
			, "-host"
			, hostname
			, "-nomtp"
		};
		return a;
	}
	
	
	
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
