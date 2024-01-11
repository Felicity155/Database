package main;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class DataGenerate_Binary {
    private static final int RECORD_SIZE = 32; // 每条记录的字节数
    private static final int MAX_STRING_LENGTH = 20; // B属性字符串的最大长度
    private static final int RECORD_COUNT = 100; // 生成的记录数量

    public static void main(String[] args) {
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream("E:\\Databean\\lab2\\lab2_code\\data.bin"))) {
            Random random = new Random();
            for (int i = 0; i < RECORD_COUNT; i++) {
                // 生成A属性
                int a = random.nextInt();

                // 生成B属性
                int bLength = random.nextInt(MAX_STRING_LENGTH + 1);
                byte[] bBytes = new byte[bLength];
                random.nextBytes(bBytes);
                String b = new String(bBytes);

                // 写入记录
                output.writeInt(a);
                output.writeUTF(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
