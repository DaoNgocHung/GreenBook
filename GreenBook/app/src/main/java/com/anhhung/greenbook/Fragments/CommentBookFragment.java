package com.anhhung.greenbook.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.anhhung.greenbook.Adapters.CommentBookAdapter;
import com.anhhung.greenbook.Models.CommentsModel;
import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentBookFragment extends Fragment {

    private RecyclerView rViewComment;
    private SharedPreferences sharedPreferences;
    private String emailUser;
    private ImageButton imgbtnSendComment;
    private EditText edtEnterComment;
    private FirebaseFirestore db;
    private CommentBookAdapter adapter;

    private String tenSach, danhMuc, idDM, idSach = null;
    private UsersModel usersModel = null;
    private CommentsModel commentsModel = null;

    private String TAG = "CommentBookFragment - Error";

    public CommentBookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tenSach = this.getArguments().getString("tenSach", " ");
        danhMuc = this.getArguments().getString("danhMuc", " ");
        idDM = this.getArguments().getString("idDM", " ");

        sharedPreferences = getActivity().getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        emailUser = sharedPreferences.getString("emailUser", null).trim();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_book, container, false);
        addControls(view);
        loadData();
        addEvents();
        return view;
    }

    private void loadData() {
        Query query = db.collection("DanhMucCollection").document(idDM).collection("SachColection").document(tenSach)
                .collection("CommentCollection");
        FirestoreRecyclerOptions<CommentsModel> options = new FirestoreRecyclerOptions.Builder<CommentsModel>()
                .setQuery(query, CommentsModel.class)
                .build();
        adapter = new CommentBookAdapter(options);

        Log.d("CommentBookFragment", adapter.toString());

        rViewComment.setHasFixedSize(true);
        rViewComment.setLayoutManager(new LinearLayoutManager(getActivity()));
        rViewComment.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    private void addEvents() {
        imgbtnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("UserModel").whereEqualTo("email", emailUser)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        usersModel = document.toObject(UsersModel.class);
                                        commentsModel =
                                                new CommentsModel(tenSach, emailUser, Timestamp.now(), edtEnterComment.getText().toString(), usersModel.getHinhDaiDien());
                                        db.collection("DanhMucCollection").document(idDM).collection("SachColection")
                                                .whereEqualTo("tenSach", tenSach)
                                                .limit(1)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                                                // here you can get the id.
                                                                idSach = documentSnapshot.getId();
                                                                db.collection("DanhMucCollection").document(idDM).collection("SachColection").document(idSach)
                                                                        .collection("CommentCollection").document()
                                                                        .set(commentsModel)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                                                                edtEnterComment.setText("");
                                                                                loadData();
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
    }

    private void addControls(View view) {
        rViewComment = view.findViewById(R.id.rViewComment);
        imgbtnSendComment = view.findViewById(R.id.imgbtnSendComment);
        edtEnterComment = view.findViewById(R.id.edtEnterComment);
        db = FirebaseFirestore.getInstance();
    }
}
