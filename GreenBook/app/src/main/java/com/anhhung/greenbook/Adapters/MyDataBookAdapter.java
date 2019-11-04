package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Activities.MoreBookActivity;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.SectionDataModel;
import com.anhhung.greenbook.R;

import java.util.ArrayList;

public class MyDataBookAdapter extends RecyclerView.Adapter<MyDataBookAdapter.ItemRowHolder> {
    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    //private BooksModel booksModel = new BooksModel();

    public MyDataBookAdapter(Context context, ArrayList<SectionDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_book, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllBooksInSection();
        ArrayList singleSectionImages = dataList.get(i).getImgList();
        itemRowHolder.itemTitle.setText(sectionName);

        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, singleSectionImages );

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);

        itemRowHolder.imgbtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MoreBookActivity.class);
                intent.putExtra("danhMuc",sectionName);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        private TextView itemTitle;
        private RecyclerView recycler_view_list;
        private ImageButton imgbtnMore;

        private ItemRowHolder(View view) {
            super(view);
            this.itemTitle = view.findViewById(R.id.itemTitle);
            this.recycler_view_list = view.findViewById(R.id.recycler_view_list);
            this.imgbtnMore= view.findViewById(R.id.imgbtnMore);
        }
    }
}
