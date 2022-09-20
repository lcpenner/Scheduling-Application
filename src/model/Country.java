package model;

/**This class is the model for Countries. */
public class Country {

    private int id;
    private String name;

    /**This method is the constructor for new countries. */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId(){ return id; }

    public String getName() { return name; }

    /**This method is used for displaying in the combo box. */
    @Override
    public String toString(){
        return name;
    }

}
