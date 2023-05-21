package ru.itis.kpfu.validators;

import java.util.List;

public class PostCreateDataErrorMessageGenerator {
    public String generateError(List<String> parsedContent) {
        if (parsedContent.stream().mapToInt(String::length).sum() == 0) {
            return "Enter text";
        }
        if (parsedContent.stream().mapToInt(String::length).sum() > 4000) {
            return "Text length must not exceed 4000 characters";
        }
        if (parsedContent.size() > 10) {
            return "There can be no more than 5 pictures in the post";
        }
        return null;
    }
}
