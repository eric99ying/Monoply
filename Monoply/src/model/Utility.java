package model;

public class Utility extends Tile {
	
	//Cost Constant
	public final int COST = 150;

	// Owner of the utility tile
	public Player owner;
	
	// Name of utility
	public String utilityName;
	
	public Utility(String uname) {
		name = "Utility";
		utilityName = uname;
	}

}
