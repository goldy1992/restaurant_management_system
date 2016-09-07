package com.mike.client.frontend.MainMenu.View;

import javax.swing.*;

/**
 * Created by Mike on 25/08/2016.
 */
public class KeyJButton extends JButton {

    private char key;

    public KeyJButton(String title, char key) {
        super(title);
        this.setKey(key);
    }

    public char getKey() { return key; }
    public void setKey(char key) { this.key = key; }
}
