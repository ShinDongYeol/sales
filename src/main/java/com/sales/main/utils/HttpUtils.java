package com.sales.main.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpUtils {


    /**
     * 기본 post 방식
     * @param targetUrl
     * @param dataMap
     * @return
     * @throws Exception
     */
    public  static String sendPostJson(String targetUrl, HashMap<String, Object> dataMap)  throws Exception {

        BufferedReader in = null;

        StringBuilder sb = new StringBuilder();
        JSONObject obj = new JSONObject(dataMap);

        String body = obj.toString();

        URL url = new URL(targetUrl); // 호출할 url

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoInput(true);
        conn.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
        conn.setUseCaches(false);
        conn.setDefaultUseCaches(false);
        conn.setConnectTimeout(3000); //연결대기시간 3초
        conn.setReadTimeout(3000);  //응답받는시간 3초.


        BufferedOutputStream wr = new BufferedOutputStream(conn.getOutputStream());
        // Request Body에 Data 셋팅.
        wr.write(body.getBytes("UTF-8"));
        // Request Body에 Data 입력.
        wr.flush();
        // OutputStream 종료.
        wr.close();
        StringBuilder str = new StringBuilder();

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));


            String inputLine ="";
            while ((inputLine = in.readLine()) != null) { // response 출력
                str.append(inputLine + "\n");
            }
            in.close();

        }else {
           throw new Exception(conn.getResponseMessage());
        }
        return str.toString();
    }


    /**
     * post 인데 
     * application/x-www-form-urlencoded 방식
     * @param targetUrl
     * @param dataMap
     * @return
     * @throws Exception
     */
    public String sendPostQuery(String targetUrl, HashMap<String, Object> dataMap)  throws Exception {

        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();

        URL url = new URL(targetUrl); // 호출할 url

        StringBuilder postData = new StringBuilder();

        //데이터 만들기
        for (Map.Entry<String, Object>  param :  dataMap.entrySet()) {

            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        //데이터를 바이트 배열로 만든다.
        byte[] postDataBytes = postData.toString().getBytes("utf-8");

        try{

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  // x-form쓰는게 한글때문이라는데.....
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);
            conn.setConnectTimeout(3000); //연결대기시간 3초
            conn.setReadTimeout(3000);  //응답받는시간 3초.

            BufferedOutputStream wr = new BufferedOutputStream(conn.getOutputStream());

            wr.write(postDataBytes, 0, postDataBytes.length);
            wr.flush();


            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                String inputLine;
                while ((inputLine = in.readLine()) != null) { // response 출력
                    sb.append(inputLine);
                }
                in.close();

            }else {
                System.out.println(conn.getResponseMessage());
            }
        }catch(Exception e ) {
            e.printStackTrace();
        }

        return sb.toString();
    }


    /**
     * get 방식 전송
     * campaign 데이터 얻기용..
     * @param requestURL
     * @param dataMap
     * @return
     * @throws Exception
     */
    public String get(String requestURL, HashMap<String, Object> dataMap) throws Exception {

        String body = null;

        StringBuilder postData = new StringBuilder();
        
        if(dataMap !=null) {

        for (Map.Entry<String, Object>  param :  dataMap.entrySet()) {
	
	            if (postData.length() != 0){
	                postData.append('&');
	            }else {
	                postData.append('?');
	            }
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	
	        requestURL = requestURL+ postData.toString();
        }

        HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
        HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
        getRequest.addHeader("Authorization", "KakaoAK 53f4cfea53e9308630e07a7a63e9e368");


        
        HttpResponse response = client.execute(getRequest);

        //Response 출력
        if (response.getStatusLine().getStatusCode() == 200) {
            ResponseHandler<String> handler = new BasicResponseHandler();
            body = handler.handleResponse(response);
        } else {
            System.out.println("response is error : " + response.getStatusLine().getStatusCode());
        }


        return body;

    }

    public  String get(String targetUrl,  HashMap<String, Object> dataMap, String token)  throws  Exception{

        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        StringBuilder postData = new StringBuilder();

        if(dataMap !=null) {
            for (Map.Entry<String, Object> param : dataMap.entrySet()) {

                if (postData.length() != 0) {
                    postData.append('&');
                } else {
                    postData.append('?');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            targetUrl = targetUrl + postData.toString();
        }

        URL url = new URL(targetUrl); // 호출할 url


        try{

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("Authorization",  token);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);
            conn.setConnectTimeout(3000); //연결대기시간 3초
            conn.setReadTimeout(3000);  //응답받는시간 3초.

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            Charset charset = Charset.forName("UTF-8");

            if(responseCode != 200) {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream(),charset));
            }else {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(),charset));
            }

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();

        }catch(Exception e ) {
          e.printStackTrace();
        }

        return sb.toString();
    }


}
