package com.kilicarslan.KLC.MainPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.kilicarslan.KLC.Adaptors.FirebaseAdaptor;
import com.kilicarslan.KLC.MainPages.SideMenu.changePage;
import com.kilicarslan.KLC.R;
import com.kilicarslan.KLC.Services.PreferenceService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private int openCounter=0;
    private PreferenceService pService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        definetions();
        events();
    }

    @SuppressLint("ResourceAsColor")
    protected void definetions() {
        pService = new PreferenceService(getApplicationContext());
        navigationView = findViewById(R.id.navView);
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);

        NavHostFragment Fragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.FragmentNav);
        NavigationUI.setupWithNavController(navigationView,Fragment.getNavController());
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this,drawer,toolbar,0,0);
        drawer.addDrawerListener(toggle);
        drawer.setScrimColor(R.drawable.mngpng);

        toggle.syncState();

        bottomNavigationView = findViewById(R.id.bottomNav); //bttom nav bul
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.FragmentNav); // göstericiyi bul

        NavigationUI.setupWithNavController(bottomNavigationView, //bagla
                navHostFragment.getNavController());

    }

    protected void events() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int req = item.getItemId();
                switch (req) {
                    case R.id.ChangeUsername:
                        startActivity(new Intent(getApplicationContext(),changePage.class));
                        break;

                    case R.id.shutTheProg:
                        System.exit(1);
                        break;

                    case R.id.about:
                        break;

                    case R.id.openOrClose:
                        openCounter++;
                        Log.i("user::",pService.get("id",""));
                        Log.i("opencounter:",String.valueOf(openCounter));
                        assert pService.get("id","").equals("admin");
                        if (pService.get("id","").equals("admin")) {
                            FirebaseAdaptor firebaseAdaptor = new FirebaseAdaptor();
                            if (openCounter%2==0) {
                                pService.pushInt("openorclose",0);
                                firebaseAdaptor.LaundryOpenOrClose(false);
                                item.setTitle(R.string.laundryStateClose);
                                item.setIcon(R.drawable.ic_baseline_lock_24);
                                //pService.connect(); //henüz hazır değil
                            } else {
                                pService.pushInt("openorclose",1);
                                firebaseAdaptor.LaundryOpenOrClose(true);
                                item.setTitle(R.string.laundryStateOpen);
                                item.setIcon(R.drawable.ic_baseline_lock_open_24);
                            }
                        }
                        break;


                }


                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            pService.defaultUserAbout();
            super.onBackPressed();

        }

    }


}