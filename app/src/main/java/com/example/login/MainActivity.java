package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private final static String LOGINS_FILE_NAME="logins.txt";
    private final static String PASSWORDS_FILE_NAME="passwords.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        final EditText login=findViewById(R.id.login);
        final EditText password=findViewById(R.id.password);

        Button btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inName=login.getText().toString();
                String inPassword=password.getText().toString();
                try {
                    FileInputStream streamInLogins = openFileInput(LOGINS_FILE_NAME);
                    FileInputStream streamInPasswords = openFileInput(PASSWORDS_FILE_NAME);

                    InputStreamReader ReadFromLogins = new InputStreamReader(streamInLogins);
                    InputStreamReader ReadFromPasswords = new InputStreamReader(streamInPasswords);

                    BufferedReader brLogins = new BufferedReader(ReadFromLogins);
                    BufferedReader brPasswords = new BufferedReader(ReadFromPasswords);

                    String log="";
                    String pas="";
                    boolean loginSuccess=false;
                    while((log=brLogins.readLine())!=null && (pas=brPasswords.readLine())!=null) {
                        Toast.makeText(getApplicationContext(),log+pas,Toast.LENGTH_LONG).show();
                        if(log.equals(inName) && pas.equals(inPassword)){
                            Toast.makeText(getApplicationContext(),getString(R.string.successLogin),Toast.LENGTH_LONG).show();
                            loginSuccess=true;
                            break;
                        }
                    }
                    brLogins.readLine();
                    brPasswords.readLine();

                    brLogins.close();
                    brPasswords.close();
                    if(!loginSuccess){
                        Toast.makeText(getApplicationContext(),getString(R.string.loginPasswordNotFound),Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.fileExeption),Toast.LENGTH_LONG).show();
                }
            }
        });
        Button btnRegistration=findViewById(R.id.btnRegistration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inName=login.getText().toString()+'\n';
                String inPassword=password.getText().toString()+'\n';
                try {
                    FileOutputStream streamToLogins = openFileOutput(LOGINS_FILE_NAME, MODE_PRIVATE);
                    FileOutputStream streamToPasswords = openFileOutput(PASSWORDS_FILE_NAME,MODE_PRIVATE);

                    OutputStreamWriter writeToLogins = new OutputStreamWriter(streamToLogins);
                    OutputStreamWriter writeToPasswords = new OutputStreamWriter(streamToPasswords);

                    BufferedWriter bwLogins = new BufferedWriter(writeToLogins);
                    BufferedWriter bwPasswords = new BufferedWriter(writeToPasswords);

                    bwLogins.write(inName);
                    bwPasswords.write(inPassword);

                    bwLogins.close();
                    bwPasswords.close();

                    Toast.makeText(getApplicationContext(),getString(R.string.successFileWrite),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.fileExeption),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
