package Gameui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import database.Database;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreboardUI extends JFrame {

    private JTable scoreTable;
    private DefaultTableModel tableModel;

    public ScoreboardUI(String playerName, int score) {
    	setBackground(new Color(25, 25, 112));
        setTitle("Scoreboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 620);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ScoreboardUI.class.getResource("/icons/icons8-scoreboard-64.png")));
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(47, 79, 79)); // Set background color
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.black);
        mainPanel.setPreferredSize(new Dimension(500, 300));

        // Create a JLabel at the top
        JLabel scoreboardLabel = new JLabel("Scoreboard");
        scoreboardLabel.setBackground(Color.BLACK);
        scoreboardLabel.setForeground(Color.GREEN);
        scoreboardLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreboardLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 34));
        scoreboardLabel.setPreferredSize(new Dimension(200, 60));
        mainPanel.add(scoreboardLabel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(scoreTable), BorderLayout.CENTER);
        setContentPane(mainPanel);

        tableModel = new DefaultTableModel();
        scoreTable = new JTable(tableModel);
        scoreTable.setForeground(new Color(230, 230, 250));
        scoreTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 25));
        scoreTable.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        scoreTable.setRowHeight(40); // Set row height
        scoreTable.setBackground(new Color(0, 0, 0)); // Set table background color

        // Add columns to the table
        tableModel.addColumn("Rank");
        tableModel.addColumn("Name");
        tableModel.addColumn("Score");
        
        
       
      
        
        
     // Set custom cell renderer for font and background color
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
        	 @Override
        	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        	        Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        	        // Set colors for specific columns
        	        if (column == 0) { // Rank column
        	            renderer.setForeground(new Color(255, 255, 255)); // White text
        	            renderer.setBackground(Color.DARK_GRAY);  
        	        } else if (column == 1) { // Name column
        	            renderer.setForeground(new Color(218, 165, 32)); // Red text
        	            renderer.setBackground(Color.DARK_GRAY);  
        	        } else if (column == 2) { // Score column
        	            renderer.setForeground(new Color(255, 99, 71)); // Green text
        	            renderer.setBackground(Color.DARK_GRAY);  
        	        }

        	        setHorizontalAlignment(SwingConstants.CENTER); // Center-align the text
        	        return renderer;
        	    }
        };
        
        
         
        
        
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        scoreTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Rank
        scoreTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Name
        scoreTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Score

        // Fetch and display scores from the database
        fetchScores();

        JScrollPane scrollPane = new JScrollPane(scoreTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JLabel lblNewLabel = new JLabel("Scoreboard ");
        lblNewLabel.setBackground(new Color(135, 206, 235));
        lblNewLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 23));
        scrollPane.setColumnHeaderView(lblNewLabel);

        // Create buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0, 0, 0));
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setForeground(new Color(0, 0, 139));
        playAgainButton.setBackground(new Color(95, 158, 160));
        playAgainButton.setHorizontalAlignment(SwingConstants.LEFT);
        playAgainButton.setFont(new Font("Segoe UI Black", Font.BOLD, 23));
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(95, 158, 160));
        exitButton.setForeground(new Color(160, 82, 45));
        exitButton.setFont(new Font("Segoe UI Black", Font.BOLD, 23));

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current ScoreboardUI window
                // Create an instance of GameUI with a default player name
                GameUI game = new GameUI(playerName);
                game.setSize(910, 700);
			    game.setLocationRelativeTo(null);
                game.setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitui exitui =new exitui();
                exitui.setVisible(true);
                // Exit the application
            }
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
    }

    private void fetchScores() {
        // Clear the table before fetching new scores
        clearTable();

        Connection conn = Database.conn();
        if (conn != null) {
            try {
                String query = "SELECT name, score FROM users ORDER BY score DESC";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                int rank = 1;
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int score = resultSet.getInt("score");

                    // Add a row to the table
                    tableModel.addRow(new Object[]{rank, name, score});
                    rank++;
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

    private void clearTable() {
        // Clear the existing rows from the table
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ScoreboardUI scoreboardUI = new ScoreboardUI("DefaultPlayer", 0);
                scoreboardUI.setVisible(true);
            }
        });
    }
}
