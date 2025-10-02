package lab2;

import java.io.*;
import java.util.*;

public class laba2 {
    public static void main(String[] args) {
        // Тестовые примеры
        String[] equations = {
                "5 + 3 = 8",
                "10 - 4 = 5",  // неверно
                "2 * 6 = 12",
                "15 / 3 = 6",  // неверно
                "7 + 2 = 9"
        };

        for (String equation : equations) {
            boolean isValid = validateEquation(equation);
            System.out.println(equation + " -> " + (isValid ? "ВЕРНО" : "НЕВЕРНО"));
        }
    }

    public static boolean validateEquation(String equation) {
        try {
            StringReader reader = new StringReader(equation);
            StreamTokenizer tokenizer = new StreamTokenizer(reader);

            // Настраиваем токенайзер для работы с математическими выражениями
            tokenizer.ordinaryChar('/');  // обрабатываем / как обычный символ
            tokenizer.ordinaryChar('*');  // обрабатываем * как обычный символ
            tokenizer.ordinaryChar('+');  // обрабатываем + как обычный символ
            tokenizer.ordinaryChar('-');  // обрабатываем - как обычный символ
            tokenizer.ordinaryChar('=');  // обрабатываем = как обычный символ

            List<Object> tokens = new ArrayList<>();

            // Разбираем уравнение на токены
            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                    tokens.add(tokenizer.nval);
                } else if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                    tokens.add(tokenizer.sval);
                } else {
                    tokens.add((char) tokenizer.ttype);
                }
            }

            // Проверяем структуру уравнения
            if (tokens.size() != 5) {
                return false; // Должно быть: число операция число = число
            }

            // Извлекаем компоненты уравнения
            double leftOperand = (double) tokens.get(0);
            char operator = (char) tokens.get(1);
            double rightOperand = (double) tokens.get(2);
            char equalsSign = (char) tokens.get(3);
            double result = (double) tokens.get(4);

            // Проверяем, что третий токен это '='
            if (equalsSign != '=') {
                return false;
            }

            // Вычисляем ожидаемый результат
            double expectedResult = calculate(leftOperand, operator, rightOperand);

            // Сравниваем с фактическим результатом
            return Math.abs(expectedResult - result) < 0.0001;

        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            return false; // Если что-то пошло не так
        }
    }

    private static double calculate(double a, char operator, double b) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}