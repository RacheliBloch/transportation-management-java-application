package com.example.myapplication2.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication2.Entities.Travel;
import com.example.myapplication2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
     SharedPreferences sharedPreferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         sharedPreferences= getSharedPreferences("USER", MODE_PRIVATE);
         EditText pass=findViewById(R.id.password);
         EditText email=findViewById(R.id.email);
        Button loginbtn=findViewById(R.id.login_btn);
        email.setText( sharedPreferences.getString("Mail","No name"));
        pass.setText(sharedPreferences.getString("Password","No name"));
        Intent i = new Intent(LoginActivity.this, NavigationActivity.class);
        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String passstring=pass.getText().toString();
                String emailstring=email.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(emailstring,passstring).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                ArrayList<Travel> mytravels=new ArrayList<Travel>();
                                if(task.isSuccessful()){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Mail",emailstring);
                                    editor.putString("Password",passstring);
                                    editor.commit();
                                   i.putExtra("MY_MAIL",emailstring);
                                    startActivity(i);
                                }
                                else
                                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();



                            }
                        }
                );
            }
        });


        Button signupbtn=findViewById(R.id.sign_up_btn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passstring=pass.getText().toString();
                String emailstring=email.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(emailstring,passstring).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())

                                                Toast.makeText(LoginActivity.this,"register secssessful, plese check your email for verification",Toast.LENGTH_LONG).show();
                                            else
                                                Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                        }
                                    });

                                else
                                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }
                        }

                );


            }
        });

    }
}