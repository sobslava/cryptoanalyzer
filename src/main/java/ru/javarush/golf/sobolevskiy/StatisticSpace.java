package ru.javarush.golf.sobolevskiy;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Идея - двигаем текст до тех пор, пока пробел не будет наиболее распространенным символом.
 */
public class StatisticSpace extends CommonData {
    public StatisticSpace() {
        // Пусто
    }

    public void decryptBySpacePercentage(String inputPath, String outputPath, String otherPath) {
        // Процент пробелов в примере
        Double samplePercentage = spacePercentage(otherPath, 0);

        // Процент пробелов для разных сдвигов
        Map<Integer, Double> shiftPercentage = new HashMap<>();
        for (int i = 1; i <= ALPHABET_CHAR.length; i++)
            shiftPercentage.put(Integer.valueOf(i), spacePercentage(inputPath, i));

        // Отклонения от sample
        Map<Integer, Double> difference = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : shiftPercentage.entrySet())
            difference.put(entry.getKey(), Math.abs(entry.getValue() - samplePercentage));

        // Сортируем по значению
        HashMap<Integer, Double> differenceSorted = difference.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (x, y) -> x, LinkedHashMap::new));

        // Первый элемент является ключом
        int key;
        if (differenceSorted.entrySet().stream().findFirst().isEmpty())
            throw new RuntimeException("Не найден элемент с ключом");
        else
            key = differenceSorted.entrySet().stream().findFirst().get().getKey();

        // Расшифровываем этим ключом
        Caesar caesar = new Caesar();
        try (
                FileReader fileReader = new FileReader(inputPath);
                FileWriter fileWriter = new FileWriter(outputPath);
        ) {
            caesar.fileDeCrypt(key, fileReader, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Считаем процент пробел после расшифровки ключом shift
     *
     * @param readFile
     * @param shift
     * @return
     */
    public Double spacePercentage(String readFile, int shift) {
        int spaceCount = 0; // Число пробелов
        int numberOfChar = 0; // Общее количество символов
        Caesar caesar = new Caesar();
        try (
                FileReader fileReader = new FileReader(readFile);
        ) {
            int chars = fileReader.read(BUFFER);
            while (chars != -1) {
                spaceCount += caesar.decrypt(BUFFER, shift).chars().filter(c -> c == ' ').count();
                numberOfChar += chars;
                chars = fileReader.read(BUFFER);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (numberOfChar != 0)
            return Double.valueOf(100.0 * spaceCount / numberOfChar);
        else
            return Double.valueOf(0);
    }
}
