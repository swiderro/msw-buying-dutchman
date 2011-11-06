package auctions;

import java.io.Serializable;

public class AuctionItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ItemDescription;
	private String Category;
	private String SubCategory;
	
	public AuctionItem(String itemDescription, String category, String subCategory){		
		this.ItemDescription = itemDescription;
		this.Category = category;		
		this.SubCategory = subCategory;
	}
	public String getItemDescription() {
		return ItemDescription;
	}
	public String getCategory() {
		return Category;
	}
	public String getSubCategory() {
		return this.SubCategory;
	}
}
