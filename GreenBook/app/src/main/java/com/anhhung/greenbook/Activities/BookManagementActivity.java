package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.anhhung.greenbook.Adapters.BookManageAdapter;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.CategoriesModel;
import com.anhhung.greenbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookManagementActivity extends AppCompatActivity {

    private BookManageAdapter bookManageAdapter;
    private RecyclerView rViewBookManage;
    private LinearLayoutManager layoutManager;
    private Toolbar actionToolbarBookManagement;

    private boolean loadBookSuccess = false;
    private List<BooksModel> booksModels = new ArrayList<>();
    private List<BooksModel> booksModelsFindBook = new ArrayList<>();
    private List<CategoriesModel> categoriesModels = new ArrayList<>();
    FirebaseFirestore db;

    private MyCallback myCallback;
    private MyCallbackCategories myCallbackCategories;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_management);
        addControls();

        addEvents();
    }



    private void addControls() {
        rViewBookManage = findViewById(R.id.rViewBookManage);
        rViewBookManage.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rViewBookManage.setLayoutManager(layoutManager);
        actionToolbarBookManagement = findViewById(R.id.actionToolbarBookManagement);
        actionToolbarBookManagement.setTitle("Book Management");
        db = FirebaseFirestore.getInstance();
        booksModels.clear();
        rViewBookManage.removeAllViews();
        categoriesModels.clear();
        readData2(new MyCallbackCategories() {
            @Override
            public void onCallback(final List<CategoriesModel> categoriesModels) {
                readData(new MyCallback() {
                    @Override
                    public void onCallback(List<BooksModel> abooksModels) {
                        booksModels = abooksModels;
                        createDataListView(rViewBookManage,booksModels);
                        booksModelsFindBook.addAll(booksModels);
                        loadBookSuccess = true;
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_user_menu,menu);
        MenuItem item = menu.findItem(R.id.mnuSeachUser);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                booksModels.clear();
                rViewBookManage.removeAllViews();
                categoriesModels.clear();
                readData2(new MyCallbackCategories() {
                    @Override
                    public void onCallback(final List<CategoriesModel> categoriesModels) {
                        readData(new MyCallback() {
                            @Override
                            public void onCallback(List<BooksModel> abooksModels) {
                                booksModels = abooksModels;
                                createDataListView(rViewBookManage,booksModels);
                                booksModelsFindBook.addAll(booksModels);
                                loadBookSuccess = true;
                            }
                        });
                    }
                });
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(loadBookSuccess == true){
                    newText = newText.toLowerCase(Locale.getDefault());
                    booksModels.clear();
                    if (newText.length() == 0) {
                    } else {
                        for (BooksModel wp : booksModelsFindBook ) {
                            if (wp.getTenSach().toLowerCase(Locale.getDefault()).contains(newText)) {
                                booksModels.add(wp);
                            }
                        }
                    }
                    bookManageAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void addEvents() {
        //Back toolbar
        setSupportActionBar(actionToolbarBookManagement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //Find Book
    public interface MyCallback {
        void onCallback(List<BooksModel> booksModels);
    }

    public interface MyCallbackCategories {
        void onCallback(List<CategoriesModel> categoriesModels);
    }

    private void getAllCategoriesName() {
        try {
            db.collection("DanhMucCollection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    CategoriesModel categoriesModel;
                                    categoriesModel = document.toObject(CategoriesModel.class);
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    categoriesModels.add(categoriesModel);
                                }
                                myCallbackCategories.onCallback(categoriesModels);
                            } else {
                                //Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d("ERR", e.toString());
        }
    }

    public void getAllDocumentsInDanhMucCollection() {
        for (int i = 0; i < categoriesModels.size(); i++) {
            try {
                db.collection("DanhMucCollection").document(categoriesModels.get(i).getId()).collection("SachColection")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        BooksModel booksModel;
                                        booksModel = document.toObject(BooksModel.class);
                                        //Log.d(TAG, document.getId() + " => " + document.getData());
                                        if(booksModel.getTrangThai()==true){
                                            booksModels.add(booksModel);
                                        }

                                    }
                                    count++;
                                    if (count - 1 == categoriesModels.size() - 1) {
                                        myCallback.onCallback(booksModels);
                                        count = 0;
                                    }
                                } else {
                                    //Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } catch (Exception e) {
                Log.d("ERR", e.toString());
            }
        }

    }

    public void readData2(MyCallbackCategories myCallbackCategories) {
        this.myCallbackCategories = myCallbackCategories;
        getAllCategoriesName();
    }

    public void readData(MyCallback myCallback) {
        this.myCallback = myCallback;
        getAllDocumentsInDanhMucCollection();
    }
    private void createDataListView(RecyclerView recyclerView, List<BooksModel> booksModels){
        bookManageAdapter = new BookManageAdapter(this,booksModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(bookManageAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
