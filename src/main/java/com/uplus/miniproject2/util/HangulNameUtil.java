package com.uplus.miniproject2.util;

import java.util.HashMap;
import java.util.Map;

public class HangulNameUtil {

    private static final char[] CHO_SUNG = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ',
            'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
    private static final char[] JUNG_SUNG = {'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ',
            'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'};
    private static final char[] JONG_SUNG = {' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ',
            'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    private static final Map<Character, Integer> strokeMap = new HashMap<>();

    static {
        // 초성
        strokeMap.put('ㄱ', 2);
        strokeMap.put('ㄲ', 4);
        strokeMap.put('ㄴ', 2);
        strokeMap.put('ㄷ', 3);
        strokeMap.put('ㄸ', 6);
        strokeMap.put('ㄹ', 5);
        strokeMap.put('ㅁ', 4);
        strokeMap.put('ㅂ', 4);
        strokeMap.put('ㅃ', 8);
        strokeMap.put('ㅅ', 2);
        strokeMap.put('ㅆ', 4);
        strokeMap.put('ㅇ', 1);
        strokeMap.put('ㅈ', 3);
        strokeMap.put('ㅉ', 6);
        strokeMap.put('ㅊ', 4);
        strokeMap.put('ㅋ', 3);
        strokeMap.put('ㅌ', 4);
        strokeMap.put('ㅍ', 4);
        strokeMap.put('ㅎ', 3);

        // 중성
        strokeMap.put('ㅏ', 2);
        strokeMap.put('ㅐ', 3);
        strokeMap.put('ㅑ', 3);
        strokeMap.put('ㅒ', 4);
        strokeMap.put('ㅓ', 2);
        strokeMap.put('ㅔ', 3);
        strokeMap.put('ㅕ', 3);
        strokeMap.put('ㅖ', 4);
        strokeMap.put('ㅗ', 2);
        strokeMap.put('ㅛ', 3);
        strokeMap.put('ㅜ', 2);
        strokeMap.put('ㅠ', 3);
        strokeMap.put('ㅡ', 1);
        strokeMap.put('ㅢ', 2);
        strokeMap.put('ㅣ', 1);

        // 종성
        strokeMap.put('ㄳ', 4);
        strokeMap.put('ㄵ', 5);
        strokeMap.put('ㄶ', 5);
        strokeMap.put('ㄺ', 7);
        strokeMap.put('ㄻ', 8);
        strokeMap.put('ㄼ', 8);
        strokeMap.put('ㄽ', 7);
        strokeMap.put('ㄾ', 9);
        strokeMap.put('ㄿ', 9);
        strokeMap.put('ㅀ', 8);
        strokeMap.put('ㅄ', 6);
    }

    public static int getTotalStrokesFromHangul(char nameChar) {
        if (nameChar >= 0xAC00 && nameChar <= 0xD7A3) {
            int syllableIndex = nameChar - 0xAC00;

            int choSungIndex = syllableIndex / (21 * 28);
            int jungSungIndex = (syllableIndex % (21 * 28)) / 28;
            int jongSungIndex = syllableIndex % 28;

            int choSungStroke = strokeMap.getOrDefault(CHO_SUNG[choSungIndex], 0);
            int jungSungStroke = strokeMap.getOrDefault(JUNG_SUNG[jungSungIndex], 0);
            int jongSungStroke = strokeMap.getOrDefault(JONG_SUNG[jongSungIndex], 0);

            return choSungStroke + jungSungStroke + jongSungStroke;
        } else {
            return 0;
        }
    }
}
