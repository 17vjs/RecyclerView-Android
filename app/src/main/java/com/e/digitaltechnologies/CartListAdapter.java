package com.e.digitaltechnologies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,weight,price;
        public ImageView image;
        public ViewHolder(final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            weight = itemView.findViewById(R.id.weight);
            price = itemView.findViewById(R.id.price);
            image=itemView.findViewById(R.id.image);

        }

    }

    private List<Cake> cakes;
  private   Context context;

    public CartListAdapter(List<Cake> cakes, Context context) {
        this.cakes = cakes;
    this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cake = inflater.inflate(R.layout.cake_in_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(cake);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cake ck = cakes.get(position);
        TextView textView = holder.name;
        textView.setText(ck.getName());
        TextView textView1 = holder.weight;
        textView1.setText(ck.getWeight());
        TextView textView2 = holder.price;
        textView2.setText(ck.getPrice());
        ImageView imageView = holder.image;
        Glide.with(context).load(ck.getImage()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return cakes.size();
    }
}
