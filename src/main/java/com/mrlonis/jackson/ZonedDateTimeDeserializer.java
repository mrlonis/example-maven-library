package com.mrlonis.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.regex.Pattern;

public final class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
    private static final Pattern COMPACT_OFFSET = Pattern.compile("([+-][0-9]{2})([0-9]{2})(?=\\[|$)");
    private static final DateTimeFormatter[] TEXT_FORMATS = {
        DateTimeFormatter.ISO_DATE_TIME,
        DateTimeFormatter.ISO_LOCAL_DATE,
        DateTimeFormatter.RFC_1123_DATE_TIME.withResolverStyle(ResolverStyle.STRICT)
    };

    @Override
    public ZonedDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (parser.hasToken(JsonToken.VALUE_STRING)) {
            String text = parser.getText().trim();
            String isoText = COMPACT_OFFSET.matcher(text.replace(' ', 'T')).replaceFirst("$1:$2");
            for (DateTimeFormatter format : TEXT_FORMATS) {
                try {
                    TemporalAccessor parsed = format.parse(format == DateTimeFormatter.ISO_DATE_TIME ? isoText : text);
                    if (format == DateTimeFormatter.ISO_LOCAL_DATE) {
                        return LocalDate.from(parsed)
                                .atStartOfDay(context.getTimeZone().toZoneId());
                    }
                    LocalDateTime local = LocalDateTime.from(parsed);
                    ZoneOffset offset = parsed.query(TemporalQueries.offset());
                    if (offset == null) {
                        return local.atZone(context.getTimeZone().toZoneId());
                    }
                    // The offset distinguishes the two occurrences of a local time during a DST overlap.
                    return ZonedDateTime.ofInstant(local, offset, ZoneId.from(parsed));
                } catch (DateTimeParseException ignored) {
                    // Try the next representation.
                }
            }
        }
        // Use Jackson's epoch rules for both numeric tokens and numeric text from XML.
        return InstantDeserializer.ZONED_DATE_TIME.deserialize(parser, context);
    }
}
