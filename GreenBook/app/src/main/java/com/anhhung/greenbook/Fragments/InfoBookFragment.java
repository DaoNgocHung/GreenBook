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
    MyCallback myCallback;

    TextView txtInfoNXB, txtInfoAuthor, txtInfoCate, txtInfoDate, txtInfoLanguage;
    RecyclerView rViewBookOffer;

    String TAG = "ERROR - InfobookFragment";
    String tenDM = "";

    FirebaseFirestore db;
    List<BooksModel> booksModels = new ArrayList<>();

    CategoriesModel category = new CategoriesModel();
    private String nameCategory;
    ArrayList<SectionDataModel> sectionDataModel = new ArrayList<SectionDataModel>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String NXB = this.getArguments().getString("NXB", " ");
        String tacGia = this.getArguments().getString("tacGia", " ");
        tenDM = this.getArguments().getString("danhMuc", " ");
        String ngonNgu = this.getArguments().getString("ngonNgu", " ");
        String ngayUpload = this.getArguments().getString("ngayUpload");
        String idDM = this.getArguments().getString("idDM");
        View view = inflater.inflate(R.layout.fragment_info_book, null);

//        db.collection("DanhMucCollection").document(idDM)
//                .collection()


        addControls(view);

        txtInfoNXB.setText(NXB);
        txtInfoAuthor.setText(tacGia);
        txtInfoCate.setText(tenDM);
        txtInfoLanguage.setText(ngonNgu);
        txtInfoDate.setText(ngayUpload);

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
        readData(new MyCallback() {
            @Override
            public void onCallback(List<BooksModel> booksModels) {
                createData(rViewBookOffer, booksModels);
            }
        });

    }

    private void createData(RecyclerView rViewBookOffer, List<BooksModel> booksModels) {
        SectionDataModel dm = new SectionDataModel();
        dm.setHeaderTitle("Offer");
        ArrayList<String> imgList = new ArrayList<>();
        ArrayList<BooksModel> bookItem = new ArrayList<>();
        for(int i = 0; i < booksModels.size(); i++){
            bookItem.add(booksModels.get(i));
            imgList.add(booksModels.get(i).getBiaSach());
        }
        dm.setAllItemsInSection(bookItem);
        dm.setImgList(imgList);
        sectionDataModel.add(dm);
        MyDataBookAdapter adapter = new MyDataBookAdapter(getActivity(), sectionDataModel);
        rViewBookOffer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rViewBookOffer.setAdapter(adapter);
    }

    public interface MyCallback {
        void onCallback(List<BooksModel> booksModels);
    }

    public void readData(MyCallback myCallback) {
        this.myCallback = myCallback;
        getAllDocumentsInDanhMucCollectionInfoBookFrag("dmForeignLanguage");
    }
    public String getIDCategory(String cate) {
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
                                nameCategory = category.getId();
                                Log.d(TAG,document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return nameCategory;
    }
    private void getAllDocumentsInDanhMucCollectionInfoBookFrag(String tenDM){
        try{
            db.collection("DanhMucCollection").document(tenDM).collection("SachColection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    BooksModel booksModel = document.toObject(BooksModel.class);
                                    Log.d("TEST - InfoBookFragment", document.getId() + " => " + document.getData());
                                    booksModels.add(booksModel);
                                }
                                myCallback.onCallback(booksModels);
                            } else {
                                Log.d("InfoBookFragment", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }catch(Exception e){
            Log.d(TAG,e.toString());
        }

    }
}
