package com.github.goldy1992.rms.client.frontend.MainMenu.View;

import javax.swing.*;

/**
 * Created by michaelg on 26/08/2016.
 */
public class KeypadPanelJButton extends JButton {
	private Integer number;

	public KeypadPanelJButton(String title, Integer number) {
		super(title);
		this.number = number;
	}

	public Integer getNumber() { return number; }
	public void setInteger(Integer number) { this.number = number; }
}
