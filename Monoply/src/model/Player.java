package model;
import java.util.*;

public class Player {
	
	//The Player class has an arraylist of owned properties
	public ArrayList<Property> ownedProperties = new ArrayList<Property>();
	public ArrayList<Railroad> ownedRailroads = new ArrayList<Railroad>();
	
	//Number of railroads, utilities, and get out of jail cards
	public int numRailroads;
	public int numUtilities;
	public int numGetOutOfJailCards;
	
	//Name of player and how much money he owns
	public String name;
	public int money;
	
	//Position of player
	public int position;
	
	//Player starts out with 1500 dollars
	public Player(String name){
		this.name = name;
		money = 1500;
		numRailroads = 0;
		numUtilities = 0;
		numGetOutOfJailCards = 0;
		position = 0;
		
	}
	
	//Moves the player num forward
	public void move(int num){
		position+=num;
	}
	
	//toString method
	public String toString(){
		return name;
	}
}
