import java.io.*;

public class Main {
    public static void main(String[] args) {
        String text = "В 2024 году я написал 15 программ и прочитал 3 книги о Java. Это мой 1-й проект!";

        analyzeText(text);
    }
//тест
    public static void analyzeText(String text) {
        StringReader reader = new StringReader(text);
        StreamTokenizer tokenizer = new StreamTokenizer(reader);

        tokenizer.wordChars('а', 'я');  // Русские буквы
        tokenizer.wordChars('А', 'Я');  // Русские заглавные
        tokenizer.wordChars('a', 'z');  // Английские буквы
        tokenizer.wordChars('A', 'Z');  // Английские заглавные
        tokenizer.wordChars('-', '-');  // Дефис для слов типа "1-й"

        int wordCount = 0;
        int digitCount = 0;
        int numberCount = 0;

        try {
            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                switch (tokenizer.ttype) {
                    case StreamTokenizer.TT_WORD:
                        wordCount++;

                        String word = tokenizer.sval;
                        for (char c : word.toCharArray()) {
                            if (Character.isDigit(c)) {
                                digitCount++;
                            }
                        }

                        if (isNumber(word)) {
                            numberCount++;
                        }
                        break;

                    case StreamTokenizer.TT_NUMBER:
                        numberCount++;
                        wordCount++;

                        double num = tokenizer.nval;
                        String numStr = String.valueOf((int) num);
                        digitCount += numStr.length();
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Текст для анализа: " + text);
        System.out.println("Статистика:");
        System.out.println("Количество слов: " + wordCount);
        System.out.println("Количество цифр: " + digitCount);
        System.out.println("Количество чисел: " + numberCount);
    }

    private static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            if (str.matches("\\d+-[а-я]+")) {
                return true;
            }
            return false;
        }
    }
}