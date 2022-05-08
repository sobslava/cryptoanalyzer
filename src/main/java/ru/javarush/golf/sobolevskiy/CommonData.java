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
    public static final int BUFFER_SIZE = 4096;
    protected static final char[] BUFFER = new char[BUFFER_SIZE];

    /**
     * Используемый алфавит для шифровки и дешифровки
     */
    public static final String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
            "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
            ".,\"\':-!? ";
    protected static final char[] ALPHABET_CHAR = ALPHABET.toCharArray();

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
    public final List<Pattern> bruteForcePatterns = Arrays.stream(bruteForceStrPatterns).
            map(Pattern::compile).collect(Collectors.toList());

    public CommonData() {
        // Пусто
    }
}