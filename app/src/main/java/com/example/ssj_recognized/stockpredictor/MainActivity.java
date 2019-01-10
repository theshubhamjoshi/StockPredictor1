package com.example.ssj_recognized.stockpredictor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Spinner spinner;
    Button predict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[]{"Cisco","EA", "eBay", "Eros", "Facebook", "HP", "IBM", "ITC", "JBL", "LG", "Microsoft", "MSI", "Qualcomm"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        spinner.setAdapter(adapter);
        predict = (Button) findViewById(R.id.begin);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, prediction.class);
                intent.putExtra("company", spinner.getSelectedItem().toString());
                startActivity(intent);
            }
        });


    }
}
