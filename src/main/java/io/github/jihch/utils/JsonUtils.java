package io.github.jihch.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成 JSON 字符串
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        String string = null;
        try {
            string = MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 将 json 结果集转化为对象
     * @param jsonData json 数据
     * @param beanType 对象中的 object 类型
     * @return
     * @param <T>
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        T t = null;
        try {
            t = MAPPER.readValue(jsonData, beanType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将 json 数据转换成 pojo 对象 list
     * @param jsonData
     * @param beanType
     * @return
     * @param <T>
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        List<T> list = null;
        try {
            list = MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 把 json 转换为 Map 工具方法
     * @param jsonData
     * @param keyType
     * @param valueType
     * @return
     * @param <K>
     * @param <V>
     */
    public static <K, V> Map<K, V> jsonToMap(String jsonData, Class<K> keyType, Class<V> valueType) {
        JavaType javaType = MAPPER.getTypeFactory().constructMapType(Map.class, keyType, valueType);
        Map<K, V> map = null;
        try {
             map = MAPPER.readValue(jsonData, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }

}
