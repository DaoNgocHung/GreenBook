package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anhhung.greenbook.Models.CollectionMapModel;
import com.anhhung.greenbook.R;

import java.util.List;

public class CustomListViewCMAdapter extends BaseAdapter {
    private Context context;
    private List<CollectionMapModel> collectionMapModelList;
    public CustomListViewCMAdapter(Context context, List<CollectionMapModel> collectionMapModels) {
        this.context = context;
        this.collectionMapModelList = collectionMapModels;
    }
    @Override
    public int getCount() {
        if (collectionMapModelList.size() != 0 && !collectionMapModelList.isEmpty()) {
            return collectionMapModelList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            view = mInflater.inflate(R.layout.custom_listview_collection_map,viewGroup,false);
        }
        TextView txtTenSachCustomList, txtSoLuongSachCustomList, txtTongGiaCustomList;
        txtTenSachCustomList = view.findViewById(R.id.txtTenSachCustomList);
        txtSoLuongSachCustomList = view.findViewById(R.id.txtSoLuongSachCustomList);
        txtTongGiaCustomList = view.findViewById(R.id.txtTongGiaCustomList);
        if (collectionMapModelList != null && !collectionMapModelList.isEmpty()){
            txtTenSachCustomList.setText(collectionMapModelList.get(i).getCollectionMapName());
            txtSoLuongSachCustomList.setText(collectionMapModelList.get(i).getTongSachBan()+"");
            txtTongGiaCustomList.setText(collectionMapModelList.get(i).getTongDoanhThuTien()+"");

        }
        return view;
    }
}
