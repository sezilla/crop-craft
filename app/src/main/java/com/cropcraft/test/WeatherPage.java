package com.cropcraft.test;


import android.Manifest;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WeatherPage extends AppCompatActivity {

    final String APP_ID = "c2cb8951d0bad4f1f08bb5f61378405c";
    final String weather_url = "https://api.openweathermap.org/data/2.5/weather";

    final long min_time = 5000;
    final float min_distance = 1000;
    static final int request_code = 101;

    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView city_name, weather_state, temp;
    ImageView weather_icon;

    EditText cityInput;

    RelativeLayout city_finder;

    LocationManager mlocationManager;
    LocationListener mlocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_page);

        weather_state = findViewById(R.id.weatherCondition);
        temp = findViewById(R.id.temperature);
        weather_icon = findViewById(R.id.weatherIcon);
        //city_finder = findViewById(R.id.cityFinder);
        city_name = findViewById(R.id.cityName);

        /*city_finder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeatherPage.this, cityFinder.class);
                startActivity(intent);
            }
        }
        );*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == request_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(WeatherPage.this, "Location retrieved successfully", Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            } else {
                Toast.makeText(WeatherPage.this, "Location permission denied", Toast.LENGTH_SHORT).show();
                // Handle the case where the user denied the location permission.
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeatherForCurrentLocation();
    }

    private void getWeatherForCurrentLocation() {
        mlocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String latitude = String.valueOf(location.getLatitude());
                String longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", latitude);
                params.put("lon", longitude);
                params.put("appid", APP_ID);
                letsdoSomeNetworking(params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                // LocationListener.super.onProviderEnabled(provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                // LocationListener.super.onProviderDisabled(provider);
                // not able to get location
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_code);
            return;
        }
        mlocationManager.requestLocationUpdates(Location_Provider, min_time, min_distance, mlocationListener);
    }




    private void letsdoSomeNetworking(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(weather_url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(WeatherPage.this, "Data retrieved successfully", Toast.LENGTH_SHORT).show();

                weatherData weatherD = weatherData.fromJson(response);
                updateUI(weatherD);

                // super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(WeatherPage.this, "Failed to retrieve weather data", Toast.LENGTH_SHORT).show();
                // Log the error or take appropriate action
                // super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void updateUI(weatherData weather) {
        temp.setText(weather.getTemperature());
        city_name.setText(weather.getCity());
        weather_state.setText(weather.getWeatherType());
        int resourceId = getResources().getIdentifier(weather.getIcon(), "drawable", getPackageName());
        weather_icon.setImageResource(resourceId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mlocationManager != null) {
            mlocationManager.removeUpdates(mlocationListener);
        }
    }
}