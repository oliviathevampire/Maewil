package io.github.vampirestudios.tdg.utils;

import com.google.gson.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

public class Identifier {

    protected final String namespace;
    protected final String path;

    protected Identifier(String[] id) {
        this.namespace = StringUtils.isEmpty(id[0]) ? "maewil" : id[0];
        this.path = id[1];
        if (!isNamespaceValid(this.namespace)) {
            throw new InvalidIdentifierException("Non [a-z0-9_.-] character in namespace of location: " + this.namespace + ':' + this.path);
        } else if (!isPathValid(this.path)) {
            throw new InvalidIdentifierException("Non [a-z0-9/._-] character in path of location: " + this.namespace + ':' + this.path);
        }
    }

    public Identifier(String id) {
        this(split(id, ':'));
    }

    public Identifier(String namespace, String path) {
        this(new String[]{namespace, path});
    }

    public static Identifier splitOn(String id, char delimiter) {
        return new Identifier(split(id, delimiter));
    }

    public static Identifier tryParse(String id) {
        try {
            return new Identifier(id);
        } catch (InvalidIdentifierException var2) {
            return null;
        }
    }

    protected static String[] split(String id, char delimiter) {
        String[] strings = new String[]{"maewil", id};
        int i = id.indexOf(delimiter);
        if (i >= 0) {
            strings[1] = id.substring(i + 1);
            if (i >= 1) {
                strings[0] = id.substring(0, i);
            }
        }

        return strings;
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

    public String toAssetsString() {
        return "/assets/" + this.namespace + "/" + this.path;
    }

    public String toDataString() {
        return "/data/" + this.namespace + "/" + this.path;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Identifier)) {
            return false;
        } else {
            Identifier identifier = (Identifier)object;
            return this.namespace.equals(identifier.namespace) && this.path.equals(identifier.path);
        }
    }

    public int hashCode() {
        return 31 * this.namespace.hashCode() + this.path.hashCode();
    }

    public int compareTo(Identifier identifier) {
        int i = this.path.compareTo(identifier.path);
        if (i == 0) {
            i = this.namespace.compareTo(identifier.namespace);
        }

        return i;
    }

    public static boolean isCharValid(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c == '_' || c == ':' || c == '/' || c == '.' || c == '-';
    }

    private static boolean isPathValid(String path) {
        return path.chars().allMatch((c) -> c == 95 || c == 45 || c >= 97 && c <= 122 || c >= 48 && c <= 57 || c == 47 || c == 46);
    }

    private static boolean isNamespaceValid(String namespace) {
        return namespace.chars().allMatch((c) -> c == 95 || c == 45 || c >= 97 && c <= 122 || c >= 48 && c <= 57 || c == 46);
    }

    @Environment(EnvType.CLIENT)
    public static boolean isValid(String id) {
        String[] strings = split(id, ':');
        return isNamespaceValid(StringUtils.isEmpty(strings[0]) ? "maewil" : strings[0]) && isPathValid(strings[1]);
    }

    public static class Serializer implements JsonDeserializer<Identifier>, JsonSerializer<Identifier> {
        public Identifier deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new Identifier(JsonHelper.asString(jsonElement, "location"));
        }

        public JsonElement serialize(Identifier identifier, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(identifier.toString());
        }
    }

}
