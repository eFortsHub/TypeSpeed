import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class test {

    public static BufferedWriter fileWriter;

    public static void main(String[] args) {
        File file = new File("./verb.txt");

        try (Scanner scanner = new Scanner(file, Charset.forName("utf-8"))) {

            int limit = 13;

            File f = new File("verbs"+limit+".json");
            if (!f.exists()) {
                f.createNewFile();

            }

            if (fileWriter == null)
                fileWriter = new BufferedWriter(new FileWriter(f));

            fileWriter.append("{\n  \"array\": [\n");

            boolean isFirst = true;
            while (scanner.hasNext()) {
                String text = scanner.nextLine().trim();
                if (!text.contains("{") || !text.contains("}")) {
                    if (text.contains(",")) {
                        text = text.replaceAll(",", "");
                    }

                 //   String[] arr = text.split(":");
                   // text = arr[0].trim().replaceAll("\"", "");

                
                    if (text.length() == limit) {

                        if(!isFirst){
                            text = ",\n"+"    {" + "\"value\":\"" + text + "\"" + "}";;
                        }else {
                            isFirst = false;
                        text = "    {" + "\"value\":\"" + text + "\"" + "}";
                        }
                        
                        fileWriter.append(text);
                        System.out.println("writing : "+text);

                    }

                }
            }

            fileWriter.append("\n  ]\n}");
            System.out.println("ended");
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}