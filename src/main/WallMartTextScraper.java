package main;
import java.io.IOException;
import java.util.List;


/**
 * This is Main class(entry-point) for this application
 * The main methods accepts 1 or 2 arguments. The first argument is a query string and second is a pagenumber. <NEWLINE>
 * The pagenumber argument allows the retrieval of results of a specific page of results, out of all results.<NEWLINE>
 * Current default is 16 results per page
 */
public class WallMartTextScraper {
	
	public static void main(String[] args) throws IOException {
		/* check parameters */
		if (args.length == 0 || args.length > 2) {
			System.out.println("Please input valid parameters.");
			return;
		}

		HTMLParser parser = new HTMLParser();
		/* only a single argument is given, return
		 * the total number of results found
		 */
		if (args.length == 1) {
			System.out.println("======================");
			System.out.println("Query: " + args[0]);
			System.out.println("Total Results: " + parser.getNumResults(args[0]));
			System.out.println("======================");
        }

		/* when two arguments are provided,
		 * return result object
		 */
		if (args.length == 2) {
			try {
				int number = Integer.parseInt(args[1]);
				if (number <= 0) {
					
					System.out.println("Please give a page value > 0 .");
				}else{
					
					int highestPageNo = parser.getPageNoUpperBound(args[0]);
					if(number <=highestPageNo){
						
						System.out.println("======================");
						System.out.println("Query: " + args[0]);
						System.out.println("Page number: " + args[1]);
						System.out.println("======================");
						
						System.out.println("========PRINTING RESULTS==============");
						//First the ArrayList of Result objects
						List<Result> results = parser.getResultsList(args[0], number);
						//Now print the results on screen
						Utils.printResults(results);
						System.out.println("========FINISH PRINTING RESULTS==============");
					}else{
						System.out.println("Given page number exceeded the number of results");
						System.out.println("Please give a page value <= "+highestPageNo);
						
					}
	                
				}
				
				
			
			} catch (NumberFormatException nfe){
				System.out.println("You should give an integer value to the second parameter.");
				return ;
			}
			catch (IOException e){
				System.out.println("Cannot connect to server");
				return ;
			}
		}

	}

}
