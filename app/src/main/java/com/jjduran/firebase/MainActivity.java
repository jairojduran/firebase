package com.jjduran.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonregister;
    private EditText Email,password;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }

        progressDialog= new ProgressDialog(this);
        buttonregister = (Button)findViewById(R.id.buttonRegister);
        Email = (EditText)findViewById(R.id.Email);
        password=(EditText)findViewById(R.id.password);


        buttonregister.setOnClickListener(this);
    }

    private void registerUser(){
        String email= Email.getText().toString().trim();
        String Password= password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"porfavor entre su email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)){
            Toast.makeText(this,"porfavor entre su clave",Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Registrando Usuario ...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    Toast.makeText(MainActivity.this,"Registrado exitosamente",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Usuario no registrado",Toast.LENGTH_SHORT).show();

                }
              progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
if (view == buttonregister){
    registerUser();
}
    }
}
