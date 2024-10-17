package BSI.seguranca;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class caesar {
    public static void cesarEncrypt(int k, String fileOriginName, String fileFinalName) throws IOException {
        File originFile = new File(fileOriginName);
        File finalFile = new File(fileFinalName);
        finalFile.getParentFile().mkdirs();
        FileOutputStream out = new FileOutputStream(finalFile);

        Scanner fileReader = new Scanner(originFile);
        StringBuilder str = new StringBuilder();
        StringBuilder encryptedData = new StringBuilder();

        while (fileReader.hasNext()) {
            str.append(fileReader.nextLine()).append('\n');;
        }

        char[] data = str.toString().toCharArray();

        for (char dataChar : data) {
            if(dataChar != '.' && dataChar != ',' && dataChar != '\n' && dataChar != ' '){
                int value = dataChar + k;
                if(dataChar > 96 && dataChar <= 122 && value > 122) {
                    char letter = (char)(48 + (k - (122-dataChar)) - 1);

                    if(letter > 57 && letter < 65) {
                        letter = (char)(65 + letter-57 - 1);
                    }
                    encryptedData.append(letter);
                } else if (dataChar > 64 && dataChar <= 90 && value > 90) {
                    char letter = (char)(97 + (k - (90-dataChar)) - 1);
                    encryptedData.append(letter);
                } else if (dataChar > 48 && dataChar <= 57 && value > 57) {
                    char letter = (char)(65 + (k - (57-dataChar)) - 1);
                    encryptedData.append(letter);
                } else {
                    encryptedData.append((char) (dataChar + k));
                }
            } else {
                encryptedData.append(dataChar);
            }
        }

        out.write(encryptedData.toString().getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

    public static void cesarDecrypt(int k, String fileOriginName, String fileFinalName) throws IOException {
        File originFile = new File(fileOriginName);
        File finalFile = new File(fileFinalName);
        finalFile.getParentFile().mkdirs();
        FileWriter out = new FileWriter(finalFile);

        Scanner fileReader = new Scanner(originFile);
        StringBuilder str = new StringBuilder();
        HashMap<Character, Integer> charMap = new HashMap<>();
        StringBuilder encryptedData = new StringBuilder();

        while (fileReader.hasNext()) {
            str.append(fileReader.nextLine()).append('\n');
        }

        char[] data = str.toString().toCharArray();
        for (char dataChar : data) {
            int charValue = dataChar - k;
            if(dataChar != '.' && dataChar != ',' && dataChar != '\n' && dataChar != ' ') {
                if (dataChar >= 97 && charValue < 97) {
                    char value = (char) (90 - (k - (dataChar - 97)) + 1);
                    encryptedData.append(value);
                } else if (dataChar > 64 && dataChar < 91 && charValue < 65) {
                    char value = (char) (57 - (k - (dataChar - 65)) + 1);
                    if(value < 48) {
                        value = (char) (122 - (48 - value) + 1);
                    }

                    encryptedData.append(value);
                } else if (dataChar > 47 && dataChar < 58 && charValue < 47) {
                    char value = (char) (122 - (k - (dataChar - 48)) + 1);
                    encryptedData.append(value);
                } else {
                    encryptedData.append((char) (dataChar - k));
                }
            } else {
                encryptedData.append(dataChar);
            }
        }

        out.write(encryptedData.toString());
        out.close();
    }
}
