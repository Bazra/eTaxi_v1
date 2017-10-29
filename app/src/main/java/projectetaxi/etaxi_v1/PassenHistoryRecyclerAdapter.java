package projectetaxi.etaxi_v1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ashim Bazracharya on 10/21/2017.
 */

public class PassenHistoryRecyclerAdapter extends
        RecyclerView.Adapter<PassenHistoryRecyclerAdapter.HistoryViewHolder>{

    ArrayList<PassengerHistory> arrayList = new ArrayList<>();

    public PassenHistoryRecyclerAdapter(ArrayList<PassengerHistory> arrayList) {

        this.arrayList = arrayList;

    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_passen_history,
                parent, false);
        HistoryViewHolder viewHolder = new HistoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

        holder.tvPassenDate.setText(arrayList.get(position).getBookingDate());
        holder.tvSourceLat.setText(arrayList.get(position).getSourceLat());
        holder.tvSourceLng.setText(arrayList.get(position).getSourceLng());
        holder.tvDestinationLat.setText(arrayList.get(position).getDestLat());
        holder.tvDestinationLng.setText(arrayList.get(position).getDestLng());
        holder.tvPassenAmount.setText(arrayList.get(position).getPassenAmount());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvPassenDate, tvSourceLat, tvSourceLng, tvDestinationLat, tvDestinationLng, tvPassenAmount;

        public HistoryViewHolder(View itemView) {

            super(itemView);

            tvPassenDate = (TextView)itemView.findViewById(R.id.tvPassDate);
            tvSourceLat = (TextView)itemView.findViewById(R.id.tvSourceLat);
            tvSourceLng = (TextView)itemView.findViewById(R.id.tvSourceLng);
            tvDestinationLat = (TextView)itemView.findViewById(R.id.tvDestinationLat);
            tvDestinationLng = (TextView)itemView.findViewById(R.id.tvDestinationLat);
            tvPassenAmount = (TextView)itemView.findViewById(R.id.tvPassAmount);
        }
    }
}