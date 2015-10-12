package edu.chapman.manusync.dto;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 *
 * DTO class used to transfer main menu item data between objects.
 */
public class MainMenuItemDTO {
    private String name;
    private int resourceId;

    public MainMenuItemDTO(String name, int resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }

    /* accessors and mutators */
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
    public void setResourceId(int resourceId) { this.resourceId = resourceId; }
    public int getResourceId() { return this.resourceId; }
}
