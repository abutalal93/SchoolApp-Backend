package com.decoders.school.Utils;

import com.squareup.okhttp.*;
import org.json.JSONObject;

import java.io.IOException;

public class PushNotificationHandler {

    public static void sendNotification(NotificationMessage notificationMessage) {

        OkHttpClient client = new OkHttpClient();

        JSONObject notificationJson = new JSONObject();

        notificationJson.put("title",notificationMessage.getTitle());

        notificationJson.put("text",notificationMessage.getBody());

        notificationJson.put("sound","default");

        JSONObject notificationData = new JSONObject(notificationMessage.getData());

        JSONObject fcmJsonRequest = new JSONObject();

        if(notificationMessage.getToken() == null || notificationMessage.getToken().isEmpty()){
            //send to topic
            fcmJsonRequest.put("to", notificationMessage.getTopic());
        }else{
            //send to token
            fcmJsonRequest.put("to", notificationMessage.getToken());
        }

        fcmJsonRequest.put("notification" , notificationJson);

        fcmJsonRequest.put("data", notificationData);

        System.out.println("json req: " + fcmJsonRequest.toString());

        String serverKey = notificationMessage.getApplicationContext().getEnvironment().getProperty("fcm_key");

        System.out.println("serverKey: "+serverKey);

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

        RequestBody body = RequestBody.create(mediaType, (fcmJsonRequest.toString()));

        Request request = new Request.Builder()
                .url(notificationMessage.getApplicationContext().getEnvironment().getProperty("fcm_url"))
                .post(body)
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .addHeader("Authorization", "key=" + serverKey)
                .addHeader("cache-control", "no-cache")
                .build();

        String responseCode = null;
        Response response = null;
        try {
            response = client.newCall(request).execute();
            responseCode = String.valueOf(response.code());
            System.out.println("body:::>>>"+response.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
            if (response != null)
                responseCode = String.valueOf(response.code());
            else
                responseCode = "500";
        }
        System.out.println("responseCode:::>>>" + responseCode);
    }


    public static void sendSms(NotificationMessage notificationMessage) {

        OkHttpClient client = new OkHttpClient();

        String smsUrl = notificationMessage.getApplicationContext().getEnvironment().getProperty("sms_url");

        HttpUrl.Builder httpBuider = HttpUrl.parse(smsUrl).newBuilder();
        httpBuider.addQueryParameter("login_name","test_1234");
        httpBuider.addQueryParameter("login_password","Testing_2020");
        httpBuider.addQueryParameter("msg",notificationMessage.getBody());
        httpBuider.addQueryParameter("mobile_number",notificationMessage.getMobileNumber().replace("+962","0"));
        httpBuider.addQueryParameter("from","Phoenic");
        httpBuider.addQueryParameter("charset","UTF-8");
        httpBuider.addQueryParameter("unicode","0");

        System.out.println("http url: "+httpBuider.toString());

        Request request = new Request.Builder()
                .url(httpBuider.build())
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("charset", "UTF-8")
                .build();


        String responseCode = null;
        Response response = null;
        try {
            response = client.newCall(request).execute();
            responseCode = String.valueOf(response.code());
            System.out.println("body: "+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            if (response != null)
                responseCode = String.valueOf(response.code());
            else
                responseCode = "500";
        }
        System.out.println("responseCode:::>>>" + responseCode);
    }
}
