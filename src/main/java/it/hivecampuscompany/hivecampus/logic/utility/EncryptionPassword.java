package it.hivecampuscompany.hivecampus.logic.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncryptionPassword {
    private static final Logger LOGGER = Logger.getLogger(EncryptionPassword.class.getName());
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
    public static String hashPasswordMD5(String password) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(), e);
            System.exit(2);
        }
        byte[] hashedBytes = md.digest(password.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}
