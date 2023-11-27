package Gameui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class exitui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					exitui frame = new exitui();
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
	public exitui() {
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(exitui.class.getResource("/icons/switch.png")));
		setTitle("Quit");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 494, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(-13, 0, 538, 326);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Do you want to quit game?");
		lblNewLabel.setForeground(new Color(255, 255, 240));
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 25));
		lblNewLabel.setBounds(75, 64, 391, 73);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Yes");
		btnNewButton.setBackground(new Color(205, 133, 63));
		btnNewButton.setForeground(new Color(178, 34, 34));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitgamesound();
				System.exit(0);
				
				//exit the system
			}
		});
		btnNewButton.setFont(new Font("Segoe UI Black", Font.PLAIN, 25));
		btnNewButton.setBounds(126, 171, 90, 45);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("No");
		btnNewButton_1.setForeground(new Color(233, 150, 122));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				playgamesound();
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
		btnNewButton_1.setBounds(281, 171, 81, 45);
		panel.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("QUIT GAME");
		lblNewLabel_1.setForeground(new Color(255, 99, 71));
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
		lblNewLabel_1.setBounds(160, 20, 306, 45);
		panel.add(lblNewLabel_1);
	
		
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
