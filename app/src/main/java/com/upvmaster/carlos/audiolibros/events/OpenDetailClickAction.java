package com.upvmaster.carlos.audiolibros.events;

import com.upvmaster.carlos.audiolibros.Main.MainActivity;

/**
 * Created by Carlos on 21/01/2017.
 */

public class OpenDetailClickAction implements ClickAction {
    private final MainActivity mainActivity;

    public OpenDetailClickAction(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void execute(int position) {
        mainActivity.mostrarDetalle(position);
    }
}
