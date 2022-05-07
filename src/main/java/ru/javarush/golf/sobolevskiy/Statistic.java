package ru.javarush.golf.sobolevskiy;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Statistic extends CommonData {

    public int findIndex(Character[] array, char c) {
        for (int i = 0; i < array.length; i++)
            if (array[i].equals(c))
                return i;
        return -1;
    }

    public void decodeBySampleFile(String inputPath, String outputPath, String otherPath)
            throws IOException {
        FileReader fileReader = new FileReader(inputPath);
        Character[] fileReaderStat = createStatistic(fileReader);
        fileReader.close();

        FileReader fileSample = new FileReader(otherPath);
        Character[] fileSampleStat = createStatistic(fileSample);
        fileSample.close();

        FileReader fileReader2 = new FileReader(inputPath);
        FileWriter fileWriter = new FileWriter(outputPath);

        int ch = fileReader2.read();
        while (ch != -1) {
            if (ALPHABET.indexOf(ch) >= 0) {
                int index = findIndex(fileReaderStat, (char) ch);
                fileWriter.write(fileSampleStat[index].charValue());
            } else
                fileWriter.write(ch);
            ch = fileReader2.read();
        }
        fileReader2.close();
        fileWriter.close();
    }
    /**
     * Создаем char[] по частоте встречаемых символов из нашего алфавита ALPHABET
     * @param fileReader
     * @return
     * @throws IOException
     */
    public Character[] createStatistic(FileReader fileReader) throws IOException {
        // Character - символ из нашего алфавита ALPHABET, Integer - число совпадений.
        HashMap<Character, Integer> statistic = new HashMap<>();
        // Начальное заполнение
        for (char ch : ALPHABET_CHAR)
            statistic.put(Character.valueOf(ch), 0);
        // Работаем над файлом
        int chars = fileReader.read(BUFFER);
        while (chars != -1) {
            addStatistic(BUFFER, statistic);
            chars = fileReader.read(BUFFER);
        }
        // Упорядочиваем по значению
        HashMap<Character, Integer> statisticSortedByValue = statistic.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (x, y) -> x, LinkedHashMap::new));
        return statisticSortedByValue.keySet().stream().toArray(Character[]::new);
    }

    /**
     * Подсчет символов переданных в CharBuffer buf.
     * @param buf - массив символов (не важно, закодированных или нет)
     * @param statistic - мапа статистики по ссылке
     * @return
     */
    public void addStatistic(char[] buf, HashMap<Character, Integer> statistic) {
        IntStream intStream = CharBuffer.wrap(buf).chars();
        intStream.mapToObj(c -> (char) c)
                .filter(c -> ALPHABET.indexOf(c) >= 0)
                .forEach(c -> statistic.put(c, statistic.getOrDefault(c, 0) + 1));
    }

    public Statistic() {}
}
