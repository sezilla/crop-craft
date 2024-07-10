package com.cropcraft.test;

import org.json.JSONException;
import org.json.JSONObject;

public class weatherData {

    private String temperature, icon, city, weatherType;
    private int condition;

    public static weatherData fromJson(JSONObject jsonObject) {
        try {
            weatherData weatherData = new weatherData();
            weatherData.city = jsonObject.getString("name");
            weatherData.condition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.weatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherData.icon = updateWeatherIcon(weatherData.condition);
            double tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            int roundedValue = (int) Math.rint(tempResult);
            weatherData.temperature = Integer.toString(roundedValue);
            return weatherData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(int condition) {
        // Your existing icon update logic
        // ...
        if(condition>=0 && condition<=300)
        {
            return "thunderstorm";
        }
        else if(condition>=300 && condition<=500)
        {
            return "drizzle";
        }
        else if(condition>=500 && condition<=600)
        {
            return "rain";
        }
        else if(condition>=600 && condition<=700)
        {
            return "snow";
        }
        else if(condition==800)
        {
            return "clear";
        }
        else if(condition>=800 && condition<=900)
        {
            return "clouds";
        }


        return "leaves"; // Default icon if no condition matches
    }

    public String getTemperature() {
        return temperature + "°C";
    }

    public String getIcon() {
        return icon;
    }

    public String getCity() {
        return city;
    }

    public String getWeatherType() {
        return weatherType;
    }
}