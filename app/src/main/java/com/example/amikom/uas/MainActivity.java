package com.example.amikom.uas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.amikom.uas.API.ApiService;
import com.example.amikom.uas.API.Server;
import com.example.amikom.uas.Adapter.NewsAdapter;
import com.example.amikom.uas.Category.Entertainment;
import com.example.amikom.uas.Category.Sports;
import com.example.amikom.uas.Category.Technology;
import com.example.amikom.uas.Entity.News;
import com.example.amikom.uas.Entity.ResponseNews;


//tambahan
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView news;
    private NewsAdapter adapter;
    List<News> list = new ArrayList<>();
    ProgressDialog loading;
    ApiService api;
    private static final int TIME_LIMIT = 1800;
    private static long backPressed;

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        news = findViewById(R.id.news);
        api = Server.getApiService();
        adapter = new NewsAdapter(MainActivity.this, list);

        news.setHasFixedSize(true);
        news.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        news.setAdapter(adapter);
        refresh();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


/*        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.bottom_navigation_items);
        fragmentManager = getSupportFragmentManager();

        //Untuk inisialisasi fragment pertama kali
        fragmentManager.beginTransaction().replace(R.id.main_container, new FragmentBuku()).commit();

        //Memberikan listener saat menu item di bottom navigation diklik
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_buku:
                        Intent enter = new Intent(MainActivity.this, Entertainment.class);
                        startActivity(enter);
                    case R.id.sports:
                        Intent sport = new Intent(MainActivity.this, Sports.class);
                        startActivity(sport);
                    case R.id.technology:
                        fragment = new FragmentFilm(MainActivity.this, Sports.class);
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });*/
    }


    public void refresh() {
        loading = new ProgressDialog(MainActivity.this);
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        showDialog();
        api.getListAllNews("id", BuildConfig.NEWS_API_TOKEN).enqueue(new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.isSuccessful()){
                    hideDialog();
                    list = response.body().getNewsList();
                    news.setAdapter(new NewsAdapter(MainActivity.this, list));
                    adapter.notifyDataSetChanged();
                } else {
                    hideDialog();
                    Toast.makeText(MainActivity.this, "Gagal mengambil data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                hideDialog();
                Toast.makeText(MainActivity.this, "Gagal menyambung ke internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        if (!loading.isShowing())
            loading.show();
    }

    private void hideDialog() {
        if (loading.isShowing())
            loading.dismiss();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (TIME_LIMIT + backPressed > System.currentTimeMillis()) {
                // super.onBackPressed();
                moveTaskToBack(true);
            } else {
                Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
            }
            backPressed = System.currentTimeMillis();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Anda yakin ingin keluar ?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        moveTaskToBack(true);
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.entertainment) {
            Intent enter = new Intent(MainActivity.this, Entertainment.class);
            startActivity(enter);
        } else if (id == R.id.sports) {
            Intent sport = new Intent(MainActivity.this, Sports.class);
            startActivity(sport);
        } else if (id == R.id.technology) {
            Intent tech = new Intent(MainActivity.this, Technology.class);
            startActivity(tech);
        /*} else if (id == R.id.favorite) {
            Intent fav = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(fav);*/
        } else if (id == R.id.about) {
            Intent about = new Intent(MainActivity.this, About.class);
            startActivity(about);
        }
         else if (id == R.id.bahasa) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        } else if (id == R.id.alarm) {
            Intent alarm = new Intent(MainActivity.this, AlarmActivity.class);
            startActivity(alarm);
        }

        else if (id == R.id.logout) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
