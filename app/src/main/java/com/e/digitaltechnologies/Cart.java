package com.e.digitaltechnologies;

import java.util.ArrayList;

public class Cart {
    ArrayList<Cake> cart_items;

    public Cart() {
        this.cart_items = new ArrayList<>();
    }

    public ArrayList<Cake> getCart_items() {
        return cart_items;
    }

    public void setCart_items(ArrayList<Cake> cart_items) {
        this.cart_items = cart_items;
    }
}
