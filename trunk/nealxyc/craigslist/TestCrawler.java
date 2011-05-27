package nealxyc.craigslist;

import java.util.ArrayList;

public class TestCrawler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LoggerInit.init();
		
		//String link = "http://sfbay.craigslist.org/sfc/apa/";
		String link = "http://sfbay.craigslist.org/search/apa/sfc?query=&srchType=A&minAsk=&maxAsk=1100&bedrooms=";
		ArrayList<Item> list = Crawler.fetchItemFromPage(link, "");
		
		System.out.println("==========");
		System.out.println(list.size() + " results.");
		System.out.println("==========");
		for(Item it : list){
			System.out.println(it.getTitle());
			//System.out.println(it.getAddress());
			System.out.println(it.getDate());
			System.out.println("==========");
		}

		

	}

}
