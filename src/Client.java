import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import sun.awt.WindowClosingListener;

public class Client extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	public String userName = "";
	private JTextField sendFiles;
	private JButton send;
	
	public Client(String host){
		super("PowerChat Client");
		serverIP = host;
		userText = new JTextField();
		sendFiles = new JTextField();
		send = new JButton();
		sendFiles.setEditable(false);
		userText.setEditable(false);
		send = new JButton("Send");
		send.setBounds(235,120,65,50);
		userText.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					sendMessage(event.getActionCommand(),host);
					userText.setText("");
				}
			  }
			);
		sendFiles.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						sendFiles();
						sendFiles.setText("Files");
					}
				  }
				);
		send.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						send();
						send.setText("Send");
					}
				  }
				);
			add(userText, BorderLayout.NORTH);
			add(sendFiles, BorderLayout.SOUTH);
			add(send);
			chatWindow = new JTextArea();
			add(new JScrollPane(chatWindow), BorderLayout.CENTER);
			chatWindow.setEditable(false);
			setSize(400,400);
			setVisible(true);
	}
	
	//connect to server
	public void startRunning(){
		try{
			connectToServer();
			setupStreams();
			whileChatting();
		}catch(EOFException eofException){
			showMessage("\nClient terminated connection");		
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			closeCrap();
		}
	}
	
	//connect to server
	private void connectToServer() throws IOException{
		showMessage("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		showMessage("Connected to: " + connection.getInetAddress().getHostName());
	}
	
	//setup streams
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\nYour streams are good to go! \n");
	}
	
	//while chatting with server
	private void whileChatting() throws IOException{
		ableToType(true);
		do{
			try{
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("\nI don't know that object type...bitch");
			}
		}while(!message.equals("SERVER - END"));
	}
	
	//close the streams
	private void closeCrap(){
		showMessage("\nClosing connections down...");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	//send messages to server
	private void sendMessage(String message, String userName){
		try{
			userName = ClientTest.getUserName();
			output.writeObject(userName + " - " + message);
			output.flush();
			showMessage("\n" + userName + " - " + message);
		}catch(IOException ioException){
			chatWindow.append("\nSomething went wrong sending the message");
		}
	}
	
	//send files
	private void sendFiles(){
		
	}
	
	//send files
	private void send(){
		
	}
	
	//change update
	private void showMessage(final String m){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(m);
				}
			}
		);
	}
	
	//user chat status
	private void sendStatus(int status)
	{
		try {
			switch(status) {
				case 0:
					status = 1;//online
				break;
				case 1:
					status = 3;//busy
				break;
				case 2:
					status = 2;//offline
				break;
				default:
					status = 4;//idle
			}

			sendMessage(message, message);
		}
		catch(Exception e) { System.exit(0);}
	}
	
	//close window prompt
	public void windowClosing(WindowEvent e)
	{
		if(JOptionPane.showConfirmDialog(chatWindow,
					"Are you sure you want to quit?",
					"Quit ",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null) == JOptionPane.YES_OPTION) {
		
			System.exit(0);
		}
	}
	
	//gives user permission to type
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userText.setEditable(tof);
				}
			}
		);	
	}
}
