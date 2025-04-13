package ghi;

import java.io.*;

public class CountLines {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        int count = 0;
        while (reader.readLine() != null) {
            count++;
        }

        System.out.println("Số dòng trong file: " + count);
        reader.close();
    }
}
