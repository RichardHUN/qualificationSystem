package hu.unideb.inf.qualificationSystem.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Duration;

/**
 * Deserializer for Duration from M:SS.mmm format.
 */
public final class TimeFormatDeserializer
        extends JsonDeserializer<Duration> {

    /** Milliseconds per minute. */
    private static final int MILLIS_PER_MINUTE = 60000;
    /** Milliseconds per second. */
    private static final int MILLIS_PER_SECOND = 1000;
    /** Expected number of parts after split. */
    private static final int EXPECTED_PARTS = 2;

    /**
     * Deserializes time from M:SS.mmm format.
     * @param p the JSON parser
     * @param ctxt the deserialization context
     * @return the Duration object
     * @throws IOException if parsing fails
     */
    @Override
    public Duration deserialize(final JsonParser p,
            final DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        if (value == null || value.isEmpty()) {
            return null;
        }

        String[] parts = value.split(":");
        if (parts.length != EXPECTED_PARTS) {
            throw new IllegalArgumentException(
                    "Invalid time format. "
                    + "Expected format: M:SS.mmm");
        }

        long minutes = Long.parseLong(parts[0]);

        String[] secondsParts = parts[1].split("\\.");
        if (secondsParts.length != EXPECTED_PARTS) {
            throw new IllegalArgumentException(
                    "Invalid time format. "
                    + "Expected format: M:SS.mmm");
        }

        long seconds = Long.parseLong(secondsParts[0]);
        long millis = Long.parseLong(secondsParts[1]);

        long totalMillis = (minutes * MILLIS_PER_MINUTE)
                + (seconds * MILLIS_PER_SECOND) + millis;
        return Duration.ofMillis(totalMillis);
    }
}



