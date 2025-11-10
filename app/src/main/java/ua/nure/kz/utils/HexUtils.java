package ua.nure.kz.utils;

public class HexUtils {
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static int getFourBits(char ch) {
        if(ch >= 'A' && ch <= 'F') {
            return ch - 'A' + 10;
        } else if(ch >= 'a' && ch <= 'f') {
            return ch - 'a' + 10;
        } else if(ch >= '0' && ch <= '9') {
            return ch - '0';
        }
        return 0;
    }

    public static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        char[] chars = hex.toCharArray();
        for(int i = 0; i < chars.length / 2; ++i) {
            bytes[i] = (byte)((getFourBits(chars[i * 2]) << 4) | getFourBits(chars[i * 2 + 1]));
        }

        return bytes;
    }
}
