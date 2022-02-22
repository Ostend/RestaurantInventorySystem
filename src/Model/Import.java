package Model;

/** Subclass of a Food to classify Food as Import. Adds CompanyName property to Food. */
public class Import extends Food {
    private String companyName;

    /**Constructor for subclass, of Food, Import. */
    public Import(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Method to get companyName.
     @return return companyName.*/
    public String getCompanyName() {
        return companyName;
    }

    /** Method to set companyName.
     @param companyName the companyName to set.*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
