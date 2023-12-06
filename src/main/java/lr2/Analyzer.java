package lr2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static List<String> functionWordsMas = Arrays.asList(
            "program", "var", "integer", "function", "procedure", "begin", "if", "else", "then", "result", "end",
            "break", "constructor", "do", "exit", "finalization", "is", "or", "property", "repeat", "shr", "until", "array",
            "case", "continue", "downto", "string", "float", "double", "char", "writeln", "write", "const", "of", "for", "to", "boolean",
            "label", "goto", "realization", "implementation", "while", "shl", "random"
    );

    public static List<String> sealersMas = Arrays.asList(
            ",", ":", ";", "+", ":=", "=", "<>", ">=", "<=", ">", "<", "[", "(", ")", ".", "]", " ", "\n", "-"
    );

    public static final String REGEX_NUMBERS = "(\\b\\d+\\.?\\d*\\b)|(\\b-\\d+(\\.\\d+)?\\b)";

    public static List<String> readFile(String fileName) throws IOException {
        List<String> textList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));

        while (reader.ready()) {
            textList.add(reader.readLine());
        }

        reader.close();
        return textList;
    }

    public static boolean checkFunctional(String word) {
        return functionWordsMas.contains(word.toLowerCase());
    }

    public static boolean checkVar(String word, String line, int j) {
        String findVar = "";
        Pattern pattern = Pattern.compile("[a-zA-Z_]+\\w*");
        Matcher matcher = pattern.matcher(word);
        while (matcher.find()) {
            findVar = word.substring(matcher.start(), matcher.end());
        }
        boolean isNormalVar = findVar.equals(word);
        boolean isLastSealer = false;

        String wrd = "";
        for (int i = j; i < line.length(); i++) {
            String ch = String.valueOf(line.charAt(i));
            wrd = (sealersMas.contains(ch)) ? "" : wrd + ch;
            if ((sealersMas.contains(ch) || functionWordsMas.contains(wrd)) && !ch.equals(" ") && !ch.equals("\n")) {
                isLastSealer = true;
            }
        }

        return (isNormalVar && isLastSealer);
    }

    public static boolean checkNumber(String word) {
        String findNumbers = "";
        Pattern pattern = Pattern.compile(REGEX_NUMBERS);
        Matcher matcher = pattern.matcher(word + " ");
        while (matcher.find()) {
            findNumbers = word.substring(matcher.start(), matcher.end());
        }
        return findNumbers.equals(word);
    }

    public static void main(String[] args) throws IOException {
        List<String> textList = readFile("src/main/java/lr2/pascal.pas");
        int lineCount = 1;
        for (String line: textList) {
            System.out.print(ANSI_BLUE + lineCount + "   " + ANSI_RESET);

            boolean isLastFunctional = false;

            boolean isStartString = false;
            boolean isLastSealer = false;



            String word = "";
            line += " ";
            for (int i = 0; i < line.length(); i++) {
                String ch = String.valueOf(line.charAt(i));
                if (ch.equals("'") && isStartString) {
                    System.out.print(word +  "' " + ANSI_GREEN + " (СТРОКА.) " + ANSI_RESET);
                    word = "";
                    isStartString = false;
                }
                if (ch.equals("'")) {
                    isStartString = true;
                }
                if (sealersMas.contains(ch) && !isStartString && !isLastSealer) {
                    if (!ch.equals(" ") && !ch.equals("\n")) {
                        isLastSealer = true;
                    }
                    if (checkFunctional(word)) { // If word is functional
                        System.out.print(word + ANSI_GREEN + " (СЛУЖ.) " + ANSI_RESET);
                        isLastFunctional = true;
                    } else if (checkVar(word, line, i) && !word.equals("") && !word.equals(" "))  {
                        System.out.print(word + ANSI_GREEN + " (ИДЕНТ.) " + ANSI_RESET);
                    } else if (checkNumber(word) && !word.equals("") && !word.equals(" ")) {
                        System.out.print(word + ANSI_GREEN + " (ЧИСЛ.) " + ANSI_RESET);
                    } else if (!word.equals("") && !word.equals(" ")) {
                        System.out.print(word + ANSI_RED + " (НЕ ОПР.) " + ANSI_RED);
                    }
                    if (!ch.equals(" ") && !ch.equals("\n")) {
                        word=ch;
                    } else {
                        word="";
                    }
                } else if (!sealersMas.contains(ch) && !isStartString && isLastSealer) {
                    if (!ch.equals(" ") && !ch.equals("\n")) {
                        System.out.print(word.trim() + ANSI_GREEN + " (РАЗД.) " + ANSI_RESET);
                        isLastSealer = false;
                        word = ch;
                    }

                }
                else {
                    word += ch ;
                }
            }
            System.out.println("\n");
            lineCount++;
        }
    }
}