package com.android.matt.techchallenge2.app;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Matt on 12/3/2015.
 */
public class DownloadXMLTask extends AsyncTask<String, Void, String> {

    ArrayList<EarthquakeItem> items = new ArrayList<EarthquakeItem>();

    @Override
    protected String doInBackground(String... url) {

        // params comes from the execute() call: params[0] is the url.
        try {
            String urlContent = downloadUrl(url[0]);


            return null;
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        } catch (XmlPullParserException xppe){
            return "Unable to parse web page.";
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

    }

    private String downloadUrl(String myUrl) throws XmlPullParserException, IOException{
        InputStream inStream = null;

        try{
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();

            inStream = conn.getInputStream();

            //get content
            getXMLContent(inStream);
            //return content

            return null;
        } finally {
            if(inStream != null){
                inStream.close();
            }
        }
    }

    private void getXMLContent(InputStream inStream) throws XmlPullParserException, IOException{
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(new InputStreamReader(inStream));  //add URL
        int eventType = xpp.getEventType();
        EarthquakeItem eItem;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            eItem = new EarthquakeItem();
            if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("item")){
                System.out.println("start item");
                eventType = xpp.next();
                while(eventType != XmlPullParser.END_TAG || !xpp.getName().equals("item")){
                    if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("title")){
                        eventType = xpp.next();
                        if(eventType == XmlPullParser.TEXT){
                            eItem.setTitle(xpp.getText());
                            System.out.println("set the title");
                        }
                    } else if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("lat")){
                        eventType = xpp.next();
                        if(eventType == XmlPullParser.TEXT){
                            eItem.setLatitude(xpp.getText());
                            System.out.println("set the lat");
                        }
                    } else if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("long")){
                        eventType = xpp.next();
                        if(eventType == XmlPullParser.TEXT){
                            eItem.setLongitude(xpp.getText());
                            System.out.println("set the long");
                        }
                    } else if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("pubDate")){
                        eventType = xpp.next();
                        if(eventType == XmlPullParser.TEXT){
                            eItem.setTime(xpp.getText());
                            System.out.println("set the time");
                        }
                    } else if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("link")){
                        eventType = xpp.next();
                        if(eventType == XmlPullParser.TEXT){
                            eItem.setShakeURL(xpp.getText());
                            System.out.println("set the url");
                        }
                    }
                    eventType = xpp.next();
                }
                items.add(eItem);
                System.out.println("added item");
            }
            eventType = xpp.next();
           /* if(eventType == XmlPullParser.START_DOCUMENT){
                System.out.println("start doc");
            } else if (eventType == XmlPullParser.START_TAG){
                System.out.println("start tag" + xpp.getName());
            } else if (eventType == XmlPullParser.END_TAG){
                System.out.println("end tag" + xpp.getName());
            } else if (eventType == XmlPullParser.TEXT){
                System.out.println("text" + xpp.getText());
            }
            eventType = xpp.next(); */
        }
        //end doc

    }
}
