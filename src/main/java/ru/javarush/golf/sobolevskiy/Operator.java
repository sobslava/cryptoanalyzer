package ru.javarush.golf.sobolevskiy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operator {
    private final String S_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
            "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
            ".,\"\':-!? ";
    private final char[] C_ALPHABET = S_ALPHABET.toCharArray();

    private final String[] string_patterns = {"\\.\\s+[А-Я]+", ":\\s+[а-я]+",
            "\\s+(\u2012|\u2013|\u2014|\u2015|\u2E3A|\u2E3B|-)\\s+",
            "!\\s+[А-Я]+", "\\?\\s+[А-Я]+", "\\.\\s*(\n|\r\n)"};
    private Pattern[] patterns = new Pattern[string_patterns.length];
    public Operator() {
        for (int i = 0; i < string_patterns.length; i++)
            patterns[i] = Pattern.compile(string_patterns[i]);
    }

    public String cryptAndDecrypt(char[] zlo, int shift, int mode) {
        StringBuilder str = new StringBuilder();
        for (char ch : zlo) {
            if (ch == '\u0000')
                break;
            int position = S_ALPHABET.indexOf(ch);
            if (position >= 0) {
                switch (mode) {
                    case 1:
                        str.append(C_ALPHABET[(position + shift) % S_ALPHABET.length()]);
                        break;
                    case 2:
                        int sss = S_ALPHABET.length() - (shift % S_ALPHABET.length());
                        str.append(C_ALPHABET[(position + sss) % S_ALPHABET.length()]);
                        break;
                    default:
                        System.out.println("Wrong parameter mode: " + mode);
                        return "";
                }
            } else {
                str.append(ch);
            }
        }
        return str.toString();
    }

    public long checkShift(char[] buf, int shift) {
        String str_buf = cryptAndDecrypt(buf, shift, 2);
        for (Pattern pattern : patterns) {
            long count = pattern.matcher(str_buf).results().count();
            if (count > 0)
                return count;
        }
        return 0;
    }
}
