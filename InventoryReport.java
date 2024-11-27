package osu.cse2123;
/**
 * A program that generates an inventory report

 * 
 * @name Connor Kovacs
 * @version 10202022
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryReport {


	/**
	 * This method will display our inventory report 
	 * 
	 * 
	 * @return void
	 *             
	 */
	public static void displayTable(List<Product>Data) {

		//let user know product summ report
		System.out.println("Product Inventory Summary Report");
		for(int idx = 0;idx<70;idx++) 
			System.out.print("-");//print the line under 70 times
		//this will be the head of our table and all formatting necessary
		System.out.println();
		System.out.printf("%-25s %-10s %-4s %-6s %-6s %-6.6s %-6.6s%n","Product Name","I Code","Type","Quant.","Price","Rating","# Rat.");
		System.out.printf("%-25s %-10s %-4s %-6s %-6s %-6.6s %-6.6s%n","-------------------------","----------","----","------","------","-------","------");
		//for loop for our data size this will 
		for(int idx = 0;idx<Data.size();idx++) {
			String rate = "";
			//if count is bigger than zero execute for loop to add rate
			if(Data.get(idx).getUserRatingCount()>0) {
				for(int i = 1;i < Data.get(idx).getAvgUserRating();i++)
					rate += "*";
			}
			//body of table formatting with adding our products to our classes
			System.out.printf("%-25.25s %-10.10s %-4.4s %6d %6.2f%6.6s %6d %n",Data.get(idx).getName(),Data.get(idx).getInventoryCode(),Data.get(idx).getType(),Data.get(idx).getQuantity(),((double)Data.get(idx).getPrice()/100),rate,Data.get(idx).getUserRatingCount());
		}
		//by obtaining the data size we can pull the the total products in the database
		System.out.println("Total products in database: " + Data.size());
	}

	/**
	 * This method will find read our the input file and set products
	 * 
	 * 
	 * @return product data
	 * @throws FileNotFoundException 
	 *             
	 */
	public static ArrayList<Product>Set(String fname)  {
		//add your strings
		String[] products;
		String rating;
		Product idx;
		//product arraylist created to store our data
		ArrayList<Product>ProductData = new ArrayList<Product>();
		//new scanner with filename
		try {
			Scanner scan = new Scanner(new File(fname));
			//loop to read data
			while(scan.hasNextLine()){
				rating = scan.nextLine();
				//add our products to a delimiter so it doesn't get caught while going into the input file
				products = rating.split(",");
				//set our new product then assign accordingly
				idx = new Product();
				idx.setName(products[0]);
				idx.setInventoryCode(products[1]);
				idx.setType(products[4]);
				idx.setPrice(Integer.parseInt(products[3]));//parse our strings into ints
				idx.setQuantity(Integer.parseInt(products[2]));
				//loop for user ratings
				for(int i = 5;i < products.length;i++) {
					idx.addUserRating(Integer.parseInt(products[5]));
				}
				//adding our data to our new summary
				ProductData.add(idx);
			}


			//close our scanner
			scan.close();

			//returning the result of List
			return ProductData;
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return ProductData;

	}


	/**
	 * This method will find the highest avg
	 * 
	 * @param ratings on list
	 * @return highest avg number
	 *             
	 */
	public static void HighMaxScore(ArrayList<Product>products) {
		//state max 
		int max = 0;
		//string name and star and our rate value that we will pull
		String productname = "";
		String stars = "*****";
		//make our ratevalues a string
		String ratevalues = "";
		Product product = products.get(0);
		//sst our int max to get our avg user method
		max = product.getAvgUserRating();
		//Initialize idx with 1
		int idx = 0;
		//loop over idx with size of product 6
		while(idx < products.size())
		{
			product = products.get(idx);
			if (max <= product.getAvgUserRating())
			{
				max = product.getAvgUserRating();
				productname = product.getName();
			}
			ratevalues = stars.substring(0, max);//set the rate values to the stars string and add first and max
			idx++;
		}
		//Display statement for highest average rating
		System.out.println("Highest Average Ranked Item: " + productname + " (" + ratevalues + ")");
	}
	/**
	 * This method will find the lowest avg
	 * 
	 * @param ratings on list
	 * @return lowest avg number
	 *             
	 */
	public static void LowMaxScore(ArrayList<Product> products){
		//set min val
		int min = 0;
		String productname = "";
		String star = "*****";
		//make our ratevalues a string
		String ratevalue = "";
		Product product = products.get(0);
		//Get the minimum
		min = product.getAvgUserRating();
		int idx = 0;
		//Go over loop
		while(idx < products.size()) {
			product = products.get(idx);
			if (min >= product.getAvgUserRating())
			{
				min = product.getAvgUserRating();
				//we get product name here through our class

				productname = product.getName();
			}
			//set rate value to our stars and create substring with first and min
			ratevalue = star.substring(0, min);
			//increment our idx
			idx++;
		}
		//lowest avg display plus name
		System.out.println("Lowest Average Ranked Item: "+ productname + " (" + ratevalue + ")");
	}

	public static void main(String[] args){
		//User input for file
		System.out.print("Enter an inventory filename: ");
		Scanner Scanner = new Scanner(System.in);
		//scan over each line
		String filename = Scanner.nextLine();

		//add our AL with our data from the filename

		ArrayList<Product> NewData;
		NewData = Set(filename);
		//displaying the data from the file from all methods created
		displayTable(NewData);
		HighMaxScore(NewData);
		LowMaxScore(NewData);


		Scanner.close();	
	}
}


