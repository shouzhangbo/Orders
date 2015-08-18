package com.test.order.db.util;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author zhengmingcheng
 */
public class MapHelper {
    
    /**
     * 把HttpServletRequest所有请求参数转换到map中（注意：如果是数组参数的话，只取第一个）
     * 
     * @param request
     * @return 
     */
    public static Map<String,String> paramsToMap(HttpServletRequest request) {
        Map map = request.getParameterMap();
        Map<String,String> resultMap = new HashMap<String,String>();
        String[] paramsArr;
        String param = "";
        
        if (map == null) return resultMap;
        
        for (Iterator it = map.entrySet().iterator() ; it.hasNext() ; ) {
            
            Map.Entry keyAndValue = (Map.Entry) it.next();
            
            // 只保存有只值的参数
            if (keyAndValue.getValue() != null && keyAndValue.getValue() instanceof String) {
                param = (String) keyAndValue.getValue();

            } else if (keyAndValue.getValue() != null && keyAndValue.getValue() instanceof String[]) {
                paramsArr = (String[]) keyAndValue.getValue();
                param = (String) paramsArr[0];

            }
            
            if (!CommonUtil.isEmpty(param)) {
                resultMap.put((String) keyAndValue.getKey(), param);
            }
            
            param = "";
        }
        
        return resultMap;
    }
    
    /**
     * request 中的参数保存到TreeMap中并且排序
     * 
     * @param request
     * @param sort asc 升序 desc 降序
     * @return 
     */
    public static TreeMap<String,String> paramsToTreeMap(HttpServletRequest request,String sort) {
        Map<String,String> paramsMap = paramsToMap(request);
        
        return sortMap(paramsMap, sort);
    }
    
    /**
     * 按url参数拼接方法拼接map中的参数
     * 
     * @param request
     * @param sort asc 升序 desc 降序
     * @return 
     */
    public static String toString(HttpServletRequest request,String sort) {
        return toString(request.getParameterMap(),sort);
    }
    
    /**
     * 把Map保存到TreeMap中并且排序
     * 
     * @param map
     * @param sort asc 升序 desc 降序
     * @return 
     */
    public static TreeMap<String,String> paramsToTreeMap(Map<String,String> map,String sort) {
        
        return sortMap(map, sort);
    }
    
    /**
     * 按url参数拼接方法拼接map中的参数
     * 
     * @param map
     * @param sort asc 升序 desc 降序
     * @return 
     */
    public static String toString(Map<String,String> map,String sort) {
        TreeMap<String,String> treeMap = paramsToTreeMap(map, sort);
        String result = "";
        
        for(Iterator<Map.Entry<String,String>> it = treeMap.entrySet().iterator();it.hasNext();){
                Map.Entry<String,String> entry = it.next();
                result += entry.getKey() + "=" + entry.getValue() + "&";
        }
        
        if (!CommonUtil.isEmpty(result) && result.lastIndexOf("&") == result.length() - 1) {
            result = result.substring(0,result.lastIndexOf("&"));
        }
        
        return result;
    }
    
    /**
     * 按url参数拼接方法拼接map中的参数的值
     * 
     * @param map
     * @param sort asc 升序 desc 降序
     * @return 
     */
    public static String toStringValues(Map<String,String> map,String sort) {
        TreeMap<String,String> treeMap = paramsToTreeMap(map, sort);
        String result = "";
        
        for(Iterator<Map.Entry<String,String>> it = treeMap.entrySet().iterator();it.hasNext();){
                Map.Entry<String,String> entry = it.next();
                result += (CommonUtil.isEmpty(entry.getValue()) ? "":entry.getValue()) + "&";
        }
        
        if (!CommonUtil.isEmpty(result) && result.lastIndexOf("&") == result.length() - 1) {
            result = result.substring(0,result.lastIndexOf("&"));
        }
        
        return result;
    }
    
    /**
     * 排序TreeMap
     * 
     * @param map
     * @param sort asc 升序 desc 降序
     * @return 
     */
    public static TreeMap<String,String> sortMap(Map<String,String> map,String sort) {
        TreeMap<String,String> treeMap = new TreeMap<String,String>(map);
        
        if (sort == null || "".equals(sort)) sort = "asc";
        
        if ("desc".equals(sort)) {
            treeMap = new TreeMap<String,String>(Collections.reverseOrder());
            treeMap.putAll(map);
        }
        
        return treeMap;
    }
    
    /**
     * 过滤map中的空值
     * 
     * @param map 
     */
    public static void filterEmptyValueParams(Map<String,String> map) {
        
        if (CommonUtil.isEmpty(map)) {
            return;
        }
        
        Iterator<Map.Entry<String,String>> it = map.entrySet().iterator();
        
        while (it.hasNext()) {
            
            if (CommonUtil.isEmpty(it.next().getValue()))
                it.remove();
        }
    }
}
