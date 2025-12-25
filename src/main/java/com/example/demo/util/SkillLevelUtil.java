package com.example.demo.util;

public class SkillLevelUtil {
    
    public static int levelRank(String level) {
        switch (level) {
            case "BEGINNER": return 1;
            case "INTERMEDIATE": return 2;
            case "EXPERT": return 3;
            default: return 0;
        }
    }
    
    public static int priorityRank(String priority) {
        switch (priority) {
            case "LOW": return 1;
            case "MEDIUM": return 2;
            case "HIGH": return 3;
            default: return 0;
        }
    }
}