import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class ProblemThree {
    public static void main(String[] args) {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("ElephantsChild.txt");
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        //File file = new File(System.getProperty("user.dir") + "/ElephantsChild.txt");

        BufferedReader br = new BufferedReader(streamReader);

        Hashtable<Integer, String> hashedWords = new Hashtable<Integer, String>();
        //hashmap that takes a word as its key and the calculated hash as its value
        HashMap<String, Integer> hashValues = new HashMap<String, Integer>();
        //arraylist of objects that stores an address, word, and calculated hash
        ArrayList<HashData> hashData = new ArrayList<HashData>();

        //given constants
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

            //store hash data for part e
            hashData.add(new HashData(hash, s, startHash));
        }


        //print the hash table's contents and keep track of how many empty addresses are in the table
        int numEmptyAddresses = 0;
        int emptyAddressStreak = 0;
        int highestEmptyAddressStreak = 0;
        int nonEmptyAddressStreak = 0;
        int highestNonEmptyAddressStreak = 0;
        for(int i = 0; i < 1000; ++i){
            String word = hashedWords.get(i);
            String value = hashValues.get(word) == null ? "null" : hashValues.get(word).toString();
            System.out.println("Hash address: " + i + ", Hashed Word: " + word + ", Hash Value of Word: " + value);

            if(value == "null"){
                nonEmptyAddressStreak = 0;
                ++numEmptyAddresses;
                ++emptyAddressStreak;
                highestEmptyAddressStreak = emptyAddressStreak > highestEmptyAddressStreak ? emptyAddressStreak : highestEmptyAddressStreak;
            } else {
                emptyAddressStreak = 0;
                ++nonEmptyAddressStreak;
                highestNonEmptyAddressStreak = nonEmptyAddressStreak > highestNonEmptyAddressStreak ? nonEmptyAddressStreak : highestNonEmptyAddressStreak;
            }
        }

        //get hash value with most distinct words associated with it
        //create a hash map to keep track of number of occurrences
        HashMap<Integer, Integer> reoccurringHashValues = new HashMap<Integer, Integer>();
        for(String key : hashValues.keySet()){
            int hash = hashValues.get(key);
            if(reoccurringHashValues.get(hash) == null){
                reoccurringHashValues.put(hash, 1);
            } else {
                int numOccurrences = reoccurringHashValues.get(hash);
                reoccurringHashValues.put(hash, numOccurrences + 1);
            }
        }

        //create an int to keep track of highest occurrences
        int mostOccurrences = 0;
        int mostOccurrencesHash = 0;
        for(Integer hash : reoccurringHashValues.keySet()){
            int value = reoccurringHashValues.get(hash);
            if (value > mostOccurrences) {
                mostOccurrences = value;
                mostOccurrencesHash = hash;
            }
        }

        //find the word placed farthest from its actual hash address and how far away it is
        HashData farthestWord = new HashData(0, "", 0);
        int farthestDistance = 0;
        for(HashData h : hashData){
            int distance = Math.abs(h.address - h.calculatedHash);
            //account for wrapping
            if(distance >= 500){
                distance = 1000 - distance;
            }
            if(distance > farthestDistance){
                farthestWord = h;
                farthestDistance = distance;
            }
        }


        System.out.println("Number of empty addresses: " + numEmptyAddresses);
        System.out.println("Longest empty streak: " + highestEmptyAddressStreak);
        System.out.println("Longest non-empty streak: " + highestNonEmptyAddressStreak);
        System.out.println("Hash address that has the most disctinct words: " + mostOccurrencesHash + " with " + mostOccurrences + " distinct words.");
        System.out.println(farthestWord.word + " is placed furthest from its address in the table, being " + Math.abs(farthestWord.calculatedHash - farthestWord.address) + " spaces away from its original hash of " + farthestWord.calculatedHash);
    }


    public static class HashData{
        public int address;
        public String word;
        public int calculatedHash;

        HashData(int address, String word, int calculatedHash){
            this.address = address;
            this.word = word;
            this.calculatedHash = calculatedHash;
        }
    }
}