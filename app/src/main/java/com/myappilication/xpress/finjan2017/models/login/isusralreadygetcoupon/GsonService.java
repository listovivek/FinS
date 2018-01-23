package com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon;

import com.myappilication.xpress.finjan2017.models.login.helpers.StaticConfig;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suresh on 28/9/17.
 */
public class GsonService {



    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {


        try {



            /*List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("Authorization", token));
            nameValuePairs.add(new BasicNameValuePair("email", email));*/




            HttpPost httpGet = new HttpPost(StaticConfig.ROOT_URL+"getUserCourseDetails");

          //  httpGet.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpGet.setHeader("Accept", "application/json");
            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());

            HttpResponse httpResponse = httpClient.execute(httpGet);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }


        return null;
    }
}
