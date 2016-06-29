package model;

public class Board {
	//Color CONSTANTS
	public static final int PURPLE = 1;
	public static final int LIGHTBLUE = 2;
	public static final int MAGENTA = 3;
	public static final int ORANGE = 4;
	public static final int RED = 5;
	public static final int YELLOW = 6;
	public static final int GREEN = 7;
	public static final int BLUE = 8;

	//Board consists of an array of Tiles, with GO being the very first tile
	public Tile[] boardTiles = {new Go(),
								new Property("Mediterranean Avenue",PURPLE,60,2,50,10,30,90,160,250),
								new CommunityChest(),
								new Property("Baltic Avenue",PURPLE,60,4,50,20,60,180,320,450),
								new Tax(true),
								new Railroad("Reading Railroad"),
								new Property("Oriental Avenue",LIGHTBLUE,100,6,50,30,90,270,400,550),
								new Chance(),
								new Property("Vermont Avenue",LIGHTBLUE,100,6,50,30,90,270,400,550),							
								new Property("Connecticut Avenue",LIGHTBLUE,120,8,50,40,100,300,450,600),		
								new Jail(),
								new Property("St. Charles Place",MAGENTA,140,10,100,50,150,450,625,750),
								new Utility("Electric Company"),
								new Property("States Avenue",MAGENTA,140,10,100,50,150,450,625,750),
								new Property("Virginia Avenue",MAGENTA,160,12,100,60,180,500,700,900),
								new Railroad("Pennsylvania Railroad"),
								new Property("St. James Place",ORANGE,180,14,100,70,200,550,750,950),
								new CommunityChest(),
								new Property("Tennessee Avenue",ORANGE,180,14,100,70,200,550,750,950),
								new Property("New York Avenue",ORANGE,200,16,100,80,220,600,800,1000),
								new Parking(),
								new Property("Kentucky Avenue",RED,220,18,150,90,250,700,875,1050),
								new Chance(),
								new Property("Indiana Avenue",RED,220,18,150,90,250,700,875,1050),
								new Property("Illinois Avenue",RED,240,20,150,100,300,750,925,1100),
								new Railroad("B&O Railroad"),
								new Property("Atlantic Avenue",YELLOW,260,22,150,110,330,800,975,1150),
								new Property("Ventnor Avenue",YELLOW,260,22,150,110,330,800,975,1150),
								new Utility("Water Works"),
								new Property("Marvin Garden",YELLOW,280,24,150,120,360,850,1025,1200),
								new GoToJail(),
								new Property("Pacific Avenue",GREEN,300,26,200,130,390,900,1100,1275),
								new Property("North Carolina Avenue",GREEN,300,26,200,130,390,900,1100,1275),
								new CommunityChest(),
								new Property("Pennsylvania Avenue",GREEN,320,28,200,150,450,1000,1200,1400),
								new Railroad("Short Line"),
								new Chance(),
								new Property("Park Place",BLUE,350,35,200,175,500,1100,1300,1500),
								new Tax(false),
								new Property("Boardwalk",BLUE,400,50,200,200,600,1400,1700,2000)};
	
	public Board(){
		
	}
}
