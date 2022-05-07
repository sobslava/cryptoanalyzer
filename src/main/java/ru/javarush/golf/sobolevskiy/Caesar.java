package ru.javarush.golf.sobolevskiy;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Caesar extends CommonData {
    public Caesar() {}

    public String crypt(char[] buf, int shift) {
        StringBuilder str = new StringBuilder();
        for (char ch : buf) {
            if (ch == '\u0000')
                break;
            int position = ALPHABET.indexOf(ch);
            if (position >= 0) {
                        str.append(ALPHABET_CHAR[(position + shift) % ALPHABET.length()]);
            } else {
                str.append(ch);
            }
        }
        return str.toString();
    }

    public String decrypt(char[] buf, int shift) {
        StringBuilder str = new StringBuilder();
        for (char ch : buf) {
            if (ch == '\u0000')
                break;
            int position = ALPHABET.indexOf(ch);
            if (position >= 0) {
                        int temp = ALPHABET.length() - (shift % ALPHABET.length());
                        str.append(ALPHABET_CHAR[(position + temp) % ALPHABET.length()]);
                } else {
                str.append(ch);
            }
        }
        return str.toString();
    }

    public void fileCrypt(int shift, FileReader fileReader, FileWriter fileWriter) throws IOException {
        int chars = fileReader.read(BUFFER);
        while (chars != -1) {
            fileWriter.write(crypt(BUFFER, shift));
            chars = fileReader.read(BUFFER);
        }
    }

    public void fileDeCrypt(int shift, FileReader fileReader, FileWriter fileWriter) throws IOException {
        int chars = fileReader.read(BUFFER);
        while (chars != -1) {
            fileWriter.write(decrypt(BUFFER, shift));
            chars = fileReader.read(BUFFER);
        }
    }
}
