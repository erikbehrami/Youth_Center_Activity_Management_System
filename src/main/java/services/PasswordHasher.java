package services;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Scanner;

public class PasswordHasher {


    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";


    public static String hashPassword(String password, String saltString)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = PasswordHasher.decodeSalt(saltString);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }


    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 128-bit salt
        random.nextBytes(salt);
        return salt;
    }

    public static String encodeSalt(byte[] salt) {
        return Base64.getEncoder().encodeToString(salt);
    }

    public static byte[] decodeSalt(String saltStr) {
        return Base64.getDecoder().decode(saltStr);
    }


    //   CODE PER GJENERIM TE PASS HASHED DHE SALT PER DUMMY INPUTS
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        try {
//
//            System.out.print("Enter your password: ");
//            String password = scanner.nextLine();
//
//
//            System.out.print("Do you want to generate a new salt? (yes/no): ");
//            String choice = scanner.nextLine().trim().toLowerCase();
//
//            String saltBase64;
//            if (choice.equals("yes")) {
//                byte[] salt = PasswordHasher.generateSalt();
//                saltBase64 = PasswordHasher.encodeSalt(salt);
//                System.out.println("Generated salt (Base64): " + saltBase64);
//            } else {
//                System.out.print("Enter your existing salt (Base64): ");
//                saltBase64 = scanner.nextLine().trim();
//            }
//
//
//            String hashedPassword = PasswordHasher.hashPassword(password, saltBase64);
//
//
//            System.out.println("Salt used (Base64): " + saltBase64);
//            System.out.println("Hashed password (Base64): " + hashedPassword);
//
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            System.out.println("Error while hashing password: " + e.getMessage());
//        }
//
//        scanner.close();
//    }


}



