package com.dam.kev.llancador;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_naveg, btn_cam, btn_telf, btn_msg;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_naveg = (Button) findViewById(R.id.btnNaveg);
        btn_cam = (Button) findViewById(R.id.btnCam);
        btn_telf = (Button) findViewById(R.id.btnTlf);
        btn_msg = (Button) findViewById(R.id.btnMsg);
        btn_naveg.setOnClickListener(this);
        btn_cam.setOnClickListener(this);
        btn_telf.setOnClickListener(this);
        btn_msg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btn_cam)) takePhoto();
        else if (v.equals(btn_naveg)) openBrowser();
        else if (v.equals(btn_telf)) openPhone();
        else if (v.equals(btn_msg)) sendMessage();

    }

    public void takePhoto() {
        File dir = getExternalFilesDir(null);
        if (dir == null) {
            Toast.makeText(this, "Unable to mount the filesystem",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(dir, "photo.jpg");
        photoUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
        startActivity(i);
    }

    public void openBrowser() {
        Uri uri = Uri.parse("http://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void openPhone() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:933015696"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("kev", "Permission error");
            return;
        }
        startActivity(callIntent);
    }

    public void sendMessage() {
        Uri uri = Uri.parse("smsto:933015696");
        Intent intent = new Intent(Intent.ACTION_SENDTO , uri);
        intent.putExtra("sms_body", "Vull macarrons per dinar");
        startActivity(intent);

    }


}
