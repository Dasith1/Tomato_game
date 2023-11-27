package Gameui;

import java.awt.EventQueue; 

 

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	  

	/**
	 * Create the frame.
	 */
	public menu() {
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(menu.class.getResource("/icons/slack.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 928, 728);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("NEW GAME");
		 
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loginui login =new loginui();
				login.setVisible(true);
				btnsound();
				dispose();
			}
		});
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.setBackground(new Color(0, 0, 128));
		btnNewButton.setFont(new Font("Segoe UI Black", Font.BOLD, 23));
		btnNewButton.setBounds(618, 143, 229, 53);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("OPTION");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				soundui s=new soundui();
				btnsound();
				s.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
		btnNewButton_1.setForeground(new Color(218, 165, 32));
		btnNewButton_1.setBackground(new Color(47, 79, 79));
		btnNewButton_1.setBounds(618, 264, 229, 53);
		contentPane.add(btnNewButton_1);
		
		JButton btnquit = new JButton("QUIT");
		btnquit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnquit.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
		btnquit.setBackground(new Color(75, 0, 130));
		btnquit.setForeground(new Color(250, 128, 114));
		btnquit.setBounds(618, 382, 229, 53);
		contentPane.add(btnquit);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		lblNewLabel.setIcon(new ImageIcon(menu.class.getResource("/icons/tomato.png")));
		lblNewLabel.setBounds(-140, 192, 1044, 872);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("TOMATO GAME");
		lblNewLabel_1.setForeground(Color.GRAY);
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.ITALIC, 50));
		lblNewLabel_1.setBounds(103, 43, 505, 183);
		contentPane.add(lblNewLabel_1);
		
	}
	//button press sound
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
