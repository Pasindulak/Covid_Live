package com.example.covid19;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

  String url = "https://www.hpb.health.gov.lk/api/get-current-statistical";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getconnect();
    }
    void getconnect(){
        final TextView sl = (TextView) findViewById(R.id.sl);
        final TextView gl = (TextView) findViewById(R.id.gl);
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

                sl.setText("No internet connection");
                gl.setText("No internet connection");
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
         TextView sac = (TextView) findViewById(R.id.sac);
         TextView gtc = (TextView) findViewById(R.id.gtc);
         TextView gtd = (TextView) findViewById(R.id.gtd);
         TextView gtr = (TextView) findViewById(R.id.gtr);
         TextView gdc = (TextView) findViewById(R.id.gdc);
         TextView gdd = (TextView) findViewById(R.id.gdd);
         TextView gac = (TextView) findViewById(R.id.gac);
        //end build text views



        stc.setText(newsearch(1,s));
        std.setText(newsearch(2,s));
        str.setText(newsearch(7,s));
        sdc.setText(newsearch(4,s));
        sdd.setText(newsearch(6,s));
         sac.setText(newsearch(3,s));
         gtc.setText(newsearch(5,s));
         gtd.setText(newsearch(12,s));
         gtr.setText(newsearch(9,s));
         gdc.setText(newsearch(10,s));
         gdd.setText(newsearch(11,s));

         date.setText(sdate(s));

        String cases = search(5,s);
         String deaths = search(12,s);
         String rec = search(9,s);
         int num = Integer.parseInt(cases)-Integer.parseInt(deaths)-Integer.parseInt(rec);
         String globactive = String.valueOf(num);
         gac.setText(addcom(globactive));

    }

    static String addcom(String s){
        long num = Long.parseLong(s);
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(true);
        return format.format(num);
    }
    static String newsearch(int n,String data){
        String s=search(n,data);
        s=addcom(s);
        return s;
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
        return "Last updated at: "+res;
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
                    search = "global_total_cases";
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
                case 9:
                    search = "global_recovered";
                    break;
                case 10:
                    search = "global_new_cases";
                    break;
                case 11:
                    search = "global_new_deaths";
                    break;
                case 12:
                    search = "global_deaths";
                    break;

                default:
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
