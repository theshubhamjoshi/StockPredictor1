package com.example.ssj_recognized.stockpredictor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class prediction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        final TextView accuracy = (TextView) findViewById(R.id.accuracy);
        final TextView prediction = (TextView) findViewById(R.id.prediction);
        final EditText date = (EditText) findViewById(R.id.date);

        int companyLogos[] = new int[]{R.drawable.ea, R.drawable.amazon, R.drawable.cisco, R.drawable.ebay, R.drawable.eros,
                                       R.drawable.facebook, R.drawable.hp, R.drawable.ibm, R.drawable.itc, R.drawable.jbl,
                                       R.drawable.lg, R.drawable.microsoft, R.drawable.msi, R.drawable.qualcomm};
        Map<String, Integer> companyLogo = new HashMap<>();
        companyLogo.put("EA", R.drawable.ea);
        companyLogo.put("Amazon", R.drawable.amazon);
        companyLogo.put("Cisco", R.drawable.cisco);
        companyLogo.put("Ebay", R.drawable.ebay);
        companyLogo.put("Eros", R.drawable.eros);
        companyLogo.put("Facebook", R.drawable.facebook);
        companyLogo.put("HP", R.drawable.hp);
        companyLogo.put("IBM", R.drawable.ibm);
        companyLogo.put("ITC", R.drawable.itc);
        companyLogo.put("JBL", R.drawable.jbl);
        companyLogo.put("LG", R.drawable.lg);
        companyLogo.put("Microsoft", R.drawable.microsoft);
        companyLogo.put("MSI", R.drawable.msi);
        companyLogo.put("Qualcomm", R.drawable.qualcomm);
        companyLogo.put("Amazon", R.drawable.amazon);



        Intent i = getIntent();
        final String a = i.getStringExtra("company");
        TextView companyTitle = (TextView) findViewById(R.id.companyTitle);
        if(a.length()>3){
            companyTitle.setText(a.toLowerCase());
        }else {
            companyTitle.setText(a);
        }


        ImageView companydp = (ImageView) findViewById(R.id.companydp);
        companydp.setImageResource(companyLogo.get(a));

        final TextView textView = (TextView) findViewById(R.id.companyname);
        textView.setText(a);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.43.72/a.php";



        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.d("##############################",a);
                    Log.d("#########################", response);

                    JSONObject jsonObject = new JSONObject(response);
                    final JSONArray datearray = jsonObject.getJSONArray("dates");
                    String current = datearray.get(0).toString();
                    date.setText(datearray.get(0).toString());
                    String string1 = jsonObject.get("accuracy").toString();
                    float int1 = Float.parseFloat(string1);
                    accuracy.setText(string1);
                    final JSONArray arrayList1 = jsonObject.getJSONArray("forecast");


                    LineChart lineChart = (LineChart) findViewById(R.id.linechart);

                    ArrayList<Entry> arrayList = new ArrayList();

                    for(int i=0; i<arrayList1.length(); i++){

                        arrayList.add(new Entry(i, Float.parseFloat(arrayList1.get(i).toString())));

                    }
                    LineDataSet lineDataSet = new LineDataSet(arrayList,"Stock Prices in $");
                    lineDataSet.setFillAlpha(110);
                    ArrayList<ILineDataSet> arrayList2 = new ArrayList<>();
                    arrayList2.add(lineDataSet);

                    LineData lineData = new LineData(arrayList2);
                    lineChart.setData(lineData);
                    RelativeLayout loadingscreen = (RelativeLayout) findViewById(R.id.loadingscreen);
                    loadingscreen.setVisibility(View.GONE);


                    float pred = Float.parseFloat(arrayList1.get(0).toString());

                    prediction.setText("$ "+String.valueOf(pred).substring(0,5));








                    if (int1<=50){
                        accuracy.setTextColor(getColor(R.color.Green));
                    }else if (int1<75){
                        accuracy.setTextColor(getColor(R.color.Yellow));
                    }else {
                        accuracy.setTextColor(getColor(R.color.Red));
                    }



                    Button datechange = (Button) findViewById(R.id.changdate);
                    datechange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);


                            String date1 = date.getText().toString();
                            int Index = 0;
                            for (int i = 0; i < datearray.length(); i++){
                                try {
                                    if (date1.equals(datearray.get(i).toString())){
                                        Index = i;
                                        break;

                                    }else{
                                        Index = -1;
                                    }
                                } catch (JSONException e) {

                                }
                            }
                            try {
                                if (Index == -1){
                                    prediction.setText("N/A");
                                }else {
                                    prediction.setText("$ "+arrayList1.get(Index).toString().substring(0, 5));
                                }
                            } catch (JSONException e) {

                            }

                        }
                    });


                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("######################", error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("company", a);
                return params;
            }
        };

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

                Log.d("######################", error.toString());

            }
        });

        queue.add(request);









        final RelativeLayout graphbutton = (RelativeLayout) findViewById(R.id.graphbutton);
        final RelativeLayout graphlayout = (RelativeLayout) findViewById(R.id.graphlayout);
        final RelativeLayout predvalue = (RelativeLayout) findViewById(R.id.predvalue);
        final RelativeLayout predtitle = (RelativeLayout) findViewById(R.id.predtitle);




        Button closeGraph = (Button) findViewById(R.id.close);
        graphbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphbutton.setVisibility(View.INVISIBLE);
                graphlayout.setVisibility(View.VISIBLE);

                graphlayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                        int x = graphbutton.getRight();
                        int y = graphbutton.getBottom();

                        int startRadius = 0;
                        int endRadius = (int) Math.hypot(graphlayout.getWidth(), graphlayout.getHeight());

                        final Animator anim = ViewAnimationUtils.createCircularReveal(graphlayout, x/2, y/2, startRadius, endRadius);
                        anim.start();
                    }
                });
                predvalue.setVisibility(View.INVISIBLE);
                predtitle.setVisibility(View.INVISIBLE);


            }
        });
        closeGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cx = (graphlayout.getLeft() + graphlayout.getRight()) / 2;
                int cy = (graphlayout.getTop() + graphlayout.getBottom()) / 2;

                int dx = Math.max(cx, graphlayout.getWidth() - cx);
                int dy = Math.max(cy, graphlayout.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);

                final Animator anim = ViewAnimationUtils.createCircularReveal(graphlayout, cx, cy, finalRadius, 0);
                anim.setInterpolator(new AccelerateDecelerateInterpolator());

                anim.start();
                anim.addListener(new AnimatorListenerAdapter() {
                                     @Override
                                     public void onAnimationEnd(Animator animation) {
                                         graphlayout.setVisibility(View.GONE);
                                         graphbutton.setVisibility(View.VISIBLE);
                                         predtitle.setVisibility(View.VISIBLE);
                                         predvalue.setVisibility(View.VISIBLE);
                                     }


                                 });

            }
        });

        final Calendar myCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener dateSetter = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                date.setText(sdf.format(myCalendar.getTime()));
            }

        };


        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Calendar myCalendar = Calendar.getInstance();
                String[] current = date.getText().toString().split("-");
                myCalendar.set(Integer.parseInt(current[0]),Integer.parseInt(current[1])-1,Integer.parseInt(current[2]));
                new DatePickerDialog(prediction.this, dateSetter, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });






    }



}


