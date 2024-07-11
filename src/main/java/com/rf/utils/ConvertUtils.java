package com.rf.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtils {

    public static <T> T copyProperties(Object source,Class<T> targetClass){
        if(source==null){
            return null;
        }

        T target = null;
        try {
            target = targetClass.newInstance();
            BeanUtils.copyProperties(source,target);
            return target;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static <T> List<T> copyListProperties(List sourceList,Class<T> targetClass){
        List<T> targetList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sourceList)) {
            return targetList;
        }
        for (Object source:sourceList){
            T target = copyProperties(source,targetClass);
            targetList.add(target);
        }
        return targetList;

    }



}
