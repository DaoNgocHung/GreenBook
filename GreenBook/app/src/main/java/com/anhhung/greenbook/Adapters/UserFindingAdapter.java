package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFindingAdapter extends RecyclerView.Adapter<UserFindingAdapter.MyViewHolder> {
    private Context mContext ;
    private List<UsersModel> usersModels ;
    private List<UsersModel> usersModelsFB;
    private FirebaseFirestore db;

    public UserFindingAdapter(Context mContext, List<UsersModel> usersModels) {
        this.mContext = mContext;
        this.usersModels = usersModels;
        this.usersModelsFB = usersModels;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.finding_user,parent,false);
        //storage = FirebaseStorage.getInstance();
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txtNameFindUser.setText("Name:" + usersModels.get(position).getHoTen());
        holder.txtMailFindUser.setText("Email: "+ usersModels.get(position).getEmail());
        if (usersModels.get(position).getQuyen().trim().equals("admin")){
            holder.rdMakeUserAdmin.setChecked(true);
        } else {
            holder.rdMakeUserMember.setChecked(true);
        }
        Glide.with(holder.itemView)
                .load(usersModels.get(position).getHinhDaiDien())
                .into(holder.imgAvatarFindUser);
        holder.rdMakeUserMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("UserModel").document(usersModels.get(position).getEmail())
                        .update("quyen","member");
            }
        });
        holder.rdMakeUserAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("UserModel").document(usersModels.get(position).getEmail())
                        .update("quyen","admin");
            }
        });
        holder.imgDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                db.collection("UserModel").document(usersModels.get(position).getEmail())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(view.getContext(),"Complete",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(),"Error",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public int getItemCount() {
        return usersModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton rdMakeUserMember, rdMakeUserAdmin;
        TextView txtNameFindUser, txtMailFindUser;
        CircleImageView imgAvatarFindUser;
        ImageView imgDeleteUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            rdMakeUserAdmin = itemView.findViewById(R.id.rdMakeUserAdmin);
            rdMakeUserMember = itemView.findViewById(R.id.rdMakeUserMember);
            txtMailFindUser = itemView.findViewById(R.id.txtMailFindUser);
            txtNameFindUser = itemView.findViewById(R.id.txtNameFindUser);
            imgAvatarFindUser = itemView.findViewById(R.id.imgAvatarFindUser);
            imgDeleteUser = itemView.findViewById(R.id.imgDeleteUser);
            db = FirebaseFirestore.getInstance();
        }
    }
}
