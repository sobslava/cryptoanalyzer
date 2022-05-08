package ru.javarush.golf.sobolevskiy;

import org.slf4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.slf4j.LoggerFactory;

public class Cryptoanalyzer {
    public static final Logger logger = LoggerFactory.getLogger(Cryptoanalyzer.class);

    // Заголовки сообщений
    private static final String IN = "Введите путь к входному файлу:";
    private static final String OUT = "Введите путь к выходному файлу:";
    private static final String SHIFT = "Введите сдвиг:";
    private static final String THIRD_FILE_TO_ANALYZE = "Введите путь к файлу для статистического анализа:";


    public static void main(String[] args) throws IOException, NotFoundException {
        logger.info("Введите номер режима:\n" +
                "\t1. Шифрование\n" +
                "\t2. Расшифровка\n" +
                "\t3. Криптоанализ (Brute Force)\n" +
                "\t4. Статистический анализ по пробелу\n" +
                "\t5. Статистический анализ (частота всех символов)\n" +
                "\tИНОЙ ВЫБОР: ВЫХОД");

        Scanner console = new Scanner(System.in);
        int mode = console.nextInt();

        switch (mode) {
            case 1: { // Шифровка
                logger.info("--- ВЫ ВЫБРАЛИ ОПЦИЮ ШИФРОВАНИЯ ФАЙЛА ---");
                logger.info(IN);
                String inputPath = console.next();
                logger.info(OUT);
                String outputPath = console.next();
                logger.info(SHIFT);
                int shift = console.nextInt();
                Caesar caesar = new Caesar();
                try (
                        FileReader fileReader = new FileReader(inputPath);
                        FileWriter fileWriter = new FileWriter(outputPath)) {
                    caesar.fileCrypt(shift, fileReader, fileWriter);
                }
                break;
            }
            case 2: { // Дешифровка
                logger.info("--- ВЫ ВЫБРАЛИ ОПЦИЮ ДЕШИФРОВАНИЯ ФАЙЛА ---");
                logger.info(IN);
                String inputPath = console.next();
                logger.info(OUT);
                String outputPath = console.next();
                logger.info(SHIFT);
                int shift = console.nextInt();
                Caesar caesar = new Caesar();
                try (
                    FileReader fileReader = new FileReader(inputPath);
                    FileWriter fileWriter = new FileWriter(outputPath)) {
                    caesar.fileDeCrypt(shift, fileReader, fileWriter);
                }
                break;
            }
            case 3: { // BruteForce
                logger.info("--- ВЫ ВЫБРАЛИ ОПЦИЮ BRUTEFORCE поиска ключа ---");
                logger.info(IN);
                String inputPath = console.next();
                BruteForce bruteForce = new BruteForce();
                int key;
                try (
                    FileReader fileReader = new FileReader(inputPath)) {
                    key = bruteForce.findShiftByPatterns(fileReader);
                }
                if (key >= 0)
                    logger.info("Найден ключ: {}", key);
                else
                    logger.info("Ключ не найден");
                break;
            }
            case 4: { // Статистический анализ по пробелу
                logger.info("--- ВЫ ВЫБРАЛИ ОПЦИЮ АНАЛИЗА ПО ПРОБЕЛУ ---");
                logger.info(IN);
                String inputPath = console.next();
                logger.info(OUT);
                String outputPath = console.next();
                logger.info(THIRD_FILE_TO_ANALYZE);
                String thirdFileToAnalyze = console.next();
                StatisticSpace statisticSpace = new StatisticSpace();
                statisticSpace.decryptBySpacePercentage(inputPath, outputPath, thirdFileToAnalyze);
                break;
            }
            case 5: { // Статистический анализ (частота всех символов)
                logger.info("--- ВЫ ВЫБРАЛИ ОПЦИЮ АНАЛИЗА ПОЛНОГО ЧАСТОТНОГО АНАЛИЗА ---");
                logger.info(IN);
                String inputPath = console.next();
                logger.info(OUT);
                String outputPath = console.next();
                logger.info(THIRD_FILE_TO_ANALYZE);
                String thirdFileToAnalyze = console.next();
                Statistic statistic = new Statistic();
                try {
                    statistic.decodeBySampleFile(inputPath, outputPath, thirdFileToAnalyze);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            default:
                break;
        }
    }
}
