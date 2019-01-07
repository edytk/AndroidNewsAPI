package com.example.amikom.uas;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.amikom.uas.DB.DataHelper;

public class DetailActivity extends AppCompatActivity {

    String img, judul, deskripsi, tgl, penulis, sumber;
    ImageView tvImg;
    TextView tvJudul, tvDeskripsi, tvTgl, tvPenulis, tvSumber;

    private ImageButton notification;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        img = getIntent().getStringExtra("imgNews");
        judul = getIntent().getStringExtra("titleNews");
        deskripsi = getIntent().getStringExtra("contentNews");
        tgl = getIntent().getStringExtra("dateNews");
        penulis = getIntent().getStringExtra("authorNews");
        sumber = getIntent().getStringExtra("sourceNews");

        bindView();

        Glide.with(getApplicationContext())
                .load(img).into(tvImg);
        tvJudul.setText(judul);
        tvDeskripsi.setText(deskripsi);
        tvTgl.setText(tgl);
        tvSumber.setText("Lihat Lebih Lengkap");
        tvPenulis.setText(penulis);

        if(getSupportActionBar() != null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //tambha
        /*notification = (ImageButton) findViewById(R.id.notif);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("insert into favorit(judul, tgl, ringkasan, image) values('" +
                        tvJudul.getText().toString() + "','" +
                        tvDeskripsi.getText().toString() + "','" +
                        tvTgl.getText().toString() + "','" +
                        tvSumber.getText().toString() + "','" +
                        tvPenulis.getText().toString() + "','" +"')");
                String toast = getString(R.string.toastberhasil);
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
                showNotif();
            }
        });*/

    }

    public void sumber (View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sumber)));
    }

    public void bindView(){
        tvImg = findViewById(R.id.img);
        tvJudul = findViewById(R.id.judul);
        tvDeskripsi = findViewById(R.id.deskripsi);
        tvPenulis = findViewById(R.id.penulis);
        tvTgl = findViewById(R.id.tgl);
        tvSumber = findViewById(R.id.sumber);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /*private void showNotif() {
        NotificationManager notificationManager;

        Intent mIntent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fromnotif", "notif");
        mIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this , FavoriteActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setColor(getResources().getColor(R.color.colorPrimary));
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.favorite_i)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.favorite_i))
                .setTicker("Filem Favorit di tambahkan ")
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("Filemku")
                .setContentText("Filem Favorit telah di tambahkan ");


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(115, builder.build());

    }*/

}
