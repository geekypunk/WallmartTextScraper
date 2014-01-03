package main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This Class has methods necessary to get the HTML document from wallmart.com, parse the results and generate 
 * the ArrayList of Result objects. <NEWLINE>
 * 
 * Default number of results is 16 per page
 *
 */
public class HTMLParser {

	public int resultsPerPage = 16;
	
	/**
	 * Default Constructor
	 */
	public HTMLParser(){
				
	}
	
	/** Contructor with required number of results per page
	 * 
	 * @param _resultsPerPage
	 */
	public HTMLParser(int _resultsPerPage){
		
		this.resultsPerPage = _resultsPerPage;
	}
	/**
	 * @param url - Request URL
	 * @return Returns Jsoup Document object
	 * @throws IOException
	 */
	public Document getDOMObject(String url) throws IOException{
		Connection conn = Jsoup.connect(url);
		//Added referrer to avert a JavaScript check in WallMart website
        conn.referrer("http://www.walmart.com/");
        Document doc = null;
		try {
			doc = conn.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
        return doc;
	}
	
	/**
	 * @param queryString - Query string
	 * @return Total number of results found
	 * @throws IOException
	 */
	public int getNumResults(String queryString) throws IOException {
		String url = makeReqUrlForPage(queryString,1);
        Document doc = getDOMObject(url);
        Elements e  = doc.select("span.floatLeft.numResults.mt5");
        if(e.size()==0){
        	return 0;
        }else{
        	 String text = e.first().ownText();
             Integer results = Integer.parseInt(text.substring(0,text.trim().indexOf(" ")));
             return results;
        }
       
		
	}
	/**
	 * @param queryString - Query string
	 * @return Returns the total number of result pages
	 * 
	 * @throws IOException
	 */
	public int getPageNoUpperBound(String queryString) throws IOException {
		
		int results = getNumResults(queryString);
		
		int totalPages = results/resultsPerPage;
		
		if(totalPages==0){
			
			return 1;
		}else{
			
			return totalPages+1;
		}
		
	}
	
	
	
	/**
	 * @param queryString - Query string
	 * @param pageNumber - page number of required results page
	 * @return Returns ArrayList of Result objects, depicting products present on the given page number 
	 * @throws IOException
	 */
	public List<Result> getResultsList(String queryString, int pageNumber) throws IOException {
		String url = makeReqUrlForPage(queryString,pageNumber);
        Document doc = getDOMObject(url);
        List<Element> elements = doc.getElementsByClass("prodInfoBox");
        List<Result>results = new ArrayList<Result>();
        Result result = null;
        Element elem = null;
        for(int i=0;i<elements.size();i++){
        	elem = elements.get(i);
        	result = new Result();
            result.setProductName(elem.getElementsByTag("a").attr("title"));
            result.setPrice(elem);
            results.add(result);
        }
        return results;

	}

	/**
	 * @param queryString - Query string
	 * @param pageNumber - page number of required results page
	 * @return Returns the url string required for making a HTTP request to retrieve results from given page number
	 * @throws IOException
	 */
	public String makeReqUrlForPage(String queryString, int number) throws IOException {
		String url = "http://www.walmart.com/search/search-ng.do?ic="+resultsPerPage+"_0&Find=Find&search_query=" + queryString + "&Find=Find&search_constraint=0" ;
        if(number>1){

            url = "http://www.walmart.com/search/search-ng.do?tab_value=all&search_query="+queryString+"&search_constraint=0&Find=Find&ss=false&ic=";
            url+= new StringBuilder().append(resultsPerPage).append("_"+resultsPerPage * (number - 1)).toString();
            return url;

        }else{
        	 return url;
        }
       


	}

	
}
