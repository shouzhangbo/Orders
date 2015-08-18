package com.test.order.db.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * 公共工具类
 *
 * @author zhengmingcheng
 */
public class CommonUtil {

    /**
     * 把source和dest转换成BigDecimal类型放到map中 <br/>
     * 
     * @param source
     * @param dest
     * @return Map<String, BigDecimal>
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException 
     */
    public static Map<String, BigDecimal> changeType2BigDecimal(Object source, Object dest) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

        BigDecimal bSource = null;
        BigDecimal bDest = null;
        Map<String, BigDecimal> mapReturnValue = new HashMap<String, BigDecimal>();

        if (source instanceof String) {
            bSource = new BigDecimal((String) source);
        } else if (source instanceof Integer) {
            bSource = new BigDecimal((Integer) source);
        } else if (source instanceof Float) {
            bSource = BigDecimal.valueOf((Float) source);
        } else if (source instanceof Double) {
            bSource = BigDecimal.valueOf((Double) source);
        } else if (source instanceof Long) {
            bSource = new BigDecimal((Long) source);
        } else if (source instanceof Character) {
            bSource = new BigDecimal((Character) source);
        } else if (source instanceof BigInteger) {
            bSource = new BigDecimal((BigInteger) source);
        } else {
            Class<?> clazz = source.getClass();
            Method method = clazz.getMethod("toString", new Class[0]);
            String str = (String) method.invoke(source, new Object[0]);
            bSource = new BigDecimal(str);
        }


        if (dest instanceof String) {
            bDest = new BigDecimal((String) dest);
        } else if (dest instanceof Integer) {
            bDest = new BigDecimal((Integer) dest);
        } else if (dest instanceof Float) {
            bDest = BigDecimal.valueOf((Float) dest);
        } else if (dest instanceof Double) {
            bDest = BigDecimal.valueOf((Double) dest);
        } else if (dest instanceof Long) {
            bDest = new BigDecimal((Long) dest);
        } else if (dest instanceof Character) {
            bDest = new BigDecimal((Character) dest);
        } else if (dest instanceof BigInteger) {
            bDest = new BigDecimal((BigInteger) dest);
        } else {
            Class<?> clazz = dest.getClass();
            Method method = clazz.getMethod("toString", new Class[0]);
            String str = (String) method.invoke(source, new Object[0]);
            bDest = new BigDecimal(str);
        }

        mapReturnValue.put("source", bSource);
        mapReturnValue.put("dest", bDest);

        return mapReturnValue;
    }
    /**
     * 分转元 int类型
    * @Title: fen2yuanByInt 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param amount
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String fen2yuanByInt(int amount){
        return String.valueOf(amount/100);
    }
    /**
     * 分转万元 int类型
    * @Title: fen2yuanByInt 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param amount
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String fen2WByInt(Long amount){
        return String.valueOf(amount/1000000);
    }
    /**
     * 元转分 int类型
    * @Title: fen2yuanByInt 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param amount
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static int yuan2fenByInt(String amount){
        return Integer.parseInt(amount)*100;
    }
    /**
     * 分转元
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String fen2yuan(String amount) throws Exception {
        if (!amount.matches("\\-?[0-9]+")) {
          throw new Exception("金额格式有误");
        }
        return String.format("%.02f", BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100))).toString();
      }
    /**
     * 元转分
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static int yuan2fen(String amount)  {
        double d = Double.parseDouble(amount)*100;
        return (int)d;
      }
  //=========================== Encode and Decode Method ========================== 
    /**
     * Base64 加密
     *
     * @param target 待加密的目标字符�?     * @param encoder 字符编码格式
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String base64Encode(String target, String encode) throws UnsupportedEncodingException {
        BASE64Encoder base64Encoder = null;

        if (target == null || "".equals(target)) {
            return "";
        }

        if (encode == null || "".equals(encode)) {
            encode = "utf-8";
        }

        base64Encoder = new BASE64Encoder();

        return base64Encoder.encode(target.getBytes(encode));
    }

    public static String base64Encode(byte[] target) throws UnsupportedEncodingException {
        BASE64Encoder base64Encoder = null;

        if (target == null || target.length == 0) {
            return "";
        }

        base64Encoder = new BASE64Encoder();

        return base64Encoder.encode(target);
    }

    /**
     * Base64 解密
     *
     * @param target 待解密的目标字符�?     * @param encode 字符编码格式
     * @return String
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String base64Decode(String target, String encode) throws UnsupportedEncodingException, IOException {
        BASE64Decoder base64Decoder = null;

        if (target == null || "".equals(target)) {
            return "";
        }

        if (encode == null || "".equals(encode)) {
            encode = "utf-8";
        }

        base64Decoder = new BASE64Decoder();

        return new String(base64Decoder.decodeBuffer(target), encode);
    }

    /**
     * Base64 解密
     *
     * @param target
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static byte[] base64Decode(String target) throws UnsupportedEncodingException, IOException {
        BASE64Decoder base64Decoder = null;

        if (target == null || "".equals(target)) {
            return null;
        }

        base64Decoder = new BASE64Decoder();

        return base64Decoder.decodeBuffer(target);
    }
    /**
     * 用BigDecimal精确计算的加法  <br/>
     * 
     * @param source
     * @param dest
     * @return
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException 
     */
    public static String cashAdd(Object source, Object dest) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BigDecimal bSource = null;
        BigDecimal bDest = null;
        Map<String, BigDecimal> mapReturnValue = changeType2BigDecimal(source, dest);

        bSource = mapReturnValue.get("source");
        bDest = mapReturnValue.get("dest");

        return bSource.add(bDest).toString();
    }

    public static String cashSubtract(Object source, Object dest) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BigDecimal bSource = null;
        BigDecimal bDest = null;
        Map<String, BigDecimal> mapReturnValue = changeType2BigDecimal(source, dest);

        bSource = mapReturnValue.get("source");
        bDest = mapReturnValue.get("dest");

        return bSource.subtract(bDest).toString();
    }

    public static String cashDivide(Object source, Object dest) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BigDecimal bSource = null;
        BigDecimal bDest = null;
        Map<String, BigDecimal> mapReturnValue = changeType2BigDecimal(source, dest);

        bSource = mapReturnValue.get("source");
        bDest = mapReturnValue.get("dest");

        return bSource.divide(bDest).toString();
    }

    public static String cashMultiply(Object source, Object dest) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BigDecimal bSource = null;
        BigDecimal bDest = null;
        Map<String, BigDecimal> mapReturnValue = changeType2BigDecimal(source, dest);

        bSource = mapReturnValue.get("source");
        bDest = mapReturnValue.get("dest");

        return bSource.multiply(bDest).toString();
    }

    //=========================== 字符串操作常用方法 ==============================
    
    /**
     * 按长度生成占位符
     * @param leng
     * @param mark
     * @return 
     */
    public static String getMarkByLeng(int leng, String mark) {
        String str = "";
        for (int i = 0; i < leng; i ++) {
            str += mark;
        }
        
        return str;
    }
    /**
     * 当字符串为null的时候替换成字段的字符串
     *
     * @param source
     * @param dest
     * @return
     */
    public static String replaceNull(String source, String dest) {
        return source == null ? dest : source;
    }

    public static void fillStatementWithParams(PreparedStatement stat, Object[] params) throws SQLException {

        if (stat == null || params == null) {
            return;
        }

        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof String) {
                stat.setString(i + 1, (String) params[i]);
            } else if (params[i] instanceof Integer) {
                stat.setInt(i + 1, (Integer) params[i]);
            } else if (params[i] instanceof Short) {
                stat.setShort(i + 1, (Short) params[i]);
            } else if (params[i] instanceof Long) {
                stat.setLong(i + 1, (Long) params[i]);
            } else if (params[i] instanceof Float) {
                stat.setFloat(i + 1, (Float) params[i]);
            } else if (params[i] instanceof Double) {
                stat.setDouble(i + 1, (Double) params[i]);
            } else if (params[i] instanceof Byte) {
                stat.setByte(i + 1, (Byte) params[i]);
            } else if (params[i] instanceof Boolean) {
                stat.setBoolean(i + 1, (Boolean) params[i]);
            } else if (params[i] instanceof java.sql.Timestamp) {
                stat.setTimestamp(i + 1, (java.sql.Timestamp) params[i]);
            } else if (params[i] instanceof java.sql.Time) {
                stat.setTime(i + 1, (java.sql.Time) params[i]);
            } else if (params[i] instanceof java.sql.Date) {
                stat.setDate(i + 1, (java.sql.Date) params[i]);
            } else if (params[i] instanceof java.util.Date) {
                java.util.Date date = (java.util.Date) params[i];
                stat.setTimestamp(i + 1, new java.sql.Timestamp(date.getTime()));
            }
        }
    }

    /**
     * 替换字符串中的第一个指定占位符
     *
     * @param srcStr
     * @param destStr
     * @param mark
     * @return
     */
    public static String replaceFirstMark(String srcStr, Object tagObj, String mark) {
        String destStr = "";

        if (mark != null && mark.equals("?")) {
            mark = "\\?";
        } else if (mark != null && mark.equals("+")) {
            mark = "\\+";
        } else if (mark != null && mark.equals(".")) {
            mark = "\\.";
        } else if (mark != null && mark.equals(":")) {
            mark = "\\:";
        } else if (mark != null && mark.equals("*")) {
            mark = "\\*";
        }

        if (tagObj == null) {
            destStr = "''";
        }

        if (tagObj instanceof String) {
            destStr = "'" + (String) tagObj + "'";
        } else if (tagObj instanceof Integer) {
            destStr = (Integer) tagObj + "";
        } else if (tagObj instanceof Short) {
            destStr = (Short) tagObj + "";
        } else if (tagObj instanceof Long) {
            destStr = (Long) tagObj + "";
        } else if (tagObj instanceof Float) {
            destStr = (Float) tagObj + "";
        } else if (tagObj instanceof Double) {
            destStr = (Double) tagObj + "";
        } else if (tagObj instanceof Byte) {
            destStr = (Byte) tagObj + "";
        } else if (tagObj instanceof Boolean) {
            destStr = (Boolean) tagObj + "";
        } else if (tagObj instanceof java.sql.Date) {
            destStr = "'" + (java.sql.Date) tagObj + "'";
        } else if (tagObj instanceof java.sql.Time) {
            destStr = (java.sql.Time) tagObj + "";
        } else if (tagObj instanceof java.sql.Timestamp) {
            destStr = "'" + (java.sql.Timestamp) tagObj + "'";
        } else if (tagObj instanceof java.util.Date) {
            destStr = "'" + (java.util.Date) tagObj + "'";
        }

        srcStr = srcStr.replaceFirst(mark, destStr);
        return srcStr;
    }

    /**
     * 替换字符串中的所有指定占位符
     *
     * @param srcStr
     * @param destList
     * @param mark
     * @return
     */
    public static String replaceAllMark(String srcStr, List<Object> destList, String mark) {
        for (Object destStr : destList) {
            srcStr = replaceFirstMark(srcStr, destStr, mark);
        }
        return srcStr;
    }

    /**
     * 替换字符串中的所有指定占位符
     *
     * @param srcStr
     * @param destList
     * @param mark
     * @return
     */
    public static String replaceAllMark(String srcStr, Object[] destList, String mark) {
        for (Object destStr : destList) {
            srcStr = replaceFirstMark(srcStr, destStr, mark);
        }
        return srcStr;
    }
    
    /**
     * 替换字符串中指定占位符
     *
     * @param srcStr
     * @param dest
     * @param mark
     * @return
     */
    public static String replaceAllMark(String srcStr, String dest, String mark) {

        if (mark != null && mark.equals("?")) {
            mark = "\\?";
        } else if (mark != null && mark.equals("+")) {
            mark = "\\+";
        } else if (mark != null && mark.equals(".")) {
            mark = "\\.";
        } else if (mark != null && mark.equals(":")) {
            mark = "\\:";
        } else if (mark != null && mark.equals("*")) {
            mark = "\\*";
        } else if (mark != null && mark.equals("$")) {
            mark = "\\$";
        }

        srcStr = srcStr.replaceFirst(mark, dest);


        return srcStr;
    }

    /**
     * 生成随机码
     *
     * @param length 生成多长的随机码
     * @return
     */
    public static String generateRandomCode(int length) {
        Random random = new Random();
        String val = "";
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) {       // 字符串
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) {     // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    
    /**
     * 生成某个范围内的随机数
     * 
     * @param min
     * @param max
     * @return 
     */
    public static int getRandom(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
    
    /**
     * 生成订单号
     * 
     * @param date
     * @return 
     */
    public static String generateOrderNo(Date date) {
        return formatDate(date, "yyyyMMddHHmmss") + generateRandomCode(7);
    }
    
    /**
     * 生成随机码
     *
     * @param length 生成多长的随机码
     * @return
     */
    public static String generateRandomNumber(int length) {
        SecureRandom random = new SecureRandom();
        String val = "";
        for (int i = 0; i < length; i++) {
        	random.setSeed(random.generateSeed(8));
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }
    
    /**
     * 生成一个长度为21位的一段唯一串码
     * 
     * @return 
     */
    public static String generateUniqueCodes() {
        return formatDate(new Date(), "yyyyMMddHHmmss") + "-" + generateRandomCode(6);
    }
    
    /**
     * 生成一个长度为12位的一段唯一串码
     * 
     * @return 
     */
    public static String generateUniqueCodes12() {
        return formatDate(new Date(), "yyyyMM") + generateRandomCode(6);
    }
    
    /**
     * 生成一个长度为15位的一段唯一串码
     * 
     * @return 
     */
    public static String generateUniqueCodes15() {
        return formatDate(new Date(), "yyyyMMdd") + generateRandomCode(7);
    }

    /**
     * 角度装换成弧度
     *
     * @param d
     * @return
     */
    public static double rad(double d) {
        return d * Math.PI / 180;
    }

    /**
     * 计算连个经纬度之间的距离km为单位
     *
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @return
     */
    public static double calculate(double lat1, double lat2, double lon1, double lon2) {

        if (Math.abs(lat1) > 90 || Math.abs(lat2) > 90) {
            return 0;
        }

        if (Math.abs(lon1) > 180 || Math.abs(lon2) > 180) {
            return 0;
        }

        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378.137;
        s = (double) Math.round(s * 10000) / 10000;
        return s;
    }

    //=========================== Encode and Decode Method ========================== 
    /**
     * Base64 加密
     *
     * @param target 待加密的目标字符串
     * @param encoder 字符编码格式
     * @return String
     * @throws UnsupportedEncodingException
     */
//    public static String base64Encode(String target, String encode) throws UnsupportedEncodingException {
//        BASE64Encoder base64Encoder = null;
//
//        if (target == null || "".equals(target)) {
//            return "";
//        }
//
//        if (encode == null || "".equals(encode)) {
//            encode = "utf-8";
//        }
//
//        base64Encoder = new BASE64Encoder();
//
//        return base64Encoder.encode(target.getBytes(encode));
//    }

//    public static String base64Encode(byte[] target) throws UnsupportedEncodingException {
//        BASE64Encoder base64Encoder = null;
//
//        if (target == null || target.length == 0) {
//            return "";
//        }
//
//        base64Encoder = new BASE64Encoder();
//
//        return base64Encoder.encode(target);
//    }

    /**
     * Base64 解密
     *
     * @param target 待解密的目标字符串
     * @param encode 字符编码格式
     * @return String
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
//    public static String base64Decode(String target, String encode) throws UnsupportedEncodingException, IOException {
//        BASE64Decoder base64Decoder = null;
//
//        if (target == null || "".equals(target)) {
//            return "";
//        }
//
//        if (encode == null || "".equals(encode)) {
//            encode = "utf-8";
//        }
//
//        base64Decoder = new BASE64Decoder();
//
//        return new String(base64Decoder.decodeBuffer(target), encode);
//    }

    /**
     * Base64 解密
     *
     * @param target
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
//    public static byte[] base64Decode(String target) throws UnsupportedEncodingException, IOException {
//        BASE64Decoder base64Decoder = null;
//
//        if (target == null || "".equals(target)) {
//            return null;
//        }
//
//        base64Decoder = new BASE64Decoder();
//
//        return base64Decoder.decodeBuffer(target);
//    }

    /**
     * MD5
     *
     * @param target
     * @return String
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String target) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(target.getBytes("utf-8"));
        byte[] md5Bytes = digest.digest();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < md5Bytes.length; i++) {
            sb.append(Character.forDigit(md5Bytes[i] >>> 4 & 0xf, 16));
            sb.append(Character.forDigit(md5Bytes[i] & 0xf, 16));
        }

        return sb.toString();
    }
    // 加密后解密   
    public static String md5JM(String inStr) {   
     char[] a = inStr.toCharArray();   
     for (int i = 0; i < a.length; i++) {   
      a[i] = (char) (a[i] ^ 't');   
     }   
     String k = new String(a);   
     return k;   
    }  

    /**
     * MD5 byte 数组
     *
     * @param src
     * @return byte[]
     * @throws NoSuchAlgorithmException
     */
    public static byte[] md5(byte[] src) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(src);
        byte[] result = digest.digest();
        return result;
    }

    /**
     * DES 加密
     *
     * @param src
     * @param rawSecretKey
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encryptWithDES(byte[] src, byte[] rawSecretKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        DESKeySpec desKeySpec = new DESKeySpec(rawSecretKey);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = secretKeyFactory.generateSecret(desKeySpec);
        SecureRandom secureRandom = new SecureRandom();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);

        return cipher.doFinal(src);
    }

    /**
     * DES 解密
     *
     * @param encryptData
     * @param rawSecretKey
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decryptWithDES(byte[] encryptData, byte[] rawSecretKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        DESKeySpec desKeySpec = new DESKeySpec(rawSecretKey);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = secretKeyFactory.generateSecret(desKeySpec);
        SecureRandom secureRandom = new SecureRandom();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, secureRandom);

        return cipher.doFinal(encryptData);
    }

    //========================== Common Verified Method ========================
    /**
     * 验证手机号 格式027-82609562 或 82696523 或 13476565335 都正确
     *
     * @param obj
     * @return boolean
     */
    public static boolean isMobileNo(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj instanceof String) {
            String str = (String) obj;
            if (str.matches("\\d{8}|\\d{11}|(\\d{3}-\\d{8})")) {
                return true;
            }
        }

        if (obj instanceof Integer) {
            String str = ((Integer) obj).toString();
            if (isMobileNo(str)) {
                return true;
            }
        }

        if (obj instanceof Long) {
            String str = ((Long) obj).toString();
            if (isMobileNo(str)) {
                return true;
            }
        }

        return false;
    }
    
    public static boolean isAvailablePwd(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj instanceof String) {
            String str = (String) obj;
            if (str.matches("^[a-z0-9]+$")) {
                return true;
               
            }
            
        }

        if (obj instanceof Integer) {
            String str = ((Integer) obj).toString();
            if (isAvailablePwd(str)) {
                return true;
               
            }
           
        }

        if (obj instanceof Long) {
            String str = ((Long) obj).toString();
            if (isAvailablePwd(str)) {
                return true;
            }
            
        }
       
        return false;
      
    }
    
    /**
     * 验证开始时间和结束时间得正确性
     * 
     * @param startDateString
     * @param endDateString
     * @param format
     * @return 
     */
    public static boolean validateStartAndEndDate(String startDateString, String endDateString, String format) {
        boolean result = true;
        
        if (isEmpty(startDateString) || isEmpty(endDateString) || startDateString.length() != endDateString.length()) {
            return false;
        }
        
        if (isEmpty(format)) {
            format = "yyyy-MM-dd";
        }
        
        try {
            Date startDate = parseString2Date(startDateString, format);
            Date endDate = parseString2Date(endDateString, format);
            
            if (startDate.getTime() > endDate.getTime()) {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        
        return result;
    }

    /**
     * 判断一个对象是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {

        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            String str = (String) obj;
            if ("".equals(str)) {
                return true;
            }
        }

        if (obj instanceof Collection) {
            Collection c = (Collection) obj;
            if (c.isEmpty()) {
                return true;
            }
        }

        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (map.isEmpty()) {
                return true;
            }
        }

        if (obj.getClass().isArray()) {
            if (Array.getLength(obj) <= 0) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * 把null转化为字符串的空
     * 
     * @param obj
     * @return 
     */
    public static Object null2Empty(Object obj) {
        if (obj == null) {
            return "";
        }
        
        return obj;
    }

    /**
     * 验证UserID
     *
     * @param uid
     * @return
     */
    public static boolean isUid(String uid) {
        if (uid == null) {
            return false;
        }

        if (uid.matches("[0-9|a-z|A-Z]{8}(-[0-9|a-z|A-Z]{4}){3}-[0-9|a-z|A-Z]{12}")) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否为数字
     *
     * @param obj
     * @return boolean
     */
    public static boolean isNumeric(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj instanceof String) {
            String str = (String) obj;
            if (str.matches("^(-)?\\d+$")) {
                return true;
            }
        }

        if (obj instanceof Integer) {
            return true;
        }

        return false;
    }
    
    /**
     * 判断一个对象是否是字符串类型，且在限定长度内
     * 
     * @param obj
     * @param lengthLimit
     * @param sizeComparisonType lower 小于限定数 equals 等于限定数 higher 大于限定数
     * @return 
     */
    public static boolean isString(Object obj,int lengthLimit,String sizeComparisonType) {
        
        if (obj == null) 
            return false;
        
        if (sizeComparisonType == null) 
            sizeComparisonType = "";
        
        if (obj instanceof String) {
            String str = (String) obj;
            
            if ("".equals(str))
                return false;
            
            if ("higher".equals(sizeComparisonType)) {
                if (str.length() > lengthLimit)  
                    return true;
                
            } else if ("equals".equals(sizeComparisonType)) {
                if (str.length() == lengthLimit)  
                    return true;
                
            } else {
                if (str.length() < lengthLimit)  
                    return true;
            }
        } 
        
        return false;
    }
    
    /**
     * 验证email格式
     * 
     * @param email
     * @return 
     */
    public static boolean isEmail(String email) {
        
        if (email == null) return false;
        
        if (email.matches("(\\w+)(\\.\\w+)*@(\\w+)(\\.\\w+)+")) return true; 
        
        return false;
    }
    
    /**
     * 验证ip格式 （只校验ip4）
     * 
     * @param ip
     * @return 
     */
    public static boolean isIp(String ip) {
        
        if (ip == null) return false;
        
        if (ip.matches("(\\d{1,3}\\.){3}\\d{1,3}")) return true; 
        
        return false;
    }

    /**
     * 判断是否为Double型
     *
     * @param obj
     * @return boolean
     */
    public static boolean isDouble(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj instanceof String) {
            String str = (String) obj;
            if (str.matches("(-)?\\d+(\\.\\d+)?")) {
                return true;
            }
        }

        if (obj instanceof Double) {
            return true;
        }
        return false;
    }

    /**
     * 计算组合c(m,n)
     *
     * @param m
     * @param n
     * @return
     */
    public static int combination(int m, int n) {

        if (m == 0 || n == 0 || m == n) {
            return 1;
        } else if (m < 0 || n < 0 || m < n) {
            return -1;
        }

        n = n > (m - n) ? (m - n) : n;

        int result = 1;
        for (int i = m, j = 1; j <= n; i--, j++) {
            result = result * i / j;
        }
        return result;
    }

    /**
     * 计算组合p(m,n)
     *
     * @param m
     * @param n
     * @return
     */
    public static int permutation(int m, int n) {

        if (m == 0 || n == 0) {
            return 1;
        } else if (m < 0 || n < 0 || m < n) {
            return -1;
        }

        int result = 1;
        for (int i = m, j = 1; j <= n; i--, j++) {
            result = result * i;
        }
        return result;
    }

//=========================== 下面是常用的对时间的操作 ==========================================
    /**
     * 按指定格式，格式化日期
     */
    public static String formatMillis(String millisecond, String format) {
        Date date;
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (millisecond != null && millisecond.matches("\\d+")) {
            date = new Date(Long.parseLong(millisecond));
            return sdf.format(date);
        }
        return "";
    }

    /**
     * 按指定格式，格式化日期
     */
    public static String formatDate(Date date, String format) {
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 按指定格式，格式化日期
     */
    public static String formatDate(long timestamp, String format) {
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(timestamp));
    }

    /**
     * 解析字符串到指定日期
     *
     * @throws ParseException
     */
    public static Date parseString2Date(String sDate, String format) throws ParseException {
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(sDate);
    }
    
    /**
     * 解析日期字符串
     * @param sDate
     * @return 
     */
    public static String parseStringDate(String sDate){
        if (CommonUtil.isEmpty(sDate)||sDate.endsWith("00000000")) {
            return "";
        } else if (sDate.length() == 6) {
            return sDate.substring(0, 4) + "/" + sDate.substring(4, 6);
        } else if (sDate.length() == 8) {
            return sDate.substring(0, 4) + "/" + sDate.substring(4, 6) + "/" + sDate.substring(6, 8);
        } else if(sDate.length() == 10){
            return sDate.replaceAll("-", "/");
        }else{
            return sDate;
        }
    }
    
    /**
     * 解析字符串到指定日期
     *
     * @throws ParseException
     */
    public static Date parseString2DateByUS(String sDate, String format) throws ParseException {
        if (format == null || "".equals(format)) {
            format = "dd/MMM/yyyy:HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.parse(sDate);
    }

    public static String showDayOfWeek(String dayString) {
        String[] days = null;
        String result = "";

        if (dayString == null) {
            return "";
        }

        days = dayString.split(",");

        for (String day : days) {

            if ("1".equals(day)) {
                result += "星期一 ";
            } else if ("2".equals(day)) {
                result += "星期二 ";
            } else if ("3".equals(day)) {
                result += "星期三 ";
            } else if ("4".equals(day)) {
                result += "星期四 ";
            } else if ("5".equals(day)) {
                result += "星期五 ";
            } else if ("6".equals(day)) {
                result += "星期六 ";
            } else if ("7".equals(day)) {
                result += "星期日 ";
            }
        }

        return result;
    }

    /**
     * 计算指定时间前后的日期
     *
     * @param incrementYear
     * @param incrementMonth
     * @param incrementDay
     * @param incrementHour
     * @param incrementMin
     * @param incrementSec
     * @return
     */
    public static Date calculatorDate(int incrementYear, int incrementMonth, int incrementDay, int incrementHour, int incrementMin, int incrementSec) {
        Calendar c = Calendar.getInstance();

        if (incrementYear != 0) {
            c.add(Calendar.YEAR, incrementYear);
        }
        if (incrementMonth != 0) {
            c.add(Calendar.MONTH, incrementMonth);
        }
        if (incrementDay != 0) {
            c.add(Calendar.DAY_OF_MONTH, incrementDay);
        }
        if (incrementHour != 0) {
            c.add(Calendar.HOUR_OF_DAY, incrementHour);
        }
        if (incrementMin != 0) {
            c.add(Calendar.MINUTE, incrementMin);
        }
        if (incrementSec != 0) {
            c.add(Calendar.SECOND, incrementSec);
        }

        return c.getTime();
    }

    /**
     * 计算指定时间前后的日期
     *
     * @param date
     * @param incrementYear
     * @param incrementMonth
     * @param incrementDay
     * @param incrementHour
     * @param incrementMin
     * @param incrementSec
     * @return
     */
    public static Date calculatorDate(Date date, int incrementYear, int incrementMonth, int incrementDay, int incrementHour, int incrementMin, int incrementSec) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        if (incrementYear != 0) {
            c.add(Calendar.YEAR, incrementYear);
        }
        if (incrementMonth != 0) {
            c.add(Calendar.MONTH, incrementMonth);
        }
        if (incrementDay != 0) {
            c.add(Calendar.DAY_OF_MONTH, incrementDay);
        }
        if (incrementHour != 0) {
            c.add(Calendar.HOUR_OF_DAY, incrementHour);
        }
        if (incrementMin != 0) {
            c.add(Calendar.MINUTE, incrementMin);
        }
        if (incrementSec != 0) {
            c.add(Calendar.SECOND, incrementSec);
        }

        return c.getTime();
    }

    /**
     * 计算两个日期相差的时间，单位根据type定义
     *
     * @param starttime 开始时间
     * @param endttime 结束时间
     * @param type 是单位计算，1 年 2 月 3 日 4 时 5 分 6 秒 默认 毫秒
     * @return
     */
    public static long diffDates(long starttime, long endtime, int type) {
        long result = endtime - starttime;

        switch (type) {
            case 1:
                result = result / (1000 * 60 * 60 * 24 * 365);
                break;
            case 2:
                result = result / (1000 * 60 * 60 * 24 * 30);
                break;
            case 3:
                result = result / (1000 * 60 * 60 * 24);
                break;
            case 4:
                result = result / (1000 * 60 * 60);
                break;
            case 5:
                result = result / (1000 * 60);
                break;
            case 6:
                result = result / 1000;
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 计算两个日期相差的时间，单位根据type定义
     *
     * @param startdate 开始时间
     * @param enddate 结束时间
     * @param type	type 是单位计算，1 年 2 月 3 日 4 时 5 分 6 秒 默认 毫秒
     * @return
     */
    public static long diffDates(Date startdate, Date enddate, int type) {
        return diffDates(startdate.getTime(), enddate.getTime(), type);
    }

    //============================= Parse XML Methods =========================//
    /**
     * 遍历 xml
     *
     * @param node
     * @param nodeNames
     * @param resultData
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    //to be completed
//    public static void traversalOfXML(Node node, List<String> nodeNames, Map<String, String> resultData) throws ParserConfigurationException, SAXException, IOException {
//
//        NodeList childNodes = node.getChildNodes();
//        Node childNode = null;
//
//        for (int i = 0; i < childNodes.getLength(); i++) {
//            childNode = childNodes.item(i);
//
//            if (childNode.hasChildNodes()) {
//                traversalOfXML(childNodes.item(i), nodeNames, resultData);
//            }
//
//            
//
//            if (nodeNames.contains(childNode.getNodeName())) {
//                resultData.put(childNode.getNodeName(), childNode.getTextContent());
//            }
//        }
//    }

    //========================== 对byte的一些常用操作 =============================
    /**
     * 把 int 或是 short 转换成byte数组
     *
     * @param target
     * @return
     */
    public static byte[] intOrShortToByteArray(Object target) {
        byte[] buffer = null;
        int size = 0;
        int offset = 0;

        if (target == null) {
            return null;
        }

        if (target instanceof Integer) {
            size = ((Integer) target).SIZE / 8;
            int numeric = (Integer) target;
            buffer = new byte[size];

            for (int i = 0; i < size; i++) {
                offset = (size - 1 - i) * 8;
                buffer[i] = (byte) ((numeric >>> offset) & 0xff);
            }
        } else if (target instanceof Short) {
            size = ((Short) target).SIZE / 8;
            short numeric = (Short) target;
            buffer = new byte[size];

            for (int i = 0; i < size; i++) {
                offset = (size - 1 - i) * 8;
                buffer[i] = (byte) ((numeric >>> offset) & 0xff);
            }
        }

        return buffer;
    }

    /**
     *
     * @param s
     * @return
     */
    public static byte[] shortToByteArray(short s) {
        byte[] shortBuf = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (shortBuf.length - 1 - i) * 8;
            shortBuf[i] = (byte) ((s >>> offset) & 0xff);
        }
        return shortBuf;
    }

    /**
     * 把byte数组转换成16进制
     *
     * @param byteArray
     * @return
     */
    public static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder result = new StringBuilder();

        if (byteArray == null) {
            return null;
        }

        for (int i = 0; i < byteArray.length; i++) {
            result.append(Character.forDigit(byteArray[i] >>> 4 & 0xf, 16));
            result.append(Character.forDigit(byteArray[i] & 0xf, 16));
        }

        return result.toString();
    }

    /**
     * 拼接两个byte数组
     *
     * @param front
     * @param back
     * @return
     */
    public static byte[] concat(byte[] front, byte[] back) {
        byte[] result = null;
        int index = 0;

        if (front == null && back != null) {
            return intercept(back, 0, back.length);
        } else if (front != null && back == null) {
            return intercept(front, 0, front.length);
        } else if (front == null && back == null) {
            return null;
        }

        result = new byte[front.length + back.length];

        for (int i = 0; i < front.length; i++) {
            result[index++] = front[i];
        }

        for (int i = 0; i < back.length; i++) {
            result[index++] = back[i];
        }
        return result;
    }

    /**
     * 截取byte数组 返回新截取数据
     *
     * @param src
     * @param offset
     * @param length
     * @return
     */
    public static byte[] intercept(byte[] src, int offset, int length) {
        byte[] newArray = new byte[length];
        System.arraycopy(src, offset, newArray, 0, length);
        return newArray;
    }

    /**
     *
     * @param src 原始字节数组
     * @param filledObject 用什么字节填充
     * @param length 填充后字节数组的长度
     * @param type 填充类型 1 左填充 3 右填充
     * @return
     */
    public static byte[] fillByteArray(byte[] src, byte filledByte, int length, int type) {
        byte[] result = new byte[length];
        if (type == 1) {
            int num = length - src.length;
            num = num > 0 ? num : 0;
            for (int i = 0; i < num; i++) {
                result[i] = filledByte;
            }

            for (int i = 0; i < length - num; i++) {
                result[num + i] = src[i];
            }
        } else if (type == 2) {
            int num = length - src.length;
            num = num > 0 ? src.length : length;

            for (int i = 0; i < num; i++) {
                result[i] = src[i];
            }

            for (int i = num; i < length; i++) {
                result[i] = filledByte;
            }
        }

        return result;
    }

    public static long byteArrayToUnsignedNumeric(byte[] src) {
        long result = 0;

        if (src == null) {
            return 0;
        }

        for (int i = 0; i < src.length; i++) {
            result = (long) result | ((long) (src[i] & 0xff) << (src.length - 1 - i) * 8);
        }

        //Testing Codes
		/*
         *  result = (long) 0xff << 8
         * |(long) 0xff << 0 ;
         */
        //Testing Codes
        return result;
    }

    public static byte[] unsignedNumericToByteArray(long src, int length) {
        byte[] result = new byte[length];

        for (int i = 0; i < length; i++) {
            result[i] = (byte) (src >>> (length - 1 - i) * 8 & 0xff);
        }

        return result;
    }

    public static byte[] hexStringToByteArray(String src) {
        byte[] result = null;
        src = src.toLowerCase();
        
        Map<String, Byte> mappingTable = new HashMap<String, Byte>() {

            {
                put("0", (byte) 0x00);
                put("1", (byte) 0x01);
                put("2", (byte) 0x02);
                put("3", (byte) 0x03);
                put("4", (byte) 0x04);
                put("5", (byte) 0x05);
                put("6", (byte) 0x06);
                put("7", (byte) 0x07);
                put("8", (byte) 0x08);
                put("9", (byte) 0x09);
                put("a", (byte) 0x0a);
                put("b", (byte) 0x0b);
                put("c", (byte) 0x0c);
                put("d", (byte) 0x0d);
                put("e", (byte) 0x0e);
                put("f", (byte) 0x0f);
            }
        };

        if (src == null || src.length() == 0) {
            return null;
        }
        int size = src.length() / 2;
        result = new byte[size];

        for (int i = 0, j = 0; i < src.length() && j < size; i += 2) {
            result[j++] = (byte) (mappingTable.get(src.charAt(i) + "") << 4 | mappingTable.get(src.charAt(i + 1) + ""));
        }

        return result;
    }

    //============================= Processing Image Methods======================================
    public static byte[] convertImageFormat(byte[] srcImage, String formatName) throws IOException {
        InputStream is = new ByteArrayInputStream(srcImage);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedImage image = ImageIO.read(is);

        if (!ImageIO.write(image, formatName, os)) {
            throw new RuntimeException("No Support Image Format");
        }

        return os.toByteArray();
    }
    
    //============================= Testing Method=================================================
    public static void printList(List<Map<String, String>> list) {
        for (Map<String, String> map : list) {
            for (Iterator<Map.Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, String> me = it.next();
                
            }
            
        }
    }

    public static void printMap(Map<String, String> map) {
        for (Iterator<Map.Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> me = it.next();
            
        }
    }
    
    /**
     * 根据key获取value
     * @param map
     * @param key
     * @return 
     */
    public static String getParam(Map<String, String> map, String key){
    
        if (CommonUtil.isEmpty(map)) {
        
            return "";
        }
        
        return (CommonUtil.isEmpty(map.get(key))) ? "" : map.get(key);
    }
    
    public static String supplementZero(String value){
    	StringBuffer buf = new StringBuffer();
    	int length = 12-value.length();
    	for(int i=0;i<length;i++){
    		buf.append(0);
    	}
    	buf.append(value);
    	return buf.toString();
    }
    
    /**
     * 根据文件名获得文件的后最
     * 
     * @param fileName
     * @return 
     */
    public static String getFileType(String fileName) {
		String str = fileName;
		str = str.substring(str.lastIndexOf(".") + 1, str.length())
				.toLowerCase();
		return str;
	}
    // 可逆的加密算法   
    public static String KL(String inStr) {   
     // String s = new String(inStr);   
     char[] a = inStr.toCharArray();   
     for (int i = 0; i < a.length; i++) {   
      a[i] = (char) (a[i] ^ 't');   
     }   
     String s = new String(a);   
     return s;   
    }
    /**
     * 将对想序列化为字符串
     * @param obj
     * @return
     */
    public static String getSerializable(Object obj){
    	 String  result = "";
    	 ByteArrayOutputStream byteArrayOutputStream = null;
    	 ObjectOutputStream objectOutputStream = null;
    	try{
	    	byteArrayOutputStream = new ByteArrayOutputStream();  
	        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);  
	        objectOutputStream.writeObject(obj);
	        result = CommonUtil.base64Encode(byteArrayOutputStream.toByteArray());
	        objectOutputStream.close();  
	        byteArrayOutputStream.close();
    	 } catch (FileNotFoundException e) {        
            e.printStackTrace();
            result = "";
        } catch (IOException e) {
            e.printStackTrace();
            result = "";
        }
    	return result;
    }
    /**
     * 反序列化
     * @param serializable
     * @return
     */
    public static Object UnSerializable(String serializable){
    	Object obj = null;
    	// ByteArrayInputStream 可接收一个字节数组 "byte[] "。供反序列化做参数
        ByteArrayInputStream BAIS = null;
        // 反序列化使用的输入流
        ObjectInputStream OIS = null;
    	try{
    		byte[] serByte = base64Decode(serializable);
    		BAIS = new ByteArrayInputStream(serByte);
            OIS = new ObjectInputStream(BAIS);
            obj = OIS.readObject();
    	}catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	 e.printStackTrace();
            System.out.println("序列化时产生错误 ");
        }
    	return obj;
    }
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
    	System.out.println(base64Decode("eyJ0aXRsZSI6IuawtOi0uSIsImRlc2NyaXB0aW9uIjoi5ZOI5bCU5ruo5biC5rC06LS5IiwicHJpY2UiOiIxMDAiLCJudW1iZXIiOiIxIiwib3JkZXJObyI6IjIwMTQxMDI2MTU1MzMwLUViNWEzcCJ9","utf-8"));
    	
	}
    
}
