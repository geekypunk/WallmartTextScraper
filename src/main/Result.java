package main;

import org.jsoup.nodes.Element;

/**
 *
 * Class for one Result object for each of the products retrieved.<NEWLINE> 
 * Each Result object contains the product name and its price 
 *  
 */
public class Result {
	
	private String productName;
	private String price;
	
	/**
	 * @return Gets the productName
	 */
	public String getProductName() {
		return productName;
	}
	
	/**Sets the productName
	 * @param productName 
	 * 
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return Gets the price
	 */
	public String getPrice() {
		return price;
	}
	/** Sets the product price
	 *  If item is unvailable, else sets the price to "Product Unvailable"
	 * @param elem - Jsoup Element object corresping to the product's price
	 */
	public void setPrice(Element elem) {
		String price = elem.getElementsByClass("camelPrice").text();
		if(price.length()==0){
			
			price = elem.getElementsByClass("PriceLBold").text();
			
			if(price.length()==0){
				
				price = "Product Unvailable";
			}
		}
		this.price = price;
	}

}
