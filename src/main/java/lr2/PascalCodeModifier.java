package lr2;

import java.util.HashMap;
import java.util.Map;

public class PascalCodeModifier {
    public static void main(String[] args) {
        // Пример кода на языке Pascal без пробелов
        String pascalCode = "" +
                "programTest;" +
                "var x: integer;" +
                "   beginx:=5;" +
                "       if x>0 then" +
                "           writeln('Positive');" +
                "   end.";

        // Создаем словарь со служебными словами Pascal
        Map<String, String> keywordMapping = new HashMap<>();
        keywordMapping.put("program", "program");
        keywordMapping.put("var", "var");
        keywordMapping.put("begin", "begin");
        keywordMapping.put("end", "end");
        keywordMapping.put("if", "if");
        keywordMapping.put("then", "then");

        // Проверяем и модифицируем код
        StringBuilder modifiedCode = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();
        boolean isInString = false;

        for (int i = 0; i < pascalCode.length(); i++) {
            char currentChar = pascalCode.charAt(i);

            if (currentChar == '\'') {
                isInString = !isInString;
            }

            if (!isInString && !Character.isLetter(currentChar)) {
                String word = currentWord.toString().toLowerCase();
                String modifiedWord = keywordMapping.getOrDefault(word, currentWord.toString());
                modifiedCode.append(modifiedWord);
                currentWord = new StringBuilder();
            }

            currentWord.append(currentChar);
            modifiedCode.append(currentChar);
        }

        // Выводим модифицированный код
        System.out.println(modifiedCode.toString());
    }
}

