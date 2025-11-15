package hu.unideb.inf.qualificationSystem.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;

public class TimeFormatSerializer extends JsonSerializer<Duration> {

    @Override
    public void serialize(Duration duration, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (duration == null) {
            gen.writeNull();
            return;
        }

        long totalMillis = duration.toMillis();
        long minutes = totalMillis / 60000;
        long seconds = (totalMillis % 60000) / 1000;
        long millis = totalMillis % 1000;

        String formatted = String.format("%d:%02d.%03d", minutes, seconds, millis);
        gen.writeString(formatted);
    }
}

