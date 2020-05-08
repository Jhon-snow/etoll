package com.example.collegeproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    EditText mname,mphone,memail,mpassword,mregister;
    ImageView imageView;
    Button capture,reg,backlogin;
    FirebaseAuth fAuth;
    public static final int CAMERA_PERM_CODE = 101;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mname=findViewById(R.id.name);
        mphone=findViewById(R.id.phone);
        memail=findViewById(R.id.email1);
        mpassword=findViewById(R.id.password1);
        mregister=findViewById(R.id.number);
        imageView=findViewById(R.id.imageView);
        capture=findViewById(R.id.capture);
        reg=findViewById(R.id.button4);
        backlogin=findViewById(R.id.backlogin);
        fAuth=FirebaseAuth.getInstance();

//        if(fAuth.getCurrentUser() != null)
//        {
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(register.this, "Camera button clicked.", Toast.LENGTH_SHORT).show();
                askCamerapermission();

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                if(TextUtils.isEmpty((email)))
                {
                    memail.setError("Email is invalid.");
                    return;
                }
                if(TextUtils.isEmpty((password)))
                {
                    memail.setError("Password is invalid.");
                    return;
                }
                if(password.length()<6)
                {
                    mpassword.setError("Password Must be >= 6 characters");
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(register.this, payment.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(register.this,"Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(register.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void askCamerapermission()
    {
       if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
       {
           ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
       }
       else
       {
           openCamera();
       }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openCamera();
            }
            else
            {
                Toast.makeText(register.this, "Camera permission requaired to use Camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera()
    {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 102)
        {
            Bitmap image=(Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(image);
        }
    }
}
