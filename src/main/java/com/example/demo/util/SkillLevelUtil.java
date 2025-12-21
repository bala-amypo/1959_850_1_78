package com.example.demo.util;

public class SkillLevelUtil {
    public static boolean isSkillLevelSufficient(String actualLevel, String requiredLevel) {
        int actualRank = getLevelRank(actualLevel);
        int requiredRank = getLevelRank(requiredLevel);
        return actualRank >= requiredRank;
    }
    
    private static int getLevelRank(String level) {
        switch (level.toUpperCase()) {
            case "BEGINNER": return 1;
            case "INTERMEDIATE": return 2;
            case "EXPERT": return 3;
            default: return 0;
        }
    }
}
