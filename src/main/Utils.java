package main;
import java.util.List;


/**
 * 
 * This class contains utility methods
 *
 */
public class Utils {
	
	
	/**
	 * @param results - List of result objects
	 * @return Prints the results to console
	 */
	public static void printResults(List<Result>results){
		//System.out.println("======================");
		for (int i = 0; i < results.size(); i++)
		{
			System.out.println("----------------------");
			System.out.println("Product Name: " + results.get(i).getProductName());
			System.out.println("Price: " + results.get(i).getPrice());
			
		}
		System.out.println("======================");
		
	}

}
