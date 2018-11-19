package project.wip.androidclient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private ArrayList<TransactionItem> mTransactionItems;

    public static class TransactionViewHolder extends RecyclerView.ViewHolder{
        public TextView textView1;
        public TextView textView2;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }

    public TransactionAdapter(ArrayList<TransactionItem> transactionItems){
        this.mTransactionItems = transactionItems;
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
        TransactionItem currentItem = mTransactionItems.get(i);
        transactionViewHolder.textView1.setText(currentItem.getmText1());
        transactionViewHolder.textView2.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return mTransactionItems.size();
    }
}
