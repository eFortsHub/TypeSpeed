import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Z {
    public static void main(String a[]) {

        File file = new File("./sentence");
        Scanner sc;
        String str = "";
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                str += s;
            }

            String[] arr = str.split(" ");

            int previous = 0;
            int index = 0;
            for (int i = 50; i < arr.length; i += 50) {
                String sentense = "{\"value\":\"";
                for (int j = previous; j < i+300; j++) {
                    String st = arr[j];

                    sentense += st+" ";

                    System.out.println(st);
                }

                sentense+="\"}";

                File f = new File(index+".json");

                if (!f.exists())
                    f.createNewFile();

                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(sentense.toLowerCase());
                bw.flush();
                bw.close();

                previous = i;
                index++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
