package org.example.shapes;

import com.google.gson.*;

import java.awt.*;
import java.lang.reflect.Type;

public class ColorAdapter implements JsonDeserializer<Color>, JsonSerializer<Color> {
    @Override
    public Color deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        if (json == null || json.getAsJsonArray() == null) {
            return null;
        }
        final JsonArray jsonArray = json.getAsJsonArray();
        return new Color(
                jsonArray.get(0).getAsInt(),
                jsonArray.get(1).getAsInt(),
                jsonArray.get(2).getAsInt(),
                jsonArray.get(3).getAsInt());
    }

    @Override
    public JsonElement serialize(Color color, Type type, JsonSerializationContext jsonSerializationContext) {
        if (color == null) {
            throw new NullPointerException("color object to serialize is null");
        }
        final JsonArray jsonArray = new JsonArray();
        jsonArray.add(color.getRed());
        jsonArray.add(color.getGreen());
        jsonArray.add(color.getBlue());
        jsonArray.add(color.getAlpha());
        return jsonArray;
    }
}
