package pl.com.stoprussia.main.navBar2;

public enum StringType {
    TEXT("text"),
    WEBSITE("website"),
    EMAIL("email"),
    PHONE("phone"),
    CONTACT("contact"),
    SMS("sms"),
    EVENT("event"),
    GPS("gps"),
    WIFI("wifi"),
    ENCRYPTED("encrypted");

    private final String name;

    StringType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

