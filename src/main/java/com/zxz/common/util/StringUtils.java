package com.zxz.common.util;

public class StringUtils {
    public static boolean isNull(CharSequence charSequence) {
        return charSequence == null;
    }

    public static boolean isNotNull(CharSequence charSequence) {
        return !isNull(charSequence);
    }

    public static boolean isBlank(CharSequence charSequence) {
        return charSequence == null || trim(charSequence).length() == 0;
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }

    public static CharSequence trim(CharSequence cs) {
        int len = cs.length();
        int st = 0;

        while ((st < len) && (cs.charAt(st) <= ' ')) {
            st++;
        }
        while ((st < len) && (cs.charAt(len - 1) <= ' ')) {
            len--;
        }
        return ((st > 0) || (len < cs.length())) ? cs.subSequence(st, len) : cs;
    }

    public static String firstToUp(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
