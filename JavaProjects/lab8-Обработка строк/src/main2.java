import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main2 {
    public static void main(String[] args) {
        System.out.println("Проверка корректности email-адресов...");

        String[] emails = {
                "valid.user@domain.com",
                "invalid!user@domain.net",
                "user@1domain.org",
                "test@sub.domain.ru",
                "wrong@domain.c",
                "longzone@domain.office",
                "standard@company.io",
                "bad#name@server.com",
                "user@domain.123",
                "correct.email@mail.ru"
        };

        String regex = "^(?![^@]*[!#$%^&*()=+|\\\\].*@)(?![^@]*@\\d)[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        StringBuilder report = new StringBuilder();

        for (int i = 0; i < emails.length; i++) {
            String email = emails[i];
            Matcher matcher = pattern.matcher(email);
            boolean isValid = matcher.matches();

            report.append("Индекс: ").append(i)
                    .append(" | Строка: ").append(email)
                    .append(" | Статус: ").append(isValid ? "валидна" : "невалидна");

            if (!isValid) {
                report.append(" | Позиция ошибки: ").append(getViolationPosition(email));
            }
            report.append("\n");
        }

        System.out.println(report.toString());
    }

    private static int getViolationPosition(String email) {
        String forbidden = "!#$%^&*()=+|\\";
        int atIndex = email.indexOf('@');

        if (atIndex == -1) return 0;

        for (int i = 0; i < atIndex; i++) {
            if (forbidden.indexOf(email.charAt(i)) != -1) return i;
        }

        if (atIndex + 1 < email.length() && Character.isDigit(email.charAt(atIndex + 1))) {
            return atIndex + 1;
        }

        int lastDotIndex = email.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex < atIndex) return email.length();

        String zone = email.substring(lastDotIndex + 1);
        if (zone.length() < 2 || zone.length() > 4 || !zone.matches("[a-zA-Z]+")) {
            return lastDotIndex + 1;
        }

        return 0;
    }
}