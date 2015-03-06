import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientTest {

	public void userName(){
		
	}
		private final static String userName = JOptionPane.showInputDialog(null, "Enter username");
		public static String getUserName() {
		      return userName;
		
 }

	public static void main(String[] args){
		Client charlie;
		String ipAddress = JOptionPane.showInputDialog(null, "Enter IP Address of Server (Blank for localhost)");
		if(ipAddress.equals(null))ipAddress = "127.0.0.1";
	
		charlie = new Client(ipAddress);
		charlie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		charlie.startRunning();

	}
}
