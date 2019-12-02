package com.anhhung.greenbook.Adapters;

import android.app.Dialog;
import android.content.Intent;
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

import java.util.List;

public class BookManageAdapter extends FirestoreRecyclerAdapter<BooksModel, BookManageAdapter.BookManageHolder> {
    private FirebaseFirestore db;
    private Dialog dialogDelete;
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
    protected void onBindViewHolder(@NonNull final BookManageHolder holder, int position, @NonNull final BooksModel model) {
        Glide.with(holder.itemView)
                .load(model.getBiaSach())
                .into(holder.imgCoverBookManage);
        holder.txtTitleBookManage.setText(model.getTenSach());
        holder.txtTacGiaBookManage.setText(model.getTacGia());
        holder.txtCateBookManage.setText(model.getDanhMuc());
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
                                intent.putExtra("title",model.getTenSach());
                                intent.putExtra("cover",model.getBiaSach());
                                intent.putExtra("nxb",model.getNXB());
                                intent.putExtra("price",model.getGiaTien());
                                intent.putExtra("language",model.getNgonNgu());
                                intent.putExtra("intro",model.getGioiThieuSach());
                                intent.putExtra("author",model.getTacGia());
                                intent.putExtra("iddm",model.getIdDM());
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
                                        db.collection("DanhMucCollection").document(model.getIdDM())
                                                .collection("SachColection").document(model.getTenSach())
                                                .delete()
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

    @NonNull
    @Override
    public BookManageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_manage,parent,false);
        return new BookManageHolder(view);
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
