package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.*;
import model.*;

// Handles all GUI related elements
public class MonoplyView {
	// CONSTANT DIMENSIONS
	public final int PANEL_LENGTH = 900;
	public final int PLAYER_INFO_WIDTH = 350;
	public final int PLAYER_INFO_LENGTH = 300;
	public final int POSITION_INFO_WIDTH = 400;
	public final int POSITION_INFO_HEIGHT = 350;
	public final int TURN_INFO_WIDTH = 200;
	public final int TURN_INFO_HEIGHT = 100;

	// Initializes elements
	public JFrame window;
	public GamePanel gamePanel;
	public MonoplyController controller;
	Dimension dim;

	public JLabel playerOneInfo;
	public JLabel playerTwoInfo;
	public JLabel playerThreeInfo;
	public JLabel playerFourInfo;
	public JLabel currentTurn;

	public String playerOneName;
	public String playerTwoName;
	public String playerThreeName;
	public String playerFourName;

	public JButton rollDice;
	public JButton endTurn;
	public JButton buyProperty;
	public JButton auctionProperty;
	public JButton buyHouse;
	public JButton sellHouse;

	public JOptionPane pane = new JOptionPane();
	public JLabel currentPositionInfo;

	public MonoplyView(MonoplyController controller) {
		// Sets the controller
		this.controller = controller;

		// Create JFrame
		window = new JFrame("Monopoly");

		dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize(1600, dim.height);
		// Centers the GUI
		// window.setLocation(dim.width/2-window.getWidth()/2,
		// dim.height/2-window.getSize().height/2);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.setResizable(false);

		window.setLayout(null);

		// Create the main Game Panel
		gamePanel = new GamePanel();
		gamePanel.setBounds(window.getWidth() / 2 - PANEL_LENGTH / 2, window.getHeight() / 2 - PANEL_LENGTH / 2 - 50,
				PANEL_LENGTH, PANEL_LENGTH);

		// Create JLabel with Title
		JLabel title = new JLabel("Monopoly", SwingConstants.CENTER);
		title.setBounds(window.getWidth() / 2 - 150, 0, 300, 100);
		title.setFont(new Font("Arial", Font.BOLD, 32));

		// Create player labels
		playerOneInfo = new JLabel("", SwingConstants.CENTER);
		playerOneInfo.setBounds(10, 200, PLAYER_INFO_WIDTH, PLAYER_INFO_LENGTH);

		playerTwoInfo = new JLabel("", SwingConstants.CENTER);
		playerTwoInfo.setBounds(10, 500, PLAYER_INFO_WIDTH, PLAYER_INFO_LENGTH);

		playerThreeInfo = new JLabel("", SwingConstants.CENTER);
		playerThreeInfo.setBounds(window.getWidth() - PLAYER_INFO_WIDTH + 10, 200, PLAYER_INFO_WIDTH,
				PLAYER_INFO_LENGTH);

		playerFourInfo = new JLabel("", SwingConstants.CENTER);
		playerFourInfo.setBounds(window.getWidth() - PLAYER_INFO_WIDTH + 10, 500, PLAYER_INFO_WIDTH,
				PLAYER_INFO_LENGTH);

		// Create current position info pane
		currentPositionInfo = new JLabel("", SwingConstants.CENTER);
		currentPositionInfo.setBounds(0, window.getHeight() - 350, POSITION_INFO_WIDTH, POSITION_INFO_HEIGHT);

		// Create current turn label
		currentTurn = new JLabel("", SwingConstants.CENTER);
		currentTurn.setBounds(0, 0, TURN_INFO_WIDTH, TURN_INFO_HEIGHT);

		// Adds elements to the GUI
		window.add(gamePanel);
		window.add(title);
		window.add(playerOneInfo);
		window.add(playerTwoInfo);
		window.add(playerThreeInfo);
		window.add(playerFourInfo);
		window.add(currentPositionInfo);
		window.add(currentTurn);

		// Set window to visible
		window.setVisible(true);

		// Get the names of the players
	}

	// Updates the board
	public void updateGraphics() {
		gamePanel.repaint();
	}

	// Asks for usernames
	public void askForUsernames() {
		playerOneName = pane.showInputDialog(gamePanel, "Player 1 Username: ", "");
		playerTwoName = pane.showInputDialog(gamePanel, "Player 2 Username: ", "");
		playerThreeName = pane.showInputDialog(gamePanel, "Player 3 Username: ", "");
		playerFourName = pane.showInputDialog(gamePanel, "Player 4 Username: ", "");
	}

	// Updates player information
	public void updatePlayerInfo() {
		playerOneInfo.setText("<html>" + playerOneName + ": $" + controller.model.playerOne.money + "<br>Position: "
				+ controller.players[0].position + "</html>");
		playerTwoInfo.setText("<html>" + playerTwoName + ": $" + controller.model.playerTwo.money + "<br>Position: "
				+ controller.players[1].position + "</html>");
		playerThreeInfo.setText("<html>" + playerThreeName + ": $" + controller.model.playerThree.money
				+ "<br>Position: " + controller.players[2].position + "</html>");
		playerFourInfo.setText("<html>" + playerFourName + ": $" + controller.model.playerFour.money + "<br>Position: "
				+ controller.players[3].position + "</html>");

	}

	public void updateCurrentTurnLabel() {
		currentTurn.setText("Current Turn: " + controller.players[controller.turn].name);
	}

	// Updates position information based on which tile the current position is
	// on
	public void updatePositionInfo() {
		int position = controller.players[controller.turn].position;
		System.out.println(position);
		String text = "<html>Position: " + position + " | ";
		Tile currentTile = controller.board.boardTiles[position];
		System.out.println(currentTile.name);

		// Different tile cases
		if (currentTile.name.equals("Property")) {
			Property currentProperty = (Property) currentTile;
			text += currentProperty.propertyName + "<br>";
			if (currentProperty.owner != null) {
				text += "Owner: " + currentProperty.owner.name + "<br>";
			} else {
				text += "Owner: " + currentProperty.owner + "<br>";
			}
			text += "Property Price: $" + currentProperty.price + "<br>";
			text += "Rent: $" + currentProperty.rent + "<br>";
			text += "Number of Houses: " + currentProperty.numHouses + "<br>";
			text += "House Cost: $" + currentProperty.housePrice + "</html>";
		} else if (currentTile.name.equals("CommunityChest")) {
			CommunityChest currentChest = (CommunityChest) currentTile;
			text += "Community Chest</html>";
		} else if (currentTile.name.equals("Tax")) {
			Tax currentTax = (Tax) currentTile;
			if (currentTax.incomeTax) {
				text += "Income Tax</html>";
			} else {
				text += "Luxury Tax</html>";
			}
		} else if (currentTile.name.equals("Chance")) {
			Chance currentChest = (Chance) currentTile;
			text += "Chance</html>";
		} else if (currentTile.name.equals("Railroad")) {
			Railroad currentRailroad = (Railroad) currentTile;
			text += currentRailroad.railroadName + "<br>";
			if (currentRailroad.owner != null) {
				text += "Owner: " + currentRailroad.owner.name + "<br>";
			} else {
				text += "Owner: " + currentRailroad.owner + "<br>";
			}
			text += "Cost: $" + currentRailroad.COST + "</html>";
		} else if (currentTile.name.equals("Utility")) {
			Utility currentUtility = (Utility) currentTile;
			text += currentUtility.utilityName + "<br>";
			if (currentUtility.owner != null) {
				text += "Owner: " + currentUtility.owner.name + "<br>";
			} else {
				text += "Owner: " + currentUtility.owner + "<br>";
			}
			text += "Cost: $" + currentUtility.COST + "</html>";
		} else if (currentTile.name.equals("Jail")) {
			Jail currentJail = (Jail) currentTile;
			text += currentJail.name + "</html>";
		} else if (currentTile.name.equals("Parking")) {
			Parking currentParking = (Parking) currentTile;
			text += "Free Parking</html>";
		}

		currentPositionInfo.setText(text);
	}

	// Adds the roll dice button, allowing player to move
	public void displayRollDice() {
		rollDice = new JButton("Roll dices");
		rollDice.setBounds(window.getWidth() / 2 - 100, dim.height - 150, 200, 100);
		window.add(rollDice);

		// Adds action listeners
		rollDice.addActionListener(controller);
		rollDice.setVisible(true);
	}

	// Displays end turn buttons, allowing players to end their turn
	public void displayEndTurn() {
		endTurn = new JButton("End Turn");
		endTurn.setBounds(window.getWidth() / 2 - 100, dim.height - 150, 200, 100);
		window.add(endTurn);

		endTurn.addActionListener(controller);
		endTurn.setVisible(true);

	}

	// Adds the buy property button
	public void displayBuyAndAuctionProperty() {
		// Create buy property button
		buyProperty = new JButton("Buy property");
		buyProperty.setBounds(window.getWidth() / 2 - 200, dim.height - 150, 200, 100);
		window.add(buyProperty);

		// Create auction property button
		auctionProperty = new JButton("Auction property");
		auctionProperty.setBounds(window.getWidth() / 2, dim.height - 150, 200, 100);
		window.add(auctionProperty);

		// Adds action Listeners
		buyProperty.addActionListener(controller);
		auctionProperty.addActionListener(controller);
		buyProperty.setVisible(true);
		auctionProperty.setVisible(true);
	}

	// Adds buyhouse and sellhouse buttons
	public void displayBuyAndSellHouse() {
		// Create buy property button
		buyHouse = new JButton("Buy House");
		buyHouse.setBounds(window.getWidth() - 240, dim.height - 240, 200, 100);
		window.add(buyHouse);

		// Create auction property button
		sellHouse = new JButton("Sell House");
		sellHouse.setBounds(window.getWidth() - 240, dim.height - 120, 200, 100);
		window.add(sellHouse);

		// Adds action Listeners
		buyHouse.addActionListener(controller);
		sellHouse.addActionListener(controller);
		buyHouse.setVisible(true);
		sellHouse.setVisible(true);
	}

	// Gets rid of roll dice
	public void removeRollDice() {
		if (rollDice != null) {
			rollDice.setVisible(false);
			window.remove(rollDice);
		}
	}

	// Gets rid of buy and remove property buttons
	public void removeBuyAuctionProperty() {
		if (buyProperty != null) {
			buyProperty.setVisible(false);
			window.remove(buyProperty);
		}
		if (auctionProperty != null) {
			auctionProperty.setVisible(false);
			window.remove(auctionProperty);
		}

	}

	// Gets rid of end Turn button
	public void removeEndTurn() {
		if (endTurn != null) {
			endTurn.setVisible(false);
			window.remove(endTurn);

		}
	}
	
	//Gets rid of buy and sell house buttons
	public void removeBuyAndSellHouse() {
		if (buyHouse != null) {
			buyHouse.setVisible(false);
			window.remove(buyHouse);
		}
		if (sellHouse != null) {
			sellHouse.setVisible(false);
			window.remove(sellHouse);
		}

	}

	/*
	 * //Adds action listeners to the rollDice button public void
	 * addDiceActionListener(ActionListener listener){
	 * rollDice.addActionListener(controller);
	 * 
	 * }
	 * 
	 * //Adds action listeners for the buyProperty and auctionProperty buttons
	 * public void addBuyAuctionActionListener(ActionListener listener){
	 * buyProperty.addActionListener(controller);
	 * auctionProperty.addActionListener(controller); }
	 */

	// Adds mouse listeners
	public void addMouseListenerPlayerInfo(MouseListener listener) {
		playerOneInfo.addMouseListener(controller);
		playerTwoInfo.addMouseListener(controller);
		playerThreeInfo.addMouseListener(controller);
		playerFourInfo.addMouseListener(controller);
	}

	public void addKeyListener(KeyListener listener) {

	}

	// GamePanel of the GUI
	class GamePanel extends JPanel {

		// Asks controller for the image at a given location and then draws the
		// image
		public void paintComponent(Graphics g) {
			g.setColor(Color.black);

			g.fillRect(0, 0, this.getWidth(), this.getHeight());

			Image boardImage;
			try {
				boardImage = ImageIO.read(new File("images/MonopolyBoard.jpg"));
				g.drawImage(boardImage, 0, 0, PANEL_LENGTH, PANEL_LENGTH, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// Message Displays called from Controller

	public void displayNormalMoveMessage(Player player, int moves) {
		pane.showMessageDialog(window, player.name + " moved " + moves + " steps forward.");
	}

	public void displayDoubleMoveMessageNoJail(Player player, int moves) {
		pane.showMessageDialog(window, player.name + " rolled a double and moved " + moves + " steps forward. "
				+ player.name + " will have an extra turn after this turn is over.");
	}

	public void displayDoubleMoveMessageJail(Player player) {
		pane.showMessageDialog(window,
				player.name + " rolled three doubles in a row. " + player.name + " was arrested and put in Jail.");
	}

	public void displayWentToJailMessage(Player player) {
		pane.showMessageDialog(window, player.name + " landed on a Go To Jail tile and promptly went to Jail.");
	}

	public void displayNoMoneyMessage(Player player) {
		pane.showMessageDialog(window, player.name + " does not have enough money!");
	}

	public int displayPlayerOptions(String[] players, int highestBid, Player bidder) {
		return pane.showOptionDialog(window,
				"<html>Current Highest Bid: " + bidder.name + " $" + highestBid + " <br>Next Bidder:</html>", "Auction",
				pane.YES_NO_CANCEL_OPTION, pane.QUESTION_MESSAGE, null, players, players[0]);
	}

	public int displayMoneyAmount() {
		String input = pane.showInputDialog(window, "Amount of money:", "(Must be higher than current bid)");
		while (true) {
			try {
				int num = Integer.parseInt(input);
				return num;
			} catch (Exception f) {
				input = pane.showInputDialog(window, "Amount of money:", "Auction");
			}
		}
	}

	public void displayNotEnoughMoneyMessage(Player player) {
		pane.showMessageDialog(window, player.name + " needs to bid more than the current highest bid.");
	}

	public void displayPlayerOwnedProperties(Player player) {
		String text = "<html>" + player.name + " Owned Properties:";
		for (int i = 0; i < player.ownedProperties.size(); i++) {
			text += player.ownedProperties.get(i).propertyName + "<br>";
		}
		text += "</html>";
		pane.showMessageDialog(window, text);
	}

	public void displayRentMessage(Player player, int moneyPaid) {
		String text = "<html>" + player.name + " paid $" + moneyPaid + " as rent.";
		text += "</html>";
		pane.showMessageDialog(window, text);
	}
}
