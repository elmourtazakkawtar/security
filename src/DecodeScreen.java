import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;

public class DecodeScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtSelectWhereTo;
	private JFileChooser openFileChooser=null;
	final JFrame fenetre = new JFrame();
	private String abc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DecodeScreen frame = new DecodeScreen();
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
	public DecodeScreen() {
		
	}

	public DecodeScreen(String abc) throws HeadlessException {
		super();
		this.abc=abc;
		
this.openFileChooser = new JFileChooser();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 224);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton selectDcodePath = new JButton("Browse");
		selectDcodePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				openFileChooser.setCurrentDirectory(new File("C:\\"));
				openFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				openFileChooser.setAcceptAllFileFilterUsed(false);
				 //openFileChooser.setFileFilter(new FileNameExtensionFilter("wav", "wav"));
				 
				 int returnVal=openFileChooser.showOpenDialog(fenetre);
				 
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		 
		                    txtSelectWhereTo.setText(openFileChooser.getSelectedFile().toString());
		 
		            } else {
		 
		            	txtSelectWhereTo.setText("no file choosen!!");
		 
		            }
			}
		});
		selectDcodePath.setBounds(8, 40, 83, 21);
		contentPane.add(selectDcodePath);
		
		txtSelectWhereTo = new JTextField();
		txtSelectWhereTo.setText("select where to decode your message");
		txtSelectWhereTo.setBounds(99, 41, 327, 19);
		contentPane.add(txtSelectWhereTo);
		txtSelectWhereTo.setColumns(10);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				encodeDecodeService t=new encodeDecodeService();
				System.out.println(t.decodificaMensagem(abc));
				//message.setText(t.decodificaMensagem(lblNewLabel.getText()));
				
				String j=txtSelectWhereTo.getText()+"/decode.pem";
				 // Open the file.
		        PrintWriter out=null;
				try {
					out = new PrintWriter(j);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} // Step 2

		        // Write the name of four oceans to the file
		        out.println(t.decodificaMensagem(abc));

		        // Close the file.
		        out.close();  // Step 4
		        
			     
				
			}
		});
		okButton.setBounds(178, 118, 83, 21);
		contentPane.add(okButton);
	}

}