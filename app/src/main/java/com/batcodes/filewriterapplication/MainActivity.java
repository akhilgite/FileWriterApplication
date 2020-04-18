package com.batcodes.filewriterapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etData;
    private Button btnDone;
    private TextView tvData;

    String fileName = "/FileWriterDemo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etData = findViewById(R.id.et_data);
        btnDone=findViewById(R.id.btn_done);
        tvData=findViewById(R.id.tv_data);

        btnDone.setOnClickListener(this);
        checkPermission();
    }

    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onClick(View view) {
        String strData=  etData.getText().toString();
        if(strData.length()>0){
            tvData.setText(strData);
            writeData(strData);
        }else {
            Toast.makeText(this, "Enter data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeData(String data) {
        File file = Environment.getExternalStorageDirectory();
        String strFilePath = file.getAbsoluteFile()+fileName;

        try {
            FileOutputStream fileOutputStream =new FileOutputStream(strFilePath);
            FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());
            fileWriter.write(data);
            fileWriter.close();
            fileOutputStream.getFD().sync();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
