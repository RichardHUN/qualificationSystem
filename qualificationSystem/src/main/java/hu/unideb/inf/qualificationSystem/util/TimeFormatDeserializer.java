package hu.unideb.inf.qualificationSystem.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Duration;

public class TimeFormatDeserializer extends JsonDeserializer<Duration> {

    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        if (value == null || value.isEmpty()) {
            return null;
        }

        String[] parts = value.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid time format. Expected format: M:SS.mmm");
        }

        long minutes = Long.parseLong(parts[0]);

        String[] secondsParts = parts[1].split("\\.");
        if (secondsParts.length != 2) {
            throw new IllegalArgumentException("Invalid time format. Expected format: M:SS.mmm");
        }

        long seconds = Long.parseLong(secondsParts[0]);
        long millis = Long.parseLong(secondsParts[1]);

        long totalMillis = (minutes * 60000) + (seconds * 1000) + millis;
        return Duration.ofMillis(totalMillis);
    }
}

