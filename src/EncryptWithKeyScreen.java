import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;

public class EncryptWithKeyScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtSelectYourKey;
	private JTextField txtSelectYourData;
	private JFileChooser openFileChooser=null;
	final JFrame fenetre = new JFrame();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//sqliteDB db=new sqliteDB();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EncryptWithKeyScreen frame = new EncryptWithKeyScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EncryptWithKeyScreen() {
		this.openFileChooser = new JFileChooser();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton keyButton = new JButton("Browse");
		keyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				openFileChooser.setCurrentDirectory(new File("C:\\"));
				 openFileChooser.setFileFilter(new FileNameExtensionFilter("pem", "pem"));
				 
				 int returnVal=openFileChooser.showOpenDialog(fenetre);
				 
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		 
		                    txtSelectYourKey.setText(openFileChooser.getSelectedFile().toString());
		 
		            } else {
		 
		                txtSelectYourKey.setText("no file choosen!!");
		 
		            }
			}
		});
		keyButton.setBounds(12, 34, 117, 25);
		contentPane.add(keyButton);
		
		JButton dataButton = new JButton("Browse");
		dataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				openFileChooser.setCurrentDirectory(new File("C:\\"));
				openFileChooser.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
				 
				 int returnVal=openFileChooser.showOpenDialog(fenetre);
				 
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		 
		                    txtSelectYourData.setText(openFileChooser.getSelectedFile().toString());
		 
		            } else {
		 
		                txtSelectYourData.setText("no file choosen!!");
		 
		            }
			}
		});
		dataButton.setBounds(12, 71, 117, 25);
		contentPane.add(dataButton);
		
		txtSelectYourKey = new JTextField();
		txtSelectYourKey.setText("select your key decoded");
		txtSelectYourKey.setBounds(141, 37, 279, 19);
		contentPane.add(txtSelectYourKey);
		txtSelectYourKey.setColumns(10);
		
		txtSelectYourData = new JTextField();
		txtSelectYourData.setText("select your data to be encrypt");
		txtSelectYourData.setBounds(141, 71, 279, 19);
		contentPane.add(txtSelectYourData);
		txtSelectYourData.setColumns(10);
		
		JLabel succesmessage = new JLabel("");
		succesmessage.setForeground(Color.GREEN);
		succesmessage.setBounds(12, 108, 410, 15);
		contentPane.add(succesmessage);
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String data=txtSelectYourData.getText();
				String key=txtSelectYourKey.getText();
				String[] cmd=new String[] {"ressouces/script_enc.sh",data,key};
				ProcessBuilder pb=new ProcessBuilder(cmd);
				try {
					Process p=pb.start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				succesmessage.setText("data successfuly encrypted in : /home/kali/stega_project");
				
				String j="/home/kali/stega_project/encrypted_data";
			      byte[] encoded=null;
				try {
					encoded = Files.readAllBytes(Paths.get(j));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      String gg= new String(encoded, StandardCharsets.US_ASCII);
			      
				sqliteDB db=new sqliteDB();
				db.add("INSERT INTO stegano (data) values ('"+j+"')");
				db.closeConnection();
				
			}
		});
		ok.setBounds(165, 150, 117, 25);
		contentPane.add(ok);
		
		
	}
}
