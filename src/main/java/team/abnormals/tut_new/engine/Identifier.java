package team.abnormals.tut_new.engine;

import com.google.gson.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

public class Identifier implements Comparable<Identifier> {
   protected final String namespace;
   protected final String path;

   protected Identifier(String[] strings_1) {
      this.namespace = StringUtils.isEmpty(strings_1[0]) ? "minecraft" : strings_1[0];
      this.path = strings_1[1];
      if (!isNamespaceValid(this.namespace)) {
         throw new InvalidIdentifierException("Non [a-z0-9_.-] character in namespace of location: " + this.namespace + ':' + this.path);
      } else if (!isPathValid(this.path)) {
         throw new InvalidIdentifierException("Non [a-z0-9/._-] character in path of location: " + this.namespace + ':' + this.path);
      }
   }

   public Identifier(String string_1) {
      this(split(string_1, ':'));
   }

   public Identifier(String string_1, String string_2) {
      this(new String[]{string_1, string_2});
   }

   public static Identifier splitOn(String string_1, char char_1) {
      return new Identifier(split(string_1, char_1));
   }

   @Nullable
   public static Identifier tryParse(String string_1) {
      try {
         return new Identifier(string_1);
      } catch (InvalidIdentifierException var2) {
         return null;
      }
   }

   protected static String[] split(String string_1, char char_1) {
      String[] strings_1 = new String[]{"minecraft", string_1};
      int int_1 = string_1.indexOf(char_1);
      if (int_1 >= 0) {
         strings_1[1] = string_1.substring(int_1 + 1, string_1.length());
         if (int_1 >= 1) {
            strings_1[0] = string_1.substring(0, int_1);
         }
      }

      return strings_1;
   }

   public String getPath() {
      return this.path;
   }

   public String getNamespace() {
      return this.namespace;
   }

   public String toString() {
      return this.namespace + ':' + this.path;
   }

   public boolean equals(Object object_1) {
      if (this == object_1) {
         return true;
      } else if (!(object_1 instanceof Identifier)) {
         return false;
      } else {
         Identifier identifier_1 = (Identifier)object_1;
         return this.namespace.equals(identifier_1.namespace) && this.path.equals(identifier_1.path);
      }
   }

   public int hashCode() {
      return 31 * this.namespace.hashCode() + this.path.hashCode();
   }

   public int compareTo(Identifier identifier_1) {
      int int_1 = this.path.compareTo(identifier_1.path);
      if (int_1 == 0) {
         int_1 = this.namespace.compareTo(identifier_1.namespace);
      }

      return int_1;
   }

   public static boolean isCharValid(char char_1) {
      return char_1 >= '0' && char_1 <= '9' || char_1 >= 'a' && char_1 <= 'z' || char_1 == '_' || char_1 == ':' || char_1 == '/' || char_1 == '.' || char_1 == '-';
   }

   private static boolean isPathValid(String string_1) {
      return string_1.chars().allMatch((int_1) -> int_1 == 95 || int_1 == 45 || int_1 >= 97 && int_1 <= 122 || int_1 >= 48 && int_1 <= 57 || int_1 == 47 || int_1 == 46);
   }

   private static boolean isNamespaceValid(String string_1) {
      return string_1.chars().allMatch((int_1) -> int_1 == 95 || int_1 == 45 || int_1 >= 97 && int_1 <= 122 || int_1 >= 48 && int_1 <= 57 || int_1 == 46);
   }

   @Environment(EnvType.CLIENT)
   public static boolean isValid(String string_1) {
      String[] strings_1 = split(string_1, ':');
      return isNamespaceValid(StringUtils.isEmpty(strings_1[0]) ? "minecraft" : strings_1[0]) && isPathValid(strings_1[1]);
   }

   public static class Serializer implements JsonDeserializer<Identifier>, JsonSerializer<Identifier> {
      public Identifier deserialize(JsonElement jsonElement_1, Type type_1, JsonDeserializationContext jsonDeserializationContext_1) throws JsonParseException {
         return new Identifier(JsonHelper.asString(jsonElement_1, "location"));
      }

      public JsonElement serialize(Identifier identifier_1, Type type_1, JsonSerializationContext jsonSerializationContext_1) {
         return new JsonPrimitive(identifier_1.toString());
      }
   }
}
