package com.e.digitaltechnologies;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    BaseClass baseClass = BaseClass.getOurInstance();
    RecyclerView cartList, selectedList;
    CartListAdapter cartAdapter;
    SelectedListAdapter selectedAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartList = findViewById(R.id.cartList);
        selectedList = findViewById(R.id.selectedList);
        cartAdapter = new CartListAdapter(baseClass.cart.getCart_items(), this);
        cartList.setAdapter(cartAdapter);
        cartList.setLayoutManager(new LinearLayoutManager(this));

        selectedAdapter = new SelectedListAdapter(baseClass.selected.getSelected_items(), this);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(selectedAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(selectedList);

        selectedList.setAdapter(selectedAdapter);
        selectedList.setLayoutManager(new LinearLayoutManager(this));

        cartList.addOnItemTouchListener(new CartListTouchListener(this, cartList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Cake ck = baseClass.cart.getCart_items().get(position);
                Button move = view.findViewById(R.id.move);
                move.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseClass.cart.getCart_items().remove(ck);
                        baseClass.selected.getSelected_items().add(ck);
                        cartAdapter.notifyDataSetChanged();
                        selectedAdapter.notifyDataSetChanged();
                    }
                });
                ImageView imageView = view.findViewById(R.id.image);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseClass.cart.getCart_items().remove(ck);
                        baseClass.selected.getSelected_items().add(ck);
                        cartAdapter.notifyDataSetChanged();
                        selectedAdapter.notifyDataSetChanged();
                    }
                });
                Button delete = view.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseClass.cart.getCart_items().remove(ck);
                        for (Cake cake : baseClass.cakeMenu.getMenu_items()) {
                            if (cake.getId().equals(ck.getId())) {
                                cake.setAddedToCart(false);
                            }
                        }
                        cartAdapter.notifyDataSetChanged();

                    }
                });
            }

        }));
        selectedList.addOnItemTouchListener(new SelectedListTouchListener(this, selectedList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Cake ck = baseClass.selected.getSelected_items().get(position);

                Button delete = view.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseClass.selected.getSelected_items().remove(ck);
                        for (Cake cake : baseClass.cakeMenu.getMenu_items()) {
                            if (cake.getId().equals(ck.getId())) {
                                cake.setAddedToCart(false);
                            }
                        }
                        selectedAdapter.notifyDataSetChanged();

                    }
                });
            }
        }));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cartAdapter.notifyDataSetChanged();
        selectedAdapter.notifyDataSetChanged();
    }

    public static interface ClickListener {
        public void onClick(View view, int position);
    }
}

class CartListTouchListener implements RecyclerView.OnItemTouchListener {

    private CartActivity.ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public CartListTouchListener(Context context, final RecyclerView recycleView, final CartActivity.ClickListener clicklistener) {

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

class SelectedListTouchListener implements RecyclerView.OnItemTouchListener {

    private CartActivity.ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public SelectedListTouchListener(Context context, final RecyclerView recycleView, final CartActivity.ClickListener clicklistener) {

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
