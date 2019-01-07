//package com.example.amikom.uas;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.example.amikom.uas.DB.DataHelper;
//
//public class FavoriteActivity extends AppCompatActivity {
//
//    protected Cursor cursor;
//    DataHelper dbHelper;
//    public String[] no;
//    public String[] judul;
//    public String[] ringkasan;
//    public String[] image;
//    ListView simpleList;
//    public static FavoriteActivity fa;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.favorite);
//
//        fa = this;
//        dbHelper = new DataHelper(this);
//        RefreshList();
//    }
//
//    public void RefreshList(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        cursor = db.rawQuery("SELECT * FROM favorit",null);
//        no = new String[cursor.getCount()];
//        judul = new String[cursor.getCount()];
//        ringkasan = new String[cursor.getCount()];
//        image = new String[cursor.getCount()];
//        cursor.moveToFirst();
//        for (int cc=0; cc < cursor.getCount(); cc++){
//            cursor.moveToPosition(cc);
//            no[cc] = cursor.getString(0).toString();
//            judul[cc] = cursor.getString(1).toString();
//            ringkasan[cc] = cursor.getString(3).toString();
//            image[cc] = cursor.getString(4).toString();
//        }
//        Log.d("Data", "onCreate: " + no + judul + ringkasan + image);
//        simpleList = (ListView) findViewById(R.id.rv_category);
//        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), no, judul,ringkasan,image);
//        simpleList.setAdapter(customAdapter);
//        simpleList.setSelected(true);
//        simpleList.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final String selection =  no[position];
//                String lihatdetail = getString(R.string.lihatdetail);
//                String deletefilm = getString(R.string.hapusfilm);
//                String pilihan = getString(R.string.pilihan);
//                final CharSequence[] dialogitem = {lihatdetail, deletefilm};
//                AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteActivity.this);
//
//                builder.setTitle(pilihan);
//                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int item) {
//                        switch(item){
//                            case 0 :
//                                Intent i = new Intent(getApplicationContext(), FavoriteDetail.class);
//                                i.putExtra("no", selection);
//                                startActivity(i);
//                                break;
//                            case 1 :
//                                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                                db.execSQL("delete from favorit where no = '"+selection+"'");
//                                RefreshList();
//                                break;
//
//                        }
//                    }
//                });
//                builder.create().show();
//            }});
//        ((CustomAdapter)simpleList.getAdapter()).notifyDataSetInvalidated();
//    }
//}
