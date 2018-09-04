package com.example.admin.authentication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnDangky, btnDangnhap , btnGuixacthucEmail ,btnResetPassword;
    EditText edtEmail, edtPassword ,edtXacthucEmail;
    private FirebaseAuth mAuth;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnDangky = findViewById(R.id.buttonDangky);
        btnDangnhap = findViewById(R.id.buttonDangnhap);
        edtEmail = findViewById(R.id.edittextEmail);
        edtPassword = findViewById(R.id.edittextPassword);
        btnGuixacthucEmail = findViewById(R.id.buttonGuiemailxacthuc);
        btnResetPassword = findViewById(R.id.buttonResetPassword);
        edtXacthucEmail = findViewById(R.id.edittextXacthucemail);

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetUser();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Dang ky thanh cong!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "That bai!!!", Toast.LENGTH_SHORT).show();
                            Log.d("BBB", task.getException().getMessage().toString());
                        }
                    }
                });

            }
        });
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetUser();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String thongbao = task.isSuccessful() ? "Thanh cong" : "That bai";
                        Toast.makeText(MainActivity.this, thongbao, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        btnGuixacthucEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtXacthucEmail.getText().toString();

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                if (user != null){
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Gui xac thuc thanh cong!!!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Loi!!", Toast.LENGTH_SHORT).show();
                                Log.d("BBB",task.getException().getMessage().toString());
                            }
                        }
                    });

                }
            }
        });
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = "phatdroid0208@gmail.com";

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this, "That bai!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    private void GetUser() {
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
    }

}
