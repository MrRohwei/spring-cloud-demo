package com.lvbok.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.boot.json.JsonParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二维码
 */
public class QRCodeGenerate {
    private static final int QRCODE_DEFAULT_SIZE = 300;
    private static final boolean QRCODE_DEFAULT_CONTAIN_LOGO = false;
    private static final int QRCODE_LOGO_DEFAULT_WIDTH = 60;
    private static final int QRCODE_LOGO_DEFAULT_HEIGHT = 60;

    public static void generateCode(String logoPath) {

    }

//    public static void main(String[] args) {
//        try {
//            BitMatrix bitMatrix = new MultiFormatWriter().encode("https://www.baidu.com", BarcodeFormat.CODE_128, QRCODE_LOGO_DEFAULT_WIDTH, QRCODE_LOGO_DEFAULT_HEIGHT);
//            MatrixToImageWriter.writeToPath(bitMatrix, "", new File("D:\\zxing.gif").toPath());
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        Map<String, Object> t = new HashMap<String, Object>();
        t.put("ip", "127.0.0.1");
        Map<String, String> returnParam = new HashMap<String, String>();
        returnParam.put("orderNo", "mallOrderDto.getOrderNo()");
        returnParam.put("totalPrice", "mallOrderDto.getTotalPayment().toString()");
        String a = null;
        String b = null;
        // task-19460 卡限制人功能开发联调
        List<Map<String, Object>> identificationList = new ArrayList<>();
        for(int i = 0; i < 3 ; i++) {
            if (null != a && b != null) {
                Map<String, Object> identification = new HashMap<>();
                identification.put("idType", null);
                identification.put("idNo", null);
                identificationList.add(identification);
            }
        }
        returnParam.put("identificationList", JSONArray.toJSON(identificationList).toString());
        t.put("returnParam", JSON.toJSONString(returnParam));
        System.out.println(JSON.toJSONString(returnParam));
        System.out.println(JSON.toJSONString(t));
    }
}
