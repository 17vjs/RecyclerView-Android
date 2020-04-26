package com.e.digitaltechnologies;

import java.util.ArrayList;

public class CakeMenu {
    ArrayList<Cake> menu_items;

    public CakeMenu() {
        this.menu_items=new ArrayList<>();
    }

    public ArrayList<Cake> getMenu_items() {
        return menu_items;
    }

    public void setMenu_items(ArrayList<Cake> menu_items) {
        this.menu_items = menu_items;
    }
}
