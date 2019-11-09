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
import com.folioreader.FolioReader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BookLibraryAdapter extends RecyclerView.Adapter<BookLibraryAdapter.MyViewHolder>{
    private Context mContext ;
    private List<BookLibraryModel> bookLibraryModels ;
    FirebaseStorage storage;
    FolioReader folioReader = FolioReader.get();
    File localFile = null;


    public BookLibraryAdapter(Context mContext, List<BookLibraryModel> booksModels) {
        this.mContext = mContext;
        this.bookLibraryModels = booksModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view_book,parent,false);
        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
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
                DownloadEpubFile(bookLibraryModels.get(position).getNoiDung());
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
    private void DownloadEpubFile(String URL){
        // [START download_to_local_file]
        StorageReference httpsReference = storage.getReferenceFromUrl(URL);


        try {
            localFile = File.createTempFile("book",".epub");
        } catch (IOException e) {
            e.printStackTrace();
        }

        httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                folioReader.openBook(localFile.getPath());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        // [END download_to_local_file]
    }
}
