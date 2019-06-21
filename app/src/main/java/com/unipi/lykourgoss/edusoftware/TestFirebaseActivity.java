/*
package com.unipi.lykourgoss.edusoftware;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.unipi.lykourgoss.edusoftware.viewmodels.LessonsFirebaseViewModel;

public class TestFirebaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firebase);

        // Obtain a new or prior instance of HotStockViewModel from the
        // ViewModelProviders utility class.
        LessonsFirebaseViewModel viewModel = ViewModelProviders.of(this).get(LessonsFirebaseViewModel.class);

        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    // update the UI here with values in the snapshot
                    String ticker = dataSnapshot.child("ticker").getValue(String.class);
                    Toast.makeText(TestFirebaseActivity.this, ticker, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
*/
