package Model;

/** Subclass of Food to classify Food as Market. Adds marketID property to Food. */
public class Market extends Food{

    private int marketId;

    /**Constructor for subclass, of Food, Market. */
    public Market(int id, String name, double price, int stock, int min, int max, int marketId){
        super(id, name, price, stock, min, max);
        this.marketId = marketId;
    }

    /** Method to get marketId.
     @return return marketId.*/
    public int getMarketId() {
        return marketId;
    }

    /** Method to set marketId.
     @param marketId the marketId to set.*/
    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }
}
