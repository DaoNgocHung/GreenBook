package com.anhhung.greenbook.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Activities.BookManagementActivity;
import com.anhhung.greenbook.Activities.EditBookManageActivity;
import com.anhhung.greenbook.Activities.ManageAdminActivity;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookManageAdapter extends RecyclerView.Adapter<BookManageAdapter.BookManageHolder> {
    private List<BooksModel> booksModels;
    private Context mContext;
    private Dialog dialogDelete;
    private FirebaseFirestore db;

    public BookManageAdapter(Context mContext, List<BooksModel> booksModels) {
        this.booksModels = booksModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BookManageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_manage,parent,false);
        return new BookManageHolder(view);
    }

    @Override
    public int getItemCount() {
        return booksModels.size();
    }

    @Override
    public void onBindViewHolder(final BookManageHolder holder, final int i) {
        Glide.with(holder.itemView)
                .load(booksModels.get(i).getBiaSach())
                .into(holder.imgCoverBookManage);
        holder.txtTitleBookManage.setText(booksModels.get(i).getTenSach());
        holder.txtTacGiaBookManage.setText(booksModels.get(i).getTacGia());
        holder.txtCateBookManage.setText(booksModels.get(i).getDanhMuc());
        holder.txtMenuOptionBookManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popupMenu =  new PopupMenu(view.getContext() ,holder.txtMenuOptionBookManage);
                popupMenu.inflate(R.menu.book_management);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.mnu_book_manage_edit:
                                Intent intent = new Intent(view.getContext(), EditBookManageActivity.class);
                                intent.putExtra("title",booksModels.get(i).getTenSach());
                                intent.putExtra("cover",booksModels.get(i).getBiaSach());
                                intent.putExtra("nxb",booksModels.get(i).getNXB());
                                intent.putExtra("price",booksModels.get(i).getGiaTien());
                                intent.putExtra("language",booksModels.get(i).getNgonNgu());
                                intent.putExtra("intro",booksModels.get(i).getGioiThieuSach());
                                intent.putExtra("author",booksModels.get(i).getTacGia());
                                intent.putExtra("iddm",booksModels.get(i).getIdDM());
                                view.getContext().startActivity(intent);
                                break;
                            case R.id.mnu_book_manage_delete:
                                dialogDelete = new Dialog(view.getContext());
                                dialogDelete.setContentView(R.layout.dialog_delete_book);
                                dialogDelete.setCancelable(false);
                                dialogDelete.show();
                                Button btnYesDelete, btnNoDelete;
                                btnYesDelete = dialogDelete.findViewById(R.id.btnYesDelete);
                                btnNoDelete = dialogDelete.findViewById(R.id.btnNoDelete);
                                btnYesDelete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View view) {
                                        db.collection("DanhMucCollection").document(booksModels.get(i).getIdDM())
                                                .collection("SachColection").document(booksModels.get(i).getTenSach())
                                                .update("trangThai",false)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(view.getContext(),"Delete Successful",Toast.LENGTH_SHORT).show();
                                                        dialogDelete.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(view.getContext(),"Delete Error",Toast.LENGTH_SHORT).show();
                                                        dialogDelete.dismiss();
                                                    }
                                                });
                                    }
                                });
                                btnNoDelete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogDelete.dismiss();
                                    }
                                });
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public class BookManageHolder extends RecyclerView.ViewHolder{
        private ImageView imgCoverBookManage;
        private TextView txtTitleBookManage, txtCateBookManage, txtTacGiaBookManage, txtMenuOptionBookManage;
        public BookManageHolder(@NonNull View itemView) {
            super(itemView);
            imgCoverBookManage = itemView.findViewById(R.id.imgCoverBookManage);
            txtTitleBookManage  = itemView.findViewById(R.id.txtTitleBookManage);
            txtCateBookManage = itemView.findViewById(R.id.txtCateBookManage);
            txtTacGiaBookManage = itemView.findViewById(R.id.txtTacGiaBookManage);
            txtMenuOptionBookManage =  itemView.findViewById(R.id.txtMenuOptionBookManage);
            db = FirebaseFirestore.getInstance();
        }
    }

}
