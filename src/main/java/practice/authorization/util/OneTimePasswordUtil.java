package practice.authorization.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class OneTimePasswordUtil {
    public static String generateCode() {

        String otp;

        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            otp = String.valueOf(secureRandom.nextInt(9000) + 1000);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No algorithm exists for generating otp.");
        }

        return otp;
    }
}
