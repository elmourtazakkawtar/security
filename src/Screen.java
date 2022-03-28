import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;



//import BusinessLogic.encodeDecodeService;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;

public class Screen extends JFrame {

	private String pass;
	private JPanel contentPane;
	private final JFileChooser openFileChooser;
	final JFrame fenetre = new JFrame();
	private JTextField lblNewLabel;
	private JTextField message;
	SecretKey key;
	private JTextField txtSelectFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Screen frame = new Screen();
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
	public Screen() {
		
		this.openFileChooser = new JFileChooser();
		
		//String pass=new String();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 588, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel showMessages = new JLabel("");
		showMessages.setForeground(Color.GREEN);
		showMessages.setBounds(26, 44, 470, 15);
		contentPane.add(showMessages);
		
		JButton btnNewButton = new JButton("Encode text");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String msg =message.getText() ;
				String filePath = lblNewLabel.getText();
				String outPath = filePath.substring(0,filePath.length()-4)+"-Encoded.wav";
				
				encodeDecodeService t=new encodeDecodeService();
				t.codificaMensagem(msg, filePath, outPath);
				System.out.println("Successfully encoded the message into " + outPath);

				
			}
		});
		btnNewButton.setBounds(123, 10, 97, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Decode");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//encodeDecodeService t=new encodeDecodeService();
				//System.out.println(t.decodificaMensagem(lblNewLabel.getText()));
				//message.setText(t.decodificaMensagem(lblNewLabel.getText()));
				
				new DecodeScreen(lblNewLabel.getText()).setVisible(true);
		        
			}
		});
		btnNewButton_1.setBounds(334, 10, 83, 21);
		contentPane.add(btnNewButton_1);
		
		JButton audioButton = new JButton("Browse");
		audioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				 openFileChooser.setCurrentDirectory(new File("C:\\"));
				 openFileChooser.setFileFilter(new FileNameExtensionFilter("wav", "wav"));
				 
				 int returnVal=openFileChooser.showOpenDialog(fenetre);
				 
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		 
		                    lblNewLabel.setText(openFileChooser.getSelectedFile().toString());
		 
		            } else {
		 
		                lblNewLabel.setText("no file choosen!!");
		 
		            }
			}
		});
		audioButton.setBounds(26, 100, 89, 21);
		contentPane.add(audioButton);
		
		lblNewLabel = new JTextField();
		lblNewLabel.setText("select audio file");
		lblNewLabel.setBounds(123, 101, 424, 19);
		contentPane.add(lblNewLabel);
		lblNewLabel.setColumns(10);
		
		message = new JTextField();
		message.setText("write here you text to encode");
		message.setBounds(26, 156, 521, 114);
		contentPane.add(message);
		message.setColumns(10);
		
		
		JButton GenerateKey = new JButton("Generate key");
		GenerateKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 //SecretKey key;
				 /*final int KEY_SIZE = 256;
				 final int T_LEN = 128;
				 
				 KeyGenerator generator=null;
				try {
					generator = KeyGenerator.getInstance("AES");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				    generator.init(KEY_SIZE);
				    key = generator.generateKey();
				    System.out.println(key);
				    message.setText(key.toString());*/
				
				
				String[] cmd=new String[] {"ressouces/script.sh"};
				ProcessBuilder pb=new ProcessBuilder(cmd);
				try {
					Process p=pb.start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				showMessages.setText("key successfuly generated in : /home/kali/stega_project");
				
				
				     /* String j="C:/Users/hp/Desktop/INPT_2021_2022/CyberSecurity/project_security_v1/bob.pem";
				      byte[] encoded=null;
					try {
						encoded = Files.readAllBytes(Paths.get(j));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				      String gg= new String(encoded, StandardCharsets.US_ASCII);
				      System.out.println(gg.replace("-----BEGIN RSA PRIVATE KEY-----","")
				    		  .replace("-----END RSA PRIVATE KEY-----", ""));
				    */
			}
			
		});
		GenerateKey.setBounds(26, 10, 91, 21);
		contentPane.add(GenerateKey);
		
		JButton encryptKey = new JButton("Encrypt");
		encryptKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String messag=message.getText();
				
				byte[] messageInBytes = messag.getBytes();
			    Cipher encryptionCipher=null;
				try {
					encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    try {
					encryptionCipher.init(Cipher.ENCRYPT_MODE,key);
				} catch (InvalidKeyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    byte[] encryptedBytes=null;
				try {
					encryptedBytes = encryptionCipher.doFinal(messageInBytes);
				} catch (IllegalBlockSizeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String enc=Base64.getEncoder().encodeToString(encryptedBytes);
			    System.out.println(enc);
			    Screen.this.message.setText(enc);
				
			}
		});
		encryptKey.setBounds(26, 133, 89, 21);
		contentPane.add(encryptKey);
		
		txtSelectFile = new JTextField();
		txtSelectFile.setText("select file");
		txtSelectFile.setBounds(123, 72, 424, 19);
		contentPane.add(txtSelectFile);
		txtSelectFile.setColumns(10);
		
		JButton fileButton = new JButton("Browse");
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFileChooser.setCurrentDirectory(new File("C:\\"));
				 openFileChooser.setFileFilter(new FileNameExtensionFilter("pem", "pem"));
				 
				 int returnVal=openFileChooser.showOpenDialog(fenetre);
				 
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		 
		                    txtSelectFile.setText(openFileChooser.getSelectedFile().toString());
		 
		            } else {
		 
		                txtSelectFile.setText("no file choosen!!");
		 
		            }
			}
		});
		fileButton.setBounds(26, 71, 89, 21);
		contentPane.add(fileButton);
		
		JButton btnNewButton_3 = new JButton("Encode file");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String j=txtSelectFile.getText();
			      byte[] encoded=null;
				try {
					encoded = Files.readAllBytes(Paths.get(j));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      String gg= new String(encoded, StandardCharsets.US_ASCII);
				String msg =message.getText() ;
				String filePath = lblNewLabel.getText();
				String outPath = filePath.substring(0,filePath.length()-4)+"-Encoded.wav";
				
				encodeDecodeService t=new encodeDecodeService();
				t.codificaMensagem(gg, filePath, outPath);
				System.out.println("Successfully encoded the message into " + outPath);
			}
		});
		btnNewButton_3.setBounds(228, 10, 89, 21);
		contentPane.add(btnNewButton_3);
		
		JButton encryptwithkey = new JButton("use the key decoded to encrypt your data");
		encryptwithkey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		encryptwithkey.setBounds(26, 292, 340, 25);
		contentPane.add(encryptwithkey);
		
		
		
		
	}
}