package org.test.kurz.tbkurz.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CommonTest {

    /**
     * Loads the file as a resource and reads it as a {@link String}.
     *
     * @param fileName used for lookup
     * @return the file read as {@link String}
     */
    public static String readFileAsString(String fileName) {
        try {
            File file = new File(Objects.requireNonNull(CommonTest.class.getClassLoader().getResource(fileName)).toURI());
            return new String(Files.readAllBytes(Paths.get(file.getPath())), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to open file " + fileName, e);
        }
    }

    public static <T> T readFileAsModel(String dir, String fileName, Class<T> c) {
        return readFileAsModel(dir, fileName, c, null);
    }

    public static <T> List<T> readFileAsListModel(String dir, String fileName, Class<T> c) {
        return (List<T>) readFileAsModel(dir, fileName, null, c);
    }

    public static <T> T readFileAsModel(String dir, String fileName, Class<T> c, Class<T> clist) {
        ObjectMapper om = ObjectMapperSingleton.getObjectMapper();
        T model;
        try {
            String path;
            if (StringUtils.isNotEmpty(dir) && !dir.endsWith(File.separator) && !fileName.startsWith(File.separator)) {
                path = dir + File.separator + fileName;
            } else {
                path = dir + fileName;
            }
            String s = readFileAsString(path);
//            if (fileName.endsWith("yaml")) {
                    // todo: fix Constructor, possibly never to be used
//                Yaml yaml = new Yaml(new Constructor(c));
//                return yaml.load(s);
//            } else {
                if (clist != null) {
                    model = om.readValue(s, om.getTypeFactory()
                            .constructCollectionType(List.class, clist));
                } else {
                    model = om.readValue(s, c);
                }
//            }
        } catch (Exception e) {
            log.error("Failed to load: {}", fileName);
            throw new IllegalStateException(e);
        }
        return model;
    }
}
