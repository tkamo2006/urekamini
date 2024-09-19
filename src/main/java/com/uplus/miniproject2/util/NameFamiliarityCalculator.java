package com.uplus.miniproject2.util;

import java.util.ArrayList;
import java.util.List;

public class NameFamiliarityCalculator {

    public static int calculateNameFamiliarity(String loginName, String clickName) {
        List<Integer> loginStrokes = getStrokes(loginName);
        List<Integer> clickStrokes = getStrokes(clickName);

        List<Integer> combinedStrokeArr = mergeNameStrokes(loginStrokes, clickStrokes);

        return calcFinalScore(combinedStrokeArr);
    }

    private static List<Integer> mergeNameStrokes(List<Integer> strokes1, List<Integer> strokes2) {
        List<Integer> result = new ArrayList<>();
        int maxLength = Math.max(strokes1.size(), strokes2.size());

        for (int i = 0; i < maxLength; i++) {
            if (i < strokes1.size()) {
                result.add(strokes1.get(i));
            }
            if (i < strokes2.size()) {
                result.add(strokes2.get(i));
            }
        }

        return result;
    }

    private static int calcFinalScore(List<Integer> strokes) {

        while (strokes.size() > 2) {
            List<Integer> newStrokes = new ArrayList<>();
            for (int i = 0; i < strokes.size() - 1; i++) {
                newStrokes.add((strokes.get(i) + strokes.get(i + 1)) % 10);
            }
            strokes = newStrokes;
        }

        return strokes.get(0) * 10 + strokes.get(1);
    }

    private static List<Integer> getStrokes(String name) {
        List<Integer> strokeList = new ArrayList<>();
        for (char ch : name.toCharArray()) {
            strokeList.add(HangulNameUtil.getTotalStrokesFromHangul(ch));
        }
        return strokeList;
    }
}
