package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Adapters.CustomListViewCMAdapter;
import com.anhhung.greenbook.Models.CollectionMapModel;
import com.anhhung.greenbook.R;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CollectionMapActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private CombinedChart mChart;
    private FirebaseFirestore db;
    private Calendar c = Calendar.getInstance();
    private String year = c.get(Calendar.YEAR)+"";
    private String month = c.get(Calendar.MONTH)+1+"";
    private Spinner spMonthCM;
    private List<CollectionMapModel> collectionMapModels = new ArrayList<>();
    private ArrayList<Integer> listSachBan = new ArrayList<>(12);
    private int sachCount = 0;
    private double sachMoney = 0;
    private int i = 1;
    private int callback = 0;
    private MyCallback myCallback;
    private int thangThongKe;
    private TextView txtYearIncomeCM, txtMonthIncomeCM;
    private ListView listViewCM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_map);
        addControls();
        addEvents();
    }

    private void addEvents() {
        spMonthCM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(spMonthCM.getSelectedItem().toString()!= "Year Statistics"){
                    initListSachBan(listSachBan);
                    year = spMonthCM.getSelectedItem().toString();
                    Toast.makeText(CollectionMapActivity.this,year +"", Toast.LENGTH_LONG).show();
                    readData(new MyCallback() {
                        @Override
                        public void onCallback(List<Integer> lisSachBan, double sachMoneyRD, List<CollectionMapModel> collectionMapModelList) {

                            showChart(listSachBan);
                            txtYearIncomeCM.setText(sachMoneyRD+"");
                            collectionMapModels = collectionMapModelList;
                            sachMoney =0;

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addControls() {
        initListSachBan(listSachBan);
        txtYearIncomeCM = findViewById(R.id.txtYearIncomeCM);
        txtMonthIncomeCM = findViewById(R.id.txtMonthIncomeCM);
        listViewCM = findViewById(R.id.listViewCM);
        spMonthCM = findViewById(R.id.spMonthCM);
        db = FirebaseFirestore.getInstance();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,addMonthData());
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spMonthCM.setAdapter(adapter);
        if(spMonthCM.getSelectedItem().toString()!= "Year Statistics"){
            initListSachBan(listSachBan);
            year = spMonthCM.getSelectedItem().toString();
            readData(new MyCallback() {
                @Override
                public void onCallback(List<Integer> lisSachBan, double sachMoneyRD, List<CollectionMapModel> collectionMapModelList) {
                    showChart(listSachBan);
                    txtYearIncomeCM.setText(sachMoneyRD+"");
                    collectionMapModels = collectionMapModelList;
                    sachMoney = 0;
                }
            });

        }
    }

    @Override
    public void onValueSelected(Entry e, final Highlight h) {
        Toast.makeText(this, "Sold book nums: "
                + e.getY(), Toast.LENGTH_SHORT).show();
        setCustomListCMData(collectionMapModels, ((int) h.getX()+1));
    }

    @Override
    public void onNothingSelected() {

    }

    private static DataSet dataChart(ArrayList<Integer> listSachBan) {
        LineData d = new LineData();
        int[] data = new int[listSachBan.size()];
        for (int i=0; i < data.length; i++)
        {
            data[i] = listSachBan.get(i).intValue();
        }
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 12; index++) {
            entries.add(new Entry(index, data[index]));
        }
        LineDataSet set = new LineDataSet(entries, "Request Ots approved");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void getArraySachBan(){
        for(i = 1; i<=12; i++){
            db.collection("CollectionMap").document((year)).collection(i+"")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()&& !task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    CollectionMapModel collectionMapModel = document.toObject(CollectionMapModel.class);
                                    sachCount += collectionMapModel.getTongSachBan();
                                    sachMoney += collectionMapModel.getTongDoanhThuTien();
                                    thangThongKe = collectionMapModel.getThangThongKe()-1;
                                    collectionMapModels.add(collectionMapModel);
                                }
                                listSachBan.set(thangThongKe,sachCount);
                            } else {
                                Log.d("ERROR", "Error getting documents: ", task.getException());
                            }
                            sachCount = 0;
                            callback+=1;
                            if(callback == 12){
                                myCallback.onCallback(listSachBan, sachMoney, collectionMapModels);
                                callback=0;
                            }
                        }
                    });

        }


    }
    public interface MyCallback {
        void onCallback(List<Integer> lisSachBan, double sachMoney, List<CollectionMapModel> collectionMapModelList);
    }
    public void readData(MyCallback myCallback) {
        this.myCallback = myCallback;
        getArraySachBan();
    }
    public ArrayList<String> addMonthData(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Year Statistics");
        list.add("2019");
        list.add("2020");
        return list;
    }
    private void showChart(ArrayList<Integer> listSachBan){
        mChart = findViewById(R.id.combinedChart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        final List<String> xLabel = new ArrayList<>();
        xLabel.add("Jan");
        xLabel.add("Feb");
        xLabel.add("Mar");
        xLabel.add("Apr");
        xLabel.add("May");
        xLabel.add("Jun");
        xLabel.add("Jul");
        xLabel.add("Aug");
        xLabel.add("Sep");
        xLabel.add("Oct");
        xLabel.add("Nov");
        xLabel.add("Dec");
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart(listSachBan));

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }
    private void initListSachBan(ArrayList<Integer> listSachBan){
        for(int j = 0; j< 12 ; j++){
            listSachBan.add(j,0);
        }
    }
    private void setCustomListCMData(List<CollectionMapModel> collectionMapModelList, int thangThongKe){
        List<CollectionMapModel> collectionMapModelCM = new ArrayList<>();
        int tongGia = 0;
        for(int i =0; i< collectionMapModelList.size();i++){
            if(collectionMapModelList.get(i).getThangThongKe()==thangThongKe){
                collectionMapModelCM.add(collectionMapModelList.get(i));
                tongGia+=collectionMapModelList.get(i).getTongDoanhThuTien();
            }
        }
        Log.d("INFO", collectionMapModelCM.toString());
        CustomListViewCMAdapter customListViewCMAdapter = new CustomListViewCMAdapter(this,collectionMapModelCM);
        txtMonthIncomeCM.setText(tongGia+"");
        listViewCM.setAdapter(customListViewCMAdapter);

    }

}
