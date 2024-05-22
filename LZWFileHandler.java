import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LZWFileHandler {

    public static void writeCompressedDataToFile(List<Integer> compressedData, String filePath, int numBytes)
            throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath))) {
            for (int data : compressedData) {
                switch (numBytes) {
                    case 1:
                        dos.writeByte(data); // Write as 1-byte value
                        break;
                    case 2:
                        dos.writeShort(data); // Write as 2-byte value
                        break;
                    case 4:
                        dos.writeInt(data); // Write as 4-byte value
                        break;
                    // Add more cases for other byte sizes if needed
                    default:
                        throw new IllegalArgumentException("Invalid number of bytes: " + numBytes);
                }
            }
        }
    }

    // Method to read compressed data from a file
    public static List<Integer> readCompressedDataFromFile(String filePath, int numBytes) throws IOException {
        List<Integer> compressedData = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            while (dis.available() > 0) {
                switch (numBytes) {
                    case 1:
                        compressedData.add((int) dis.readByte()); // Read the integer as a 2-byte short
                        break;
                    case 2:
                        compressedData.add((int) dis.readShort()); // Read the integer as a 2-byte short
                        break;
                    case 4:
                        compressedData.add((int) dis.readInt()); // Read the integer as a 2-byte short
                        break;
                    // Add more cases for other byte sizes if needed
                    default:
                        throw new IllegalArgumentException("Invalid number of bytes: " + numBytes);
                }
            }
        }
        return compressedData;
    }

}
