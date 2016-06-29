package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.*;
import view.MonoplyView;

// Handles controlling the model elements and changing the view
public class MonoplyController implements MouseListener, ActionListener{

	// Initialize monoplyview in order to access the GUI
	public MonoplyView view;
	public MonoplyModel model;
	
	// Initialize elements
	public Board board;
	public Player[] players;
	
	//Tracks the current player turn (starting from 0)
	public int turn = 0;
	
	//Keeps track if the player has an extra turn from doubles
	public boolean extraTurn = false;
	
	//Keeps track of how many doubles rolled in a roll
	public int numDoublesinRow = 0;
	
	public MonoplyController() {
		this.model = new MonoplyModel();
		this.view = new MonoplyView(this);
		
		board = model.board;
		
		//Testing
		/*for(int i=0;i<board.boardTiles.length;i++){
			System.out.println(board.boardTiles[i].name);
		}*/
		
		view.askForUsernames();
		
		model.createPlayers(view.playerOneName,view.playerTwoName,view.playerThreeName,view.playerFourName);
		players = new Player[4];
		players[0] = model.playerOne;
		players[1] = model.playerTwo;
		players[2] = model.playerThree;
		players[3] = model.playerFour;
		
		view.updatePlayerInfo();
		
		view.updateCurrentTurnLabel();
		
		view.displayRollDice();
		
		//Activate all Listeners
		//view.addDiceActionListener(this);
		view.addMouseListenerPlayerInfo(this);
		
	}

	//Mouse Events
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==view.playerOneInfo){
			System.out.println("Player 1 Pressed");
			view.displayPlayerOwnedProperties(players[0]);
		}else if(e.getSource()==view.playerTwoInfo){
			System.out.println("Player 2 Pressed");
			view.displayPlayerOwnedProperties(players[1]);
		}else if(e.getSource()==view.playerThreeInfo){
			System.out.println("Player 3 Pressed");
			view.displayPlayerOwnedProperties(players[2]);
		}else if(e.getSource()==view.playerFourInfo){
			System.out.println("Player 4 Pressed");
			view.displayPlayerOwnedProperties(players[3]);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//Button Events
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Button Pressed! ");
		if(e.getSource()==view.rollDice){
			rollDiceAndMove();
		}else if(e.getSource()==view.buyProperty){
			buyProperty();
		}else if(e.getSource()==view.auctionProperty){
			auctionProperty();
		}else if(e.getSource()==view.endTurn){
			nextTurn();
		}
		
		//Testing purposes
		System.out.println(players[turn].name + " is at position " + players[turn].position);
		
	}
	
	//Move position and update current position
	public void rollDiceAndMove(){
		Random random = new Random();
		int dice1 = random.nextInt(6) + 1;
		int dice2 = random.nextInt(6) + 1;
		
		players[turn].move(dice1+dice2);
		
		//If double occurs
		if(dice1==dice2){
			if(numDoublesinRow == 3){
				players[turn].position = 10; // Go to jail
				view.displayDoubleMoveMessageJail(players[turn]);
			}else{
				extraTurn = true;
				numDoublesinRow++;
				view.displayDoubleMoveMessageNoJail(players[turn],dice1+dice2);
			}
		}else{
			view.displayNormalMoveMessage(players[turn],dice1+dice2);
			numDoublesinRow = 0;
		}
		if(players[turn].position == 30){
			view.displayWentToJailMessage(players[turn]);
			players[turn].position = 10;
		}
		view.updatePositionInfo();
		view.updatePlayerInfo();
		view.removeRollDice();
		afterMovePhase();
	}
	
	//Buys the current property
	//PRECONDITION: position of players[turn] must be on a property tile
	public void buyProperty(){
		Player player = players[turn];
		Property property = (Property)board.boardTiles[player.position];
		
		//Makes sure the player has enough money
		if(player.money>=property.price){
			player.money-=property.price;
			property.owner = player;
			player.ownedProperties.add(property);
		}else{
			view.displayNoMoneyMessage(player);
		}
		
		view.updatePositionInfo();
		view.updatePlayerInfo();
		
		//Removes buy auction buttons and adds end turn button
		view.removeBuyAuctionProperty();
		view.displayEndTurn();
	}
	
	//Auctions off the current property
	//PRECONDITION: position of players[turn] must be on a property tile
	public void auctionProperty(){
		//Declare the highest bid and the bidder variables
		int highestBid = 0;
		Player currentLeader = players[turn];
		
		String[] playerNames = new String[5];
		for(int i=0;i<4;i++){
			playerNames[i] = players[i].name;
		}
		playerNames[4] = "End Auction";
		
		int choice = 0;
		
		//Keeps asking for bid until the players agree to end the auction 
		do{
			choice = view.displayPlayerOptions(playerNames,highestBid,currentLeader);
			if(choice!=4){
				int money = view.displayMoneyAmount();
				if(money>highestBid){
					highestBid = money;
					currentLeader = players[choice];
				}else{
					view.displayNotEnoughMoneyMessage(players[choice]);
				}
			}
		}while(choice!=4);
			
		//Gives the property to highest bidder
		currentLeader.money-=highestBid;
		Property property = (Property)board.boardTiles[players[turn].position];
		property.owner = currentLeader;
		currentLeader.ownedProperties.add(property);
		view.updatePositionInfo();
		view.updatePlayerInfo();
		
		//Removes buy and auction buttons and adds end turn button
		view.removeBuyAuctionProperty();
		view.displayEndTurn();
	}
	
	//Calculates the rent paid on the current property tile
	//PRECONDITION: position of players[turn] must be on a property tile
	public void payPropertyRent(){
		Player player = players[turn];
		Property currentProperty = (Property)board.boardTiles[player.position];
		
		int rentPaid = currentProperty.rent;
		
		if(allColorsBought(player, currentProperty.color)){
			rentPaid*=2;
		}
		
		if(player.money>=rentPaid){
			player.money-=rentPaid;
			currentProperty.owner.money+=rentPaid;
			view.displayRentMessage(player, rentPaid);
		}else{
			view.displayNoMoneyMessage(player);
		}
		
		
	}
	
	//Intermediate methods that happens right after the player moves such as paying rent, displaying buttons for buying,
	//auctioning, mortgaging, buying houses, etc...
	public void afterMovePhase(){
		Player player = players[turn];
		Tile currentPosition = board.boardTiles[player.position];
		
		//If the current position is property: if the property is already owned, pay rent, else present the option to buy or auction
		if(currentPosition.name.equals("Property")){
			Property currentProperty = (Property)currentPosition;
			if(currentProperty.owner!=null){
				payPropertyRent();
			}else{
				view.displayBuyAndAuctionProperty();
				//view.addBuyAuctionActionListener(this);
			}
		}else{
			view.displayEndTurn();
		}
	}
	
	//Proceeds to next turn, if the current player does not have an extra turn
	public void nextTurn(){
		if(extraTurn){
			extraTurn = false;
		}else{
			turn++;
			if(turn>=4){
				turn = 0;
			}
		}
		view.removeEndTurn();
		view.displayRollDice();
		view.updateCurrentTurnLabel();
		//view.addDiceActionListener(this);
	}
	
	//Returns a boolean indicating if a player has all properties on a given color so he or she can
	//buy a house or double the rent
	public boolean allColorsBought(Player player, int color){
		for(int i=0;i<board.boardTiles.length;i++){
			if(board.boardTiles[i].name.equals("Property")){
				Property currentProperty = (Property)board.boardTiles[i];
				if(currentProperty.color==color && currentProperty.owner!=player){
					return false;
				}
			}
		}
		return true;
	}
	
	//Returns all properties with the specified color in an array
	public Property[] sameColorProperties(int color){
		Property[] output;
		if(color == Board.PURPLE || color == Board.BLUE){
			output = new Property[2];
		}else{
			output = new Property[3];
		}
		int counter = 0;
		for(int i=0;i<board.boardTiles.length;i++){
			if(board.boardTiles[i].name.equals("Property")){
				Property currentProperty = (Property)board.boardTiles[i];
				if(currentProperty.color==color){
					output[counter] = currentProperty;
					counter++;
				}
			}
		}
		return output;
	}
	
}
