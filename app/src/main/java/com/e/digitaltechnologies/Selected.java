package com.e.digitaltechnologies;

import java.util.ArrayList;

public class Selected {
    ArrayList<Cake> selected_items;

    public Selected() {
        this.selected_items = new ArrayList<>();
    }

    public ArrayList<Cake> getSelected_items() {
        return selected_items;
    }

    public void setSelected_items(ArrayList<Cake> selected_items) {
        this.selected_items = selected_items;
    }
}
