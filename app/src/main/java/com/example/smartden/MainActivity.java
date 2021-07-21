package com.example.smartden;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartden.databinding.ActivityMainBinding;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ActivityMainBinding activityMainBinding;

    public static final String URL = "http://control.smartden.net.in/smartden";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        callListeners();
    }

    public void callListeners(){
        getServerData();

        activityMainBinding.btnLoadResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkUtility.isInternetAvailable(MainActivity.this)){
                    activityMainBinding.btnLoadResponse.setVisibility(View.GONE);
                    activityMainBinding.pbLoading.setVisibility(View.VISIBLE);
                    getServerData();
                }else {
                    Toast.makeText(MainActivity.this, "Internet is off.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getServerData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activityMainBinding.btnLoadResponse.setVisibility(View.VISIBLE);
                activityMainBinding.pbLoading.setVisibility(View.GONE);
                activityMainBinding.tvResponse.setVisibility(View.GONE);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("name", "Karan");
                return new JSONObject(params2).toString().getBytes();
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
}