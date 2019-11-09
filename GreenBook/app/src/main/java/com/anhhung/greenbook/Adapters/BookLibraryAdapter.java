package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Activities.InfoBookActivity;
import com.anhhung.greenbook.Models.BookLibraryModel;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class BookLibraryAdapter extends RecyclerView.Adapter<BookLibraryAdapter.MyViewHolder>{
    private Context mContext ;
    private List<BookLibraryModel> bookLibraryModels ;


    public BookLibraryAdapter(Context mContext, List<BookLibraryModel> booksModels) {
        this.mContext = mContext;
        this.bookLibraryModels = booksModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(bookLibraryModels.get(position).getTenSach());
        Glide.with(holder.itemView)
                .load(bookLibraryModels.get(position).getBiaSach())
                .into(holder.img_book_thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InfoBookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("anhBia", bookLibraryModels.get(position).getBiaSach());
                bundle.putString("tenSach", bookLibraryModels.get(position).getTenSach());
                bundle.putString("noiDung", bookLibraryModels.get(position).getNoiDung());
                bundle.putDouble("giaTien", bookLibraryModels.get(position).getGiaTien());
                bundle.putString("NXB", bookLibraryModels.get(position).getNXB());
                bundle.putString("danhMuc", bookLibraryModels.get(position).getDanhMuc());
                bundle.putString("tacGia", bookLibraryModels.get(position).getTacGia());
                bundle.putString("ngonNgu", bookLibraryModels.get(position).getNgonNgu());
                bundle.putString("ngayMua", bookLibraryModels.get(position).getNgayMua().toString());
                intent.putExtras(bundle);
                // start the activity
               // mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookLibraryModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_book_title = itemView.findViewById(R.id.labelBookName) ;
            img_book_thumbnail = itemView.findViewById(R.id.imgBookCategories);
            cardView = itemView.findViewById(R.id.cardviewCategories);


        }
    }
}
