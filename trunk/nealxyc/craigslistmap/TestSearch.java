package nealxyc.craigslistmap;

import java.io.PrintWriter;
import java.util.ArrayList;

import nealxyc.craigslist.Crawler;

import org.json.JSONArray;

public class TestSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PrintWriter pw = new PrintWriter(System.out) ;
		
		
		String link = "http://sfbay.craigslist.org/search/apa/sfc?query=&srchType=A&minAsk=&maxAsk=1100&bedrooms=" ;
		
		int count = 52 ;
		int index = 0 ;
		ArrayList<nealxyc.craigslist.Item> result = new ArrayList<nealxyc.craigslist.Item>();
		
		
		if(link != null && !link.equals("") ){
			ArrayList<nealxyc.craigslist.Item> list ;
			
			fetchLoop:
			while(index < count){
				list = Crawler.fetchItemFromPage(link, index);
				
				if(list != null){
					for(nealxyc.craigslist.Item it : list){
						result.add(it);
						index ++ ;
						if(index >= count){
							break fetchLoop;
						}
					}
				}else{
					break ;
				}
			}
			
		}
		
		JSONArray array = new JSONArray();
		
		System.out.println("Result count=" + result.size());
		int i = 1 ;
		for(nealxyc.craigslist.Item it: result){
			//array.put(it.getTitle());
			pw.println(i++ + " " + it.getTitle());
		}
		
		pw.println(array);
		pw.flush();
		pw.close();

	}

}
