package com.javarush.springbootforum.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:site.properties")
@RequiredArgsConstructor
@Slf4j
public class ImageConfiguration {
    @Value("${path.to.default.avatar.images.for.search}")
    private String imgDefaultAvatarsPath;

    @Value("${default.extensions.for.avatar.images}")
    private String[] extensionsAvatarsImage;

    private final ResourceLoader resourceLoader;

    @Bean
    public List<String> getDefaultAvatarImagesList() {
        try {
            Resource resource = resourceLoader.getResource(imgDefaultAvatarsPath);
            File file = resource.getFile();
            List<File> images = (List<File>) FileUtils.listFiles(file, extensionsAvatarsImage, true);

            return images.stream()
                    .map(File::getName)
                    .toList();
        } catch (IOException e) {
            log.warn("Avatar images not found, check folder with images: " + imgDefaultAvatarsPath);
            return new ArrayList<>();
        }
    }
}
