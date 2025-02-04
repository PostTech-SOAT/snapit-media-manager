package com.snapit.framework.context;

public class ContextHolder {

    private static final ThreadLocal<String> EMAIL = ThreadLocal.withInitial(() -> "empty");

    public static void setEmail(String subject) {
        EMAIL.set(subject);
    }

    public static String getEmail() {
        return EMAIL.get();
    }

    public static void removeEmail() {
        EMAIL.remove();
    }
}