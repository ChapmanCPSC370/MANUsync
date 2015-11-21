package edu.chapman.manusync.dto;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/22/15.
 */
public class UserDTO {

    private String parseId, username, password, firstName, lastName, parseProductionLineId, productionLineId;

    /* used to log in user */
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /* used for creating a new user */
    public UserDTO(String username, String password, String firstName, String lastName,
                   String parseProductionLineId, String productionLineId) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.parseProductionLineId = parseProductionLineId;
        this.productionLineId = productionLineId;
    }

    /* used for filling in information once logged in */
    public UserDTO(String parseId, String username, String password, String firstName, String lastName,
                   String parseProductionLineId, String productionLineId) {
        this.parseId = parseId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.parseProductionLineId = parseProductionLineId;
        this.productionLineId = productionLineId;
    }


    /* accessors and mutators limited to hide data */

    public String getParseId() { return this.parseId; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getParseProductionLineId() { return this.parseProductionLineId; }
    public String getProductionLineId() { return this.productionLineId; }

}
