package ru.javarush.golf.sobolevskiy;

import org.slf4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.slf4j.LoggerFactory;

public class Cryptoanalyzer {
    public static final Logger logger = LoggerFactory.getLogger(Cryptoanalyzer.class);
    private static final String INPUT_PATH = "input.txt";
    private static final String OUTPUT_TXT = "output.txt";
    private static final String OTHER_TXT = "other.txt";

    public static void main(String[] args) throws IOException {
        logger.info("Введите номер режима:\n" +
                "\t1. Шифрование\n" +
                "\t2. Расшифровка\n" +
                "\t3. Криптоанализ (Brute Force)\n" +
                "\t4. Статистический анализ (частота всех символов)\n" +
                "\t5. Статистический анализ по пробелу\n" +
                "\t6. ВЫХОД");
        logger.info("Программа использует файлы: input.txt, output.txt, other.txt");

        Scanner console = new Scanner(System.in);
        int mode = console.nextInt();

        switch (mode) {
            case 1: { // Шифровка
                logger.info("Введите сдвиг:");
                int shift = console.nextInt();
                Caesar caesar = new Caesar();
                try (
                        FileReader fileReader = new FileReader(INPUT_PATH);
                        FileWriter fileWriter = new FileWriter(OUTPUT_TXT)) {
                    caesar.fileCrypt(shift, fileReader, fileWriter);
                }
                break;
            }
            case 2: { // Дешифровка
                logger.info("Введите сдвиг:");
                int shift = console.nextInt();
                Caesar caesar = new Caesar();
                try (
                    FileReader fileReader = new FileReader(INPUT_PATH);
                    FileWriter fileWriter = new FileWriter(OUTPUT_TXT)) {
                    caesar.fileDeCrypt(shift, fileReader, fileWriter);
                }
                break;
            }
            case 3: { // BruteForce
                logger.info("Поиск ключа ...");
                BruteForce bruteForce = new BruteForce();
                int key;
                try (
                    FileReader fileReader = new FileReader(INPUT_PATH)) {
                    key = bruteForce.findShiftByPatterns(fileReader);
                }
                if (key >= 0)
                    logger.info("Найден ключ: {}", key);
                else
                    logger.info("Ключ не найден");
                break;
            }
            case 4: { // Статистический анализ (частота всех символов)
                logger.info("Статистический анализ (частота всех символов)");
                Statistic statistic = new Statistic();
                try {
                    statistic.decodeBySampleFile(INPUT_PATH, OUTPUT_TXT, OTHER_TXT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 5: { // Статистический анализ по пробелу
                logger.info("Статистический анализ по пробелу");
                StatisticSpace statisticSpace = new StatisticSpace();
                statisticSpace.decryptBySpacePercentage(INPUT_PATH, OUTPUT_TXT, OTHER_TXT);
                break;
            }
            default:
                break;
        }
    }
}
