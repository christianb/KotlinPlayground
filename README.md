Java 8 introduces the java.time package which is finally a worthwhile Java standard solution! Read this article, Java SE 8 Date and Time, for a good amount of information on java.time outside of hours and minutes.

Take a look at `LocalDateTime` class.

Hours and minutes:

```java
boolean isOpen() {
        LocalDateTime now = java.time.LocalDateTime.now()

        // now.hour returns the hour-of-day, from 0 to 23
        // so every hour that is greater equals 10 means open
        return now.hour >= 10
}
```
