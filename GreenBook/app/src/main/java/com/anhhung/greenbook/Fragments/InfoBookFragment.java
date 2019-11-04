package com.anhhung.greenbook.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anhhung.greenbook.Adapters.MyDataBookAdapter;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.CategoriesModel;
import com.anhhung.greenbook.Models.SectionDataModel;
import com.anhhung.greenbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoBookFragment extends Fragment {

    TextView txtInfoNXB, txtInfoAuthor, txtInfoCate, txtInfoDate, txtInfoLanguage;
    RecyclerView rViewBookOffer;

    String TAG = "ERROR - InfobookFragment";

    FirebaseFirestore db;
    List<BooksModel> booksModels = new ArrayList<>();

    CategoriesModel category;
    ArrayList<SectionDataModel> sectionDataModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String NXB = this.getArguments().getString("NXB", " ");
        String tacGia = this.getArguments().getString("tacGia", " ");
        String danhMuc = this.getArguments().getString("danhMuc", " ");
        String ngonNgu = this.getArguments().getString("ngonNgu", " ");

        View view = inflater.inflate(R.layout.fragment_info_book, null);

        addControls(view);

        txtInfoNXB.setText(NXB);
        txtInfoAuthor.setText(tacGia);
        txtInfoCate.setText(danhMuc);
        txtInfoLanguage.setText(ngonNgu);

        createData(danhMuc, rViewBookOffer);

        // Inflate the layout for this fragment
        return view;
    }


    private void addControls(View view) {
        txtInfoNXB = view.findViewById(R.id.txtInfoNXB);
        txtInfoAuthor = view.findViewById(R.id.txtInfoAuthor);
        txtInfoCate = view.findViewById(R.id.txtInfoCate);
        txtInfoDate = view.findViewById(R.id.txtInfoDate);
        txtInfoLanguage = view.findViewById(R.id.txtInfoLanguage);
        rViewBookOffer = view.findViewById(R.id.rViewBookOffer);

        db = FirebaseFirestore.getInstance();

    }

    private void createData(String nameCate, RecyclerView rViewBookOffer) {
        SectionDataModel dm = new SectionDataModel();
        dm.setHeaderTitle("Offer");
        ArrayList<String> imgList = new ArrayList<>();
        ArrayList<BooksModel> bookItem = new ArrayList<>();
        db.collection("DanhMucCollection").document("dmForeignLanguage").collection("SachColection")
                //.whereEqualTo("danhMuc", nameCate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BooksModel booksModel;
                                booksModel = document.toObject(BooksModel.class);
                                Log.d("TEST - InfoBookFragment", document.getId() + " => " + document.getData());
                                booksModels.add(booksModel);
                            }
                        } else {
                            Log.d("InfoBookFragment", "Error getting documents: ", task.getException());
                        }
                    }
                });
        for(int i = 0; i < booksModels.size(); i++){
            bookItem.add(booksModels.get(i));
            imgList.add(booksModels.get(i).getBiaSach());
            dm.setAllItemsInSection(bookItem);
            dm.setImgList(imgList);
            sectionDataModel.add(dm);
            Log.d("Notice - InfoFragment","i" );
        }

        MyDataBookAdapter adapter = new MyDataBookAdapter(getActivity(), sectionDataModel);
        rViewBookOffer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rViewBookOffer.setAdapter(adapter);
    }


    public String getIDCategory(String cate) {
        String nameCategory;
        db.collection("DanhMucCollection")
                .whereEqualTo("tenDanhMuc", cate)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                category = document.toObject(CategoriesModel.class);
                                Log.d("ID-Category",document.getId() + " => " + document.getData());

                            }
                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });
        nameCategory = category.getId();
        return nameCategory;
    }
}
