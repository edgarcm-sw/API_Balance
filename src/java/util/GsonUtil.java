package util;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Fábrica de Gson configurada con adaptadores para java.sql.Timestamp
 * (ISO8601) y java.sql.Time (HH:mm:ss).
 *
 * Uso: GsonUtil.createGson() en lugar de new Gson()
 */
public class GsonUtil {

    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Timestamp.class, new TimestampAdapter())
                .registerTypeAdapter(Time.class, new TimeAdapter())
                .create();
    }

    /**
     * Serializa/deserializa java.sql.Timestamp en formato ISO8601:
     * "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    private static class TimestampAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {

        @Override
        public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
            return new JsonPrimitive(fmt.format(src));
        }

        @Override
        public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonNull()) return null;
            String dateStr = json.getAsString();
            try {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
                return new Timestamp(fmt.parse(dateStr).getTime());
            } catch (Exception e1) {
                try {
                    // Fallback: "yyyy-MM-dd HH:mm:ss"
                    return Timestamp.valueOf(dateStr.replace("T", " ").replace("Z", ""));
                } catch (Exception e2) {
                    throw new JsonParseException("Cannot parse timestamp: " + dateStr, e2);
                }
            }
        }
    }

    /**
     * Serializa/deserializa java.sql.Time en formato "HH:mm:ss" (24h).
     */
    private static class TimeAdapter implements JsonSerializer<Time>, JsonDeserializer<Time> {

        @Override
        public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString()); // "HH:mm:ss"
        }

        @Override
        public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonNull()) return null;
            try {
                return Time.valueOf(json.getAsString()); // parsea "HH:mm:ss"
            } catch (Exception e) {
                throw new JsonParseException("Cannot parse time: " + json.getAsString(), e);
            }
        }
    }
}
