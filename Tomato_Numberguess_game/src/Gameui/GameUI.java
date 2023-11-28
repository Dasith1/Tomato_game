package Gameui;

import com.perisic.tomato.engine.GameEngine;

import database.Database;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameUI extends JFrame {
	 private String playerName; // Store the current player name
	 private int score;  
    private GameEngine gameEngine;
    private int level;
    private int remainingGames;
    

    private JLabel levelLabel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel gameImageLabel;
    private JButton[] buttons;
    private JButton endGameButton; // New button to end the game
    private JButton pauseButton; // New button to pause the game

    private int secondsRemaining;
    private Timer timer;
    
    private boolean isPaused = false;

    public GameUI(String playerName) {
    	this.playerName = playerName;
         
    	fetchUserScore();
        gameEngine = new GameEngine("Player");
        level = 1;
        remainingGames = 2;
        score = 10;
         setLocationRelativeTo(null);
        setTitle("Game API");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        
        levelLabel = new JLabel("Level: " + level);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        timeLabel = new JLabel("Time : N/A");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        gameImageLabel = new JLabel();

         
 
        JPanel infoPanel = new JPanel(new GridLayout(1, 3));
        infoPanel.setForeground(Color.BLACK);
        infoPanel.add(levelLabel);
        infoPanel.add(scoreLabel);
        infoPanel.add(timeLabel);
        infoPanel.setBackground(Color.DARK_GRAY);

        getContentPane().add(infoPanel, BorderLayout.NORTH);

        getContentPane().add(gameImageLabel, BorderLayout.CENTER);

        buttons = new JButton[10];
        JPanel buttonPanel = new JPanel(new GridLayout(2, 5));
        buttonPanel.setBackground(Color.BLACK);

        for (int i = 0; i < buttons.length; i++) {
            final int buttonValue = i;
            buttons[i] = new JButton(String.valueOf(i));
            buttons[i].setFont(new Font("Arial", Font.BOLD, 30));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBackground(Color.BLUE);
            buttons[i].setPreferredSize(new Dimension(30, 40));
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(buttonValue);
                    playgamesound();
                }
            });
            buttonPanel.add(buttons[i]);
        }

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // New button to end the game
        endGameButton = new JButton("End Game");
        endGameButton.setVerticalAlignment(SwingConstants.BOTTOM);
        endGameButton.setFont(new Font("Arial", Font.PLAIN, 18));
        endGameButton.setForeground(Color.WHITE);
        endGameButton.setBackground(Color.RED);
        endGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endGame();
            }
        });

        JPanel endGamePanel = new JPanel();
        endGamePanel.add(endGameButton);
        endGamePanel.setBackground(Color.BLACK);
        getContentPane().add(endGamePanel, BorderLayout.EAST);

        // New button to pause the game
        pauseButton = new JButton("Pause");
        pauseButton.setVerticalAlignment(SwingConstants.BOTTOM);
        pauseButton.setFont(new Font("Arial", Font.BOLD, 18));
        pauseButton.setForeground(Color.black);
        pauseButton.setBackground(Color.orange); // Choose a color for the pause button
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseGame();
            }
        });

        JPanel pausePanel = new JPanel();
        pausePanel.add(pauseButton);
        pausePanel.setBackground(Color.BLACK);
        getContentPane().add(pausePanel, BorderLayout.WEST);
        
        
        initializeTimer();
        showNextGame();
    }
    private void fetchUserScore() {
        Connection conn = Database.conn();
        if (conn != null) {
            try {
                String query = "SELECT score FROM users WHERE name = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, playerName);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    score = resultSet.getInt("score");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void pauseGame() {
    
    	if (timer.isRunning()) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Paused", "Pause", JOptionPane.INFORMATION_MESSAGE);
            pauseButton.setText("Start");
            pauseButton.setBackground(Color.green);
            isPaused = true;
             
            
        } else {
            timer.start();
            JOptionPane.showMessageDialog(this, "Game Start", "Start", JOptionPane.INFORMATION_MESSAGE);
            pauseButton.setText("Pause");
            pauseButton.setBackground(Color.orange);
            isPaused = false;
        }
    }

    private void initializeTimer() {
        secondsRemaining = 180; // 3 minutes
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        timer.start();
    }

    private void updateTimer() {
        secondsRemaining--;

        if (secondsRemaining >= 0) {
            int minutes = secondsRemaining / 60;
            int seconds = secondsRemaining % 60;
            timeLabel.setText("Time : " + String.format("%02d:%02d", minutes, seconds));
        } else {
            timer.stop();
            timeLabel.setText("Time's up!");
            handleTimeUp();
        }
    }

    private void handleButtonClick(int buttonValue) {
        if (!isPaused) { // Check if the game is not paused
            if (gameEngine.checkSolution(buttonValue)) {
                score += 10;
                scoreLabel.setText("Score: " + score);

                remainingGames--;

                if (remainingGames == 0) {
                    level++;
                    levelLabel.setText("Level: " + level);
                    remainingGames = 2;
                }

                // Reset the timer and start a new time
                secondsRemaining = 180;
                timer.restart();

                showNextGame();
            } else {
                // Handle incorrect answer: reduce score
                score--;
                scoreLabel.setText("Score: " + score);
            }
        }
    }

    private void handleTimeUp() {
        
        JOptionPane.showMessageDialog(this, "Time's up! Your final score is " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void showNextGame() {
        BufferedImage gameImage = gameEngine.nextGame();

        if (gameImage != null) {
            ImageIcon icon = new ImageIcon(gameImage);
            gameImageLabel.setIcon(icon);
         // Assuming the solution is between 0 and 9 (or some other range)
            for (int i = 0; i < 10; i++) {
                // Temporarily set the solution to each possible value and check the result
                if (gameEngine.checkSolution(i)) {
                    System.out.println("\n Solution for the current game: " + i);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error loading game.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     

    private void endGame() {
    	// Save the player's name and score in the database
        savePlayerScore();

        // Create an instance of ScoreboardUI and display it
        ScoreboardUI scoreboardUI = new ScoreboardUI(playerName, score);
        scoreboardUI.setVisible(true);

        dispose(); // Close the current GameUI window
    }
    
    private void savePlayerScore() {
        Connection conn = Database.conn();
        if (conn != null) {
            try {
                String updateQuery = "UPDATE users SET score = ? WHERE name = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
                preparedStatement.setInt(1, score);
                preparedStatement.setString(2, playerName);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
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
	
public void setScore(int score) {
    this.score = score;
    scoreLabel.setText("Score: " + this.score);
}

    public static void main(String[] args) {
    	 String playerName = "Player1";
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameUI gameUI = new GameUI(playerName);
                gameUI.setSize(910, 600);
                gameUI.setVisible(true);
                
            }
        });
    }
}