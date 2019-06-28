package com.unipi.lykourgoss.edusoftware;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.unipi.lykourgoss.edusoftware.fragments.LessonsFragment;
import com.unipi.lykourgoss.edusoftware.models.User;
import com.unipi.lykourgoss.edusoftware.viewmodels.CurrentViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private CurrentViewModel currentViewModel;

    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get user info
        currentViewModel = ViewModelProviders.of(this).get(CurrentViewModel.class);
        // todo after SignInActivity (in which we set the User of our currentViewModel)
        currentViewModel.setUser(new User("asdfg", "a@t.ler", "Anna",
                "https://www.greeka.com/photos/dodecanese/leros/hero/leros-island-1920.jpg"));
        User user = currentViewModel.getUser();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // set user info on navigation header of drawer
        View headerView = navigationView.getHeaderView(0);
        ((TextView) headerView.findViewById(R.id.nav_header_user_name)).setText(user.getName());
        ((TextView) headerView.findViewById(R.id.nav_header_user_email)).setText(user.getEmail());
        Picasso.get().load(user.getPhotoUri()).into((ImageView) headerView.findViewById(R.id.nav_header_user_photo));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); // takes care of rotating the hamburger icon

        //checkIfSignedIn();

        if (savedInstanceState == null) {
            currentViewModel.setEditEnabled(false);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LessonsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_all_lessons);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_all_lessons:
                currentViewModel.setEditEnabled(false);
                // todo show all lessons (parentId = null)
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LessonsFragment()).commit();
                break;
            case R.id.nav_my_lessons:
                currentViewModel.setEditEnabled(true);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LessonsFragment()).commit();
                break;
            case R.id.nav_user_settings:
                // todo user setting fragment
                break;
            case R.id.nav_share:
                // todo copy to clipboard Google Drive link of the apk
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                // todo not sure if needed
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START); // START means the drawer is on the left side
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.menu_item_delete_all:
                // will be implemented by fragment that currently is displayed,
                // delete all items that are being displayed
                return false;*/
            case R.id.menu_item_help:
                // help dialog
                // todo add help case: dialog for how to edit, create, delete, delete all, long click for details
                return true;
            case R.id.menu_item_about:
                // todo about dialog
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
