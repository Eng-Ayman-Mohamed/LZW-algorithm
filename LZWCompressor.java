import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LZWCompressor {
    private static final int DICTIONARY_SIZE = 256;

    public List<Integer> compress(byte[] input) {
        // Initialize dictionary with single-byte values
        Map<List<Byte>, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < DICTIONARY_SIZE; i++) {
            List<Byte> byteList = new ArrayList<>();
            byteList.add((byte) i);
            dictionary.put(byteList, i);
        }

        List<Byte> w = new ArrayList<>();
        List<Integer> compressedData = new ArrayList<>();

        for (byte b : input) {
            List<Byte> wb = new ArrayList<>(w);
            wb.add(b);
            if (dictionary.containsKey(wb)) {
                w = wb;
            } else {
                compressedData.add(dictionary.get(w));
                dictionary.put(wb, dictionary.size());
                w = new ArrayList<>();
                w.add(b);
            }
        }

        if (!w.isEmpty()) {
            compressedData.add(dictionary.get(w));
        }

        return compressedData;
    }
}
