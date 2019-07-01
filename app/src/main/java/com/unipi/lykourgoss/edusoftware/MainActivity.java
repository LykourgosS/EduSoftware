package com.unipi.lykourgoss.edusoftware;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipi.lykourgoss.edusoftware.fragments.LessonsFragment;
import com.unipi.lykourgoss.edusoftware.models.User;
import com.unipi.lykourgoss.edusoftware.viewmodels.CurrentViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private NavigationView navigationView;

    private CurrentViewModel currentViewModel;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); // takes care of rotating the hamburger icon

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }

        currentViewModel = ViewModelProviders.of(this).get(CurrentViewModel.class);

        String uid = mAuth.getCurrentUser().getUid();
        String userEmail = mAuth.getCurrentUser().getEmail();

        currentViewModel.setUser(new User(uid, userEmail));

        // set user info on navigation header of drawer
        View headerView = navigationView.getHeaderView(0);
        /*((TextView) headerView.findViewById(R.id.nav_header_user_name)).setText(currentViewModel.getUser().getValue().getName());*/
        ((TextView) headerView.findViewById(R.id.nav_header_user_email)).setText(currentViewModel.getUser().getValue().getEmail());
        /*Picasso.get().load(user.getPhotoUri()).into((ImageView) headerView.findViewById(R.id.nav_header_user_photo));*/

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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LessonsFragment()).commit();
                break;
            case R.id.nav_my_lessons:
                currentViewModel.setEditEnabled(true);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LessonsFragment()).commit();
                break;
            case R.id.nav_sign_out:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this, SignInActivity.class));
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
            //todo fragment navigation
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
