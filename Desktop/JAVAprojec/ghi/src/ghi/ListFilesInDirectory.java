package ghi;

import java.io.File;

public class ListFilesInDirectory {
    public static void main(String[] args) {
        File folder = new File("duong_dan_thu_muc"); // thay đường dẫn cụ thể
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                System.out.println("File: " + file.getName());
            }
        }
    }
}
