package com.chailijun.baselib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 判断字符串中是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串中是否包含英文字母
     *
     * @param str
     * @return
     */
    public static boolean isContainChar(String str) {
        Pattern p = Pattern.compile("[a-zA-z]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 去掉字符串中的中文
     *
     * @param str
     * @return
     */
    public static String removeChinese(String str) {
        Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher mat = pat.matcher(str);
        return mat.replaceAll("");
    }


    /**
     * 去掉字符串中的字母
     *
     * @param str
     * @return
     */
    public static String removeChar(String str) {
        Pattern pat = Pattern.compile("[a-zA-z]");
        Matcher mat = pat.matcher(str);
        return mat.replaceAll("");
    }

    /**
     * 全角转半角
     *
     * @param input String.
     * @return 半角字符串
     */
    public static String toHalfAngleString(String input) {
        if (input == null) {
            return "";
        }
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        return new String(c);
    }


    /**
     * 将带有声调的拼音转换成带有数字的拼音（例如：xià--->xia4）
     * @param pinyin
     * @return
     */
    public static String transformPinyin(String pinyin){

        char aa[]={'ā','á','ǎ','à'};//āáǎà
        char oo[]={'ō','ó','ǒ','ò'};//ōóǒò
        char ee[]={'ē','é','ě','è'};//ēéěè
        char ii[]={'ī','í','ǐ','ì'};//īíǐì
        char uu[]={'ū','ú','ǔ','ù'};//ūúǔù
        char vv[]={'ǖ','ǘ','ǚ','ǜ'};//ǖǘǚǜ

        char[] pinyinCharArray = pinyin.toCharArray();
        int length = pinyinCharArray.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < 4; j++) {
                if (aa[j]==pinyinCharArray[i]){
                    pinyinCharArray[i]='a';
                    return String.valueOf(pinyinCharArray)+(j+1);
                }
            }

            for (int j = 0; j < 4; j++) {
                if (oo[j]==pinyinCharArray[i]){
                    pinyinCharArray[i]='o';
                    return String.valueOf(pinyinCharArray)+(j+1);
                }
            }

            for (int j = 0; j < 4; j++) {
                if (ee[j]==pinyinCharArray[i]){
                    pinyinCharArray[i]='e';
                    return String.valueOf(pinyinCharArray)+(j+1);
                }
            }

            for (int j = 0; j < 4; j++) {
                if (ii[j]==pinyinCharArray[i]){
                    pinyinCharArray[i]='i';
                    return String.valueOf(pinyinCharArray)+(j+1);
                }
            }

            for (int j = 0; j < 4; j++) {
                if (uu[j]==pinyinCharArray[i]){
                    pinyinCharArray[i]='u';
                    return String.valueOf(pinyinCharArray)+(j+1);
                }
            }

            for (int j = 0; j < 4; j++) {
                if (vv[j]==pinyinCharArray[i]){
                    pinyinCharArray[i]='v';
                    return String.valueOf(pinyinCharArray)+(j+1);
                }
            }
        }

        return pinyin+5;


    }



}
