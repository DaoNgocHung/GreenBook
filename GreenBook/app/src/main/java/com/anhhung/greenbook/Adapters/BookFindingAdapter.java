package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Activities.InfoBookActivity;
import com.anhhung.greenbook.Models.BookLibraryModel;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;


import java.util.List;
import java.util.Locale;

public class BookFindingAdapter extends RecyclerView.Adapter<BookFindingAdapter.MyViewHolder> {
    private Context mContext ;
    private List<BooksModel> booksModels ;
    private List<BooksModel> booksModelsFB;
    FirebaseStorage storage;


    public BookFindingAdapter(Context mContext, List<BooksModel> booksModels) {
        this.mContext = mContext;
        this.booksModels = booksModels;
        this.booksModelsFB = booksModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.finding_book,parent,false);
        storage = FirebaseStorage.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txtTenSachFindingBook.setText(booksModels.get(position).getTenSach());
        holder.txtTenTacGiaFindingBook.setText("Tác giả: "+ booksModels.get(position).getTacGia());
        holder.txtDanhMucFindingBook.setText("Danh Mục: "+booksModels.get(position).getDanhMuc());
        Glide.with(holder.itemView)
                .load(booksModels.get(position).getBiaSach())
                .into(holder.img_book_thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InfoBookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("anhBia", booksModels.get(position).getBiaSach());
                bundle.putString("tenSach", booksModels.get(position).getTenSach());
                bundle.putLong("soNguoiMua", booksModels.get(position).getSoNguoiMua());
                bundle.putFloat("danhGia", booksModels.get(position).getDanhGia());
                bundle.putString("noiDung", booksModels.get(position).getNoiDung());
                bundle.putString("gioiThieu", booksModels.get(position).getGioiThieuSach());
                bundle.putDouble("giaTien", booksModels.get(position).getGiaTien());
                bundle.putString("NXB", booksModels.get(position).getNXB());
                bundle.putString("danhMuc", booksModels.get(position).getDanhMuc());
                bundle.putString("tacGia", booksModels.get(position).getTacGia());
                bundle.putString("ngonNgu", booksModels.get(position).getNgonNgu());
                bundle.putLong("soNguoiMua", booksModels.get(position).getSoNguoiMua());
                bundle.putLong("luotDanhGia",booksModels.get(position).getLuotDanhGia());
                bundle.putString("idDM",booksModels.get(position).getIdDM());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return booksModels.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSachFindingBook, txtTenTacGiaFindingBook, txtDanhMucFindingBook;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTenSachFindingBook = itemView.findViewById(R.id.txtTenSachFindBook) ;
            txtTenTacGiaFindingBook = itemView.findViewById(R.id.txtTacGiaFindBook);
            txtDanhMucFindingBook = itemView.findViewById(R.id.txtDanhMucFindBook);
            img_book_thumbnail = itemView.findViewById(R.id.img_find_book);
            cardView = itemView.findViewById(R.id.cardViewFindingBook);
        }
    }



}
