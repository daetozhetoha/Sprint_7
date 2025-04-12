package edu.praktikum.sprint7.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static String randomString() {
        return randomString(10);
    }

    public static String randomString(int length) {
        Random random = new Random();
        int leftLimit = 97;
        int rightLimit = 122;
        StringBuilder buffer = new StringBuilder(length);

        for(int i = 0; i < length; ++i) {
            int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (float)(rightLimit - leftLimit + 1));
            buffer.append(Character.toChars(randomLimitedInt));
        }

        return buffer.toString();
    }

    public static String randomMetroStation() {
        int randomStationNumber = ThreadLocalRandom.current().nextInt(1, 226);
        return String.valueOf(randomStationNumber);
    }

    public static int randomRentTime() {
        return ThreadLocalRandom.current().nextInt(1, 8);
    }

    public static String getRandomPhone() {
        String s = "123456789";
        StringBuffer phoneNumber = new StringBuffer();

        for (int i = 0; i < 13; i++) {
            phoneNumber.append(s.charAt(new Random().nextInt(s.length())));
        }
        return phoneNumber.toString();
    }

    public static String getFutureDate(int daysToAdd) {
        LocalDate futureDate = LocalDate.now().plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Задайте нужный формат
        return futureDate.format(formatter);
    }

}
