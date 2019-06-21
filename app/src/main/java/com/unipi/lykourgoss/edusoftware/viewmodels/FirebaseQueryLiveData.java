package com.unipi.lykourgoss.edusoftware.viewmodels;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 20,June,2019.
 */

public class FirebaseQueryLiveData extends LiveData<DataSnapshot> {

    private static final String LOG_TAG = "FirebaseQueryLiveData";

    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();

    private boolean listenerRemovePending = false;
    private final Handler handler = new Handler();
    private final Runnable removeListener = new Runnable() {
        @Override
        public void run() {
            query.removeEventListener(listener);
            listenerRemovePending = false;
        }
    };

    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        if (listenerRemovePending) { // listener is a about to be removed
            // abort removing listener
            handler.removeCallbacks(removeListener);
        }
        else {
            query.addValueEventListener(listener);
        }
        listenerRemovePending = false;
    }

    /*Here, I'm using a Handler to schedule the removal of the database listener (by posting a
    Runnable callback that performs the removal) on a two second delay after the LiveData becomes
    inactive. If it becomes active again before those two seconds have elapsed, we simply eliminate
    that scheduled work from the Handler, and allow the listener to keep listening.*/
    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending = true;
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }
}
