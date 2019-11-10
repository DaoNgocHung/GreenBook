package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Models.BookLibraryModel;
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

public class BookListViewLibraryAdapter extends RecyclerView.Adapter<BookListViewLibraryAdapter.MyViewHolder> {
    private Context mContext ;
    private List<BookLibraryModel> bookLibraryModels ;
    FirebaseStorage storage;
    FolioReader folioReader = FolioReader.get();
    File localFile = null;

    public BookListViewLibraryAdapter(Context mContext, List<BookLibraryModel> bookLibraryModels) {
        this.mContext = mContext;
        this.bookLibraryModels = bookLibraryModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.listview_book_library,parent,false);
        storage = FirebaseStorage.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListViewLibraryAdapter.MyViewHolder holder, final int position) {
        holder.txtTenSachLibrary.setText(bookLibraryModels.get(position).getTenSach());
        holder.txtTenTacGiaLibrary.setText("Tác giả: "+ bookLibraryModels.get(position).getTacGia());
        holder.txtNXBLibrary.setText("NXB: "+bookLibraryModels.get(position).getNXB());
        holder.txtDanhMucLibrary.setText("Danh Mục: "+bookLibraryModels.get(position).getDanhMuc());
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

        TextView txtTenSachLibrary, txtTenTacGiaLibrary, txtNXBLibrary, txtDanhMucLibrary;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTenSachLibrary = itemView.findViewById(R.id.txtTenSachLibrary) ;
            txtTenTacGiaLibrary = itemView.findViewById(R.id.txtTenTacGiaLibrary);
            txtDanhMucLibrary = itemView.findViewById(R.id.txtDanhMucLibrary);
            txtNXBLibrary = itemView.findViewById(R.id.txtNXBLibrary);
            img_book_thumbnail = itemView.findViewById(R.id.imgBiaSachLibrary);
            cardView = itemView.findViewById(R.id.cardViewListViewLibrary);
        }
    }
    private void DownloadEpubFile(String URL){
        StorageReference httpsReference = storage.getReferenceFromUrl(URL);
        try {
            localFile = File.createTempFile("booklist",".epub");
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
    }
}
