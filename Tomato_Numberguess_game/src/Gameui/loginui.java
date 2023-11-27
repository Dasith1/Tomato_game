package Gameui;

import database.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.sql.*;

import com.mysql.cj.protocol.a.NativeConstants.StringLengthDataType;
import com.perisic.tomato.peripherals.GameGUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class loginui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtusername;
	private JPasswordField txtpassword;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	
	Connection conn=null;
	PreparedStatement pst=null;
	ResultSet rs= null;
	
	
	public loginui() {
		 
		//connection
	     conn=Database.conn();	
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(loginui.class.getResource("/icons/profile.png")));
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1013, 686);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(107, 142, 35));
		panel.setBackground(Color.BLACK);
		panel.setBounds(-11, -9, 1058, 664);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Sign in");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Uname= txtusername.getText();
				char[]password=txtpassword.getPassword();
				
				try {
					String sql = "SELECT * FROM users WHERE name=? AND password=?";
					pst=conn.prepareStatement(sql);
					pst.setString(1, Uname);
					pst.setString(2, new String(password)); // Fix password comparison
					rs = pst.executeQuery();
					 
					if(!rs.next()) {
						JOptionPane.showMessageDialog(null, "Invalid username or password");
					}else {
						//String name =rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4);
						//JOptionPane.showMessageDialog(null,"Login Successful" );
						String name = rs.getString("name");
                        int score = rs.getInt("score");
                        JOptionPane.showMessageDialog(null, "Login Successful");
						
                        GameUI game = new GameUI(name);
						game.setVisible(true);
						entrysound();
					    game.setSize(910, 700);
					    game.setLocationRelativeTo(null);
						dispose();
					}
					
					
				}catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
				
			
			}
		});
		btnNewButton.setBackground(new Color(175, 238, 238));
		btnNewButton.setFont(new Font("Segoe UI Black", Font.BOLD, 26));
		btnNewButton.setBounds(348, 407, 136, 42);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("PASSWORD");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(344, 267, 201, 48);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.BOLD, 26));
		
		JLabel lblNewLabel = new JLabel("USERNAME");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(344, 105, 215, 66);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 27));
		
		JLabel lblNewLabel_3 = new JLabel("Don't have an account?");
		lblNewLabel_3.setForeground(new Color(230, 230, 250));
		lblNewLabel_3.setFont(new Font("Segoe UI Black", Font.PLAIN, 25));
		lblNewLabel_3.setBounds(450, 517, 294, 58);
		panel.add(lblNewLabel_3);
		
		txtusername = new JTextField();
		txtusername.setFont(new Font("Segoe UI", Font.PLAIN, 23));
		txtusername.setBounds(344, 181, 382, 42);
		panel.add(txtusername);
		txtusername.setToolTipText("Username");
		txtusername.setBackground(new Color(230, 230, 250));
		txtusername.setColumns(10);
		
		txtpassword = new JPasswordField();
		txtpassword.setBounds(344, 325, 382, 42);
		panel.add(txtpassword);
		txtpassword.setToolTipText("Password");
		txtpassword.setBackground(new Color(230, 230, 250));
		txtpassword.setFont(new Font("Segoe UI", Font.PLAIN, 23));
		
		JButton btnNewButton_1 = new JButton("Sign up");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signupui sign =new signupui();
				sign.setVisible(true);
				btnsound();
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		btnNewButton_1.setBounds(776, 534, 113, 28);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("BACK");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu m=new menu();
				m.setVisible(true);
				exitsound();
				dispose();
			}
		});
		btnNewButton_2.setForeground(Color.GRAY);
		btnNewButton_2.setBackground(new Color(250, 235, 215));
		btnNewButton_2.setFont(new Font("Segoe UI Black", Font.PLAIN, 25));
		btnNewButton_2.setBounds(50, 517, 118, 34);
		panel.add(btnNewButton_2);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(loginui.class.getResource("/icons/tomato3.png")));
		lblNewLabel_1.setBounds(-496, 181, 1753, 1275);
		panel.add(lblNewLabel_1);
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
public static void  entrysound() {
	
	try {
		File  soundfile =new File("src\\sound\\entrysound.wav");
		AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(soundfile);
		Clip clip =AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}catch (Exception e) {
		// TODO: handle exception
	}
	
}

public static void exitsound() {
	
	try {
		File  soundfile =new File("src\\sound\\exittoner.wav");
		AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(soundfile);
		Clip clip =AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}catch (Exception e) {
		// TODO: handle exception
	}
	
}
}