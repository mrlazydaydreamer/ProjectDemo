package com.example.hrish.projectdemo;

import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {
    private ProgressDialog pg;
    TextView textView, textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pg = new ProgressDialog(this);
        textView = findViewById(R.id.editText);
        textView1 = findViewById(R.id.editText3);
    }

    public void loginClick(View v){

        try {
            loginAction(textView.getText().toString(), textView1.getText().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void loginAction(String username, String password) throws InterruptedException {
        if(username==null||password==null)
            return;
        //Now Encrypting the password using md5
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] message = md5.digest(password.getBytes());
            BigInteger num = new BigInteger(1,message);
            StringBuilder passwordBuilder = new StringBuilder(num.toString(16));
            while (passwordBuilder.length()<32){
                passwordBuilder.insert(0, "0");
            }
            password = passwordBuilder.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error:L017",Toast.LENGTH_LONG).show();
            return;
        }
        System.out.print(password);
        new LoginProcess(new LoginProcess.LoginResult() {
            @Override
            public void loginSuccessful() {
                pg.dismiss();
                textView.setText("");
                textView1.setText("");
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
            }

            @Override
            public void loginFailed() {
                pg.dismiss();
                textView1.setText("");
                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
            }
        }).execute(username,password);
        pg.setMessage("Logging In...");
        pg.show();
    }
}

