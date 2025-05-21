package com.az1.app.constant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class QuestionConstant {
    public final static String q1 = "get os info";
    public final static String q2 = "get cpu info";
    public final static String q3 = "get ram info";
    public final static String q4 = "get disk info";

    public static List<String> getAllQuestion() {
        List<String> questions = new ArrayList<>();
        Field[] fields = QuestionConstant.class.getFields();
        for (Field field : fields) {
            try {
                questions.add((String) field.get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return questions;
    }
}
