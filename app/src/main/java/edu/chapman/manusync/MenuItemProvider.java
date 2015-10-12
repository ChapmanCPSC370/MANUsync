package edu.chapman.manusync;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.chapman.manusync.dto.MainMenuItemDTO;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 */
@Singleton
public class MenuItemProvider {
    private static final String TAG = MenuItemProvider.class.getSimpleName();

    private final Context context;
    private final List<MainMenuItemDTO> items;

    @Inject
    public MenuItemProvider(Context context) {
        this.context = context;
        items = initMenuItems();
    }

    //TODO: Fix naming of item to be human readable/user friendly
    /* Iterates through all items in resource.drawables and gets only the menu items (prefaced with "menu_") */
    private List<MainMenuItemDTO> initMenuItems() {
        Field[] drawables = R.drawable.class.getFields();
        List<MainMenuItemDTO> itemInformation = new ArrayList<>();
        for (Field field : drawables) {
            try {
                if (field.getName().startsWith("menu_"))
                    itemInformation.add(new MainMenuItemDTO( field.getName(), field.getInt(null)));
            } catch (IllegalAccessException e) {
                Log.e(TAG, "An exception occurred while initializing images", e);
                return null;
            }
        }
        return itemInformation;
    }

    public List<MainMenuItemDTO> getImages() {
        return items;
    }
}
