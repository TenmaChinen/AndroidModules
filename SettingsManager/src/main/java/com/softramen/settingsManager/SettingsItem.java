package com.softramen.settingsManager;

public class SettingsItem {
	private final int itemType;
	private final String label;

	/*   S W I T C H   */

	private boolean state = false;
	public SettingsItem( final String label , final boolean state ) {
		itemType = Type.SWITCH;
		this.label = label;
		this.state = state;
	}

	/*   S P I N N  E R   */
	private String[] options = null;
	private int optionPosition = -1;

	public SettingsItem( final String label , final String[] options , final int optionPosition ) {
		itemType = Type.SPINNER;
		this.label = label;
		this.options = options;
		this.optionPosition = optionPosition;
	}

	/*   B U T T O N   */

	private boolean clicked = false;

	public SettingsItem( final String label ) {
		itemType = Type.BUTTON;
		this.label = label;
	}

	/*   G E T T E R S   */

	public int getItemType() {
		return itemType;
	}

	public String getLabel() {
		return label;
	}

	public boolean getState() {
		return state;
	}

	public String[] getOptions() {
		return options;
	}

	public int getOptionPosition() {
		return optionPosition;
	}

	public String getOptionText() {
		return options[ optionPosition ];
	}

	/*   S E T T E R S   */

	public void setState( final boolean state ) {
		this.state = state;
	}
	public void setOptionPosition( final int optionPosition ) {
		this.optionPosition = optionPosition;
	}

	public void setClicked() {
		clicked = true;
	}

	public static class Type {
		public final static int SWITCH = 0;
		public final static int SPINNER = 1;
		public final static int BUTTON = 2;
	}
}
