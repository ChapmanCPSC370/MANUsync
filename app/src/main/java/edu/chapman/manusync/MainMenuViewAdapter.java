package edu.chapman.manusync;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import edu.chapman.manusync.dto.MainMenuItemDTO;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 */
public class MainMenuViewAdapter extends BaseAdapter {
    private static final String TAG = MainMenuViewAdapter.class.getSimpleName();

    private final Context context;
    private final List<MainMenuItemDTO> menuItems;

    @Inject
    public MainMenuViewAdapter(Context context, MenuItemProvider menuItemProvider) {
        this.context = context;
        menuItems = menuItemProvider.getImages();
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        //TODO: make the view look prettier.
        if( view == null )
            view = LayoutInflater.from(context).inflate(R.layout.item_main_menu, parent, false);

        MainMenuItemDTO current = menuItems.get(position);
        ImageView itemImage = (ImageView) view.findViewById(R.id.main_menu_item_iv);
        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), current.getResourceId());
        itemImage.setImageBitmap(imageBitmap);
        TextView itemName = (TextView) view.findViewById(R.id.main_menu_item_tv);
        itemName.setText(current.getName());

        return view;
    }
}
