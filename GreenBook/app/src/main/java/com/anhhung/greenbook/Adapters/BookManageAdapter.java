package com.anhhung.greenbook.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class BookManageAdapter extends FirestoreRecyclerAdapter<BooksModel, BookManageAdapter.BookManageHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BookManageAdapter(@NonNull FirestoreRecyclerOptions<BooksModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookManageHolder holder, int position, @NonNull BooksModel model) {
        Glide.with(holder.itemView)
                .load(model.getBiaSach())
                .into(holder.imgCoverBookManage);
        holder.txtTitleBookManage.setText(model.getTenSach());
        holder.txtTacGiaBookManage.setText(model.getTacGia());
        holder.txtCateBookManage.setText(model.getDanhMuc());
    }

    @NonNull
    @Override
    public BookManageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_manage,parent,false);
        return new BookManageHolder(view);
    }

    public class BookManageHolder extends RecyclerView.ViewHolder{
        private ImageView imgCoverBookManage;
        private TextView txtTitleBookManage, txtCateBookManage, txtTacGiaBookManage;
        public BookManageHolder(@NonNull View itemView) {
            super(itemView);
            imgCoverBookManage = itemView.findViewById(R.id.imgCoverBookManage);
            txtTitleBookManage  = itemView.findViewById(R.id.txtTitleBookManage);
            txtCateBookManage = itemView.findViewById(R.id.txtCateBookManage);
            txtTacGiaBookManage = itemView.findViewById(R.id.txtTacGiaBookManage);
        }
    }
}
