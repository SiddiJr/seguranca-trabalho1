package BSI.seguranca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class vernam {
    public static void decrypto (String cipheredFileName, String keyFileName) throws IOException {
        StringBuilder cipheredString = fromFile(cipheredFileName);
        StringBuilder keyString = fromFile(keyFileName);

        ArrayList<String> keyBinary = toBinary(keyString.toString());
        ArrayList<String> cipheredStringToBinary = new ArrayList<>(List.of(cipheredString.toString().split("\\s+")));
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < cipheredStringToBinary.size(); i++) {
            for (int j = 0; j < cipheredStringToBinary.getFirst().length(); j++) {
                result.append(cipheredStringToBinary.get(i).charAt(j) ^ keyBinary.get(i).charAt(j));
            }
            result.append(" ");
        }

        String[] a = result.toString().split(" ");

        StringBuilder finalString = new StringBuilder();

        for(String data : a) {
            finalString.append((char)(Integer.parseInt(data, 2)));
        }

        saveToFile(finalString.toString(), "aberto.txt");
    }

    public static void crypto (String keyFileName, String openedFileName) throws IOException {
        StringBuilder cipheredString = fromFile(keyFileName);
        StringBuilder openString = fromFile(openedFileName);

        ArrayList<String> cipheredStringToBinary = toBinary(cipheredString.toString());
        ArrayList<String> openStringToBinary = toBinary(openString.toString());
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < cipheredStringToBinary.size(); i++) {
            if(cipheredStringToBinary.get(i).length() < 7) {
                cipheredStringToBinary.set(i, "0" + cipheredStringToBinary.get(i));
            }

            if (openStringToBinary.get(i).length() < 7) {
                openStringToBinary.set(i, "0" + openStringToBinary.get(i));
            }

            for (int j = 0; j < cipheredStringToBinary.getFirst().length(); j++) {
                result.append(cipheredStringToBinary.get(i).charAt(j) ^ openStringToBinary.get(i).charAt(j));
            }
            result.append(" ");
        }

        StringBuilder finalString = new StringBuilder();

        String[] arrayBinary = result.toString().split(" ");

        for(String data : arrayBinary) {
            int value = Integer.parseInt(data);
            finalString.append((char)(value));
        }

        System.out.println(finalString);
        saveToFile(result.toString(), "cifrado.txt");
    }

    public static void saveToFile(String data, String fileName) throws IOException {
        String currentPath = Paths.get("").toAbsolutePath() + "/src/main/java/BSI/seguranca/";
        File finalFile = new File(currentPath + fileName);
        finalFile.getParentFile().mkdirs();
        FileOutputStream out = new FileOutputStream(finalFile);

        out.write(data.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

    public static ArrayList<String> toBinary(String textoInicial) {
        char[] stringArray = textoInicial.toCharArray();
        ArrayList<String> str = new ArrayList<>();

        for (char letter : stringArray) {
            str.add(Integer.toBinaryString(letter));
        }

        return str;
    }

    public static StringBuilder fromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);

        Scanner fileReader = new Scanner(file);
        StringBuilder str = new StringBuilder();

        while (fileReader.hasNextLine()) {
            str.append(fileReader.nextLine());
        }

        for (int i = 0; i <= str.length() - 1; i++) {
            char letter = str.charAt(i);
            if (!Character.isLetterOrDigit(letter) && !Character.isSpaceChar(letter)) {
                str.deleteCharAt(i);
            }
        }

        fileReader.close();
        return str;
    }
}
