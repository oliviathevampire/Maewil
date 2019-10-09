package team.abnormals.tut_new.engine;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;

public class JsonHelper {
   private static final Gson GSON = (new GsonBuilder()).create();

   public static boolean hasString(JsonObject jsonObject_1, String string_1) {
      return hasPrimitive(jsonObject_1, string_1) && jsonObject_1.getAsJsonPrimitive(string_1).isString();
   }

   @Environment(EnvType.CLIENT)
   public static boolean isString(JsonElement jsonElement_1) {
      return jsonElement_1.isJsonPrimitive() && jsonElement_1.getAsJsonPrimitive().isString();
   }

   public static boolean isNumber(JsonElement jsonElement_1) {
      return jsonElement_1.isJsonPrimitive() && jsonElement_1.getAsJsonPrimitive().isNumber();
   }

   @Environment(EnvType.CLIENT)
   public static boolean hasBoolean(JsonObject jsonObject_1, String string_1) {
      return hasPrimitive(jsonObject_1, string_1) && jsonObject_1.getAsJsonPrimitive(string_1).isBoolean();
   }

   public static boolean hasArray(JsonObject jsonObject_1, String string_1) {
      return hasElement(jsonObject_1, string_1) && jsonObject_1.get(string_1).isJsonArray();
   }

   public static boolean hasPrimitive(JsonObject jsonObject_1, String string_1) {
      return hasElement(jsonObject_1, string_1) && jsonObject_1.get(string_1).isJsonPrimitive();
   }

   public static boolean hasElement(JsonObject jsonObject_1, String string_1) {
      if (jsonObject_1 == null) {
         return false;
      } else {
         return jsonObject_1.get(string_1) != null;
      }
   }

   public static String asString(JsonElement jsonElement_1, String string_1) {
      if (jsonElement_1.isJsonPrimitive()) {
         return jsonElement_1.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + string_1 + " to be a string, was " + getType(jsonElement_1));
      }
   }

   public static String getString(JsonObject jsonObject_1, String string_1) {
      if (jsonObject_1.has(string_1)) {
         return asString(jsonObject_1.get(string_1), string_1);
      } else {
         throw new JsonSyntaxException("Missing " + string_1 + ", expected to find a string");
      }
   }

   public static String getString(JsonObject jsonObject_1, String string_1, String string_2) {
      return jsonObject_1.has(string_1) ? asString(jsonObject_1.get(string_1), string_1) : string_2;
   }

   public static boolean asBoolean(JsonElement jsonElement_1, String string_1) {
      if (jsonElement_1.isJsonPrimitive()) {
         return jsonElement_1.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + string_1 + " to be a Boolean, was " + getType(jsonElement_1));
      }
   }

   public static boolean getBoolean(JsonObject jsonObject_1, String string_1) {
      if (jsonObject_1.has(string_1)) {
         return asBoolean(jsonObject_1.get(string_1), string_1);
      } else {
         throw new JsonSyntaxException("Missing " + string_1 + ", expected to find a Boolean");
      }
   }

   public static boolean getBoolean(JsonObject jsonObject_1, String string_1, boolean boolean_1) {
      return jsonObject_1.has(string_1) ? asBoolean(jsonObject_1.get(string_1), string_1) : boolean_1;
   }

   public static float asFloat(JsonElement jsonElement_1, String string_1) {
      if (jsonElement_1.isJsonPrimitive() && jsonElement_1.getAsJsonPrimitive().isNumber()) {
         return jsonElement_1.getAsFloat();
      } else {
         throw new JsonSyntaxException("Expected " + string_1 + " to be a Float, was " + getType(jsonElement_1));
      }
   }

   public static float getFloat(JsonObject jsonObject_1, String string_1) {
      if (jsonObject_1.has(string_1)) {
         return asFloat(jsonObject_1.get(string_1), string_1);
      } else {
         throw new JsonSyntaxException("Missing " + string_1 + ", expected to find a Float");
      }
   }

   public static float getFloat(JsonObject jsonObject_1, String string_1, float float_1) {
      return jsonObject_1.has(string_1) ? asFloat(jsonObject_1.get(string_1), string_1) : float_1;
   }

   public static long asLong(JsonElement jsonElement_1, String string_1) {
      if (jsonElement_1.isJsonPrimitive() && jsonElement_1.getAsJsonPrimitive().isNumber()) {
         return jsonElement_1.getAsLong();
      } else {
         throw new JsonSyntaxException("Expected " + string_1 + " to be a Long, was " + getType(jsonElement_1));
      }
   }

   public static long getLong(JsonObject jsonObject_1, String string_1, long long_1) {
      return jsonObject_1.has(string_1) ? asLong(jsonObject_1.get(string_1), string_1) : long_1;
   }

   public static int asInt(JsonElement jsonElement_1, String string_1) {
      if (jsonElement_1.isJsonPrimitive() && jsonElement_1.getAsJsonPrimitive().isNumber()) {
         return jsonElement_1.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + string_1 + " to be a Int, was " + getType(jsonElement_1));
      }
   }

   public static int getInt(JsonObject jsonObject_1, String string_1) {
      if (jsonObject_1.has(string_1)) {
         return asInt(jsonObject_1.get(string_1), string_1);
      } else {
         throw new JsonSyntaxException("Missing " + string_1 + ", expected to find a Int");
      }
   }

   public static int getInt(JsonObject jsonObject_1, String string_1, int int_1) {
      return jsonObject_1.has(string_1) ? asInt(jsonObject_1.get(string_1), string_1) : int_1;
   }

   public static byte asByte(JsonElement jsonElement_1, String string_1) {
      if (jsonElement_1.isJsonPrimitive() && jsonElement_1.getAsJsonPrimitive().isNumber()) {
         return jsonElement_1.getAsByte();
      } else {
         throw new JsonSyntaxException("Expected " + string_1 + " to be a Byte, was " + getType(jsonElement_1));
      }
   }

   public static byte getByte(JsonObject jsonObject_1, String string_1, byte byte_1) {
      return jsonObject_1.has(string_1) ? asByte(jsonObject_1.get(string_1), string_1) : byte_1;
   }

   public static JsonObject asObject(JsonElement jsonElement_1, String string_1) {
      if (jsonElement_1.isJsonObject()) {
         return jsonElement_1.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + string_1 + " to be a JsonObject, was " + getType(jsonElement_1));
      }
   }

   public static JsonObject getObject(JsonObject jsonObject_1, String string_1) {
      if (jsonObject_1.has(string_1)) {
         return asObject(jsonObject_1.get(string_1), string_1);
      } else {
         throw new JsonSyntaxException("Missing " + string_1 + ", expected to find a JsonObject");
      }
   }

   public static JsonObject getObject(JsonObject jsonObject_1, String string_1, JsonObject jsonObject_2) {
      return jsonObject_1.has(string_1) ? asObject(jsonObject_1.get(string_1), string_1) : jsonObject_2;
   }

   public static JsonArray asArray(JsonElement jsonElement_1, String string_1) {
      if (jsonElement_1.isJsonArray()) {
         return jsonElement_1.getAsJsonArray();
      } else {
         throw new JsonSyntaxException("Expected " + string_1 + " to be a JsonArray, was " + getType(jsonElement_1));
      }
   }

   public static JsonArray getArray(JsonObject jsonObject_1, String string_1) {
      if (jsonObject_1.has(string_1)) {
         return asArray(jsonObject_1.get(string_1), string_1);
      } else {
         throw new JsonSyntaxException("Missing " + string_1 + ", expected to find a JsonArray");
      }
   }

   public static JsonArray getArray(JsonObject jsonObject_1, String string_1, @Nullable JsonArray jsonArray_1) {
      return jsonObject_1.has(string_1) ? asArray(jsonObject_1.get(string_1), string_1) : jsonArray_1;
   }

   public static <T> T deserialize(@Nullable JsonElement jsonElement_1, String string_1, JsonDeserializationContext jsonDeserializationContext_1, Class<? extends T> class_1) {
      if (jsonElement_1 != null) {
         return jsonDeserializationContext_1.deserialize(jsonElement_1, class_1);
      } else {
         throw new JsonSyntaxException("Missing " + string_1);
      }
   }

   public static <T> T deserialize(JsonObject jsonObject_1, String string_1, JsonDeserializationContext jsonDeserializationContext_1, Class<? extends T> class_1) {
      if (jsonObject_1.has(string_1)) {
         return deserialize(jsonObject_1.get(string_1), string_1, jsonDeserializationContext_1, class_1);
      } else {
         throw new JsonSyntaxException("Missing " + string_1);
      }
   }

   public static <T> T deserialize(JsonObject jsonObject_1, String string_1, T object_1, JsonDeserializationContext jsonDeserializationContext_1, Class<? extends T> class_1) {
      return jsonObject_1.has(string_1) ? deserialize(jsonObject_1.get(string_1), string_1, jsonDeserializationContext_1, class_1) : object_1;
   }

   public static String getType(JsonElement jsonElement_1) {
      String string_1 = StringUtils.abbreviateMiddle(String.valueOf(jsonElement_1), "...", 10);
      if (jsonElement_1 == null) {
         return "null (missing)";
      } else if (jsonElement_1.isJsonNull()) {
         return "null (json)";
      } else if (jsonElement_1.isJsonArray()) {
         return "an array (" + string_1 + ")";
      } else if (jsonElement_1.isJsonObject()) {
         return "an object (" + string_1 + ")";
      } else {
         if (jsonElement_1.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive_1 = jsonElement_1.getAsJsonPrimitive();
            if (jsonPrimitive_1.isNumber()) {
               return "a number (" + string_1 + ")";
            }

            if (jsonPrimitive_1.isBoolean()) {
               return "a boolean (" + string_1 + ")";
            }
         }

         return string_1;
      }
   }

   @Nullable
   public static <T> T deserialize(Gson gson_1, Reader reader_1, Class<T> class_1, boolean boolean_1) {
      try {
         JsonReader jsonReader_1 = new JsonReader(reader_1);
         jsonReader_1.setLenient(boolean_1);
         return gson_1.getAdapter(class_1).read(jsonReader_1);
      } catch (IOException var5) {
         throw new JsonParseException(var5);
      }
   }

   @Nullable
   public static <T> T deserialize(Gson gson_1, Reader reader_1, Type type_1, boolean boolean_1) {
      try {
         JsonReader jsonReader_1 = new JsonReader(reader_1);
         jsonReader_1.setLenient(boolean_1);
         return (T) gson_1.getAdapter(TypeToken.get(type_1)).read(jsonReader_1);
      } catch (IOException var5) {
         throw new JsonParseException(var5);
      }
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public static <T> T deserialize(Gson gson_1, String string_1, Type type_1, boolean boolean_1) {
      return deserialize(gson_1, new StringReader(string_1), type_1, boolean_1);
   }

   @Nullable
   public static <T> T deserialize(Gson gson_1, String string_1, Class<T> class_1, boolean boolean_1) {
      return (T) deserialize(gson_1, (new StringReader(string_1)), (Class)class_1, boolean_1);
   }

   @Nullable
   public static <T> T deserialize(Gson gson_1, Reader reader_1, Type type_1) {
      return deserialize(gson_1, reader_1, type_1, false);
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public static <T> T deserialize(Gson gson_1, String string_1, Type type_1) {
      return deserialize(gson_1, string_1, type_1, false);
   }

   @Nullable
   public static <T> T deserialize(Gson gson_1, Reader reader_1, Class<T> class_1) {
      return deserialize(gson_1, reader_1, class_1, false);
   }

   @Nullable
   public static <T> T deserialize(Gson gson_1, String string_1, Class<T> class_1) {
      return deserialize(gson_1, string_1, class_1, false);
   }

   public static JsonObject deserialize(String string_1, boolean boolean_1) {
      return deserialize(new StringReader(string_1), boolean_1);
   }

   public static JsonObject deserialize(Reader reader_1, boolean boolean_1) {
      return deserialize(GSON, reader_1, JsonObject.class, boolean_1);
   }

   public static JsonObject deserialize(String string_1) {
      return deserialize(string_1, false);
   }

   public static JsonObject deserialize(Reader reader_1) {
      return deserialize(reader_1, false);
   }
}
