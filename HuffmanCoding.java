import java.io.*;
import java.util.PriorityQueue;
import java.util.HashMap;

class HuffmanNode implements Comparable<HuffmanNode> {
    int data;
    char c;
    HuffmanNode left, right;

    public HuffmanNode(char c, int data) {
        this.c = c;
        this.data = data;
        this.left = this.right = null;
    }

    public int compareTo(HuffmanNode node) {
        return this.data - node.data;
    }
}

class HuffmanCompression {
    private HashMap<Character, String> huffmanCodes = new HashMap<>();

    public void compressFile(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            int[] frequency = new int[256];

            int c;
            while ((c = fileInputStream.read()) != -1) {
                frequency[c]++;
            }

            PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
            for (int i = 0; i < 256; i++) {
                if (frequency[i] > 0) {
                    priorityQueue.offer(new HuffmanNode((char) i, frequency[i]));
                }
            }

            while (priorityQueue.size() > 1) {
                HuffmanNode left = priorityQueue.poll();
                HuffmanNode right = priorityQueue.poll();
                HuffmanNode newNode = new HuffmanNode('\0', left.data + right.data);
                newNode.left = left;
                newNode.right = right;
                priorityQueue.offer(newNode);
            }

            generateCodes(priorityQueue.peek(), "");

            FileOutputStream fileOutputStream = new FileOutputStream("compressed_" + filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(huffmanCodes);
            fileInputStream.close();

            fileInputStream = new FileInputStream(filePath);
            StringBuilder stringBuilder = new StringBuilder();
            while ((c = fileInputStream.read()) != -1) {
                stringBuilder.append(huffmanCodes.get((char) c));
            }

            String encoded = stringBuilder.toString();
            byte[] encodedBytes = new byte[encoded.length() / 8 + 1];
            int idx = 0;
            for (int i = 0; i < encoded.length(); i += 8) {
                encodedBytes[idx++] = (byte) Integer.parseInt(encoded.substring(i, Math.min(i + 8, encoded.length())), 2);
            }

            objectOutputStream.writeObject(encodedBytes);
            objectOutputStream.close();
            fileOutputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decompressFile(String compressedFilePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(compressedFilePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            HashMap<Character, String> huffmanCodes = (HashMap<Character, String>) objectInputStream.readObject();
            byte[] encodedBytes = (byte[]) objectInputStream.readObject();
            fileInputStream.close();

            FileOutputStream fileOutputStream = new FileOutputStream("decompressed_" + compressedFilePath);
            StringBuilder encodedString = new StringBuilder();
            for (byte b : encodedBytes) {
                encodedString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }

            HashMap<String, Character> reverseMapping = new HashMap<>();
            for (HashMap.Entry<Character, String> entry : huffmanCodes.entrySet()) {
                reverseMapping.put(entry.getValue(), entry.getKey());
            }

            int idx = 0;
            while (idx < encodedString.length()) {
                int end = idx + 1;
                while (!reverseMapping.containsKey(encodedString.substring(idx, end)) && end <= encodedString.length()) {
                    end++;
                }
                if (end <= encodedString.length()) {
                    char c = reverseMapping.get(encodedString.substring(idx, end));
                    fileOutputStream.write(c);
                    idx = end;
                }
            }

            fileOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void generateCodes(HuffmanNode root, String code) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.c, code);
        }
        generateCodes(root.left, code + "0");
        generateCodes(root.right, code + "1");
    }
    
    
    public static void main(String[] args) {
        HuffmanCompression huffman = new HuffmanCompression();
        String filePath = "file1.txt";
        
        // Compress file
        huffman.compressFile(filePath);
        System.out.println("File compressed successfully.");
        
        // Decompress file
        try{

            huffman.decompressFile("compressed_" + filePath);
        }
        catch(Exception e)
        {
            System.out.println("File decompressed successfully.");
        }
    }
}


