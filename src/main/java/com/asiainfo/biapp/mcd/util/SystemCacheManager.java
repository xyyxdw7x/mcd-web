package com.asiainfo.biapp.mcd.util;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.biframe.privilege.cache.service.IdAndName;

public class SystemCacheManager
{

    private SystemCacheManager()
    {
        sysCacheContainer = new HashMap();
    }

    public static SystemCacheManager getInstance()
    {
        if(instance == null)
            instance = new SystemCacheManager();
        return instance;
    }

    public String getNameById(String dicName, Object key)
    {
        String res = "";
        if(sysCacheContainer.containsKey(dicName))
        {
            Object obj = sysCacheContainer.get(dicName);
            if(obj instanceof Map)
            {
                Map objMap = (Map)obj;
                Object obj2 = objMap.get(key);
                if(obj2 instanceof String)
                    res = obj2.toString();
                else
                if(obj2 instanceof IdAndName)
                {
                    IdAndName idObj = (IdAndName)obj2;
                    res = idObj.getName();
                }
            } else
            if(obj instanceof String)
                res = obj.toString();
        }
        return res;
    }

    public void setSubCache(String dicName, Map subCache)
    {
        sysCacheContainer.put(dicName, subCache);
    }

    public Map getSysCacheContainer()
    {
        return sysCacheContainer;
    }

    public void setSysCacheContainer(Map sysCacheContainer)
    {
        this.sysCacheContainer = sysCacheContainer;
    }

    private Map sysCacheContainer;
    private static SystemCacheManager instance;
}
