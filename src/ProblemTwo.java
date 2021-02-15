import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class ProblemTwo {

    public static void main(String[] args) {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("ElephantsChild.txt");
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        //File file = new File(System.getProperty("user.dir") + "/ElephantsChild.txt");

        BufferedReader br = new BufferedReader(streamReader);

        Hashtable<Integer, String> hashedWords = new Hashtable<Integer, String>();
        HashMap<String, Integer> hashValues = new HashMap<String, Integer>();
        int C = 123;
        int m = 1000;

        ArrayList<String> words = new ArrayList<String>();

        try {
            String st = "";

            //initialize the arrayList of words by separating lines by spaces
            while ((st = br.readLine()) != null) {
                for (String s : st.split("\\s+")) {
                    //removes anything that's not an apostrophe, dash, or letter
                    String formattedString = s.replaceAll("[^'\\-a-zA-Z]*", "");
                    words.add(formattedString);
                }
            }
        } catch (IOException i) {
            System.out.println("IOException");
        }

        //hash the words
        boolean tableFull = false;
        for (String s : words) {
            int hash = 0;
            for (Character c : s.toCharArray()) {
                hash = (hash * C + (int) (c)) % m;
            }

            //add the word and its calculated hash to the hashtable
            int startHash = hash;
            while(hashedWords.get(hash) != null){
                if(s.equals(hashedWords.get(hash))) break;
                ++hash;
                if(startHash == hash){
                    tableFull = true;
                    break;
                }
                if(hash >= 1000){
                    hash = 0;
                }
            }

            if(tableFull) break;

            hashedWords.put(hash, s);

            //Store the hash values for each word in a hash map to reference later in part b
            hashValues.put(s, startHash);
        }


        //print the hash table's contents
        for(int i = 0; i < 1000; ++i){
            String word = hashedWords.get(i);
            String value = hashValues.get(word) == null ? "null" : hashValues.get(word).toString();
            System.out.println("Hash address: " + i + ", Hashed Word: " + word + ", Hash Value of Word: " + value);
        }
    }
}
