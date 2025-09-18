package su.opencode.digsigcik.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@UtilityClass
public class CommonZipUtils {

    public static List<Path> unzip(@NonNull Path dir, @NonNull InputStream zipFileContentStream) throws IOException {
        assert dir.toFile().isDirectory() : "Должна быть существующая директория";

        List<Path> upzipped = new ArrayList<>();

        try (ZipInputStream zis = new ZipInputStream(zipFileContentStream))
        {
            ZipEntry entry;

            while((entry = zis.getNextEntry()) != null)
                upzipped.add(
                        createFile(dir, entry, zis)
                );

            return upzipped;
        }
    }

    private static Path createFile(Path dir, ZipEntry entry, ZipInputStream zis) throws IOException {

        var file = dir.resolve(entry.getName());

        Files.copy(zis, file);

        return file;
    }

}
