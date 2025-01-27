package kafka;

import store.Storage;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HexFormat;

public class KafkaLoader {

    private final String logsRoot;
    private final Storage storage;

    public KafkaLoader(String logsRoot, Storage storage) {
        this.logsRoot = logsRoot;
        this.storage = storage;
    }

    public static KafkaLoader load(String logsRoot, Storage storage) throws IOException {

        final var path = logsRoot + "__cluster_metadata-0/00000000000000000000.log";

        try (final var fileInputStream = new FileInputStream(path)) {
            System.out.println(HexFormat.ofDelimiter("").formatHex(fileInputStream.readAllBytes()));
        }
        return new KafkaLoader(logsRoot, storage);
    }
}
