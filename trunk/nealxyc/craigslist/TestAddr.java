package nealxyc.craigslist;

import java.util.ArrayList;

public class TestAddr {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LoggerInit.init();
		
		//String link = "http://sfbay.craigslist.org/sfc/apa/";
		String link = "http://sfbay.craigslist.org/search/apa/sfc?query=&srchType=A&minAsk=&maxAsk=1100&bedrooms=";
		ArrayList<Item> list = Crawler.fetchItemFromPage(link, 0);
		
		System.out.println("==========");
		System.out.println(list.size() + " results.");
		int count = 3 ;
		for(Item it : list){
			
			it.setAddress(Crawler.fetchAddr(it.getLink()));
			System.out.println(it.getTitle());
			System.out.println(it.getAddress());
			System.out.println("==========");
			count -- ;
			if(count <= 0){
				break ;
			}
		}
		
	}

}
