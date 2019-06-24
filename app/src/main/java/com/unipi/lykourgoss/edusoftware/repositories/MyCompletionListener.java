package com.unipi.lykourgoss.edusoftware.repositories;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 24,June,2019.
 */

public class MyCompletionListener implements DatabaseReference.CompletionListener {

    private boolean result;

    @Override
    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
        result = databaseError == null;
    }

    public boolean getResult(){
        return result;
    }
}
