package edu.chapman.manusync.dto;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/22/15.
 */
public class UserDTO {

    private String username, password, firstName, lastName, creationDate;
    private int userId, productionLineId;

    /* used to log in user */
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /* used for creating a new user */
    public UserDTO(String username, String password, String firstName, String lastName, int productionLineId) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.productionLineId = productionLineId;
    }

    /* used for filling in information once logged in */
    public UserDTO(int userId, String username, String password, String firstName, String lastName,
                   int productionLineId, String creationDate) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.productionLineId = productionLineId;
        this.creationDate = creationDate;
    }


    /* accessors and mutators limited to hide data */

    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public int getProductionLineId() { return this.productionLineId; }

}
