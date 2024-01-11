package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerate_TXT {

    public static void main(String[] args) {
        int numRecords = 10240; // 生成的记录数量
        String fileName = "E:\\Databean\\lab2\\lab2_code\\data.txt"; // 存储文件名

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Random random = new Random();

            for (int i = 0; i < numRecords; i++) {
                int a = random.nextInt(100); // 生成随机数值
                String b = generateRandomString(2, 10); // 生成长度在2到10之间的随机字符串
                String record = a + "\t" + b; // 拼接记录字符串
                writer.write(record); // 写入文件
                writer.newLine(); // 换行
            }

            System.out.println("生成"+numRecords+"条记录成功，已存储到文件 " + fileName);
        } catch (IOException e) {
            System.out.println("存储文件失败：" + e.getMessage());
        }
    }

    // 生成指定长度范围内的随机字符串
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
