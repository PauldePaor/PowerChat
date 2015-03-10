import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientTest {

	public final static String userName() {
		int correct = 0;
		while(correct == 0){
		String username = JOptionPane.showInputDialog(null, "Enter username");
	    if(username.equals("") || username == null){
	    	correct = 0;
	    	JOptionPane.showMessageDialog(null, "You must enter a username");
	    }
	    else{
	    	correct = 1;
	    	JOptionPane.showMessageDialog(null,"Welcome to PowerChat " + username);
	}
		return username;
}
		return null;
		}
	
	public static void main(String[] args){
		Client charlie;
		int ran = 0;
		while(ran != 1){
			userName();
			ran = 1;
		}
		String ipAddress = JOptionPane.showInputDialog(null, "Enter IP Address of Server (Blank for localhost)");
		if(ipAddress.equals(null))ipAddress = "127.0.0.1";
	
		charlie = new Client(ipAddress);
		charlie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		charlie.startRunning();

	}
}


