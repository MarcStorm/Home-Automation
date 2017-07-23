package COURSE02148.homeautomation.frontend;

import java.io.*;

public class Pipe {
    /***
     * Writes all lines read from the reader.
     * @param reader The source reader
     * @param writer The destination writer
     * @throws IOException
     */
    public static void pipe(Reader reader, Writer writer) throws IOException {
        pipe(reader, writer, 4092);
    }

    /***
     *  Writes all lines read from the reader.
     * @param reader the source reader
     * @param writer the destination writer
     * @param buffersize size of the buffer to use
     * @throws IOException
     */
    public static void pipe(Reader reader, Writer writer, int buffersize) throws IOException {
        char[] buffer = new char[buffersize];
        while (reader.read(buffer) != -1) {
            writer.write(buffer);
        }
    }

    /***
     * Writes all lines read from the reader.
     * @param is The input stream
     * @param os The output stream
     * @throws IOException
     */
    public static void pipe(InputStream is, OutputStream os) throws IOException {
        pipe(is, os, 4092);
    }

    /***
     *  Writes all lines read from the reader.
     * @param is The input stream
     * @param os The output stream
     * @param buffersize size of the buffer to use
     * @throws IOException
     */
    public static void pipe(InputStream is, OutputStream os, int buffersize) throws IOException {
        byte[] buffer = new byte[buffersize];
        while (is.read(buffer) != -1) {
            os.write(buffer);
        }
    }
}