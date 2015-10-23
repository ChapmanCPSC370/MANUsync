package edu.chapman.manusync.listener;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import edu.chapman.manusync.R;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/23/15.
 * <p/>
 * Updated the ActionProgressButton on new edit text changes. If the button shows "error"
 * it will renew it to show the original button.
 */
public class EditProgressButtonListener implements TextWatcher {

    private ActionProcessButton button;
    private EditText editText;
    private int colorRed, colorBlack;

    public EditProgressButtonListener(ActionProcessButton button) {
        this.button = button;
        this.editText = null;
    }

    public EditProgressButtonListener(ActionProcessButton button, EditText editText, Context context) {
        this.button = button;
        this.editText = editText;
        colorRed = context.getResources().getColor(R.color.color_alizarin);
        colorBlack = context.getResources().getColor(R.color.color_black);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //not necessary
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (button.getProgress() == -1)
            button.setProgress(0);
        if (editText != null)
            if (editText.getCurrentTextColor() == colorRed)
                editText.setTextColor(colorBlack);
    }

    @Override
    public void afterTextChanged(Editable s) {
        //not necessary
    }
}
