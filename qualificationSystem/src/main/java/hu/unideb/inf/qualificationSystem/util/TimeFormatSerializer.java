package hu.unideb.inf.qualificationSystem.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;

/**
 * Serializer for Duration to M:SS.mmm format.
 */
public final class TimeFormatSerializer
        extends JsonSerializer<Duration> {

    /** Milliseconds per minute. */
    private static final int MILLIS_PER_MINUTE = 60000;
    /** Milliseconds per second. */
    private static final int MILLIS_PER_SECOND = 1000;

    /**
     * Serializes Duration to M:SS.mmm format.
     * @param duration the duration to serialize
     * @param gen the JSON generator
     * @param serializers the serializer provider
     * @throws IOException if writing fails
     */
    @Override
    public void serialize(final Duration duration,
            final JsonGenerator gen,
            final SerializerProvider serializers) throws IOException {
        if (duration == null) {
            gen.writeNull();
            return;
        }

        long totalMillis = duration.toMillis();
        long minutes = totalMillis / MILLIS_PER_MINUTE;
        long seconds = (totalMillis % MILLIS_PER_MINUTE)
                / MILLIS_PER_SECOND;
        long millis = totalMillis % MILLIS_PER_SECOND;

        String formatted = String.format("%d:%02d.%03d",
                minutes, seconds, millis);
        gen.writeString(formatted);
    }
}

