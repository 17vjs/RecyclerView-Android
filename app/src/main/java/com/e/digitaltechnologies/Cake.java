package com.e.digitaltechnologies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import java.io.ObjectStreamException;
import java.util.ArrayList;

public class Cake {
    String image;
    String name;
    String weight,price;
String id;
boolean isAddedToCart=false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAddedToCart() {
        return isAddedToCart;
    }

    public void setAddedToCart(boolean addedToCart) {
        isAddedToCart = addedToCart;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public Cake(JSONObject object){
        try {
            this.id = object.getString("id");
            if(!this.isAddedToCart){
           for(Cake ck: BaseClass.getOurInstance().cart.getCart_items()){
               if(ck.getId().equals(this.id)){
                   this.isAddedToCart=true;
               }
           }} if(!this.isAddedToCart){
            for(Cake ck: BaseClass.getOurInstance().selected.getSelected_items()){
                if(ck.getId().equals(this.id)){
                    this.isAddedToCart=true;
                }
            }
            }
            this.name = object.getString("cake_name");
            this.price=object.getJSONArray("w_l_p").getJSONObject(0).getString("price");
            this.weight=object.getJSONArray("w_l_p").getJSONObject(0).getString("weight");
            this.image=object.getJSONArray("w_l_p").getJSONObject(0).getString("pictures");
            Gson gson=new Gson();
ImageURL imageURL=gson.fromJson(image,ImageURL.class);
this.image="http://kekizadmin.com/uploads/catrgories/"+imageURL.getFile_name();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static ArrayList<Cake> fromJson(JSONArray jsonObjects) {
        ArrayList<Cake> cakes = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                JSONObject jsonObject=jsonObjects.getJSONObject(i);
                    cakes.add(new Cake(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return cakes;
    }
}
