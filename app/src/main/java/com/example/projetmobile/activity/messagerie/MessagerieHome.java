package com.example.projetmobile.activity.messagerie;
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
import android.widget.Button;
import android.widget.TextView;

import com.example.projetmobile.R;
import com.example.projetmobile.activity.PlanDeFormation.ModulesActivity2;
import com.example.projetmobile.activity.aboutUs.About;
import com.example.projetmobile.activity.authentication.LoginActivity;
import com.example.projetmobile.activity.authentication.WelcomeFragment;
import com.example.projetmobile.activity.emploi.YearActivity;
import com.example.projetmobile.activity.evenement.Event;
import com.example.projetmobile.activity.pedagogie.Absence;
import com.example.projetmobile.activity.users.SettingsActivity;
import com.example.projetmobile.model.Users;
import com.google.android.material.navigation.NavigationView;

public class MessagerieHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button refreshButton;
    private Fragment currentFragment;

    private String mode = "messageNewReceived"; //messageSent or messageReceived

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagerie_home);

        refreshButton = (Button) findViewById(R.id.refresh_message_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });


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
    public void onResume()
    {
        super.onResume();
        refresh();
    }

    public String getMode() {
        return mode;
    }

    public void refresh() {
        this.currentFragment = MessageFragment.newInstance();
        this.startTransactionFragment(this.currentFragment);
        switch (mode){
            case "messageReceived" :
                this.navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case "messageSent" :
                this.navigationView.getMenu().getItem(2).setChecked(true);
                break;
            default:
                this.navigationView.getMenu().getItem(0).setChecked(true);
                break;
        }
    }

    public void setMessagesNumber(int nb){
        String title = "Nouveaux messages";
        switch (mode){
            case "messageSent" :
                title = "Boite d'envoie";
                break;
            case "messageReceived" :
                title = "Boite de réception";
                break;
        }
        this.toolbar.setTitle(title+" ("+nb+")");
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.nav_new_received_message :
                this.showNewReceivedFragment();
                this.navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case R.id.nav_received_message :
                this.showReceivedFragment();
                this.navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case R.id.nav_sent_message :
                this.showSentFragment();
                this.navigationView.getMenu().getItem(2).setChecked(true);
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
        this.toolbar = (Toolbar) findViewById(R.id.activity_message_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_message_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_message_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    private void showNewReceivedFragment(){
        this.mode = "messageNewReceived";
        this.toolbar.setTitle("Nouveaux messages");
        this.currentFragment = MessageFragment.newInstance();
        this.startTransactionFragment(this.currentFragment);
    }
    private void showReceivedFragment() {
        this.mode = "messageReceived";
        this.toolbar.setTitle("Boite de réception");
        this.currentFragment = MessageFragment.newInstance();
        this.startTransactionFragment(this.currentFragment);
    }

    private void showSentFragment() {
        this.mode = "messageSent";
        this.toolbar.setTitle("Boite d'envoie");
        this.currentFragment = MessageFragment.newInstance();
        this.startTransactionFragment(this.currentFragment);
    }

    private void startTransactionFragment(Fragment fragment){
        for (int i=0; i<3; i++)
            this.navigationView.getMenu().getItem(i).setChecked(false);
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_message_frame_layout, fragment).commit();
        }
    }

    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_message_frame_layout);
        if (visibleFragment == null){
            // 1.1 - Show News Fragment
            this.showNewReceivedFragment();
            // 1.2 - Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }
}
