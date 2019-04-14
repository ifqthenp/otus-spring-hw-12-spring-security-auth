appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%date [%thread] [%-5level] %logger{40} - %message%n"
    }
}

root(OFF, ["STDOUT"])
