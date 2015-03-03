import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientTest {
	public static void main(String[] args){
		Client charlie;
		String ipAddress = JOptionPane.showInputDialog(null, "Enter IP Address of Server [Blank for localhost]");
		if(ipAddress.equals(""))ipAddress = "127.0.0.1";
	
		charlie = new Client(ipAddress);
		charlie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		charlie.startRunning();

	}
}
