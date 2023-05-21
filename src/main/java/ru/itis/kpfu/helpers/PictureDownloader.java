package ru.itis.kpfu.helpers;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PictureDownloader {


    private final Path downloadedPicturesTargetFolder = getDownloadedPicturesTargetFolder();

    public String download(String url) {

        URL pictureUrl;

        try {
            pictureUrl = new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }

        downloadedPicturesTargetFolder.toFile().mkdir();

        Path picturePath = Paths.get(downloadedPicturesTargetFolder.toString() + "/" + (downloadedPicturesTargetFolder.toFile().listFiles().length + 1) + "." + FilenameUtils.getExtension(pictureUrl.getPath()));

        try (BufferedInputStream in = new BufferedInputStream(pictureUrl.openStream());
             OutputStream out = new BufferedOutputStream(new FileOutputStream(picturePath.toFile()))) {
            picturePath.toFile().createNewFile();
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } catch (IOException e) {
            return null;
        }

        return picturePath.toString();
    }

    public Path getDownloadedPicturesTargetFolder() {
        Properties properties = new Properties();
        try {
            properties.load(PictureDownloader.class.getResourceAsStream("/application.properties"));
            return Paths.get(properties.getProperty("postImagesPath"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
