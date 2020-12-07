package com.lvbok.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

    public static JSONObject httpGet(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "text/json;charset=utf-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return JSON.parseObject(buf.toString().trim());
    }

    public static String httpGet1(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "text/json;charset=utf-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return buf.toString().trim();
    }

    public static String httpGet2(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return buf.toString().trim();
    }

    public static String httpPut(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        int code = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//        logger.error("code=" + conn.getResponseCode());
        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return (buf.toString().trim()) != null ? (buf.toString().trim()) : (code + "");
    }

    public static JSONObject httpPost(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/json;charset=utf-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return JSON.parseObject(buf.toString().trim());
    }

    public static JSONObject httpPost(String urlStr, String postText) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        byte[] bytes = postText.getBytes("utf-8");
        OutputStream os = conn.getOutputStream();
        os.write(bytes);
        os.close();
        //
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            //
            inputLine = in.readLine();
        }
        in.close();
        //
        return JSON.parseObject(buf.toString().trim());
    }

    public static Object post(List<NameValuePair> message, String to)
            throws ParseException, IOException, JSONException {

        HttpPost post = new HttpPost(to);
        UrlEncodedFormEntity params = new UrlEncodedFormEntity(message, "UTF-8");
        post.setEntity(params);
        System.out.println(("[] Post \"" + message + "\" to " + post.getURI()));
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("[] Server Status Code: " + statusCode);

        String respString = null;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            respString = EntityUtils.toString(entity, "UTF-8");
        }
        System.out.println("[] Server return: " + respString);
        EntityUtils.consume(entity);

        JSONObject result = JSON.parseObject(respString.trim());
        if (result != null && result.containsKey("success") && result.get("success").equals("true")) {
            return result.get("data");
        } else {
//            logger.info(message);
            return "error";
        }
    }

    public static String httpPostApplication(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return buf.toString().trim();
    }

    public static String RestfulGet(String url) {
        String returnString = "";
        try {
            URL restServiceURL = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpConnection.setRequestProperty("contentType", "utf-8");
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException(
                        "HTTP GET Request Failed with Error code : " + httpConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(
                    new InputStreamReader((httpConnection.getInputStream()), "utf-8"));
            String output = responseBuffer.readLine();
            // System.out.println("Output from Server: \n");
            StringBuffer buf = new StringBuffer();
            while (output != null) {
                buf.append(output).append("\r\n");
                output = responseBuffer.readLine();
            }
            responseBuffer.close();
            httpConnection.disconnect();
            returnString = buf.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public static String RestfulPost(String url, String input) {
        String result = "";
        try {
            URL targetUrl = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Charsert", "UTF-8");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(
                    new InputStreamReader((httpConnection.getInputStream()), "UTF-8"));
            String output;
            System.out.println("Output from Server:\n");
            StringBuffer buf = new StringBuffer();
            while ((output = responseBuffer.readLine()) != null) {
                buf.append(output).append("\r\n");
            }
            httpConnection.disconnect();
            responseBuffer.close();
            result = buf.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static JSONObject post(JSONObject json, String to)
            throws ParseException, IOException, JSONException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(to);
        post.setHeader("Content-Type", "application/json");
        JSONObject result = null;
        java.io.InputStream inStream = null;
        try {
            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);
            // 发送请求
            HttpResponse httpResponse = client.execute(post);
            // 获取响应输入流
            inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                strber.append(line + "\n");
            }
            inStream.close();
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = JSON.parseObject(strber.toString());
            } else {
//                logger.error("请求服务端失败" + json);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject doPutOrPost(String type, String urlString, JSONObject jsonObject,
                                         String callMethed) {
        try {
            String result = "";
            String info = callMethed + ",调用SDK的restful接口URL:" + urlString + ",请求参数:" + jsonObject.toString() + ",请求方式:"
                    + type;
//            logger.info(info);
            URL url = new URL(urlString);
            int code = 0;
            // 打开restful链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 提交模式
            conn.setRequestMethod(type);
            conn.setConnectTimeout(200000);// 连接超时 单位毫秒
            conn.setReadTimeout(200000);// 读取超时 单位毫秒
            conn.setDoOutput(true);// 是否输入参数
            conn.setDoInput(true);
            // 设置访问提交模式，表单提交
            conn.setRequestProperty("Content-Type", "application/json");
            // 设置请求头
            // conn.setRequestProperty(SDKKeyName, SDKKey);
            // conn.setRequestProperty(SDKValueName, SDKValue);
            // ----------------传入数据----------------------
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());
            code = conn.getResponseCode();
            outputStream.flush();
            // 判断是否创建成功
//            logger.info(info + ",调用所得code值为:" + code);
            System.out.println("---------code=" + code);
            if (code == 200) {// 执行成功
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                reader.close();
                result = buffer.toString();
//                logger.info(info + ",调用结果信息为:" + result);
                JSONObject jsonObj = JSON.parseObject(result);
                return jsonObj;
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                reader.close();
                result = buffer.toString();
//                logger.info(info + ",调用结果信息为:" + result);
                JSONObject jsonObj = JSON.parseObject(result);
                return jsonObj;

            }
        } catch (Exception e) {
//            logger.error("请求失败", e);
            return null;
        }
    }

    public static int intOf(Object obj, int initValue) {
        // 1.判断参数是否合法
        if (obj == null || "".equals(obj)) {
            return initValue;
        } else {
            try {
                return Integer.parseInt(obj.toString());
            } catch (Exception e) {
                e.getMessage();
                return initValue;
            }
        }
    }
}
