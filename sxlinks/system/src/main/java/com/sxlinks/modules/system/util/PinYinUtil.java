package com.sxlinks.modules.system.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {

    /**
     * This method is used to get pinyin of Chinese character
     * @param chinese : Chinese character
     * @param firstLetter : If this field is true,get the first letter of pinyin,else get all
     * @param upper : if this field is true,get uppercase.else get lowercase
     * @return
     */
    public static String getPinYin(String chinese,boolean firstLetter,boolean upper){
        char[] chineseArra = chinese.toCharArray();
        HanyuPinyinOutputFormat hypyFormat = new HanyuPinyinOutputFormat();
        if(upper){
            hypyFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        }else{
            hypyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        hypyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String pinyin = "";
        try{
            if(firstLetter){
                for(int i =0 ;i<chineseArra.length;i++){
                    /**
                     * Judge whether it is a Chinese character,
                     * if it is,then convert to pinyin,
                     * otherwise do not convert
                     */
                    if(String.valueOf(chineseArra[i]).matches("[\u4e00-\u9fa5]+")){
                        pinyin += PinyinHelper.toHanyuPinyinStringArray(chineseArra[i],hypyFormat)[0].substring(0,1);
                    }else{
                        continue;
                    }
                }
            }else{
                for(int i =0 ;i<chineseArra.length;i++){
                    if(String.valueOf(chineseArra[i]).matches("[\u4e00-\u9fa5]+")){
                        pinyin += PinyinHelper.toHanyuPinyinStringArray(chineseArra[i],hypyFormat);
                    }else{
                        continue;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return pinyin;
    }

    public static String getFullPinyin(String name) {
        // 创建格式化对象
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        //设置大小写格式
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //设置声调格式
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // 放置输入结果
        StringBuilder result = new StringBuilder();
        // 字符数组
        char[] charArray = name.toCharArray();
        // 遍历字符
        for (char c : charArray) {
            // 中文会被变成全拼，非中文会被直接拼接在结果字符串中
            if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                String[] pinyinArray = new String[0];
                try {
                    pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, outputFormat);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
                if (pinyinArray != null) {
                    result.append(pinyinArray[0]);
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
