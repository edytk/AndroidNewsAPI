package com.example.amikom.uas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amikom.uas.DB.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;
    EditText mCnfPassword;
    //    EditText mName;
    Button mBtnRegister;
    TextView mLogin;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

//        mName = (EditText) findViewById(R.id.name);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mCnfPassword = (EditText) findViewById(R.id.cnf_password);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mLogin = (TextView) findViewById(R.id.login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String name = mName.getText().toString().trim();
                String user = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String cnf_password = mCnfPassword.getText().toString().trim();

                if (password.equals(cnf_password)){
                    long val = db.addUser(user,password);
                    if (val > 0){
                        Toast.makeText(RegisterActivity.this,"Sukses Registered!", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    } else{
                        Toast.makeText(RegisterActivity.this,"Register Failed!", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Toast.makeText(RegisterActivity.this,"Password not matching!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
