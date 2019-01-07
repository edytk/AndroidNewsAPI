//package com.example.amikom.uas;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.example.amikom.uas.DB.DataHelper;
//
//public class FavoriteDetail {
//    protected Cursor cursor;
//    private TextView tvJudul;
//    private TextView tvDeskripsi;
//    private  TextView tvTgl;
//    private  TextView tvSumber;
//    private  TextView tvPenulis;
//    DataHelper dbHelper;
//    private ImageView image;
//    private TextView no;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_favorite_detail);
//
//        dbHelper = new DataHelper(this);
//        tvJudul = (TextView) findViewById(R.id.tvJudul);
//        tvDeskripsi = (TextView) findViewById(R.id.tvDeskripsi);
//        tvTgl = (TextView) findViewById(R.id.tvTgl);
//        tvSumber = (TextView) findViewById(R.id.tvSumber);
//        tvPenulis = (TextView) findViewById(R.id.tvPenulis);
//
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        cursor = db.rawQuery("SELECT * FROM favorit WHERE no = '" +
//                getIntent().getStringExtra("no") + "'", null);
//        cursor.moveToFirst();
//        if (cursor.getCount()>0)
//        {
//            cursor.moveToPosition(0);
//            no.setText(cursor.getString(0).toString());
//
//            Glide.with(getApplicationContext())
//                    .load(cursor.getString(4)).override(350,350).into(image);
//
//            tvJudul.setText(cursor.getString(1).toString());
//            tvDeskripsi.setText(cursor.getString(2).toString());
//            tvTgl.setText(cursor.getString(3).toString());
//        }
//
//    }
//}
