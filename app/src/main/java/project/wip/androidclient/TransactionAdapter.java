package project.wip.androidclient;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class implements the construction of the activity_main.
 * @author Sebastian Rieck
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private ArrayList<TransactionItem> transactionItems;

    public static class TransactionViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewDay;
        public TextView textViewMonth;
        public TextView textViewName;
        public TextView textViewReference;
        public TextView textViewAmount;
        private CardView cardView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDay = itemView.findViewById(R.id.textViewDay);
            textViewMonth = itemView.findViewById(R.id.textViewMonth);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewReference = itemView.findViewById(R.id.textViewReference);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public TransactionAdapter(ArrayList<TransactionItem> transactionItems){
        this.transactionItems = transactionItems;
    }

    /**
     * This method is called when the adapter is created and is used to initialize the ViewHolder
     * @param viewGroup The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param i The view type of the new View.
     * @return the layout id of the TransactionViewHolder
     * @author Sebastian Rieck
     */
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transaction, viewGroup, false);
        return new TransactionViewHolder(v);
    }

    /**
     * This method is called for each ViewHolder to bind it to the adapter. This is where the data will be passed to the ViewHolder.
     * @param transactionViewHolder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param i The position of the item within the adapter's data set.
     * @author Sebastian Rieck
     */
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder transactionViewHolder, int i) {
        TransactionItem currentItem = transactionItems.get(i);
        transactionViewHolder.textViewDay.setText(currentItem.getDay());
        transactionViewHolder.textViewMonth.setText(currentItem.getMonth());
        transactionViewHolder.textViewName.setText(currentItem.getName());
        transactionViewHolder.textViewName.setSelected(true);
        transactionViewHolder.textViewReference.setText(currentItem.getReference());
        transactionViewHolder.textViewReference.setSelected(true);
        transactionViewHolder.textViewAmount.setText(currentItem.getAmount());
        transactionViewHolder.textViewAmount.setSelected(true);

        if(currentItem.getAmount().contains("-")){
            transactionViewHolder.cardView.setCardBackgroundColor(Color.parseColor("#ffefef"));
        } else {
            transactionViewHolder.cardView.setCardBackgroundColor(Color.parseColor("#effff0"));
        }
    }

    /**
     * @return the size of the collection
     * @author Sebastian Rieck
     */
    @Override
    public int getItemCount() {
        return transactionItems.size();
    }
}
