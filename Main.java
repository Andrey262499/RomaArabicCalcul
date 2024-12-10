import java.util.Scanner;

public class Main {
    private static final String ROMAN_NUMERALS = "IVXLCDM";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение: ");
        String input = scanner.nextLine();
        try {
            System.out.println(calculate(input));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static String calculate(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Некорректный ввод. Используйте формат: ");
        }

        String firstOperand = parts[0];
        String operator = parts[1];
        String secondOperand = parts[2];

        boolean isRoman = isRoman(firstOperand) && isRoman(secondOperand);
        boolean isArabic = isArabic(firstOperand) && isArabic(secondOperand);

        if (!isRoman && !isArabic) {
            throw new Exception("Числа должны быть либо арабскими, либо римскими");
        }

        int num1 = isRoman ? (isValidRoman(firstOperand) ? romanToArabic(firstOperand) : throwException("Недопустимое римское число.")) : Integer.parseInt(firstOperand);
        int num2 = isRoman ? (isValidRoman(secondOperand) ? romanToArabic(secondOperand) : throwException("Недопустимое римское число.")) : Integer.parseInt(secondOperand);

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Числа должны быть в диапазоне от 1 до 10.");
        }

        int result = performOperation(num1, num2, operator);
        return isArabic ? String.valueOf(result) : arabicToRoman(result);
    }

    private static boolean isRoman(String str) {
        return str.chars().allMatch(c -> ROMAN_NUMERALS.indexOf(c) != -1);
    }

    private static boolean isArabic(String str) {
        return str.matches("\\d+");
    }

    private static boolean isValidRoman(String roman) {
        return roman.equals("I") || roman.equals("II") || roman.equals("III") ||
                roman.equals("IV") || roman.equals("V") || roman.equals("VI") ||
                roman.equals("VII") || roman.equals("VIII") || roman.equals("IX") ||
                roman.equals("X");
    }

    private static int romanToArabic(String roman) {
        int total = 0;
        for (int i = 0; i < roman.length(); i++) {
            int value = romanValue(roman.charAt(i));
            if (i + 1 < roman.length() && romanValue(roman.charAt(i + 1)) > value) {
                total -= value;
            } else {
                total += value;
            }
        }
        return total;
    }

    private static int romanValue(char r) {
        switch (r) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }

    private static String arabicToRoman(int num) throws Exception {
        if (num < 1) throw new Exception("Результат не может быть менее 1 для римских чисел.");
        StringBuilder result = new StringBuilder();
        while (num >= 1000) { result.append("M"); num -= 1000; }
        while (num >= 900) { result.append("CM"); num -= 900; }
        while (num >= 500) { result.append("D"); num -= 500; }
        while (num >= 400) { result.append("CD"); num -= 400; }
        while (num >= 100) { result.append("C"); num -= 100; }
        while (num >= 90) { result.append("XC"); num -= 90; }
        while (num >= 50) { result.append("L"); num -= 50; }
        while (num >= 40) { result.append("XL"); num -= 40; }
        while (num >= 10) { result.append("X"); num -= 10; }
        while (num >= 9) { result.append("IX"); num -= 9; }
        while (num >= 5) { result.append("V"); num -= 5; }
        while (num >= 4) { result.append("IV"); num -= 4; }
        while (num >= 1) { result.append("I"); num -= 1; }
        return result.toString();
    }

    private static int performOperation(int a, int b, String operator) throws Exception {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) throw new Exception("Деление на ноль.");
                return a / b;
            default:
                throw new Exception("Недопустимая операция");
        }
    }

    private static int throwException(String message) throws Exception {
        throw new Exception(message);
    }
}
