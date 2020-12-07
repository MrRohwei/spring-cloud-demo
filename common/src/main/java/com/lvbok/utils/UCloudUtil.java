package com.lvbok.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvbok.dto.Param;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 *
 */
public class UCloudUtil {

    private static final String PUBLIC_KEY = "48xK1YC6OjVHPzvkW7gEEGFjNCcqlPPHKznEMmFvB";
    private static final String PRIVATE_KEY = "30ypRlel7XrLFAJJ9Pyea61RaVHtkkNeY5OfUxn515E73tIN6CMgpgQ90lHwpfKdEI";
    private static final String APPID = "uaicensor-h45v5tqv";
//    private static final String UCLOUDURL = "http://api.uai.ucloud.cn/v1/image/scan";
    private static final String UCLOUDURL = "http://api.uai.ucloud.cn/tupu/censor";
    private static final String IMG_URL = "https://pic4.zhimg.com/80/v2-e110c4ec6cbc6b51f1bac346d4d85ab1_720w.jpg?source=1940ef5c";

    public static void sortParam(List<Param> params) {
        if (params != null) {
            // 排序
            Collections.sort(params, Comparator.comparing(Param::getParamKey));
        }
    }

    public static void sortParam(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        if (params != null) {
            // 排序
            params.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(p -> result.put(p.getKey(), p.getValue()));
        }
    }

    public static String stitchParams(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(entry.getKey()).append(entry.getValue());
        }
        return sb.toString();
    }

    public static String stitchParams(List<Param> params) {
        StringBuilder builder = new StringBuilder();
        if (params != null) {
            for (Param param : params) {
                if (param.getParamValue() == null){
                    continue;
                }
                builder.append(param.getParamKey());
                builder.append(param.getParamValue());
            }
        }
        return builder.toString();
    }


    public static String SHA1(String str) throws DigestException {
        try {
            //指定sha1算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            //获取字节数组
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new DigestException("签名错误！");
        }
    }

    public static String sha1Encrypt(String str) {
        System.out.println(str);
        if (str == null || str.length() == 0) {
            return null;
        }

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {

            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(buf);

        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws DigestException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(UCLOUDURL);
        String boundary = UUID.randomUUID().toString().replace("-","");
        post.setHeader("Content-Type", "multipart/form-data");
        List<Param> parms = new ArrayList<>();
        parms.add(new Param("ResourceId", APPID));
        parms.add(new Param("PublicKey", PUBLIC_KEY));
        parms.add(new Param("Timestamp", System.currentTimeMillis()));
        parms.add(new Param("Url", IMG_URL));
//        System.out.println("排序前" + parms);
        sortParam(parms);
//        System.out.println("排序后" + parms);
        String sign = stitchParams(parms);
//        System.out.println("拼接后" + sign);
        sign += PRIVATE_KEY;

        // 请求头
        Map<String, String> headers = new HashMap<>();
        String s1 = SHA1(sign);
        System.out.println(s1);
        headers.put("Signature", s1);
        headers.put("PublicKey",PUBLIC_KEY);
        headers.put("ResourceId",APPID);
        headers.put("Timestamp",System.currentTimeMillis() + "");
        Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            post.addHeader(next.getKey(), next.getValue());
        }

        try {
            // 请求参数
            List<NameValuePair> content = new ArrayList<>();
            content.add(new BasicNameValuePair("Scenes", "porn"));
            content.add(new BasicNameValuePair("Method", "url"));
            content.add(new BasicNameValuePair("Url", IMG_URL));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(content, "UTF-8");
            post.setEntity(entity);

            for (Header header : post.getAllHeaders()) {
                System.out.println(header.getName() + "<======>" + header.getValue());
            }
            CloseableHttpResponse response = client.execute(post);
            System.out.println(EntityUtils.toString(response.getEntity()));
//
//            if (response != null && response.getStatusLine().getStatusCode() == 200) {
//                JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(response.getEntity()));
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
