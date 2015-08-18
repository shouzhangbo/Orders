package com.test.order.db.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author zhengxiaoguang
 */
public class JsonUtil {
    
    private final static Log log = LogFactory.getLog(JsonUtil.class);
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 把一个实体转换成json字符串，并且添加respCode和remark字段
     * 
     * @param respCode
     * @param remark
     * @param obj
     * @param clasz
     * @return 
     */
    public static String toJson(String respCode,String remark,Object obj,Class clazz) {
        Map<String,Object> propertiesMap = new HashMap<String,Object>();
        
        propertiesMap.put("respCode", respCode);
        propertiesMap.put("remark", remark);
        
        return addJsonProperties(propertiesMap, obj, clazz);
    }
    
    /**
     * 输出json字符串，并且添加respCode和remark字段
     * 
     * @param respCode
     * @param remark
     * @param jsonString
     * @return 
     */
    public static String toJson(String respCode,String remark,String jsonString) {
        Map<String,Object> propertiesMap = new HashMap<String,Object>();
        
        propertiesMap.put("respCode", respCode);
        propertiesMap.put("remark", remark);
        
        return addJsonProperties(propertiesMap, jsonString);
    }
    
    /**
     * 把一个实体转换成json字符串，并且添加respCode和remark字段
     * 
     * @param respCode
     * @param remark
     * @param obj
     * @return 
     */
    public static String toJson(String respCode,String remark,Object obj) {
        Map<String,Object> propertiesMap = new HashMap<String,Object>();
        
        propertiesMap.put("respCode", respCode);
        propertiesMap.put("remark", remark);
        
        return addJsonProperties(propertiesMap, obj, null);
    }
    
    /**
     * Map to json
     * 
     * @param map
     * @param type
     * @return 
     */
    public static String toJson(Map<String,String> map, Type type) {
        Gson gson = getGson();
        return gson.toJson(map,type);
    }
    
    /**
     * Map to json
     * 
     * @param map
     * @return 
     */
    public static String toJson(Map<String,String> map) {
        Gson gson = getGson();
        return gson.toJson(map);
    }
    
    /**
     * 把一个实体转换成json字符串，并且添加指定的字段
     * 
     * @param propertiesMap
     * @param obj
     * @param clasz
     * @return 
     */
    public static String addJsonProperties(Map<String,Object> propertiesMap,Object obj,Class clazz) {
        String jsonString;
        Gson gson = getGson();
        
        if (clazz != null) {
            jsonString = gson.toJson(obj, clazz);
        } else {
            jsonString = gson.toJson(obj);
        }
        
        return addJsonProperties(propertiesMap, jsonString);
    }
    
    /**
     * 输出json字符串，并且添加指定的字段
     * 
     * @param propertiesMap
     * @param jsonString
     * @return 
     */
    public static String addJsonProperties(Map<String,Object> propertiesMap,String jsonString) {
        JsonObject jsonObject;
        JsonParser jsonParser = new JsonParser();
        Gson gson = getGson();
        
        if (jsonString == null)
            return jsonString;
        
        jsonObject = (JsonObject) jsonParser.parse(jsonString);
        
        for (Iterator<Map.Entry<String,Object>> it = propertiesMap.entrySet().iterator(); it.hasNext() ;) {
            Map.Entry<String,Object> keyAndValue = it.next();
            
            if (keyAndValue.getValue() instanceof String) {
 
                jsonObject.addProperty(keyAndValue.getKey(), (String)keyAndValue.getValue());
            } else if (keyAndValue.getValue() instanceof Boolean) {
                
                jsonObject.addProperty(keyAndValue.getKey(), (Boolean)keyAndValue.getValue());
            } else if (keyAndValue.getValue() instanceof Number) {
                
                jsonObject.addProperty(keyAndValue.getKey(), (Number)keyAndValue.getValue());
            } else if (keyAndValue.getValue() instanceof Character) {
                
                jsonObject.addProperty(keyAndValue.getKey(), (Character)keyAndValue.getValue());
            } else if (keyAndValue.getValue() instanceof List || keyAndValue.getValue() instanceof Map) {

                jsonObject.add(keyAndValue.getKey(), gson.toJsonTree(keyAndValue.getValue()));
            }
        }
        
        jsonString = jsonObject.toString();
        
        return jsonString;
    }
    
    /**
     * 输出json字符串，并且添加指定的字段
     * 
     * @param propertiesMap
     * @param jsonString
     * @return 
     */
    public static String addStringProperties(Map<String,String> propertiesMap,String jsonString) {
        JsonObject jsonObject;
        JsonParser jsonParser = new JsonParser();
        
        if (jsonString == null)
            return jsonString;
        
        jsonObject = (JsonObject) jsonParser.parse(jsonString);
        
        for (Iterator<Map.Entry<String,String>> it = propertiesMap.entrySet().iterator(); it.hasNext() ;) {
            Map.Entry<String,String> keyAndValue = it.next();
            
            jsonObject.addProperty(keyAndValue.getKey(), keyAndValue.getValue());
        }
        
        jsonString = jsonObject.toString();
        
        return jsonString;
    }
    
    /**
     * 把一个实体转换成json字符串，并且添加指定的字段
     * 
     * @param propertiesMap
     * @param obj
     * @return 
     */
    public static String addJsonProperties(Map<String,Object> propertiesMap,Object obj) {
        
        return addJsonProperties(propertiesMap, obj, null);
    }
    
    /**
     * 根据GsonBuilder生成Gson
     * 
     * @param dateFormat
     * @param isOpenExcludeWithoutExposeAnnotation
     * @return 
     */
//    public static Gson getGson(String dateFormat,boolean isOpenExcludeWithoutExposeAnnotation) {
//        
//        if (dateFormat == null || "".equals(dateFormat)) dateFormat = DATE_FORMAT;
//        
//        GsonBuilder gsonBuilder = new GsonBuilder();
//
//        gsonBuilder.setDateFormat(dateFormat);
//                
//        if (isOpenExcludeWithoutExposeAnnotation) {
//            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
//        }
//        
//        // 注册HibernateProxy类的序列化适配器
//        gsonBuilder.registerTypeHierarchyAdapter(HibernateProxy.class, new HibernateProxySerializer());
//        
//        return gsonBuilder.create();
//    }
    
    /**
     * 得到一个自定义默认值的Gson
     * 
     * @return 
     */
    public static Gson getGson() {
        
        return null;//getGson(null,false);
    }
    
    /**
     * 解析JsonString To JsonObject
     * 
     * @param json
     * @return JsonObject
     */
    public static JsonObject parseJson(String json) {
        JsonElement jsonElement;
        JsonObject jsonObject = null;
        JsonParser jsonPaser = new JsonParser();
        try {
            if (CommonUtil.isEmpty(json)) {
                return null;
            }
            jsonElement = jsonPaser.parse(json);
            // 我们接口返回的全部都是JsonObject格式，所以如果是其他的就手动解析一下
            if (!jsonElement.isJsonObject())
                throw new Exception("不是JsonObject格式");
            jsonObject = jsonElement.getAsJsonObject();
        } catch (Exception ex) {
            log.error("解析jsonString异常.JsonString="+json, ex);
        }
        return jsonObject;
    }
    
    /**
     * 解析jsonString到一个Map中，其中把所有基本类型都转化为String类型
     * 
     * @param json
     * @return 
     */
    public static Map<String,Object> parseJsonToMap(String json) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        
        if (CommonUtil.isEmpty(json))
            return resultMap;
        
        JsonObject jsonObject = parseJson(json);
        
        for (Iterator<Map.Entry<String,JsonElement>> it = jsonObject.entrySet().iterator(); it.hasNext() ;) {
            Map.Entry<String,JsonElement> keyAndValue = it.next();
            
            if (keyAndValue.getValue().isJsonArray()) {
                
                resultMap.put(keyAndValue.getKey(), parseJsonArray(keyAndValue.getValue().getAsJsonArray()));
            } else if (keyAndValue.getValue().isJsonObject()) {
            
                resultMap.put(keyAndValue.getKey(), parseJsonObject(jsonObject));
            } else if (keyAndValue.getValue().isJsonNull()) {
            
                resultMap.put(keyAndValue.getKey(), "");
            } else if (keyAndValue.getValue().isJsonPrimitive()) {
                
                resultMap.put(keyAndValue.getKey(), keyAndValue.getValue().getAsString());
            }
        }
        
        return resultMap;
    }
    
    /**
     * 解析JsonObject到一个Map中，其中把所有基本类型都转化为String类型
     * 
     * @param jsonObject
     * @return 
     */
    public static Map<String,Object> parseJsonObject(JsonObject jsonObject) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        
        if (jsonObject == null)
            return resultMap;
        
        for (Iterator<Map.Entry<String,JsonElement>> it = jsonObject.entrySet().iterator(); it.hasNext() ;) {
            Map.Entry<String,JsonElement> keyAndValue = it.next();
            
            if (keyAndValue.getValue().isJsonArray()) {
            
                resultMap.put(keyAndValue.getKey(), parseJsonArray(keyAndValue.getValue().getAsJsonArray()));
            } else if (keyAndValue.getValue().isJsonObject()) {
            
                resultMap.put(keyAndValue.getKey(), parseJsonObject(jsonObject));
            } else if (keyAndValue.getValue().isJsonNull()) {
                
                resultMap.put(keyAndValue.getKey(), "");
            } else if (keyAndValue.getValue().isJsonPrimitive()) {
                
                resultMap.put(keyAndValue.getKey(), keyAndValue.getValue().getAsString());
            }
        }
        
        return resultMap;
    }
    
    /**
     * 解析JsonArray到一个List中，其中把所有基本类型都转化为String类型
     * 
     * @param jsonArray
     * @return 
     */
    public static List<Object> parseJsonArray(JsonArray jsonArray) {
        List<Object> resultList = new ArrayList<Object>();
        JsonElement jsonElement = null;
        
        if (jsonArray == null)
            return resultList;
        
        Iterator<JsonElement> it = jsonArray.iterator();
        
        while(it.hasNext()) {
            jsonElement = it.next();
            
            if (jsonElement.isJsonArray()) {
            
                resultList.add(parseJsonArray(jsonElement.getAsJsonArray()));
            } else if (jsonElement.isJsonObject()) {
            
                resultList.add(parseJsonObject(jsonElement.getAsJsonObject()));
            } else if (jsonElement.isJsonNull()) {
                
                // 这里不解析
            } else if (jsonElement.isJsonPrimitive()) {
                
                resultList.add(jsonElement.getAsString());
            }
            
        }
        
        return resultList;
    }
    
    /**
     * 设置热respCode和remark
     * 
     * @param respCode
     * @param remark
     * @param jsonMap
     * @return 
     */
    public static void setRespCodeAndRespMsg(String respCode, String remark,Map<String,Object> jsonMap) {
        
        jsonMap.put("respCode", respCode);
//        jsonMap.put("remark", remark);
        jsonMap.put("respMsg", remark);
        
    }
    
    public static String setRespCodeAndRespMsg(String respCode, String remark) {
        
        Map<String,String> jsonMap = new HashMap<String,String>();
        
        jsonMap.put("respCode", respCode);
//        jsonMap.put("remark", remark);
        jsonMap.put("respMsg", remark);
        
        return getGson().toJson(jsonMap);
    }
    
    /**
     * 设置热respCode和remark
     * 
     * @param respCode
     * @param remark
     * @param request
     * @param requestAttributeName
     * @param returnViewName
     * @return 
     */
    public static String setRespCodeAndRespMsg(String respCode, String remark, HttpServletRequest request,String requestAttributeName, String returnViewName) {
        Map<String,String> jsonMap = new HashMap<String,String>();
        
        jsonMap.put("respCode", respCode);
//        jsonMap.put("remark", remark);
        jsonMap.put("respMsg", remark);
        
        String jsonString = getGson().toJson(jsonMap);
        request.setAttribute(requestAttributeName, jsonString);
        
        return returnViewName;
    }
    
    /**
     * 设置热respCode和remark
     * 
     * @param respCode
     * @param remark
     * @param jsonMap
     * @param request
     * @param requestAttributeName
     * @param returnViewName
     * @return 
     */
    public static String setRespCodeAndRespMsg(String respCode, String remark,Map<String,Object> jsonMap, HttpServletRequest request,String requestAttributeName, String returnViewName) {
        
        if (jsonMap == null) {
            jsonMap = new HashMap<String,Object>();
        }
        
        jsonMap.put("respCode", respCode);
//        jsonMap.put("remark", remark);
        jsonMap.put("respMsg", remark);
        
        String jsonString = getGson().toJson(jsonMap);
        request.setAttribute(requestAttributeName, jsonString);
        
        return returnViewName;
    }
    
    /**
     * 设置RequetAttribute属性
     * 
     * @param jsonString
     * @param request
     * @param requestAttributeName
     * @param returnViewName
     * @return 
     */
    public static String setRequestAttribute(String jsonString, HttpServletRequest request,String requestAttributeName, String returnViewName) {
        request.setAttribute(requestAttributeName, jsonString);
        return returnViewName;
    }
    
    /**
     * 检查返回来的json数据状态
     * 
     * @param respJson
     * @return String 如果没有错误就返回空，有错误就返回json中的错误信息
     */
//    public static String checkResponseJson(String respJson){
//        
//        
//        try {
//            if (CommonUtil.isEmpty(respJson)) {
//                return "联网请求数据失败！";
//            }
//            
//            JsonObject jsonObject = new JsonParser().parse(respJson).getAsJsonObject();
//            
//            if (!TransStateCode.COMMON_SUCCESS_CODE.equals(jsonObject.get("respCode").getAsString())) {
//                return CommonUtil.isEmpty(jsonObject.get("remark").getAsString()) == false ? 
//                        jsonObject.get("remark").getAsString() : CommonUtil.isEmpty(jsonObject.get("respMsg").getAsString()) == false ?  
//                        jsonObject.get("respMsg").getAsString() : "请求失败！";
//            }
//            
//        } catch (Exception ex) {
//            log.error("解析json数据异常！jsonString＝"+respJson, ex);
//            return "解析json数据异常";
//        }
//        
//        return "";
//    }
    
    /**
     * 组装返回的 json 中响应状体字段 Status
     * 
     * @param respCode
     * @param remark
     * @param respMsg
     * @return 
     */
    public static Map<String,String> createStatusField(String respCode, String remark, String respMsg) {
        Map<String,String> status = new HashMap<String,String>();
        
        status.put("respCode", respCode);
//        status.put("remark", remark);
        status.put("respMsg", respMsg);
        
        return status;
    }
    
    
    /**
     * 设置返回页面的json模型
     * 
     * @param respCode
     * @param remark
     * @param moduleName
     * @param module
     * @param request
     * @param requestAttributeName
     * @param returnViewName
     * @return 
     */
    public static String setResponseJsonModel(String respCode, String respMsg, String moduleName, Object module, HttpServletRequest request,String requestAttributeName, String returnViewName) {
        
        Map<String,Object> jsonMap = new HashMap<String,Object>();;
        
        jsonMap.put("respCode", respCode);
        jsonMap.put("respMsg", respMsg);
        jsonMap.put(moduleName, module);
        
        String jsonString = getGson().toJson(jsonMap);
        request.setAttribute(requestAttributeName, jsonString);
        
        return returnViewName;
    }
}
