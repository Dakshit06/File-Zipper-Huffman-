Huffman Compression Program
This Java program compresses and decompresses files using the Huffman coding algorithm.

1. Download the Java Program
Download: Get the Huffman Compression program files from here.

Extract: Unzip the downloaded file to a convenient location on your computer.

2. Compile the Java Program
Navigate to the Directory: Open a terminal or command prompt and go to the extracted folder's location.

Compile the Program: Run the following command to prepare the program for use:

bash
Copy code
javac HuffmanCompression.java
3. Compress a File
Compress a File: Shrink a file to save space.
bash
Copy code
java HuffmanCompression compress file1.txt
Replace file1.txt with the name of the file you want to compress.
4. Decompress a File
Decompress a File: Restore a compressed file to its original state.
bash
Copy code
java HuffmanCompression decompress compressed_file1.txt
Replace compressed_file1.txt with the name of the compressed file you want to decompress.
5. Verify Compression and Decompression
Verify: Check if compression and decompression worked.
Compare the original file with the decompressed file to ensure correctness.
Use any file comparison tool or check their contents manually.
Example
Usage Example: Demonstration of how to use the Huffman Compression program.
bash
Copy code
java HuffmanCompression compress file1.txt
java HuffmanCompression decompress compressed_file1.txt
Notes
File Extensions: Ensure files have the correct extensions (.txt).
Input File Name: The provided file name file1.txt is used as an example. Replace it with your actual file name.
Output File Name: The output file names are generated automatically by the program.
File Accessibility: Ensure that input files are accessible and in the same directory as the program.
