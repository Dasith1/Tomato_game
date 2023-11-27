package Gameui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.border.SoftBevelBorder;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.xdevapi.PreparableStatement;

import database.Database;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class signupui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtname;
	private JTextField txtemail;
	private JPasswordField passwordbox;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	
	Connection conn =null;
	PreparedStatement pst=null;
	ResultSet rs =null;
	
	
	
	public signupui() {
		
		 conn=Database.conn();	
		
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(signupui.class.getResource("/icons/profile.png")));
		setTitle("SIGN UP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1076, 697);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.BLACK);
		panel_1.setBorder(null);
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(-20, -36, 1197, 703);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setForeground(new Color(250, 240, 230));
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(295, 145, 147, 65);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setForeground(new Color(250, 240, 230));
		lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(297, 232, 125, 79);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setForeground(new Color(250, 240, 230));
		lblNewLabel_3.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
		lblNewLabel_3.setBounds(295, 321, 174, 105);
		panel_1.add(lblNewLabel_3);
		
		txtname = new JTextField();
		txtname.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtname.setBounds(460, 155, 389, 48);
		panel_1.add(txtname);
		txtname.setColumns(10);
		
		txtemail = new JTextField();
		txtemail.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtemail.setBounds(460, 250, 389, 48);
		panel_1.add(txtemail);
		txtemail.setColumns(10);
		
		passwordbox = new JPasswordField();
		passwordbox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordbox.setBounds(460, 352, 389, 48);
		panel_1.add(passwordbox);
		
		JButton btnsignup = new JButton("SIGN UP");
		btnsignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				String name=txtname.getText();
				String email=txtemail.getText();
				char[] password =passwordbox.getPassword();
				 
				
			
				
				String sql ="INSERT INTO users(name,email,password, score) VALUES(?,?,?, 0)";


				
				
				 try {
				    pst=conn.prepareStatement(sql);
				    pst.setString(1, name);
				    pst.setString(2, email);
				    pst.setString(3, String.valueOf(password));
				    
				    
				    if (name.isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Name cannot be empty!");
				        return;
				    }

				    // Check if the email field is empty
				    if (email.isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Email cannot be empty!");
				        return;
				    }

				    // Check if the password field is empty
			        if (password.length == 0) {
			            JOptionPane.showMessageDialog(null, "Password cannot be empty!");
			            return;
			        }
				    
				    
				  
				    // Check if the email address is already in use
				    String checkEmailSql = "SELECT * FROM users WHERE email = ?";
				    PreparedStatement checkEmailPst = conn.prepareStatement(checkEmailSql);
				    checkEmailPst.setString(1, email);
				    rs = checkEmailPst.executeQuery();
				    if (rs.next()) {
				        // Display an error message if the email address is already in use
				        JOptionPane.showMessageDialog(null, "Email address is already in use. Please enter a different email address.");
				        return;
				    }

				    // Insert the new user into the database
				    pst.execute();

				    // Display a success message
				    JOptionPane.showMessageDialog(null, "Signup complete!");

				    // Open the login UI
				    loginui log = new loginui();
				    log.setVisible(true);
				    dispose();

				} catch (SQLException ex) {
				    // Display an error message if something goes wrong
				    JOptionPane.showMessageDialog(null, ex);
				}
				
			 
				 btnsound() ;
			}
		});
		btnsignup.setForeground(new Color(147, 112, 219));
		btnsignup.setBackground(new Color(210, 180, 140));
		btnsignup.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
		btnsignup.setBounds(460, 436, 147, 48);
		panel_1.add(btnsignup);
		
		JLabel lblNewLabel_5 = new JLabel("Already have an account?");
		lblNewLabel_5.setForeground(new Color(255, 218, 185));
		lblNewLabel_5.setFont(new Font("Segoe UI Black", Font.PLAIN, 26));
		lblNewLabel_5.setBounds(460, 603, 343, 30);
		panel_1.add(lblNewLabel_5);
		
		JButton btnsignin = new JButton("SIGN IN");
		btnsignin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginui login =new loginui();
				login.setVisible(true);
				btnsound(); 
				dispose();
			}
		});
		btnsignin.setForeground(new Color(218, 112, 214));
		btnsignin.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		btnsignin.setBounds(825, 601, 119, 32);
		panel_1.add(btnsignin);
		
		JLabel lblNewLabel_5_1 = new JLabel("Create new account");
		lblNewLabel_5_1.setForeground(new Color(255, 218, 185));
		lblNewLabel_5_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 30));
		lblNewLabel_5_1.setBounds(302, 68, 411, 48);
		panel_1.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(signupui.class.getResource("/icons/tomato3.png")));
		lblNewLabel.setBounds(-615, 349, 1931, 1010);
		panel_1.add(lblNewLabel);
	}
public static void  btnsound() {
		
		try {
			File  soundfile =new File("src\\sound\\achive-sound-132273.wav");
			AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(soundfile);
			Clip clip =AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
