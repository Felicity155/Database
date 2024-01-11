package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Record {
    int A;
    String B;
    int id;
    Record(int id , int A, String B) {
        this.B = B;
        this.A = A;
        this.id = id;
    }
}

public class hello {

    private static boolean readNextRecord(BufferedReader reader, Record record) throws IOException {
        String line = reader.readLine();
        if (line == null) {
            return false;
        }
        String[] fields = line.split("\t");
        record.A = Integer.parseInt(fields[0]);
        record.B = fields[1];
        return true;
    }

    private static void writeTempFile(List<Record> buffer, int count, int fileIndex) throws IOException {
        String filename = "temp" + fileIndex + ".txt";
        FileWriter writer = new FileWriter(filename);
        for (int i = 0; i < count; i++) {
            Record record = buffer.get(i);
            writer.write(record.A + "\t" + record.B + "\n");
        }
        writer.close();
        System.out.println("写入文件：" + filename);
    }

    private static void multiwayMergeSort(String filename, int memSize, int colIndex, int chunkSize) throws IOException {
        System.out.println("开始多路归并排序");
        List<Record> buffer = new ArrayList<>();
        int totalFileSize = 0;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while (true) {
            int count = 0;
            while (count < chunkSize) {
                Record record = new Record(0,0, " ");
                if (!readNextRecord(reader, record)) {
                    break;
                }
                buffer.add(record);
                count++;
            }
            if (buffer.isEmpty()) {
                break;
            }
            // 对内存缓冲区中的记录按照属性 A 进行排序
            buffer.sort((a, b) -> Integer.compare(a.A, b.A));
            writeTempFile(buffer, buffer.size(), totalFileSize++);
            buffer.clear();
        }
        reader.close();

        // 通过优先队列进行 k 路归并
        int k = totalFileSize;
        List<BufferedReader> inputs = new ArrayList<>();
        PriorityQueue<Record> winnerTree = new PriorityQueue<>((a, b) -> Integer.compare(a.A, b.A));
        for (int i = 0; i < k; i++) {
            String filenameIndex = "temp" + i + ".txt";
            BufferedReader inputReader = new BufferedReader(new FileReader(filenameIndex));
            inputs.add(inputReader);
            Record record = new Record(i,0, " ");
            if (readNextRecord(inputReader, record)) {
                winnerTree.offer(record);
            }
        }
        FileWriter writer = new FileWriter("output.txt");
        while (!winnerTree.isEmpty()) {
            Record record = winnerTree.poll();
            writer.write(record.A + "\t" + record.B + "\n");
            int fileIndex = record.id;
            if (readNextRecord(inputs.get(fileIndex), record)) {
                winnerTree.offer(record);
            } else {
                inputs.get(fileIndex).close();
            }
        }
        writer.close();

        // 删除临时文件
        for (int i = 0; i < k; i++) {
            String filenameIndex = "temp" + i + ".txt";
            try {
                java.nio.file.Files.delete(java.nio.file.Paths.get(filenameIndex));
            } catch (IOException e) {
                System.err.println("Error deleting temp file " + filenameIndex);
                e.printStackTrace();
            }
        }

        System.out.println("多路归并排序完成");
    }

    public static void main(String[] args) throws IOException {
        String filename = "E:\\Databean\\lab2\\lab2_code\\data.txt";
        int memSize = 1024*16;
        int colIndex = 2;
        int chunkSize = memSize / (Character.BYTES*10 +  Integer.BYTES);
        multiwayMergeSort(filename, memSize, colIndex, chunkSize);

    }
}
