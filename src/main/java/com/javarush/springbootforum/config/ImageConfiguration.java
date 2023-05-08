package com.javarush.springbootforum.config;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Configuration
@PropertySource("classpath:site.properties")
public class ImageConfiguration { // todo пока не конфигурация...
    @Value("${path.to.default.avatar.images.for.search}")
    private String imgDefaultAvatarsPath;

    @Value("${default.extensions.for.avatar.images}")
    private String[] extensionsAvatarsImage;

    @Bean
    public List<String> getDefaultAvatarImagesList() {
        try {
            File file = ResourceUtils.getFile(imgDefaultAvatarsPath);
            List<File> images = (List<File>) FileUtils.listFiles(file, extensionsAvatarsImage, true);

            return images.stream()
                    .map(File::getName)
                    .toList();
        } catch (FileNotFoundException e) {
            // todo запись в логи?!
            return new ArrayList<>();
        }
    }
}
