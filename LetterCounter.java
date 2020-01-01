import java.util.*;

public class LetterCounter {
    private static final char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private static HashMap<Character, Integer> map = new HashMap<>();

    private static void fillDictionary(String quote) {
        for (int i = 0; i < quote.length(); i++){
            if (quote.charAt(i) < 65 || quote.charAt(i) > 122 || quote.charAt(i) < 97 && quote.charAt(i) > 90) continue;
            char c = Character.toUpperCase(quote.charAt(i));
            map.merge(c, 1, (a, b) -> a + b);
        }
    }

    private static HashMap<Character, Integer> sortByValue(HashMap<Character, Integer> map){
        List<Map.Entry<Character, Integer>> list = new LinkedList<>(map.entrySet());

        list.sort((o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

        HashMap<Character, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Character, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private static void getLetterFrequency(HashMap<Character, Integer> map){
        for (char c : map.keySet()) {
            System.out.println(c + " comes up " + map.get(c) + " times");
        }
        for (char c : letters){
            if (!map.keySet().contains(c)){
                System.out.println(c + " comes up 0 times");
            }
        }
    }

    public static void main(String[] args) {
        String quote = "Wherefore rejoice? What conquest brings he home?\n" +
                "What tributaries follow him to Rome,\n" +
                "To grace in captive bonds his chariot-wheels?\n" +
                "You blocks, you stones, you worse than senseless things!\n" +
                "O you hard hearts, you cruel men of Rome,\n" +
                "Knew you not Pompey?";
        System.out.println("You entered: " + quote);
        fillDictionary(quote);
        map = sortByValue(map);
        getLetterFrequency(map);
    }
}