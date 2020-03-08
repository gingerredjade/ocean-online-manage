package com.ocean.util;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityUtil {

    public static String getMetaData(String metaData) {
        try {
            JSONArray jsonArray = new JSONArray();
            if (StringUtils.isBlank(metaData)) {
                return jsonArray.toJSONString();
            }
            String[] infos = metaData.split("\n");
            for (String info : infos) {
                jsonArray.add(info);
            }
            return jsonArray.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> List<T> getEntityList(List<Object[]> list, Class<T> clazz, Object model) {
        List<T> result = new ArrayList<>();
        if (list.isEmpty()) {
            return result;
        }
        Object[] count = list.get(0);
        List<Map> attributeInfoList = getFiledInfo(model);
        Class[] c2 = new Class[attributeInfoList.size()];
        if (attributeInfoList.size() != count.length) {
            return result;
        }
        for (int i = 0; i < attributeInfoList.size(); i++) {
            c2[i] = (Class) attributeInfoList.get(i).get("type");
        }
        try {
            for (Object[] eachObject : list) {
                Constructor<T> constructor = clazz.getConstructor(c2);
                result.add(constructor.newInstance(eachObject));
            }
        } catch (Exception e) {
            return result;
        }
        return result;
    }


    public static <T> T getEntity(List<Object[]> list, Class<T> clazz, Object model) {
        if (list.size() > 0) {
            List<T> tempList = getEntityList(list, clazz, model);
            return tempList.get(0);
        } else {
            return null;
        }
    }


    private static Object getFiledValueByName(String filedName, Object model) {
        try {
            String firstLetter = filedName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + filedName.substring(1);
            Method method = model.getClass().getMethod(getter);
            return method.invoke(model);
        } catch (Exception e) {
            return null;
        }
    }

    private static List<Map> getFiledInfo(Object model) {
        Field[] fields = model.getClass().getDeclaredFields();
        List<Map> list = new ArrayList<>(fields.length);
        Map infoMap;
        for (Field field : fields) {
            infoMap = new HashMap(3);
            infoMap.put("type", field.getType());
            infoMap.put("name", field.getName());
            infoMap.put("value", getFiledValueByName(field.getName(), model));
            list.add(infoMap);
        }
        return list;
    }

}
