package com.prashant.framework.utils;

import com.prashant.framework.base.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtil {

    private ScreenshotUtil() {
    }

    public static String capture(String filePrefix) {
        try {
            File source = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            Path directory = Paths.get("screenshots");
            Files.createDirectories(directory);

            Path destination = directory.resolve(filePrefix + "_" + timestamp + ".png");
            Files.copy(source.toPath(), destination);
            return destination.toString();
        } catch (IOException e) {
            return "Screenshot capture failed: " + e.getMessage();
        }
    }
}
