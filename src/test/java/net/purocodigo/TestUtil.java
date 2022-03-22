package net.purocodigo;

import net.purocodigo.encuestabackend.models.request.UserRegisterRequestModel;
import java.util.Random;

public class TestUtil {

    public static UserRegisterRequestModel createValidUser() {
        UserRegisterRequestModel user = new UserRegisterRequestModel();
        user.setEmail(generateRandomString(16) + "@gmail.com");
        user.setName(generateRandomString(8));
        user.setPassword(generateRandomString(8));
        return user;
    }

    public static String generateRandomString(int len) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();

    }

}
