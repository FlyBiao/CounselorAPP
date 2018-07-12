package com.cesaas.android.counselor.order.custom.toprightmenu;

/**
 * Author：Bro0cL on 2016/12/26.
 */

public class MenuItemMenu {
    private int icon;
    private String text;

    public MenuItemMenu() {}

    public MenuItemMenu(String text) {
        this.text = text;
    }

    public MenuItemMenu(int iconId, String text) {
        this.icon = iconId;
        this.text = text;
    }

    public int getIcon() {
        return icon;

}
    public void setIcon(int iconId) {
        this.icon = iconId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
