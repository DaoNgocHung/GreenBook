package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Models.BookLibraryModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.folioreader.FolioReader;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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
    private InterstitialAd mInterstitialAd;

    private MyCallback myCallback;


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
        MobileAds.initialize(mContext, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
                readData(new MyCallback() {
                    @Override
                    public void onCallback(final File file) {
                        if(bookLibraryModels.get(position).getGiaTien()==0){
                            if (mInterstitialAd.isLoaded() ) {
                                mInterstitialAd.show();
                                mInterstitialAd.setAdListener(new AdListener() {
                                    @Override
                                    public void onAdLoaded() {
                                        // Code to be executed when an ad finishes loading.
                                    }

                                    @Override
                                    public void onAdFailedToLoad(int errorCode) {
                                        // Code to be executed when an ad request fails.
                                    }

                                    @Override
                                    public void onAdOpened() {
                                        // Code to be executed when the ad is displayed.


                                    }

                                    @Override
                                    public void onAdClicked() {
                                        // Code to be executed when the user clicks on an ad.
                                    }

                                    @Override
                                    public void onAdLeftApplication() {
                                        // Code to be executed when the user has left the app.
                                    }

                                    @Override
                                    public void onAdClosed() {
                                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                        folioReader.openBook(file.getPath());
                                    }
                                });
                            }
                            else {
                                Toast.makeText(mContext,"Please waiting load advertised...", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            folioReader.openBook(file.getPath());
                        }
                    }
                });
            }
        });
    }
    public interface MyCallback {
        void onCallback(File file);
    }
    public void readData(MyCallback myCallback) {
        this.myCallback = myCallback;

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
    private void DownloadEpubFile(String URL) {
        // [START download_to_local_file]
        StorageReference httpsReference = storage.getReferenceFromUrl(URL);


        try {
            localFile = File.createTempFile("book", ".epub");
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                myCallback.onCallback(localFile);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("ERR", exception.toString());
            }
        });
    }

    }
