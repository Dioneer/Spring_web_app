package pegas.service.clientService;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
public class ImageClientService {
    @Value("${image.path.part}")
    private String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream inputStream){
        Path fullPath = Path.of(bucket, imagePath);
        try(inputStream){
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, inputStream.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
    @SneakyThrows
    public Optional<byte[]> get(String imagePath){
        Path fullPath = Path.of(bucket, imagePath);
        return Files.exists(fullPath)?Optional.of(Files.readAllBytes(fullPath)): Optional.empty();
    }
}