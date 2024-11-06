package com.sxlinks.utils;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils1 {
    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToString(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }
    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间转换成时间戳
     * @param time
     * @return
     */
    public static long dateToTimestamp(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(time);
            long ts = date.getTime() / 1000;
            return ts;
        } catch (ParseException e) {
            return 0;
        }
    }


    /**
     * 时间戳转时间(11位时间戳)
     * @param time
     * @return
     */
    public static String timestampToDate(long time) {
        String dateTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLong = Long.valueOf(time);
        dateTime = simpleDateFormat.format(new Date(timeLong * 1000L));
        return dateTime;
    }

    //从html中提取纯文本
    public static String StripHT(String strHtml) {
        String txtcontent = strHtml.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        return txtcontent;
    }




        /**
         * 判断字符串中是否存在4字节字符
         * @param input 输入字符串
         * @return 包含4字节返回true， 否则为false
         */
        public static boolean containsMb4Char(String input) {
            if (input == null) {
                return false;
            }
            byte[] bytes = input.getBytes(Charset.forName("UTF-8"));
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                //four bytes
                if ((b & 0XF0) == 0XF0) {
                    return true;
                } else if ((b & 0XE0) == 0XE0) {
                    //three bytes
                    //forward 2 byte
                    i += 2;
                } else if ((b & 0XC0) == 0XC0) {
                    i += 1;
                }
            }
            return false;
        }

        /**
         * 替换可能存在的utf8 4字节字符
         * @param input 输入字符串
         * @param replacement 替换为的字符串
         * @return 替换后的utf8字符串
         */
        public static String replaceMb4Char(String input, String replacement) {
            if (input == null) {
                throw new IllegalArgumentException("input can not be null when replaceMb4Char");
            }

            StringBuilder sb = new StringBuilder(input.length());
            byte[] bytes = input.getBytes(Charset.forName("UTF-8"));
            char[] chars = input.toCharArray();
            int charIdx = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                //four bytes
                if ((b & 0XF0) == 0XF0) {
                    sb.append(replacement);
                    //utf-8四字节字符unicode后变为2个字符， 故字符下标多加1
                    charIdx+=2;
                    i+=3;
                    continue;
                } else if ((b & 0XE0) == 0XE0) {
                    //three bytes
                    //forward 2 byte
                    i += 2;
                } else if ((b & 0XC0) == 0XC0) {
                    i += 1;
                }
                sb.append(chars[charIdx]);
                charIdx++;
            }
            return sb.toString();
        }

    /**
     * @param htmlStr
     * @return 删除Html标签
     * @author LongJin
     */
    public static String delHTMLTag(String htmlStr,String reg) {
        Pattern p_w = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher m_w = p_w.matcher(htmlStr);
        //System.out.println("匹配规则["+reg+"]:"+m_w.matches());
        htmlStr = m_w.replaceAll(""); // 过滤script标签

        //htmlStr = htmlStr.replaceAll(" ", ""); //过滤
        return htmlStr.trim(); // 返回文本字符串
    }

    public static String convertUTFMB4(String input) {
        String str="";
        if(containsMb4Char(input)){
            str=replaceMb4Char(input, "");
        }else{
            str=input;
        }
        return str;
    }

    public static List initDemoData(){
        List lsSpiderRule=new ArrayList();

        return lsSpiderRule;
    }

    /**
     * 从html中获取网页编码
     * @param html
     * @return
     */
    public static String getEncodingFromHtml(String html){
        String rstr="UTF-8";
        String regex="<meta[^>]*?charset=(\\w+)[\\W]*?>";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(html);
        if(m.find()){
            rstr=m.group(1);
        }
        return rstr;
    }

    /**
     * 判断是否是详情页url
     * @param url
     * @return
     */
    public static boolean isDetailUrl(String url){
        boolean rstr=false;

        String[] array_str1=url.split("//");
        String s1="";
        String s2="";
        if(array_str1.length>1){
            s1=array_str1[0];
            s2=array_str1[1];
        }
        //含有第2部分
        String[] array_str2=s2.split("/");
        String temp_s1="";
        String temp_s2="";
        temp_s1=array_str2[0];

        if(array_str2.length<=1){       //分隔后/的为第二部分
            rstr=false;
        }
       // System.out.println("arr2.length="+array_str2.length+" rstr="+rstr+" tostring="+array_str2.toString());

        if(array_str2.length>=2) {          //第2部的 / 后必须不能为空，否则判定为目录
            String[] array_str3 = {}; //最后一位
            String str3 = array_str2[array_str2.length - 1];
            String temp_s3 = "";
            if (str3 != null & !str3.equals("")) {
                if (str3.split("\\.").length > 1) {       //以.结尾的, 小数点要用\\转义
                    array_str3 = str3.split("\\.");
                    rstr = true;
                }

                if (str3.split("\\?").length > 1) {       //以?结尾的, 小数点要用\\转义
                    array_str3 = str3.split("\\?");
                    rstr = true;
                }

            }
        }

        return rstr;
    }

    /**
     * 从URL中获取通配URL
     * @param str
     * @return
     */
    public static String getUrlByGeneric(String str){
        String rstr="";
        HashMap m=new HashMap();

        String[] array_str1=str.split("//");
        String s1="";
        String s2="";
        if(array_str1.length>1){
            s1=array_str1[0];
            s2=array_str1[1];

        }

        String[] array_str2=s2.split("/");
        String temp_s1="";
        String temp_s2="";
        temp_s1=array_str2[0];
        for(int i=1;i<array_str2.length-1;i++){   //从url的第2段开始替换,到倒数第二段结束,格式: http://www.xg9961.com/{替换}/{替换}/55949.html，
            if(!temp_s2.equals("")) {//不为空，在后面拼接/
                temp_s2 = temp_s2 + "/" + array_str2[i].replace(array_str2[i], "{" + "str" + i + "}");
            }
            else{
                temp_s2 = array_str2[i].replace(array_str2[i], "{" + "str" + i + "}");
            }
        }
        String[] array_str3={}; //最后一位
        String str3=array_str2[array_str2.length-1];
        String temp_s3="";
        if(str3!=null &!str3.equals("")) {
            //这种结构http://jinan.iqilu.com/jnms/2019/0531/4282448.shtml
            //System.out.println("str3.length="+str3.split("\\.").length);
            if (str3.split("\\.").length > 1) {       //以.结尾的, 小数点要用\\转义
                array_str3 = str3.split("\\.");

                temp_s3 = array_str3[0].replace(array_str3[0], "{infoid}");
                temp_s3 =temp_s3+"."+ array_str3[1].replace(array_str3[1], "{last}");
            }

            //这种结构https://3w.huanqiu.com/a/de583b/7N8BgJSOs7K?agt=8
            if(str3.split("\\?").length>1){       //以?结尾的, 小数点要用\\转义
                array_str3=str3.split("\\?");
                temp_s3 = array_str3[0].replace(array_str3[0], "{infoid}");
                temp_s3 =temp_s3+"?"+ array_str3[1].replace(array_str3[1], "{last}");
            }
            if(!str3.contains(".")&&!str3.contains("?")){   //不包含.或?的
                temp_s3="{last}";
            }


        }
        String str4="";
        if(!temp_s2.equals(""))temp_s2="/"+temp_s2;
        if(!temp_s3.equals(""))temp_s3="/"+temp_s3;
        rstr=s1+"//"+temp_s1+temp_s2+temp_s3;
        return rstr;
    }


    //错误 : java.lang.IllegalArgumentExceptionObject must not be null
    public static void main(String[] args) {
            //String str="spider_node_id";
            //System.out.println("indexof"+str.indexOf("spider"));
        String str="<meta http-equiv=\"content-type\" content=\"text/html;charset=gb2312\" />";
        System.out.println(getEncodingFromHtml(str));
        String url="http://www.ccccimg.com/sfsf/ssdfsdf/sdfds/sdsf.html?id=33/";
        String url_file="http://www.fysdgc.com/sdfs.swf";
        String url2="https://mp.weixin.qq.com/{infoid}?{last}";
        //url2="https://blog.csdn.net/zhang705018505/article/details/91344647";

        System.out.println("isDeatil:"+isDetailUrl(url));
        System.out.println("isFile:"+StringUtils.isFile(url_file));

        System.out.println("generic url:"+getUrlByGeneric(url2));       //url通配地址提取

    }
}
