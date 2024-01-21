package com.pinting.site.common.utils;

import com.pinting.core.util.PropertiesUtil;
import com.pinting.core.util.StringUtil;

public class GlobEnv extends PropertiesUtil
{

    public GlobEnv()
    {
    }

    public static String getStaticRoot()
    {
        String rootPath = get("server.static");
        if(StringUtil.isBlank(rootPath))
            return null;
        StringBuffer buffer = new StringBuffer();
        if(rootPath.startsWith("http://"))
        {
            buffer.append(rootPath.substring(0, rootPath.indexOf("//") + 2));
            String start = rootPath.substring(rootPath.indexOf("//") + 2);
            if(start.indexOf("/") != -1)
                buffer.append(start.substring(0, start.indexOf("/")));
            else
                buffer.append((new StringBuilder(String.valueOf(start))).append("/").toString());
        }
        return buffer.toString();
    }

    public static String getStaticURL(String path)
    {
        return getURL(get("server.static"), path);
    }

    public static String getStaticZUI(String path)
    {
        return getURL(get("server.zui"), path);
    }

    public static String getWebURL(String path)
    {
        return getURL(get("server.web"), path);
    }

    public static String getPath(String prefixPath)
    {
        if(StringUtil.isBlank(prefixPath))
            return null;
        else
            return get(prefixPath);
    }

    public static String getDownloadURL(String path)
    {
        return getURL(get("server.download"), path);
    }

    private static String getURL(String base, String path)
    {
        if(StringUtil.isBlank(base))
            return null;
        if(StringUtil.isNotBlank(base) && StringUtil.isBlank(path))
            return base;
        String url = null;
        if(base.endsWith("/") && !path.startsWith("/"))
            url = (new StringBuilder(String.valueOf(base))).append(path).toString();
        else
        if(!base.endsWith("/") && path.startsWith("/"))
            url = (new StringBuilder(String.valueOf(base))).append(path).toString();
        else
        if(base.endsWith("/") && path.startsWith("/"))
        {
            base = base.substring(0, base.length() - 1);
            url = (new StringBuilder(String.valueOf(base))).append(path).toString();
        } else
        if(!base.endsWith("/") && !path.startsWith("/"))
            url = (new StringBuilder(String.valueOf(base))).append("/").append(path).toString();
        return url;
    }
}