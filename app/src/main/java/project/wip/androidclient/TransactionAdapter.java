package project.wip.androidclient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private ArrayList<TransactionItem> transactionItems;

    public static class TransactionViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewDay;
        public TextView textViewMonth;
        public TextView textViewName;
        public TextView textViewReference;
        public TextView textViewAmount;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDay = itemView.findViewById(R.id.textViewDay);
            textViewMonth = itemView.findViewById(R.id.textViewMonth);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewReference = itemView.findViewById(R.id.textViewReference);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
        }
    }

    public TransactionAdapter(ArrayList<TransactionItem> transactionItems){
        this.transactionItems = transactionItems;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transaction, viewGroup, false);
        TransactionViewHolder transactionViewHolder = new TransactionViewHolder(v);
        return transactionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder transactionViewHolder, int i) {
        TransactionItem currentItem = transactionItems.get(i);
        transactionViewHolder.textViewDay.setText(currentItem.getDay());
        transactionViewHolder.textViewMonth.setText(currentItem.getMonth());
        transactionViewHolder.textViewName.setText(currentItem.getName());
        transactionViewHolder.textViewReference.setText(currentItem.getReference());
        transactionViewHolder.textViewAmount.setText(currentItem.getAmount());
    }

    @Override
    public int getItemCount() {
        return transactionItems.size();
    }
}
