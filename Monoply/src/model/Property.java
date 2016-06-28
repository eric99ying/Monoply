package model;

public class Property extends Tile{

	// Actual name of the property
	public String propertyName;
	
	// Price, mortgage price, rent, house cost
	public int price;
	public int mortgage;
	public int rent;
	public int housePrice;
	
	public int house1rent;
	public int house2rent;
	public int house3rent;
	public int house4rent;
	public int hotelrent;
	public int baserent;
	
	// Number of houses
	public int numHouses;
	
	// Color of property, name of owner, and mortgaged or not
	public int color;
	public Player owner = null;
	public boolean mortgaged = false;
	
	// Initializes the property, starts off with no owner
	public Property(String pname, int color, int price, int rent, int housePrice, int house1, int house2, int house3, int house4, int hotel){
		name = "Property";
		propertyName = pname;
		numHouses = 0;
		this.color = color;
		this.price = price;
		this.rent = rent;
		this.housePrice = housePrice;
		mortgage = price/2;
		house1rent = house1;
		house2rent = house2;
		house3rent = house3;
		house4rent = house4;
		hotelrent = hotel;
		baserent = rent;
		
	}
	
	// Handles buying of houses and changing the rent accordingly
	public void addHouse(){
		numHouses++;
		switch(numHouses){
			case 1:
				rent = house1rent;
				break;
			case 2:
				rent = house2rent;
				break;
			case 3:
				rent = house3rent;
				break;
			case 4:
				rent = house4rent;
				break;
			case 5:
				rent = hotelrent;
				break;
		}
			
	}
	
	// Handles selling of houses and changing the rent accordingly
	public void sellHouse(){
		numHouses--;
		switch(numHouses){
			case 1:
				rent = house1rent;
				break;
			case 2:
				rent = house2rent;
				break;
			case 3:
				rent = house3rent;
				break;
			case 4:
				rent = house4rent;
				break;
			case 0:
				rent = baserent;
				break;
		}
			
	}
	
}
