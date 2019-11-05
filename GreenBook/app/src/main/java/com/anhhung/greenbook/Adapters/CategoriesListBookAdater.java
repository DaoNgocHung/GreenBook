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
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class CategoriesListBookAdater extends RecyclerView.Adapter<CategoriesListBookAdater.MyViewHolder>{
    private Context mContext ;
    private List<BooksModel> booksModels ;


    public CategoriesListBookAdater(Context mContext, List<BooksModel> booksModels) {
        this.mContext = mContext;
        this.booksModels = booksModels;
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

        holder.tv_book_title.setText(booksModels.get(position).getTenSach());
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
                intent.putExtras(bundle);
                // start the activity
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return booksModels.size();
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
