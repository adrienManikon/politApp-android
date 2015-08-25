package enyx.ch.politapp.api;

import java.io.InputStream;

import enyx.ch.politapp.PolitApplication;

/**
 * Created by adrien.manikon on 25.08.15.
 */
public abstract class Api {

    protected static InputStream createInputStream(int resId) {
        return PolitApplication.getContext().getResources().openRawResource(resId);
    }

}
