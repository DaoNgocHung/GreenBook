package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Activities.InfoBookActivity;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {
    private ArrayList<BooksModel> itemsList;
    private Context mContext;
    private ArrayList<String> imgList;
    BooksModel singleBook;


    public SectionListDataAdapter(Context context, ArrayList<BooksModel> itemsList, ArrayList<String> imgList) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.imgList = imgList;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        singleBook = itemsList.get(i);
        holder.tvTitle.setText(singleBook.getTenSach());
        Glide.with(holder.itemView)
                .load(imgList.get(i))
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView itemImage;

        private SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = view.findViewById(R.id.tvTitle);
            this.itemImage = view.findViewById(R.id.itemImage);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    singleBook = itemsList.get(i);
                    if(singleBook.getTrangThai()==true){
                        Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), InfoBookActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("anhBia", singleBook.getBiaSach());
                        bundle.putString("tenSach", singleBook.getTenSach());
                        bundle.putLong("soNguoiMua", singleBook.getSoNguoiMua());
                        bundle.putFloat("danhGia", singleBook.getDanhGia());
                        bundle.putString("noiDung", singleBook.getNoiDung());
                        bundle.putString("gioiThieu", singleBook.getGioiThieuSach());
                        bundle.putDouble("giaTien", singleBook.getGiaTien());
                        bundle.putString("NXB", singleBook.getNXB());
                        bundle.putString("danhMuc", singleBook.getDanhMuc());
                        bundle.putString("tacGia", singleBook.getTacGia());
                        bundle.putString("ngonNgu", singleBook.getNgonNgu());
                        bundle.putLong("soNguoiMua", singleBook.getSoNguoiMua());
                        bundle.putLong("luotDanhGia",singleBook.getLuotDanhGia());
                        bundle.putString("idDM",singleBook.getIdDM());
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                    else{
                        Toast.makeText(mContext, "Book does not exist.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
