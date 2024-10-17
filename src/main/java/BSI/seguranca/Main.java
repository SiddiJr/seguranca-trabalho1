package BSI.seguranca;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static BSI.seguranca.caesar.cesarDecrypt;
import static BSI.seguranca.caesar.cesarEncrypt;
import static BSI.seguranca.vernam.crypto;
import static BSI.seguranca.vernam.decrypto;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String[] line = in.nextLine().split(" ");

        String currentPath = Paths.get("").toAbsolutePath() + "/src/main/java/BSI/seguranca/";
        if(line[0].equals("cesar") && line[1].equals("-c")) {
            cesarEncrypt(Integer.parseInt(line[2]), currentPath + line[3],  currentPath + line[4]);
        } else if (line[0].equals("cesar") && line[1].equals("-d")) {
            cesarDecrypt(Integer.parseInt(line[2]), currentPath + line[3], currentPath + line[4]);
        } else if (line[0].equals("analise")) {
            frequencyAnalysis(currentPath + line[1]);
        } else if (line[0].equals("vernam") && line[1].equals("-c")) {
            crypto(currentPath + line[2], currentPath + line[3]);
        } else if (line[0].equals("vernam") && line[1].equals("-d")) {
            decrypto(currentPath + line[2], currentPath + line[3]);
        }
    }

    public static void frequencyAnalysis (String fileName) throws FileNotFoundException {
        File originFile = new File(fileName);

        Scanner fileReader = new Scanner(originFile);
        StringBuilder str = new StringBuilder();
        HashMap<Character, Integer> charMap = new HashMap<>();

        while (fileReader.hasNext()) {
            str.append(fileReader.nextLine()).append('\n');
        }

        char[] data = str.toString().toCharArray();
        for (char dataChar : data) {
            if(dataChar != '.' && dataChar != ',' && dataChar != '\n' && dataChar != ' '){
                charMap.compute(dataChar, (key, value) -> (value == null) ? 1 : value + 1);
            }
        }

        HashMap<Character, Double> freqCharMap = new HashMap<>();

        float allCharCount = 0;
        for (int value : charMap.values()) {
            allCharCount += value;
        }

        for (char key : charMap.keySet()) {
            int value = charMap.get(key);
            double x = ((double) value*100)/allCharCount;
            freqCharMap.put(key, x);

            if(freqCharMap.containsKey(Character.toUpperCase(key)) && freqCharMap.containsKey(Character.toLowerCase(key)) && !Character.isDigit(key)) {
                if(freqCharMap.get(Character.toUpperCase(key)) > freqCharMap.get(Character.toLowerCase(key))) {
                    freqCharMap.remove(Character.toLowerCase(key));
                } else {
                    freqCharMap.remove(Character.toUpperCase(key));
                }
            }
        }

        List<Integer> changedIndex = new ArrayList<>();

        String alphSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        char[] freq = {'a', 'e', 'o', 's', 'r', 'i', 'n', 'd', 'm', 'u', 't', 'c', 'l', 'p', 'v', 'g', 'h', 'q', 'b', 'f', 'z', 'j', 'x', 'k', 'w', 'y'};

        int i = 0;
        while(true) {
            Map.Entry<Character, Double> maxEntry = Collections.max(freqCharMap.entrySet(), Map.Entry.comparingByValue());
            char key = maxEntry.getKey();

            for (int j = 0; j < data.length; j++) {
                if(data[j] == key && !changedIndex.contains(j)) {
                    data[j] = freq[i];
                    changedIndex.add(j);
                }
            }

            freqCharMap.remove(key);
            if(freqCharMap.isEmpty()) break;
            i++;
        }

        System.out.println(data);
    }
}