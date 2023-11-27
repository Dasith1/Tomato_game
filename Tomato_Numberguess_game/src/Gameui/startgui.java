package Gameui;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.stream.Stream;

public class startgui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startgui frame = new startgui();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public startgui() {
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(startgui.class.getResource("/icons/slack.png")));
		setTitle("Number guess");
		
		setResizable(false);
		
		setForeground(new Color(175, 231, 175));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 942, 602);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 947, 586);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnquit = new JButton("QUIT");
		
		btnquit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitgamesound();
				dispose();
				
				
			}
		});
		btnquit.setBackground(new Color(255, 128, 64));
		btnquit.setFont(new Font("Tahoma", Font.BOLD, 28));
		btnquit.setBounds(324, 253, 283, 52);
		panel.add(btnquit);
		
		JButton btnplaygame = new JButton("Play Game");
		btnplaygame.addMouseListener(new MouseAdapter() {
			 
		});
		
		
		
		btnplaygame.setForeground(new Color(230, 230, 250));
		btnplaygame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu m =new menu();
				playgamesound();
				m.setVisible(true);
				dispose();
				
			}
		});
		btnplaygame.setBounds(324, 109, 283, 52);
		panel.add(btnplaygame);
		btnplaygame.setFont(new Font("Tahoma", Font.BOLD, 28));
		btnplaygame.setBackground(new Color(30, 144, 255));
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(startgui.class.getResource("/icons/tomato3.png")));
		lblNewLabel.setBounds(-605, 247, 1609, 997);
		panel.add(lblNewLabel);
	}
	
	public static void  playgamesound() {
		
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
	
	public static void quitgamesound() {
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
