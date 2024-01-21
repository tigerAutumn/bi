package com.pinting.cfcatransfer.bigangwan.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * 
 * <pre>
 * Modify Information:
 * Author       Date        Description
 * ============ =========== ============================
 * zhangxing    2009-05-04  Create this file
 * 
 * </pre>
 * 
 */
public class CABase64 {
    private static final Base64Encoder encoder = new Base64Encoder();

    /**
     * encode the input data producing a base 64 encoded byte array.
     * 
     * @return a byte array containing the base 64 encoded data.
     */
    public static byte[] encode(byte[] data) {
        int len = (data.length + 2) / 3 * 4;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(len);

        try {
            encoder.encode(data, 0, data.length, outputStream);
        } catch (IOException e) {
            throw new RuntimeException("exception encoding base64 string: " + e);
        }

        return outputStream.toByteArray();
    }

    /**
     * Encode the byte data to base 64 writing it to the given output stream.
     * 
     * @return the number of bytes produced.
     */
    public static int encode(byte[] data, OutputStream out) throws IOException {
        return encoder.encode(data, 0, data.length, out);
    }

    /**
     * Encode the byte data to base 64 writing it to the given output stream.
     * 
     * @return the number of bytes produced.
     */
    public static int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
        return encoder.encode(data, off, length, out);
    }

    /**
     * decode the base 64 encoded input data. It is assumed the input data is
     * valid.
     * 
     * @return a byte array representing the decoded data.
     */
    public static byte[] decode(byte[] data) {
        int len = data.length / 4 * 3;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);

        try {
            encoder.decode(data, 0, data.length, bOut);
        } catch (IOException e) {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }

        return bOut.toByteArray();
    }

    /**
     * decode the base 64 encoded String data - whitespace will be ignored.
     * 
     * @return a byte array representing the decoded data.
     */
    public static byte[] decode(String data) {
        int len = data.length() / 4 * 3;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);

        try {
            encoder.decode(data, bOut);
        } catch (IOException e) {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }

        return bOut.toByteArray();
    }

    /**
     * decode the base 64 encoded String data writing it to the given output
     * stream, whitespace characters will be ignored.
     * 
     * @return the number of bytes produced.
     */
    public static int decode(String data, OutputStream out) throws IOException {
        return encoder.decode(data, out);
    }

    /**
     * encode data to base64String
     * 
     * @param data
     * @param charSet
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode(String data, String charSet) throws UnsupportedEncodingException {
        if (data == null || data.length() == 0) {
            return "";
        } else {
            return new String(encode(data.getBytes(charSet)), charSet);
        }
    }

    public static String encode(byte[] data, String charSet) throws UnsupportedEncodingException {
        return new String(encode(data), charSet);
    }

    /**
     * decode data to base64String
     * 
     * @param data
     * @param charSet
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decode(String data, String charSet) throws UnsupportedEncodingException {
        if (data == null || data.length() == 0) {
            return "";
        } else {
            return new String(decode(data.getBytes(charSet)), charSet);
        }
    }
}

class Base64Encoder {
    protected final byte[] encodingTable = { (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I',
            (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U',
            (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f', (byte) 'g',
            (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's',
            (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z', (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
            (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) '+', (byte) '/' };

    protected byte padding = (byte) '=';

    /*
     * set up the decoding table.
     */
    protected final byte[] decodingTable = new byte[128];

    protected void initialiseDecodingTable() {
        for (int i = 0; i < encodingTable.length; i++) {
            decodingTable[encodingTable[i]] = (byte) i;
        }
    }

    public Base64Encoder() {
        initialiseDecodingTable();
    }

    /**
     * encode the input data producing a base 64 output stream.
     * 
     * @return the number of bytes produced.
     */
    public int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
        int modulus = length % 3;
        int dataLength = (length - modulus);
        int a1, a2, a3;

        for (int i = off; i < off + dataLength; i += 3) {
            a1 = data[i] & 0xff;
            a2 = data[i + 1] & 0xff;
            a3 = data[i + 2] & 0xff;

            out.write(encodingTable[(a1 >>> 2) & 0x3f]);
            out.write(encodingTable[((a1 << 4) | (a2 >>> 4)) & 0x3f]);
            out.write(encodingTable[((a2 << 2) | (a3 >>> 6)) & 0x3f]);
            out.write(encodingTable[a3 & 0x3f]);
        }

        /*
         * process the tail end.
         */
        int b1, b2, b3;
        int d1, d2;

        switch (modulus) {
            case 0: /* nothing left to do */
                break;
            case 1:
                d1 = data[off + dataLength] & 0xff;
                b1 = (d1 >>> 2) & 0x3f;
                b2 = (d1 << 4) & 0x3f;

                out.write(encodingTable[b1]);
                out.write(encodingTable[b2]);
                out.write(padding);
                out.write(padding);
                break;
            case 2:
                d1 = data[off + dataLength] & 0xff;
                d2 = data[off + dataLength + 1] & 0xff;

                b1 = (d1 >>> 2) & 0x3f;
                b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
                b3 = (d2 << 2) & 0x3f;

                out.write(encodingTable[b1]);
                out.write(encodingTable[b2]);
                out.write(encodingTable[b3]);
                out.write(padding);
                break;
        }

        return (dataLength / 3) * 4 + ((modulus == 0) ? 0 : 4);
    }

    private boolean ignore(char c) {
        return (c == '\n' || c == '\r' || c == '\t' || c == ' ');
    }

    /**
     * decode the base 64 encoded byte data writing it to the given output
     * stream, whitespace characters will be ignored.
     * 
     * @return the number of bytes produced.
     */
    public int decode(byte[] data, int off, int length, OutputStream out) throws IOException {
        byte b1, b2, b3, b4;
        int outLen = 0;

        int end = off + length;

        while (end > off) {
            if (!ignore((char) data[end - 1])) {
                break;
            }

            end--;
        }

        int i = off;
        int finish = end - 4;

        i = nextI(data, i, finish);

        while (i < finish) {
            b1 = decodingTable[data[i++]];

            i = nextI(data, i, finish);

            b2 = decodingTable[data[i++]];

            i = nextI(data, i, finish);

            b3 = decodingTable[data[i++]];

            i = nextI(data, i, finish);

            b4 = decodingTable[data[i++]];

            out.write((b1 << 2) | (b2 >> 4));
            out.write((b2 << 4) | (b3 >> 2));
            out.write((b3 << 6) | b4);

            outLen += 3;

            i = nextI(data, i, finish);
        }

        outLen += decodeLastBlock(out, (char) data[end - 4], (char) data[end - 3], (char) data[end - 2], (char) data[end - 1]);

        return outLen;
    }

    private int nextI(byte[] data, int i, int finish) {
        while ((i < finish) && ignore((char) data[i])) {
            i++;
        }
        return i;
    }

    /**
     * decode the base 64 encoded String data writing it to the given output
     * stream, whitespace characters will be ignored.
     * 
     * @return the number of bytes produced.
     */
    public int decode(String data, OutputStream out) throws IOException {
        byte b1, b2, b3, b4;
        int length = 0;

        int end = data.length();

        while (end > 0) {
            if (!ignore(data.charAt(end - 1))) {
                break;
            }

            end--;
        }

        int i = 0;
        int finish = end - 4;

        i = nextI(data, i, finish);

        while (i < finish) {
            b1 = decodingTable[data.charAt(i++)];

            i = nextI(data, i, finish);

            b2 = decodingTable[data.charAt(i++)];

            i = nextI(data, i, finish);

            b3 = decodingTable[data.charAt(i++)];

            i = nextI(data, i, finish);

            b4 = decodingTable[data.charAt(i++)];

            out.write((b1 << 2) | (b2 >> 4));
            out.write((b2 << 4) | (b3 >> 2));
            out.write((b3 << 6) | b4);

            length += 3;

            i = nextI(data, i, finish);
        }

        length += decodeLastBlock(out, data.charAt(end - 4), data.charAt(end - 3), data.charAt(end - 2), data.charAt(end - 1));

        return length;
    }

    private int decodeLastBlock(OutputStream out, char c1, char c2, char c3, char c4) throws IOException {
        byte b1, b2, b3, b4;

        if (c3 == padding) {
            b1 = decodingTable[c1];
            b2 = decodingTable[c2];

            out.write((b1 << 2) | (b2 >> 4));

            return 1;
        } else if (c4 == padding) {
            b1 = decodingTable[c1];
            b2 = decodingTable[c2];
            b3 = decodingTable[c3];

            out.write((b1 << 2) | (b2 >> 4));
            out.write((b2 << 4) | (b3 >> 2));

            return 2;
        } else {
            b1 = decodingTable[c1];
            b2 = decodingTable[c2];
            b3 = decodingTable[c3];
            b4 = decodingTable[c4];

            out.write((b1 << 2) | (b2 >> 4));
            out.write((b2 << 4) | (b3 >> 2));
            out.write((b3 << 6) | b4);

            return 3;
        }
    }

    private int nextI(String data, int i, int finish) {
        while ((i < finish) && ignore(data.charAt(i))) {
            i++;
        }
        return i;
    }
}
