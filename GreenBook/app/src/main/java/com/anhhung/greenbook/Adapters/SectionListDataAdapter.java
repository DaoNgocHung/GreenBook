package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Activities.InfoBookActivity;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.folioreader.FolioReader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder>{
    private ArrayList<BooksModel> itemsList;
    private Context mContext;
    private ArrayList<String> imgList;
    FolioReader folioReader = FolioReader.get();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference httpsReference;
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
    public void downloadEpub(String noiDung){
        httpsReference = storage.getReferenceFromUrl(noiDung);
        String URL = httpsReference.getPath();
        storageRef.child(URL).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                folioReader.openBook("file:///android_asset/epub/loi_song_toi_gian_cua_nguoi_nhat_sasaki_fumio.epub");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
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

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //downloadEpub(noiDung);
                    int i = getAdapterPosition();
                    singleBook = itemsList.get(i);
                    Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), InfoBookActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("anhBia",singleBook.getBiaSach());
                    bundle.putString("tenSach",singleBook.getTenSach());
                    bundle.putLong("soNguoiMua", singleBook.getSoNguoiMua());
                    bundle.putFloat("danhGia", singleBook.getDanhGia());
                    bundle.putString("noiDung",singleBook.getNoiDung());
                    bundle.putString("gioiThieu", singleBook.getGioiThieuSach());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);

                }
            });


        }
    }
}
