package com.ahaya.earthquakeviewer.sample;

import android.location.Location;
import android.util.JsonReader;

import com.ahaya.earthquakeviewer.entity.Earthquake;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeJSONParse {

    public static List<Earthquake> parseJSON(InputStream input) throws IOException {
        //创建一个JSON reader 来解析输入流
        JsonReader reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
        try {
            //创建一个用来春芳地震数据的空列表
            List<Earthquake> earthquakes = null;
            //数据源的根源节点
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                //我们只对子对象感兴趣，标记为features的地震数组；
                if (name.equals("features")) {
                    earthquakes = readEarthquakeArray(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return earthquakes;
        } finally {
            reader.close();
        }
    }

    private static List<Earthquake> readEarthquakeArray(JsonReader reader) throws IOException {
        List<Earthquake> earthquakes = new ArrayList<Earthquake>();

        //地震的详细信息存储在一个数组中
        reader.beginArray();

        while (reader.hasNext()) {
            earthquakes.add(readEarthquake(reader));
        }
        reader.endArray();
        return earthquakes;

    }

    public static Earthquake readEarthquake(JsonReader reader) throws IOException {
        String id = null;
        Location location = null;
        Earthquake earthquakeProperties = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextString();
            } else if (name.equals("geometry")) {
                location = readLocation(reader);
            } else if (name.equals("properties")) {
                earthquakeProperties = readEarthquakeProperties(reader);
            } else {
                reader.skipValue();
            }

        }
        reader.endObject();
        if(earthquakeProperties != null){
        return new Earthquake(id,
                earthquakeProperties.getMDate(),
                earthquakeProperties.getMDetails(),
                location,
                earthquakeProperties.getMMagnitude(),
                earthquakeProperties.getMLink());
        }
        return null;

    }
    private static Earthquake readEarthquakeProperties(JsonReader reader) throws IOException {
        Date date = null;
        String details = null;
        double magnitude = -1;
        String link = null;

        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("time")){
                long time = reader.nextLong();
                date = new Date(time);
            }else if(name.equals("place")){
                details = reader.nextString();
            }else if(name.equals("url")){
                link = reader.nextString();
            }else if(name.equals("mag")){
                magnitude = reader.nextDouble();
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Earthquake(null,date,details,null,magnitude,link);
    }

    private static Location readLocation(JsonReader reader)throws IOException {
        Location location = null;
        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("coordinates")){
                List<Double> coords = readDoublesArray(reader);
                location = new Location("dummy");
                location.setLatitude(coords.get(0));
                location.setLongitude(coords.get(1));
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return location;
    }
    private static List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()){
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }
}

