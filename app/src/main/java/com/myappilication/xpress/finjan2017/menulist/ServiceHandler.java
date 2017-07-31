package com.myappilication.xpress.finjan2017.menulist;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ServiceHandler {
 
    static String result = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public final static String Value="";
    String date, signs;
 
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method, List<NameValuePair> vParams,
                                  String datee, String sign) {
        date = datee;
        signs=sign;
        return this.makeServiceCall(url, method, vParams);
    }
 
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            
            //Setting user agent
            /*httpClient.getParams().setParameter(
            	    HttpProtocolParams.USER_AGENT,  
            	    "Mozilla/5.0 (Linux; U; Android-4.0.3; en-us; Galaxy Nexus Build/IML74K) " +

                            "AppleWebKit/535.7 (KHTML, like Gecko) CrMo/16.0.912.75 Mobile Safari/535.7"
            	);*/
             
            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                  // httpPost.setHeader("Accept", "application/json");
                    //in the pdf value   Cf48f82e34baa8fe5c58ce5f588d06d3f4299987ab0845935671dd64ad672cc8

                    /*String finall="FINSAFE, "+date+", "+signs;
                    httpPost.setHeader("Content-type", "application/json");
                    httpPost.setHeader("Authorization", finall);*/
                   /* httpPost.setHeader("Authorization", date);
                    httpPost.setHeader("Authorization", signs);*/

                }
                httpResponse = httpClient.execute(httpPost);
               InputStream inputStream = httpResponse.getEntity().getContent();
                result = convertInputStreamToString(inputStream);

                Log.d("", "");
            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);
            }
            httpEntity = httpResponse.getEntity();
           // response = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}