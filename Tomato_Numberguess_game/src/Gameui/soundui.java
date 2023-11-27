package Gameui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jaco.mp3.player.MP3Player;

import java.io.File;

public class soundui extends JFrame {

    public static final String song = "src\\sound\\bgmusic.mp3";
    private MP3Player mp3Player;
    private boolean isMusicPlaying = false;
    private Thread musicThread;

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    soundui frame = new soundui();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public soundui() {
       
        setResizable(false);
        setTitle("Sound");
        setIconImage(Toolkit.getDefaultToolkit().getImage(soundui.class.getResource("/icons/volume.png")));
        setBounds(100, 100, 539, 304);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.setForeground(new Color(245, 222, 179));
        panel.setBounds(-13, 0, 544, 276);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Sound");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Segoe UI Black", Font.ITALIC, 23));
        lblNewLabel.setBounds(46, 83, 101, 60);
        panel.add(lblNewLabel);

        JButton btnsoundon = new JButton("On");
        btnsoundon.setForeground(new Color(250, 235, 215));
        btnsoundon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isMusicPlaying) {
                    JOptionPane.showMessageDialog(null, "Sound ON");
                    startMusic();
                    isMusicPlaying = true;
                }
            }
        });
        btnsoundon.setBackground(new Color(218, 165, 32));
        btnsoundon.setFont(new Font("Segoe UI Black", Font.BOLD, 23));
        btnsoundon.setBounds(187, 74, 212, 41);
        panel.add(btnsoundon);

        JButton btnsoundoff = new JButton("Off");
        btnsoundoff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isMusicPlaying) {
                    JOptionPane.showMessageDialog(null, "Sound OFF");
                    stopMusic();
                    isMusicPlaying = false;
                }
            }
        });
        btnsoundoff.setBackground(new Color(255, 99, 71));
        btnsoundoff.setFont(new Font("Segoe UI Black", Font.BOLD, 23));
        btnsoundoff.setBounds(187, 125, 212, 37);
        panel.add(btnsoundoff);

        // Handle window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Do not stop the music when closing the window
                dispose();
            }
        });
    }

    private void startMusic() {
        mp3Player = new MP3Player(new File(song));
        musicThread = new Thread(() -> {
            mp3Player.play();
        });
        musicThread.start();
    }

    private void stopMusic() {
        if (mp3Player != null) {
            mp3Player.stop();
            try {
                musicThread.join(); // Wait for the music thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
