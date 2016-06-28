package model;

import controller.MonoplyController;

public class MonoplyModel {

	MonoplyController controller;
	
	//Initialize model elements
	public Board board;
	public Player playerOne;
	public Player playerTwo;
	public Player playerThree;
	public Player playerFour;
	
	public MonoplyModel() {

		//Set model elements
		board = new Board();
	
	}
	
	//Creates the players with names given from controller
	public void createPlayers(String one, String two, String three, String four){
		playerOne = new Player(one);
		playerTwo = new Player(two);
		playerThree = new Player(three);
		playerFour = new Player(four);
		
	}

}
