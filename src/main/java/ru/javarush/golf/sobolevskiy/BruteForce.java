package ru.javarush.golf.sobolevskiy;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * BruteForce
 */
public class BruteForce extends CommonData {
    private final Caesar caesar = new Caesar();

    public BruteForce() {
        // Пусто
    }
    /**
     * Поиск ключа. Считаем для каждого сдвига число совпадений пунктуационным паттернам.
     * Выводим сдвиг для которого число совпадений больше всего.
     * @param fileReader - FileReader файла с информацией.
     * @return ключ или -1 (если ключ не найден).
     * @throws IOException
     */
    public int findShiftByPatterns(FileReader fileReader) throws IOException {
        int chars = fileReader.read(BUFFER);
        // key - сдвиг, value - число совпадение по массиву паттернов
        Map<Long, Long> matches = new HashMap<>();
        while (chars != -1) {
            for (int shift = 0; shift < ALPHABET_CHAR.length; shift++)
                matches.put(
                        Long.valueOf(shift),
                        matches.getOrDefault(Long.valueOf(shift), Long.valueOf(0)) +
                                countPatternsMatches(BUFFER, shift)
                        );
            chars = fileReader.read(BUFFER);
        }
        // Сортируем matches по значению (по числу совпадений по паттернам)
        HashMap<Long, Long> matchesSortedByValue = matches.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (x, y) -> x, LinkedHashMap::new));
        // Выводим значение сдвига
        Map.Entry<Long, Long> firstElement = matchesSortedByValue.entrySet().iterator().next();
        return firstElement.getKey().intValue();
    }

    /**
     * Подсчет числа совпадений паттернов в расшифрованном тексте.
     * @param buf - массив символов, на котором испытываем ключ key
     * @param shift - испытуемый ключ
     * @return
     */
    public Long countPatternsMatches(char[] buf, int shift) {
        String strBuf = caesar.decrypt(buf, shift);
        Long count = Long.valueOf(0);
        for (Pattern pattern : bruteForcePatterns) {
            count += pattern.matcher(strBuf).results().count();
        }
        return count;
    }
}
