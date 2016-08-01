package com.proton.library.widget.mtfdialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Orhan Obut
 */
public interface Holder {

    void addHeader(View view);

    void addFooter(View view);

    void setBackgroundColor(int colorResource);

    void setBackground(int resourceId);

    View getView(LayoutInflater inflater, ViewGroup parent);

    void setOnKeyListener(View.OnKeyListener keyListener);

    View getInflatedView();

}
