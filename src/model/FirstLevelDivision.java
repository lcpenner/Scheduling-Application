package model;

/**This class is the model for divisions. */
public class FirstLevelDivision {

    private int id;
    private String name;
    private int countryId;

    /**This method is the constructor for new divisions. */
    public FirstLevelDivision(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    public int getId(){ return id; }

    public String getName() { return name; }

    public int getCountryId(){ return countryId; }

    /**This method is used for displaying in the combo box. */
    @Override
    public String toString(){
        return name;
    }
}
