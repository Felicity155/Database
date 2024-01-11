package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerate_TXT {

    public static void main(String[] args) {
        int numRecords = 10240; // ���ɵļ�¼����
        String fileName = "E:\\Databean\\lab2\\lab2_code\\data.txt"; // �洢�ļ���

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Random random = new Random();

            for (int i = 0; i < numRecords; i++) {
                int a = random.nextInt(100); // ���������ֵ
                String b = generateRandomString(2, 10); // ���ɳ�����2��10֮�������ַ���
                String record = a + "\t" + b; // ƴ�Ӽ�¼�ַ���
                writer.write(record); // д���ļ�
                writer.newLine(); // ����
            }

            System.out.println("����"+numRecords+"����¼�ɹ����Ѵ洢���ļ� " + fileName);
        } catch (IOException e) {
            System.out.println("�洢�ļ�ʧ�ܣ�" + e.getMessage());
        }
    }

    // ����ָ�����ȷ�Χ�ڵ�����ַ���
    private static String generateRandomString(int minLength, int maxLength) {
        Random random = new Random();
        int length = minLength + random.nextInt(maxLength - minLength + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }
}
