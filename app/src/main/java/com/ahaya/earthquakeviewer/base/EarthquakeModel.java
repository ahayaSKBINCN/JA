package com.ahaya.earthquakeviewer.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ahaya.earthquakeviewer.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class EarthquakeModel extends AndroidViewModel {



    private static final String TAG = "EarthquakeUpdate";
    private MutableLiveData<List<Earthquake>> earthquakes;

    public EarthquakeModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Earthquake>> getEarthquakes() {
        if (null == earthquakes) {
            earthquakes = new MutableLiveData<>();
            loadData();
        }
        return earthquakes;
    }

    private void processStream(InputStream inputStream) {
        //创建一个新的XML Pull Parser；
        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            //分配一个新的输入流
            xpp.setInput(inputStream, null);
            int eventType = xpp.getEventType();
            //为提取name标签分配变量；
            String name;
            //继续解析，直到文档结束
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //检查结果标签是否为start标签；
                if (eventType == XmlPullParser.START_TAG &&
                        xpp.getName().equals("result")) {
                    eventType = xpp.next();
                    //处理结果标签中的每一个结果
                    while (!(eventType == XmlPullParser.END_TAG && xpp.getName().equals("result"))) {
                        //提取POI的名称；
                        name = xpp.nextText();
//                        doSomethingWidthName(name)
                    }
                    //移动到下一个标签
                    eventType = xpp.next();
                }
                //对于每一个POI名称执行一些操作
            }
            //移动到下一个结果标签
            eventType = xpp.next();
        }catch (XmlPullParserException e){
            Log.e("XmlPullParserException","",e);
        }catch (IOException e){
            Log.e("IOException","",e);
        }

    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        new AsyncTask<Void, Void, List<Earthquake>>() {

            @Override
            protected List<Earthquake> doInBackground(Void... voids) {
                ArrayList<Earthquake> earthquakes = new ArrayList<>(0);
                String myFeed = getApplication().getString(R.string.earthquake_feed);
                try {
                    URL url = new URL(myFeed);
                    URLConnection connection = url.openConnection();
                    HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        //处理流入数据以生成结果；
//                        earthquakes = processStream(inputStream);
                        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = builderFactory.newDocumentBuilder();
                        //解析地震源数据
                        Document dom = builder.parse(inputStream);
                        Element docEle = dom.getDocumentElement();
                        //获取每个地震条目的列表
                        NodeList nodeList = docEle.getElementsByTagName("entry");
                        if(nodeList !=null && nodeList.getLength()>0){
                            for (int i = 0; i <nodeList.getLength() ; i++) {
                                //检查加载是否意取消，如果已取消，则返回到目前为止已经加载的数据；
                                if(isCancelled()){
                                    Log.d(TAG,"Load Canceled");
                                    return earthquakes;
                                }
                                Element entry = (Element) nodeList.item(i);
                                Element id = (Element) entry.getElementsByTagName("id").item(0);
                                Element title = (Element) entry.getElementsByTagName("title").item(0);
                                Element g =(Element) entry.getElementsByTagName("georss:point").item(0);
                                Element when = (Element) entry.getElementsByTagName("updated").item(0);
                                Element link = (Element) entry.getElementsByTagName("link").item(0);

                                String idString = id.getFirstChild().getNodeValue();
                                String details = title.getFirstChild().getNodeValue();
                                String hostname = "http://earthquake.usgs.gov";
                                String linkString = hostname + link.getAttribute("href");
                                String point = g.getFirstChild().getNodeValue();
                                String dt = when.getFirstChild().getNodeValue();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
                                Date qdate = new GregorianCalendar(0,0,0).getTime();

                                try {
                                    qdate = simpleDateFormat.parse(dt);
                                } catch (ParseException e) {
                                    Log.e(TAG,"ParseException",e);
                                }
                                String[] location = point.split(" ");
                                Location l = new Location("dummyGPS");
                                l.setLatitude(Double.parseDouble(location[0]));
                                l.setLongitude(Double.parseDouble(location[1]));

                                String magnitudeString = details.split(" ")[1];
                                int end = magnitudeString.length()-1;
                                double magnitude = Double.parseDouble(magnitudeString.substring(0,end));

                                if(details.contains("-")){
                                    details= details.split("-")[1].trim();
                                }else details = "";

                                final Earthquake eathquakes = new Earthquake(
                                        idString,
                                        qdate,
                                        details,
                                        l,
                                        magnitude,
                                        linkString
                                );
                                earthquakes.add(eathquakes);
                            }
                        }
                    }
                    httpURLConnection.disconnect();
                } catch (MalformedURLException e) {
                    Log.e(TAG, "URL_EXCEPTION", e);
                } catch (IOException e) {
                    Log.e(TAG, "IOEXCEPTION", e);
                } catch (ParserConfigurationException e) {
                    Log.e(TAG,"ParserConfigurationException",e);
                } catch (SAXException e) {
                    Log.e(TAG,"SAXException",e);
                }
                return earthquakes;
            }

            @Override
            protected void onPostExecute(List<Earthquake> data) {
                earthquakes.setValue(data);
            }
        }.execute();
    }
}
