package database;

public class TestItem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String link = "http://abc.craigslist.org/xyz" ;
		Item item = new Item(link);
		item.setAddress("USF");
		item.setDescription("a simple test");
		item.setTitle("map");
		item.save();
		
		Item newItem = Item.getByLink(link);
		if(newItem != null) {
			System.out.println(newItem.getAddress());
			System.out.println(newItem.getDate());
		} else {
			System.out.println("item not found!");
		}

		//System.out.println();
	}
}
