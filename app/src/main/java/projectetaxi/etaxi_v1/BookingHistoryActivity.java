package projectetaxi.etaxi_v1;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class BookingHistoryActivity extends AppCompatActivity {

    RecyclerView rvPassenHistory;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<PassengerHistory> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        rvPassenHistory = (RecyclerView) findViewById(R.id.rvPassenHistory);

        layoutManager = new LinearLayoutManager(this);

        rvPassenHistory.setLayoutManager(layoutManager);
        rvPassenHistory.setHasFixedSize(true);

        BackgroundTaskPassengerHistory backgroundTaskPassengerHistory = new BackgroundTaskPassengerHistory(
                BookingHistoryActivity.this);

        arrayList = backgroundTaskPassengerHistory.getList();

        adapter = new PassenHistoryRecyclerAdapter(arrayList);
        rvPassenHistory.setAdapter(adapter);
    }
}
