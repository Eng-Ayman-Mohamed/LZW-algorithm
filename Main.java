import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "a";
        String fileExtension = "txt";
        int numBytes = 2;
        String compressedFilePath = inputFilePath + "_compressed" + ".LZW";
        String decompressedFilePath = inputFilePath + "_decompressed";

        try {
            // Read image file into byte array
            File inputFile = new File(inputFilePath + "." + fileExtension);
            byte[] input = Files.readAllBytes(inputFile.toPath());

            // Compress
            long startTime = System.nanoTime();
            LZWCompressor compressor = new LZWCompressor();
            List<Integer> compressedData = compressor.compress(input);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
            System.out.println("compression time: " + duration + " ms");

            // Write compressed data to file
            LZWFileHandler.writeCompressedDataToFile(compressedData, compressedFilePath, numBytes);
            System.out.println("Compressed data saved to: " + compressedFilePath);

            // Read compressed data from file
            List<Integer> readCompressedData = LZWFileHandler.readCompressedDataFromFile(compressedFilePath, numBytes);

            // Decompress
            startTime = System.nanoTime();
            LZWDecompressor decompressor = new LZWDecompressor();
            byte[] decompressedData = decompressor.decompress(readCompressedData);
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
            System.out.println("DeCompression time: " + duration + " ms");

            // Write decompressed byte array back to image file
            File outputFile = new File(decompressedFilePath + "." + fileExtension);
            Files.write(outputFile.toPath(), decompressedData);

            System.out.println("Decompression complete. Decompressed file saved to: " + outputFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
