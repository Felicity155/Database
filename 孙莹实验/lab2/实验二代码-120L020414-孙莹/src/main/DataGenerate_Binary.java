package main;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class DataGenerate_Binary {
    private static final int RECORD_SIZE = 32; // ÿ����¼���ֽ���
    private static final int MAX_STRING_LENGTH = 20; // B�����ַ�������󳤶�
    private static final int RECORD_COUNT = 100; // ���ɵļ�¼����

    public static void main(String[] args) {
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream("E:\\Databean\\lab2\\lab2_code\\data.bin"))) {
            Random random = new Random();
            for (int i = 0; i < RECORD_COUNT; i++) {
                // ����A����
                int a = random.nextInt();

                // ����B����
                int bLength = random.nextInt(MAX_STRING_LENGTH + 1);
                byte[] bBytes = new byte[bLength];
                random.nextBytes(bBytes);
                String b = new String(bBytes);

                // д���¼
                output.writeInt(a);
                output.writeUTF(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
