package PSK.FlowerShop.usecase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileHelper {
    public void saveTextToFile(String text, String filePath) throws IOException {
        byte[] data = text.getBytes();
        Files.write(Path.of(filePath), data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}