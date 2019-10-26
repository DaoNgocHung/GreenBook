package com.anhhung.greenbook.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Models.CategoriesModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CategoryAdapter extends FirestoreRecyclerAdapter<CategoriesModel, CategoryAdapter.CategoryHolder> {

    public CategoryAdapter(@NonNull FirestoreRecyclerOptions<CategoriesModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryHolder holder, int position, @NonNull CategoriesModel model) {
        holder.txtNameCategory.setText(model.getTenDanhMuc());
        Glide.with(holder.itemView)
                .load(model.getAnhBia())
                .into(holder.imgBackgroundCategory);
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_category,parent,false);
        return new CategoryHolder(view);
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        TextView txtNameCategory;
        ImageView imgBackgroundCategory;

        public CategoryHolder (View itemView){
            super(itemView);
            txtNameCategory = itemView.findViewById(R.id.txtNameCategory);
            imgBackgroundCategory = itemView.findViewById(R.id.imgBackgroundCategory);
        }
    }
}
