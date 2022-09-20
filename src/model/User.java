package model;

/**This class is the model for users. */
public class User {

    private int id;
    private String name;
    private String password;

    /**This method is the constructor for new users. */
    public User(int userId, String userName, String password) {
        this.id = userId;
        this.name = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
