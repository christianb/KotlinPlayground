import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

    }

    boolean isOpen() {
        LocalDateTime now = LocalDateTime.now();

        // now.hour returns the hour-of-day, from 0 to 23
        // so every hour that is greater equals 10 means open
        boolean isOpenByTime = now.getHour() >= 10;

        DayOfWeek dayOfWeek = now.getDayOfWeek();
        boolean isOpenByWeekDay = dayOfWeek != DayOfWeek.MONDAY && dayOfWeek != DayOfWeek.TUESDAY;

        return isOpenByTime && isOpenByWeekDay;
    }
}
