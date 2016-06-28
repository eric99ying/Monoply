package model;

public class Railroad extends Tile{

	//Cost Constant
	public final int COST = 200;
	
	//Name of railroad and owner
	public String railroadName;
	public Player owner;
	
	public Railroad(String rname){
		name = "Railroad";
		railroadName = rname;
	}
	
}
