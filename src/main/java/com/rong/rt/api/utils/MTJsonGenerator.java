package com.rong.rt.api.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class MTJsonGenerator {

    public static void main(String[] args) throws IOException {
        Path texturesPath = Paths.get("assets/rongtech/textures/items/metaitems");
        Path modelsPath = Paths.get("assets/rongtech/models/item/metaitems");
        Files.walk(texturesPath)
            .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".png"))
            .forEach(textureFile -> {
                String relativePath = texturesPath.relativize(textureFile).toString().replace('\\', '/');
                String fileText = "{\n" +
                    "  \"parent\": \"item/generated\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"rongtech:items/metaitems/" + relativePath.replace(".png", "") + "\"\n" +
                    "  }\n" +
                    "}";
                Path modelPath = modelsPath.resolve(relativePath.replace(".png", ".json"));
                try {
                    Files.createDirectories(modelPath.getParent());
                    Files.write(modelPath, Collections.singleton(fileText), StandardCharsets.UTF_8);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
    }

}
