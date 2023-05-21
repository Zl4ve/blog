package ru.itis.kpfu.parsers;

import ru.itis.kpfu.helpers.PictureDownloader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostTextParser {

    private static final String PICTURE_URL_REGEX = "(?:http(s)?:\\/\\/)?[\\w.\\-]+(?:\\.[\\w\\.\\-]+)+[\\w\\-\\.\\_\\~:\\/\\?\\#\\[\\]@!\\$&'\\(\\)\\*\\+,\\;=\\.]+(?:png|jpg|jpeg|gif|svg)+((\\?.*)$|$)*";

    private final PictureDownloader downloader = new PictureDownloader();

    public List<String> parse(String text) {

        // Split text and URI's
        text = text.replace("<img>", "*split*").replace("</img>", "*split*");
        List<String> parsedText = Stream.of(text.split("\\*split\\*")).collect(Collectors.toList());
        List<String> pictureUrls = new ArrayList<>();

        // Remove spaces from URI's
        for (int i = 0; i < parsedText.size(); i++) {
            if (parsedText.get(i).replaceAll("\\s+", "").matches(PICTURE_URL_REGEX)) {
                parsedText.set(i, parsedText.get(i).replaceAll("\\s+", ""));
                pictureUrls.add(parsedText.get(i));
            }
        }

        // Get paths of downloaded pictures
        for (int i = 0; i < pictureUrls.size(); i++) {
            String downloadedPicturePath = downloader.download(pictureUrls.get(i));
            if (downloadedPicturePath == null) {
                pictureUrls.set(i, "");
            } else {
                pictureUrls.set(i, downloadedPicturePath);
            }
        }

        // Change web URI's to paths of downloaded pictures
        int count = 0;
        for (int i = 0; i < parsedText.size(); i++) {
            if (parsedText.get(i).matches(PICTURE_URL_REGEX)) {
                parsedText.set(i, pictureUrls.get(count));
                count++;
            }
        }

        // List must consist of pairs text - picture_url. If the pattern is broken, add an empty string.
        for (int i = 0; i < parsedText.size(); i++) {
            if (i % 2 == 0 && parsedText.get(i).matches(downloader.getDownloadedPicturesTargetFolder().toString() + "/[0-9]+.(png|jpg|jpeg|gif|svg)")) {
                parsedText.add(i, "");
            } else if (i % 2 != 0 && !parsedText.get(i).matches(downloader.getDownloadedPicturesTargetFolder().toString() + "/[0-9]+.(png|jpg|jpeg|gif|svg)")) {
                parsedText.add(i, "");
            }
        }
        if (parsedText.size() % 2 != 0) {
            parsedText.add("");
        }

        return parsedText;

    }
}
