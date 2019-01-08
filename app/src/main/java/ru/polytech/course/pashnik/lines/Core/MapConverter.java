package ru.polytech.course.pashnik.lines.Core;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapConverter implements JsonSerializer<Map<Cell, ColorType>>,
        JsonDeserializer<Map<Cell, ColorType>> {

    private Gson gson = new Gson();

    @Override
    public JsonElement serialize(Map<Cell, ColorType> src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<Cell, ColorType> entry : src.entrySet()) {
            Cell cell = entry.getKey();
            String jsonStr = gson.toJson(cell);
            jsonObject.addProperty(jsonStr, entry.getValue().toString());
        }
        return jsonObject;
    }

    @Override
    public Map<Cell, ColorType> deserialize(JsonElement json, Type typeOfT,
                                            JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        final Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();
        final Map<Cell, ColorType> map = new HashMap<>();
        final Gson gson = new Gson();
        for (Map.Entry<String, JsonElement> entry : set) {
            String cellStr = entry.getKey();
            Cell cell = gson.fromJson(cellStr, Cell.class);
            ColorType colorType = gson.fromJson(entry.getValue(), ColorType.class);
            map.put(cell, colorType);
        }
        return map;
    }
}
