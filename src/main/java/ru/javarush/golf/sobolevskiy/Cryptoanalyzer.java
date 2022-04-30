package ru.javarush.golf.sobolevskiy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Cryptoanalyzer {
    private static final int ZLO_SIZE = 666;
    private static final int MAX_SEARCH_RANGE = 66;
    private static char[] zlo = new char[ZLO_SIZE];
    private static final String inputPath = "input.txt";
    private static final String outputPath = "output.txt";
    private static final String otherPath = "other.txt";

    private static void crypt(int shift, int mode) throws IOException {
        FileReader fileReader = new FileReader(inputPath);
        FileWriter fileWriter = new FileWriter(outputPath);
        Operator operator = new Operator();
        int chars = fileReader.read(zlo);
        while (chars != -1) {
            fileWriter.write(operator.cryptAndDecrypt(zlo, shift, mode));
            chars = fileReader.read(zlo);
        }
        fileReader.close();
        fileWriter.close();
    }

    private static int bruteForce() throws IOException {
        int i;
        FileReader fileReader = new FileReader(inputPath);
        Operator operator = new Operator();
        int chars = fileReader.read(zlo);
        while (chars != -1) {
            for (i = 1; i <= MAX_SEARCH_RANGE; i++) {
                if (operator.checkShift(zlo, i) > 0) {
                    return i;
                }
            }
            chars = fileReader.read(zlo);
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Введите номер режима:\n" +
                "\t1. Шифрование\n" +
                "\t2. Расшифровка\n" +
                "\t3. Криптоанализ\n" +
                "\t4. ВЫХОД");
        System.out.println("Программа использует файлы: input.txt, output.txt, other.txt");

        Scanner console = new Scanner(System.in);
        int mode = console.nextInt();

        if (mode == 4)
            return;

        // Шифровка
        if (mode == 1) {
            System.out.println("Введите сдвиг:");
            int shift = console.nextInt();
            crypt(shift, mode);
            return;
        }

        // Дешифровка
        if (mode == 2) {
            System.out.println("Введите сдвиг:");
            int shift = console.nextInt();
            crypt(shift, mode);
            return;
        }

        // Криптоанализ
        if (mode == 3) {
            System.out.println("Поиск ключа ...");
            int ret = bruteForce();
            if (ret > 0)
                System.out.println("Найден ключ: " + ret);
            else
                System.out.println("Ключ не найден");
            return;
        }
    }
}
