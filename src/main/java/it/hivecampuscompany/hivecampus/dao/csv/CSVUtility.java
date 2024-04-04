package it.hivecampuscompany.hivecampus.dao.csv;

import java.util.Base64;

public class CSVUtility {
    private CSVUtility(){}
    public static String encodeBytesToBase64(byte[] content) {
        // Codifica l'array di byte in una stringa Base64
        return Base64.getEncoder().encodeToString(content);
    }

   public static byte[] decodeBase64ToBytes(String content) {
        // Decodifica la stringa Base64 in un array di byte
        return Base64.getDecoder().decode(content);
    }
}
