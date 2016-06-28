package model;

public class Tax extends Tile{
	
	// Income tax or luxury tax
	public boolean incomeTax;
	
	public Tax(boolean income){
		name = "Tax";
		incomeTax = income;
	}
	
}
