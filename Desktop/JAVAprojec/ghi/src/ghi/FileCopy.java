package ghi;

import java.io.*;

public class FileCopy {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("input.txt");
        FileOutputStream fos = new FileOutputStream("output.txt");

        int c;
        while ((c = fis.read()) != -1) {
            fos.write(c);
        }

        fis.close();
        fos.close();
    }
}
