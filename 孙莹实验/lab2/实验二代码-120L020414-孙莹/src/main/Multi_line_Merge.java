package main;

import java.io.*;
import java.util.*;

public class Multi_line_Merge {
        private static final int BUFFER_SIZE = 1024; // 内存缓冲区大小
        private static final int CHUNK_SIZE = 100; // 文件分块大小
        private static final String FILE_PREFIX = "chunk_"; // 文件名前缀
        private static final String input = "E:\\Databean\\lab2\\lab2_code\\data.txt";
        private static final String output = "E:\\Databean\\lab2\\lab2_code\\sorted.txt";

        public static void main(String[] args) throws IOException {
            // 多路归并排序
            long startTime = System.currentTimeMillis();

            externalMergeSort(input, output, CHUNK_SIZE, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    int a1 = Integer.parseInt(o1.split("\t")[0]);
                    int a2 = Integer.parseInt(o2.split("\t")[0]);
                    return Integer.compare(a1, a2);
                }
            });
            long endTime = System.currentTimeMillis();

            System.out.println("排序完成，耗时：" + (endTime - startTime) + "ms");
        }
        /**
         * 多路归并排序
         *
         * @param inputFile    待排序文件
         * @param outputFile   排序结果文件
         * @param chunkSize    文件分块大小
         * @param comparator   比较器
         * @throws IOException
         */
        private static void externalMergeSort(String inputFile, String outputFile, int chunkSize, Comparator<String> comparator) throws IOException {
            // 文件分块
            List<File> chunkFiles = chunk(inputFile, chunkSize, comparator);

            // 多路归并排序
            PriorityQueue<BufferedReader> queue = new PriorityQueue<>(chunkFiles.size(), new Comparator<BufferedReader>() {
                @Override
                public int compare(BufferedReader o1, BufferedReader o2) {
                    try {
                        String s1 = o1.readLine();
                        String s2 = o2.readLine();
                        if (s1 == null && s2 == null) {
                            return 0;
                        }
                        if (s1 == null) {
                            return 1;
                        }
                        if (s2 == null) {
                            return -1;
                        }
                        return comparator.compare(s1, s2);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });

            for (File file : chunkFiles) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                queue.offer(reader);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            while (!queue.isEmpty()) {
                BufferedReader reader = queue.poll();
                String line = reader.readLine();
                if (line != null) {
                    writer.write(line);
                    writer.newLine();
                    queue.offer(reader);
                } else {
                    reader.close();
                }
            }
            writer.close();
        }

        /**
         * 文件分块
         *
         * @param inputFile  待分块文件
         * @param chunkSize  文件分块大小
         * @param comparator 比较器
         * @return 分块后的文件列表
         * @throws IOException
         */
        private static List<File> chunk(String inputFile, int chunkSize, Comparator<String> comparator) throws IOException {
            List<File> chunkFiles = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String[] buffer = new String[chunkSize];
            int count = 0;
            while (true) {
                int index = 0;
                while (index < chunkSize && buffer[index] == null) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    buffer[index++] = line;
                }
                if (index == 0) {
                    break;
                }
                Arrays.sort(buffer, 0, index, comparator);
                File file = new File(FILE_PREFIX + count++);
                chunkFiles.add(file);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < index; i++) {
                    writer.write(buffer[i]);
                    writer.newLine();
                    buffer[i] = null;
                }
                writer.close();
            }
            reader.close();
            return chunkFiles;
        }
    }
