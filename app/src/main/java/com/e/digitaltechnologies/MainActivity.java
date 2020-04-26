package com.e.digitaltechnologies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressDialog progressBar;
    private RequestQueue requestQueue;
    private RecyclerViewAdapter adapter;
    BaseClass baseClass = BaseClass.getOurInstance();
    private FlexboxLayoutManager flexboxLayoutManager;

    private  void initializeCart(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preferencesFile), Context.MODE_PRIVATE);
        String jsonCart = sharedPreferences.getString("cart", "no item");
        String jsonSelected = sharedPreferences.getString("selected", "no item");
        Gson gson = new Gson();

        if (jsonCart != null && !jsonCart.equals("no item")) {
            baseClass.cart = gson.fromJson(jsonCart, Cart.class);
        }
        if (jsonSelected != null && !jsonSelected.equals("no item")) {
            baseClass.selected = gson.fromJson(jsonSelected, Selected.class);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeCart();
        recyclerView = findViewById(R.id.recyclerView);
        requestQueue = Volley.newRequestQueue(this);
        flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                Button add_to_cart = view.findViewById(R.id.add_to_cart);
                add_to_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cake ck = baseClass.cakeMenu.getMenu_items().get(position);

                        baseClass.cart.getCart_items().add(ck);
                        ck.setAddedToCart(true);
                        adapter.notifyItemChanged(position);



                    }
                });
            }
        }));
        getCakes(this, new VolleyCallback() {
            @Override
            public void onSuccess(boolean result) throws JSONException {
                if (result) {
                    Toast.makeText(MainActivity.this, "Fetched", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Not Fetched", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public static interface ClickListener {
        public void onClick(View view, int position);
    }

    void getCakes(final Context context, final VolleyCallback volleyCallback) {
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setMessage("Fetching cakes...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                "http://kekizadmin.com/kekiz_api/actions.php?action=get_cakes&category=27",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            baseClass.cakeMenu.setMenu_items(Cake.fromJson(response.getJSONArray("data")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter = new RecyclerViewAdapter(baseClass.cakeMenu.getMenu_items(), context);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(flexboxLayoutManager);

                        progressBar.dismiss();
                        try {
                            volleyCallback.onSuccess(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Cakes", error.getMessage());
                        progressBar.dismiss();
                        try {
                            volleyCallback.onSuccess(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preferencesFile), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonCart = gson.toJson(baseClass.cart);
        sharedPreferences.edit().putString("cart", jsonCart).apply();
        String jsonSelected = gson.toJson(baseClass.selected);
        sharedPreferences.edit().putString("selected", jsonSelected).apply();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}

class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private MainActivity.ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context, final RecyclerView recycleView, final MainActivity.ClickListener clicklistener) {

        this.clicklistener = clicklistener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }


        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
            clicklistener.onClick(child, rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
