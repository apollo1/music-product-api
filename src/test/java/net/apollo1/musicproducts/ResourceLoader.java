package net.apollo1.musicproducts;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResourceLoader {

    private static String readFileContents(Resource resource) {
        try {
            var stream = resource.getInputStream();
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadResource(String path, String file) {
        var loader = new DefaultResourceLoader();
        var resource = loader.getResource(String.format("%s/%s", path, file));
        return readFileContents(resource);
    }

    public static String loadResponse(String path, String filename) {
        return loadResource("classpath:responses/" + path, filename + ".json");
    }
}
