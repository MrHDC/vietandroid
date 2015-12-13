package com.android.conghd.justanapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toInteractive(View view) {
//        Toast toast = Toast.makeText(this,"abc",Toast.LENGTH_SHORT);
//        toast.show();
        Intent intent = new Intent(this, Interactive.class);
        startActivity(intent);
    }
}
