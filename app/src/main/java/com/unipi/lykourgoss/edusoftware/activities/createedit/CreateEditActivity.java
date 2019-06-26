package com.unipi.lykourgoss.edusoftware.activities.createedit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.unipi.lykourgoss.edusoftware.R;

public abstract class CreateEditActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.EXTRA_TITLE";
    public static final String EXTRA_INDEX =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.EXTRA_INDEX";
    public static final String EXTRA_DESCRIPTION =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.EXTRA_DESCRIPTION";
    public static final String EXTRA_CHILD_COUNT =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.EXTRA_CHILD_COUNT";
    public static final String EXTRA_PARENT_ID =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.EXTRA_PARENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_edu_entity_menu, menu);
        return true; //true or false = we either want to display or don't want to display the menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save_edu_entity:
                saveEntity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void saveEntity();
}
