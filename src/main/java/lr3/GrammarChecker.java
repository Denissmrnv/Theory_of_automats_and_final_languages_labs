package lr3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrammarChecker {
    public static void grammarCheckWithRules(Set<String> nTerminals, String[][] rules, Set<String> sets, List<String> waysList, String grammar, String expression) {

        char ch = grammar.charAt(grammar.length() - 1);
        int index = grammar.length() - 1;
        boolean flag = true;

        if (!grammar.equals(expression)) {
            for (char charGrammar : grammar.toCharArray()) {
                if (nTerminals.contains(String.valueOf(charGrammar))) {
                    flag = false;
                }
            }
        }

        if (grammar.equals(expression)) {

            for (String way: waysList) {
                System.out.println(way);
            }

            System.out.println("\n" + grammar);

            return;
        }

        if (flag) {
            return;
        }

        while (true) {
            if (nTerminals.contains(String.valueOf(ch))) {
                break;
            } else {
                index--;
                ch = grammar.charAt(index);
            }
        }

        if (grammar.length() > expression.length() || sets.contains(grammar)) {
            return;
        }

        sets.add(grammar);

        for (int i = 0; i < rules.length; i++) {
            if (rules[i][0].equals(String.valueOf(ch))) {
                waysList.add("№ " + rules[i][2] + "     " + grammar + " -> " + grammar.substring(0, index) + rules[i][1] + grammar.substring(index + 1, grammar.length()));
                grammarCheckWithRules(nTerminals, rules, sets, waysList,grammar.substring(0, index) + rules[i][1] + grammar.substring(index + 1, grammar.length()),expression);
                waysList.remove(waysList.size() - 1);
            }
        }
    }

    public static void main(String[] args) {

        Set<String> nTerminalsSet = new HashSet<>();
        List<String> waysList = new ArrayList<>();
        String[][] rules = new String[][] {

        new String[] {"E", "E+T", "1"},
        new String[] {"E", "T", "2"},
        new String[] {"T", "T*F", "3"},
        new String[] {"T", "F", "4"},
        new String[] {"F", "(E)", "5"},
        new String[] {"F", "a", "6"},
        new String[] {"F", "b", "7"},

//        new String[] {"S", "0S", "1"},
//        new String[] {"S", "1P", "2"},
//        new String[] {"P", "1S", "3"},
//        new String[] {"P", "0Q", "4"},
//        new String[] {"Q", "0P", "5"},
//        new String[] {"Q", "1Q", "5"},
//        new String[] {"S", "0", "6"},
//        new String[] {"P", "1", "7"},

//        new String[] {"S", "SS", "1"},
//        new String[] {"S", "(S)", "2"},
//        new String[] {"S", "()", "3"},


        };


        for (String[] rule : rules) {
            nTerminalsSet.add(rule[0]);
        }

        grammarCheckWithRules(nTerminalsSet, rules, new HashSet<>(), waysList, "E", "(a+b)*a+(a+b)*a");
//        grammarCheckWithRules(nTerminalsSet, rules, new HashSet<>(), waysList, "S", "((())())");
//        grammarCheckWithRules(nTerminalsSet, rules, new HashSet<>(), waysList, "S", "01000101");
    }
}