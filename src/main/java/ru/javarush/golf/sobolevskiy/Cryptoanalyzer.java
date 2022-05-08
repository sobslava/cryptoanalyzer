package ru.javarush.golf.sobolevskiy;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Cryptoanalyzer {
    private static final String INPUT_PATH = "input.txt";
    private static final String OUTPUT_TXT = "output.txt";
    private static final String OTHER_TXT = "other.txt";

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

        switch (mode) {
            case 1: { // Шифровка
                System.out.println("Введите сдвиг:");
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
                System.out.println("Введите сдвиг:");
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
                System.out.println("Поиск ключа ...");
                BruteForce bruteForce = new BruteForce();
                int key = -1;
                try (
                    FileReader fileReader = new FileReader(INPUT_PATH)) {
                    key = bruteForce.findShiftByPatterns(fileReader);
                }
                if (key >= 0)
                    System.out.println("Найден ключ: " + key);
                else
                    System.out.println("Ключ не найден");
                break;
            }
            case 4: { // Статистический анализ
                System.out.println("Статистический анализ ...");
                Statistic statistic = new Statistic();
                try {
                    statistic.decodeBySampleFile(INPUT_PATH, OUTPUT_TXT, OTHER_TXT);
                } catch (IOException e) {
                    System.err.println(e.getStackTrace());
                }
                break;
            }
            default:
                break;
        }
    }
}
