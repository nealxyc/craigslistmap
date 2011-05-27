package nealxyc.craigslist;

import java.util.ArrayList;

public class TestCrawler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String link = "http://sfbay.craigslist.org/search/apa/sfc?query=&srchType=A&minAsk=&maxAsk=1000&bedrooms=";
		ArrayList<Item> list = Crawler.fetchItemFromPage(link, "");
		
		for(Item it : list){
			System.out.println(it.getTitle());
			//System.out.println(it.getAddress());
			System.out.println(it.getDate());
			System.out.println("==========");
		}

	}

}
