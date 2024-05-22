import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LZWDecompressor {
    private static final int DICTIONARY_SIZE = 256;

    public byte[] decompress(List<Integer> compressedData) {
        // Initialize dictionary with single-byte values
        Map<Integer, List<Byte>> dictionary = new HashMap<>();
        for (int i = 0; i < DICTIONARY_SIZE; i++) {
            List<Byte> byteList = new ArrayList<>();
            byteList.add((byte) i);
            dictionary.put(i, byteList);
        }

        List<Byte> w = new ArrayList<>();
        w.add((byte) (int) compressedData.remove(0));
        List<Byte> decompressedData = new ArrayList<>(w);

        for (int k : compressedData) {
            List<Byte> entry = new ArrayList<>();
            if (dictionary.containsKey(k)) {
                entry = dictionary.get(k);
            } else if (k == dictionary.size()) {
                entry = new ArrayList<>(w);
                entry.add(w.get(0));
            }

            decompressedData.addAll(entry);

            List<Byte> wNew = new ArrayList<>(w);
            wNew.add(entry.get(0));
            dictionary.put(dictionary.size(), wNew);

            w = entry;
        }

        // Convert list to byte array
        byte[] result = new byte[decompressedData.size()];
        for (int i = 0; i < decompressedData.size(); i++) {
            result[i] = decompressedData.get(i);
        }

        return result;
    }
}
