package ghi;

import java.io.*;

public class WriteReadIntegers {
    public static void main(String[] args) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("numbers.dat"));
        int[] nums = {1, 2, 3, 4, 5};
        for (int n : nums) {
            dos.writeInt(n);
        }
        dos.close();

        DataInputStream dis = new DataInputStream(new FileInputStream("numbers.dat"));
        try {
            while (true) {
                int num = dis.readInt();
                System.out.println(num);
            }
        } catch (EOFException e) {
            // hết file
        }
        dis.close();
    }
}
