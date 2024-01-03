package pers.helen.kafkademo.sender.util;

public class PhoneNumberValidator {
    private static final String PHONE_NUMBER_PATTERN = "^1[3-9]\\d{9}$";

    public static boolean isValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }
}
