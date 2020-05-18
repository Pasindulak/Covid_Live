package com.example.covid19;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

  String url = "https://www.hpb.health.gov.lk/api/get-current-statistical";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //final String [] report= new String[1];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getconnect();
    }
    void getconnect(){
        final RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        StringRequest sreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        resp(response);
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("pasindu","Error");
                error.printStackTrace();
                requestQueue.stop();
            }
        }
        );
        requestQueue.add(sreq);
    }
     void resp(String s){
        //build text views
        TextView stc = (TextView) findViewById(R.id.stc);
         TextView std = (TextView) findViewById(R.id.std);
         TextView str = (TextView) findViewById(R.id.str);
         TextView sdc = (TextView) findViewById(R.id.sdc);
         TextView sdd = (TextView) findViewById(R.id.sdd);
         TextView date = (TextView) findViewById(R.id.date);
        //end build text views

        stc.setText(search(1,s));
        std.setText(search(2,s));
        str.setText(search(7,s));
        sdc.setText(search(4,s));
        sdd.setText(search(6,s));
         date.setText(sdate(s));

        //Log.d("Count",search(1,s));
    }
    static String drive(Pattern p,String data) throws IOException {

        String s;
        Matcher m = p.matcher(data);
        m.find();
        s= m.group(2);
        return s;
    }
    static String sdate(String data){
        String res="";
        String search="update_date_time";
        Pattern p;
        String sss="("+search+"\\Q\":\"\\E"+")"+"([^\"]+)";
        p = Pattern.compile(sss);
        try {
            res = drive(p, data);
        }
        catch(IOException e){

        }
        return "Updated in : "+res;
    }
    static String search(int n,String data){
        String res="";
        String search="";
        Pattern p;
        try{
            switch (n){
                case 1:    //Total cases
                    search = "local_total_cases";
                    break;
                case 2:
                    search = "local_deaths";
                    break;
                case 3:
                    search = "local_active_cases";
                    break;
                case 4:
                    search = "local_new_cases";
                    break;
                case 5:
                    search = "local_deaths";
                    break;
                case 6:
                    search = "local_new_deaths";
                    break;
                case 7:
                    search = "local_recovered";
                    break;
                case 8:
                    search = "update_date_time";

                    break;
            }
            String sss="("+search+"\\Q\":\\E"+")"+"([0-9]+)";
            p = Pattern.compile(sss);
            res=drive(p,data);
        }
        catch(IOException e){
            //res="Please Check your Internet Conncetion!";
        }

        return res;
    }
}
