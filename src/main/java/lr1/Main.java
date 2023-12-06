package lr1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        testNumber("130", generateAuto(5, 4));
//        testNumber("1300000013", readAutoFromFile("testFile.txt"));

    }

    public static void testNumber(String strNumber, List<String> stateList) {
        char startState = stateList.get(0).charAt(0);
        char lastState = startState;
        for (int i = 0; i < stateList.size(); i++) {
            System.out.println(stateList.get(i));
        }
        System.out.println("______");
        for (int i = 0; i < strNumber.length(); i++) {
            char curChar = strNumber.charAt(i);
            for (int j = 0; j < stateList.size(); j++) {
                String curStr = stateList.get(j).replaceAll(" ", "");
                if (lastState == curStr.charAt(0) && curChar == curStr.charAt(1)) {
                   lastState = curStr.charAt(2);
                    System.out.println(stateList.get(j));
                    break;
                }
            }
        }
        if (lastState == startState) {
            System.out.println("\nЧисло " + strNumber + " делится ");
        } else {
            System.out.println("\nЧисло " + strNumber + " не делится");
        }
        System.out.println("Кол-во состояний: " + stateList.size());
        System.out.println("\nНачальное состояние: " + startState);
        System.out.println("Кол-во дуг: " + stateList.size());
    }

    public static List<String> generateAuto(int p, int q) {
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<String> stateList = new ArrayList<>();
        int endIndex = 0;
        for(int i = 0; i < q; i++) {
            for (int j = 0; j < p; j++) {
                stateList.add(new String(alphabet.charAt(i) + " " + alphabet.charAt(j)) + " " + alphabet.charAt(endIndex));
                endIndex = endIndex == q - 1 ? 0 : endIndex + 1;
            }
        }
        return stateList;
    }

    public static List<String> readAutoFromFile(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/denis/study/_4_Kurs/_Avtomats/LR1/src/main/java/lr1/" + fileName));
        List<String> stateList = new ArrayList<>();

        String line = bufferedReader.readLine();
        while (line != null) {
            stateList.add(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return stateList;
    }
}