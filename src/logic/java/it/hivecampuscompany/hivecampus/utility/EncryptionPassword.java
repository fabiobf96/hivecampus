package it.hivecampuscompany.hivecampus.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionPassword {
    private EncryptionPassword(){

    }
    /**
     * Hashes a password using MD5 algorithm.
     * This method takes a plaintext password as input and returns its hash
     * in hexadecimal format. The hashing is done using the MD5 algorithm. If
     * the algorithm is not supported on the platform, the method will catch
     * a NoSuchAlgorithmException, print the stack trace, and return null.

     * @param password The plaintext password to hash.
     * @return The hashed password in hexadecimal format, or null if MD5 algorithm is not supported.
     */
    public static String hashPasswordMD5(String password) throws NoSuchAlgorithmException{

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashedBytes = md.digest(password.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}
