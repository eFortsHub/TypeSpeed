import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import netscape.javascript.JSObject;

public class Main {
    public static void main(String[] args) {

        // structure: pronoun + verb + word + pronoun + word + verb + word + verb * 40

        // for normal visitor: 30 content
        // pronoun 2-5
        // verbs 3-8
        // word 3-6

        int proLimit = 8, verbLimit = 8, wordLimit = 6;

        ArrayList<String> proList = getList(2, proLimit, "pronouns_");
        ArrayList<String> verbList = getList(3, verbLimit, "verbs");
        ArrayList<String> wordList = getList(3, wordLimit, "words_");

        print("pronounce: " + proList.size());
        print("verb: " + verbList.size());
        print("word: " + wordList.size());

        int i = 0;

        while (i < 500) { // testing with 3
            i++;
            String content = buildParagraph(proList, verbList, wordList);
            String source = "{\"value\":\"" + content + "\"}";

            File fp = new File("contents/guests");

            File file = new File(fp, i+".json");

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileWriter fw;
            try {
                fw = new FileWriter(file);
                Scanner sc = new Scanner(source);
                while (sc.hasNextLine()) {
                    String s = sc.nextLine();
                    fw.append(s);
                }
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private static String buildParagraph(ArrayList<String> pronouns, ArrayList<String> verbs,
            ArrayList<String> words) {
        StringBuilder paragraph = new StringBuilder();

        // structure: pronoun + verb + word + pronoun + word + verb + word + verb * 40

        for (int i = 0; i < 100; i++) {
            paragraph.append(getRandomStringFromList(pronouns));
            paragraph.append(" ");

            paragraph.append(getRandomStringFromList(verbs));
            paragraph.append(" ");

            paragraph.append(getRandomStringFromList(words));
            paragraph.append(" ");

            paragraph.append(getRandomStringFromList(pronouns));
            paragraph.append(" ");

            paragraph.append(getRandomStringFromList(words));
            paragraph.append(" ");

            paragraph.append(getRandomStringFromList(verbs));
            paragraph.append(" ");

            paragraph.append(getRandomStringFromList(words));
            paragraph.append(" ");

            paragraph.append(getRandomStringFromList(verbs));
            paragraph.append(" ");

        }

        return paragraph.toString();
    }

    private static String getRandomStringFromList(ArrayList<String> list) {
        String s = "";

        Random random = new Random();
        int randomNumber = random.nextInt(list.size() - 1);
        s = list.get(randomNumber);

        if (randomNumber % 3 == 0 && randomNumber %5==0) {
            return s;
        }

        return s.toLowerCase();
    }

    public static void print(Object x) {
        System.out.println(x);
    }

    public static ArrayList<String> getList(int min, int limit, String file) {
        ArrayList<String> list = new ArrayList<>();

        // create pronouns file list
        ArrayList<File> proFiles = new ArrayList<>();
        for (int i = min; i <= limit; i++) {
            String parent = file.replace("_", "");
            proFiles.add(new File(parent, file + i + ".json"));
        }

        for (File f : proFiles) {
            try (Scanner sc = new Scanner(f)) {
                String fileContent = "";
                while (sc.hasNextLine()) {
                    fileContent += sc.nextLine();
                }

                try {
                    JSONObject jo = (JSONObject) new JSONParser().parse(fileContent);

                    JSONArray ja = (JSONArray) jo.get("array");

                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject job = (JSONObject) ja.get(i);
                        list.add((String) job.get("value"));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}