package com.uplus.miniproject2.util;

import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.hobby.HobbyCategory;
import java.util.List;

public class HobbyFamiliarityCalculator {

    public static double calculateHobbyFamiliarity(List<Hobby> hobbies1, List<Hobby> hobbies2) {
        double maxScore = 100.0;
        double score = 0.0;

        for (Hobby hobby1 : hobbies1) {
            for (Hobby hobby2 : hobbies2) {
                if (hobby1.getHobbyCategory() == HobbyCategory.ETC && hobby2.getHobbyCategory() == HobbyCategory.ETC) {
                    if (isMatch(hobby1.getName(), hobby2.getName())) {
                        score += 5;
                    }

                    continue;
                }

                if (hobby1.getHobbyCategory() == hobby2.getHobbyCategory()) {
                    score += 8;

                    if (hobby1.getName().equals(hobby2.getName())) {
                        score += 30;
                    }
                }

                if (isMatch(hobby1.getName(), hobby2.getName())) {
                    score += 7;
                }
            }
        }

        return Math.min(score, maxScore);
    }

    private static boolean isMatch(String hobbyName1, String hobbyName2) {
        if (hobbyName1.length() < hobbyName2.length()) {
            return hobbyName2.contains(hobbyName1);
        }

        return hobbyName1.contains(hobbyName2);
    }
}
