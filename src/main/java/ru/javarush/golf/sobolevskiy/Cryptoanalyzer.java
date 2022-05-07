package ru.javarush.golf.sobolevskiy;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Cryptoanalyzer {
    private static final String inputPath = "input.txt";
    private static final String outputPath = "output.txt";
    private static final String otherPath = "other.txt";

    public static void main(String[] args) throws IOException {
        System.out.println("Введите номер режима:\n" +
                "\t1. Шифрование\n" +
                "\t2. Расшифровка\n" +
                "\t3. Криптоанализ (Brute Force)\n" +
                "\t4. Статистический анализ\n" +
                "\t5. ВЫХОД");
        System.out.println("Программа использует файлы: input.txt, output.txt, other.txt");

        Scanner console = new Scanner(System.in);
        int mode = console.nextInt();

        if (mode == 5)
            return;

        // Шифровка
        if (mode == 1) {
            System.out.println("Введите сдвиг:");
            int shift = console.nextInt();
            Caesar caesar = new Caesar();
            caesar.fileCrypt(shift, new FileReader(inputPath), new FileWriter(outputPath));
            return;
        }

        // Дешифровка
        if (mode == 2) {
            System.out.println("Введите сдвиг:");
            int shift = console.nextInt();
            Caesar caesar = new Caesar();
            caesar.fileDeCrypt(shift, new FileReader(inputPath), new FileWriter(outputPath));
            return;
        }

        // Криптоанализ
        if (mode == 3) {
            System.out.println("Поиск ключа ...");
            BruteForce bruteForce = new BruteForce();
            int key = bruteForce.findShiftByPatterns(new FileReader(inputPath));
            System.out.println("Найден ключ: " + key);
            return;
        }

        // Статистический анализ
        if (mode == 4) {
            System.out.println("Статистический анализ ...");
            Statistic statistic = new Statistic();
            statistic.decodeBySampleFile(inputPath, outputPath, otherPath);
            return;
        }
    }
}
