package com.anhhung.greenbook.Adapters;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Models.BillDetailModel;
import com.anhhung.greenbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class BillAdapter extends FirestoreRecyclerAdapter<BillDetailModel, BillAdapter.BillHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BillAdapter(@NonNull FirestoreRecyclerOptions<BillDetailModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BillHolder holder, int position, @NonNull BillDetailModel model) {
        holder.txtBookTitleBill.setText(model.getBookTitle());
        holder.txtEmailBill.setText(model.getEmail());
        holder.txtPriceBill.setText(String.valueOf(model.getPrice()));
        holder.txtTimePurchaseBill.setText(model.getTimePurchase().toString());
        holder.txtStatusBill.setText(model.getStatus());
        if(holder.txtStatusBill.getText().equals("Transaction Successful")){
            holder.backgroundBill.setBackgroundColor(Color.parseColor("#B3DE81"));
            holder.cardViewBill.setBackgroundColor(Color.parseColor("#B3DE81"));
        } else {
            holder.backgroundBill.setBackgroundColor(Color.parseColor("#ff4646"));
            holder.cardViewBill.setBackgroundColor(Color.parseColor("#ff4646"));
        }
    }

    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_bill,parent,false);
        return new BillHolder(view);
    }

    public class BillHolder extends RecyclerView.ViewHolder {

        TextView txtBookTitleBill, txtEmailBill, txtPriceBill, txtTimePurchaseBill, txtStatusBill;
        LinearLayout backgroundBill;
        CardView cardViewBill;

        public BillHolder(@NonNull View itemView) {
            super(itemView);
            txtBookTitleBill = itemView.findViewById(R.id.txtBookTitleBill);
            txtEmailBill = itemView.findViewById(R.id.txtEmailBill);
            txtPriceBill = itemView.findViewById(R.id.txtPriceBill);
            txtTimePurchaseBill = itemView.findViewById(R.id.txtTimePurchaseBill);
            txtStatusBill = itemView.findViewById(R.id.txtStatusBill);
            backgroundBill = itemView.findViewById(R.id.backgroundBill);
            cardViewBill = itemView.findViewById(R.id.cardViewBill);
        }
    }
}
