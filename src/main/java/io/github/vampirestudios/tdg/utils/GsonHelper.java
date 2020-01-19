package io.github.vampirestudios.tdg.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;

public class GsonHelper {
    private static final Gson GSON;
    
    public static boolean isStringValue(JsonObject jsonObject, String string) {
        return isValidPrimitive(jsonObject, string) && jsonObject.getAsJsonPrimitive(string).isString();
    }
    
    public static boolean isStringValue(JsonElement jsonElement) {
        return jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString();
    }
    
    public static boolean isNumberValue(JsonElement jsonElement) {
        return jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber();
    }
    
    public static boolean isBooleanValue(JsonObject jsonObject, String string) {
        return isValidPrimitive(jsonObject, string) && jsonObject.getAsJsonPrimitive(string).isBoolean();
    }
    
    public static boolean isArrayNode(JsonObject jsonObject, String string) {
        return isValidNode(jsonObject, string) && jsonObject.get(string).isJsonArray();
    }
    
    public static boolean isValidPrimitive(JsonObject jsonObject, String string) {
        return isValidNode(jsonObject, string) && jsonObject.get(string).isJsonPrimitive();
    }
    
    public static boolean isValidNode(JsonObject jsonObject, String string) {
        return jsonObject != null && jsonObject.get(string) != null;
    }
    
    public static String convertToString(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a string, was " + getType(jsonElement));
    }
    
    public static String getAsString(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return convertToString(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a string");
    }
    
    public static String getAsString(JsonObject jsonObject, String string2, String string3) {
        if (jsonObject.has(string2)) {
            return convertToString(jsonObject.get(string2), string2);
        }
        return string3;
    }

    public static boolean convertToBoolean(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsBoolean();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a Boolean, was " + getType(jsonElement));
    }
    
    public static boolean getAsBoolean(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return convertToBoolean(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a Boolean");
    }
    
    public static boolean getAsBoolean(JsonObject jsonObject, String string, boolean boolean3) {
        if (jsonObject.has(string)) {
            return convertToBoolean(jsonObject.get(string), string);
        }
        return boolean3;
    }
    
    public static float convertToFloat(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsFloat();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a Float, was " + getType(jsonElement));
    }
    
    public static float getAsFloat(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return convertToFloat(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a Float");
    }
    
    public static float getAsFloat(JsonObject jsonObject, String string, float float3) {
        if (jsonObject.has(string)) {
            return convertToFloat(jsonObject.get(string), string);
        }
        return float3;
    }
    
    public static long convertToLong(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsLong();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a Long, was " + getType(jsonElement));
    }
    
    public static long getAsLong(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return convertToLong(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a Long");
    }
    
    public static long getAsLong(JsonObject jsonObject, String string, long long3) {
        if (jsonObject.has(string)) {
            return convertToLong(jsonObject.get(string), string);
        }
        return long3;
    }
    
    public static int convertToInt(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsInt();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a Int, was " + getType(jsonElement));
    }
    
    public static int getAsInt(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return convertToInt(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a Int");
    }
    
    public static int getAsInt(JsonObject jsonObject, String string, int integer) {
        if (jsonObject.has(string)) {
            return convertToInt(jsonObject.get(string), string);
        }
        return integer;
    }
    
    public static byte convertToByte(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsByte();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a Byte, was " + getType(jsonElement));
    }
    
    public static byte getAsByte(JsonObject jsonObject, String string, byte byte3) {
        if (jsonObject.has(string)) {
            return convertToByte(jsonObject.get(string), string);
        }
        return byte3;
    }
    
    public static JsonObject convertToJsonObject(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a JsonObject, was " + getType(jsonElement));
    }
    
    public static JsonObject getAsJsonObject(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return convertToJsonObject(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a JsonObject");
    }
    
    public static JsonObject getAsJsonObject(JsonObject jsonObject1, String string, JsonObject jsonObject3) {
        if (jsonObject1.has(string)) {
            return convertToJsonObject(jsonObject1.get(string), string);
        }
        return jsonObject3;
    }
    
    public static JsonArray convertToJsonArray(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a JsonArray, was " + getType(jsonElement));
    }
    
    public static JsonArray getAsJsonArray(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return convertToJsonArray(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a JsonArray");
    }

    public static JsonArray getAsJsonArray(JsonObject jsonObject, String string, JsonArray jsonArray) {
        if (jsonObject.has(string)) {
            return convertToJsonArray(jsonObject.get(string), string);
        }
        return jsonArray;
    }
    
    public static <T> T convertToObject(JsonElement jsonElement, String string, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> class4) {
        if (jsonElement != null) {
            return jsonDeserializationContext.deserialize(jsonElement, class4);
        }
        throw new JsonSyntaxException("Missing " + string);
    }
    
    public static <T> T getAsObject(JsonObject jsonObject, String string, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> class4) {
        if (jsonObject.has(string)) {
            return GsonHelper.<T>convertToObject(jsonObject.get(string), string, jsonDeserializationContext, class4);
        }
        throw new JsonSyntaxException("Missing " + string);
    }
    
    public static <T> T getAsObject(JsonObject jsonObject, String string, T object, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> class5) {
        if (jsonObject.has(string)) {
            return GsonHelper.<T>convertToObject(jsonObject.get(string), string, jsonDeserializationContext, class5);
        }
        return object;
    }
    
    public static String getType(JsonElement jsonElement) {
        String string2 = StringUtils.abbreviateMiddle(String.valueOf(jsonElement), "...", 10);
        if (jsonElement == null) {
            return "null (missing)";
        }
        if (jsonElement.isJsonNull()) {
            return "null (json)";
        }
        if (jsonElement.isJsonArray()) {
            return "an array (" + string2 + ")";
        }
        if (jsonElement.isJsonObject()) {
            return "an object (" + string2 + ")";
        }
        if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive3 = jsonElement.getAsJsonPrimitive();
            if (jsonPrimitive3.isNumber()) {
                return "a number (" + string2 + ")";
            }
            if (jsonPrimitive3.isBoolean()) {
                return "a boolean (" + string2 + ")";
            }
        }
        return string2;
    }

    public static <T> T fromJson(Gson gson, Reader reader, Class<T> class3, boolean boolean4) {
        try {
            JsonReader jsonReader5 = new JsonReader(reader);
            jsonReader5.setLenient(boolean4);
            return gson.getAdapter(class3).read(jsonReader5);
        }
        catch (IOException iOException5) {
            throw new JsonParseException(iOException5);
        }
    }

    public static <T> T fromJson(Gson gson, Reader reader, Type type, boolean boolean4) {
        try {
            JsonReader jsonReader5 = new JsonReader(reader);
            jsonReader5.setLenient(boolean4);
            return (T)gson.getAdapter(TypeToken.get(type)).read(jsonReader5);
        }
        catch (IOException iOException5) {
            throw new JsonParseException(iOException5);
        }
    }

    public static <T> T fromJson(Gson gson, String string, Type type, boolean boolean4) {
        return GsonHelper.fromJson(gson, new StringReader(string), type, boolean4);
    }

    public static <T> T fromJson(Gson gson, String string, Class<T> class3, boolean boolean4) {
        return GsonHelper.fromJson(gson, new StringReader(string), class3, boolean4);
    }

    public static <T> T fromJson(Gson gson, Reader reader, Type type) {
        return GsonHelper.fromJson(gson, reader, type, false);
    }

    public static <T> T fromJson(Gson gson, String string, Type type) {
        return GsonHelper.fromJson(gson, string, type, false);
    }

    public static <T> T fromJson(Gson gson, Reader reader, Class<T> class3) {
        return GsonHelper.fromJson(gson, reader, class3, false);
    }

    public static <T> T fromJson(Gson gson, String string, Class<T> class3) {
        return GsonHelper.fromJson(gson, string, class3, false);
    }
    
    public static JsonObject parse(String string, boolean boolean2) {
        return parse(new StringReader(string), boolean2);
    }
    
    public static JsonObject parse(Reader reader, boolean boolean2) {
        return GsonHelper.fromJson(GsonHelper.GSON, reader, JsonObject.class, boolean2);
    }
    
    public static JsonObject parse(String string) {
        return parse(string, false);
    }
    
    public static JsonObject parse(Reader reader) {
        return parse(reader, false);
    }
    
    static {
        GSON = new GsonBuilder().create();
    }
}
