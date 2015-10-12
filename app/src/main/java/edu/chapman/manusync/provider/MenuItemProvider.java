package edu.chapman.manusync.provider;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.chapman.manusync.R;
import edu.chapman.manusync.dto.MainMenuItemDTO;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 */
@Singleton
public class MenuItemProvider {

    private final Context context;
    private final List<MainMenuItemDTO> items;

    @Inject
    public MenuItemProvider(Context context) {
        this.context = context;
        items = initMenuItems();
    }

    /* Iterates through all items in resource.drawables and gets only the menu items (prefaced with "menu_") */
    private List<MainMenuItemDTO> initMenuItems() {
        String[] itemsArray = context.getResources().getStringArray(R.array.menu_items);
        List<MainMenuItemDTO> itemInformation = new ArrayList<>();
        for (String item : itemsArray) {
            itemInformation.add(new MainMenuItemDTO(item));
        }
        return itemInformation;
    }

    public List<MainMenuItemDTO> getMenuItems() {
        return items;
    }
}
