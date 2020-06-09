package com.example.projetmobile.activity.authentication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.projetmobile.R;
import com.example.projetmobile.activity.PlanDeFormation.MainActivity1;
import com.example.projetmobile.activity.PlanDeFormation.ModulesActivity;
import com.example.projetmobile.activity.emploi.YearActivity;
import com.example.projetmobile.activity.evenement.Event;
import com.example.projetmobile.activity.messagerie.MessagerieHome;
import com.example.projetmobile.activity.pedagogie.Cours;
import com.example.projetmobile.activity.pedagogie.Absence;
import com.example.projetmobile.activity.users.SettingsActivity;
import com.example.projetmobile.model.Users;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Users user;


    private Fragment welcomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = Users.getCurrentUser();

        //Configure all views
        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.showFirstFragment();
    }

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home :
                this.showHomeFragment();
                break;
            case R.id.nav_event :
                this.showEventPage();
                break;
            case R.id.nav_timetable :
                this.showEmploiPage();
                break;
            case R.id.nav_plandeformation :
                this.showPlandeformation();
                break;
            case R.id.nav_cours :
                this.showCoursPage();
                break;
            case R.id.nav_note :
                this.showNotePage();
                break;
            case R.id.nav_settings :
                this.showSettingPage();
                break;
            case R.id.nav_messagerie:
                this.showMessageriePage();
                break;
            case R.id.nav_logout:
                this.logoutUser();
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set user name in drawer header
        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.user_profile_name);
        if(user != null && userName != null) userName.setText(user.displayName()+"\n("+user.getRole()+")");
    }


    private void showHomeFragment(){
        if (this.welcomeFragment == null) this.welcomeFragment = WelcomeFragment.newInstance();
        this.startTransactionFragment(this.welcomeFragment);
    }
    private void showEmploiPage() {
        Intent intent = new Intent(getApplicationContext(), YearActivity.class);
        startActivity(intent);
        this.navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void showPlandeformation() {
        Intent intent = new Intent(getApplicationContext(), ModulesActivity.class);
        startActivity(intent);
        this.navigationView.getMenu().getItem(0).setChecked(true);
    }


    private void showMessageriePage(){
        Intent intent = new Intent(getApplicationContext(), MessagerieHome.class);
        startActivity(intent);
        this.navigationView.getMenu().getItem(0).setChecked(true);
    }
    private void showEventPage(){
        Intent intent = new Intent(getApplicationContext(), Event.class);
        startActivity(intent);
        this.navigationView.getMenu().getItem(0).setChecked(true);
    }
    private void showCoursPage(){
        Intent intent = new Intent(getApplicationContext(), Cours.class);
        startActivity(intent);
        this.navigationView.getMenu().getItem(0).setChecked(true);
    }
    private void showNotePage(){
        Intent intent = new Intent(getApplicationContext(), Absence.class);
        startActivity(intent);
        this.navigationView.getMenu().getItem(0).setChecked(true);
    }
    private void showSettingPage(){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
        this.navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Users.setCurrentUser(null);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("email", user.getEmail());
        startActivity(intent);
    }



    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            // 1.1 - Show News Fragment
            this.showHomeFragment();
            // 1.2 - Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }
}