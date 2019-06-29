package com.unipi.lykourgoss.edusoftware.activities.createedit;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.unipi.lykourgoss.edusoftware.R;

public abstract class CreateEditActivity extends AppCompatActivity {

    // todo remove -> protected CurrentViewModel currentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
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
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void saveEntity();
}
