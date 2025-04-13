package ghi;

import java.io.*;

public class KeyboardToFile {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FileWriter writer = new FileWriter("output.txt");

        String line;
        System.out.println("Nhập dữ liệu (gõ 'exit' để thoát):");
        while (!(line = reader.readLine()).equals("exit")) {
            writer.write(line + "\n");
        }

        reader.close();
        writer.close();
    }
}
