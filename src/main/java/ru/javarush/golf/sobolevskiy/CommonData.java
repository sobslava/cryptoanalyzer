package ru.javarush.golf.sobolevskiy;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Параметры для реализации различных операций
 */
public class CommonData {
    /**
     * Буфер в который будут считываться данные из файла
     */
    public final int BUFFER_SIZE = 4096;
    public char[] BUFFER = new char[BUFFER_SIZE];

    /**
     * Используемый алфавит для шифровки и дешифровки
     */
    public final String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
            "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
            ".,\"\':-!? ";
    public final char[] ALPHABET_CHAR = ALPHABET.toCharArray();

    List<Character> ALPHABET_LIST = ALPHABET.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

    /**
     * Пунктуационные паттерны
     */
    public final String[] bruteForceStrPatterns = {
            "\\.\\s+[А-Я]",
            ":\\s+[а-я]+",
            "[а-я]\\s+\\[а-я]",
            "\\s+(\u2012|\u2013|\u2014|\u2015|\u2E3A|\u2E3B|-)\\s+",
            "!\\s+[А-Я]",
            "\\?\\s+[А-Я]",
            "\\.\\s*(\n|\r\n)"
    };
    public final List<Pattern> bruteForcePatterns = Arrays.asList(bruteForceStrPatterns).stream().
            map(u -> Pattern.compile(u)).collect(Collectors.toList());

    public CommonData() {
    }
}