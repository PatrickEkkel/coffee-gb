package eu.rekawek.coffeegb;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EkkelUtils {


    private static String toHex(int c) {
        return String.format("0x%X", c);
    }
    public static void dumpVideoRam(Dumpable dumpable) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/tmp/coffeegb_videoram.dump"));
            int startVideoRam = 0x8000;
            int endVideoRam = 0x9FFF;

           // String generatedCode = "HashMap<Integer,Integer> bgMap = new HashMap<>();\n";
            String generatedCode = "new int[] {\n";
            int breakline = 16;
            int counter = 0;
            for(int i=startVideoRam;i<endVideoRam;i++) {
               int value =  dumpable.read(i);
             //  generatedCode += " bgMap.put(" + toHex(i) + "," + toHex(value) + ");\n";
                 generatedCode += toHex(value) + ",";
                 if(counter == breakline) {
                     generatedCode += "\n";
                     counter = 0;
                 }
                 counter++;
            }

            generatedCode += "\n};";

            bufferedWriter.write(generatedCode);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
