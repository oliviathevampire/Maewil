package io.github.vampirestudios.tdg.utils;

import io.netty.util.ResourceLeakDetector;

public class SharedConstants {
    public static final ResourceLeakDetector.Level NETTY_LEAK_DETECTION;
    public static boolean IS_RUNNING_IN_IDE;
    public static final char[] ILLEGAL_FILE_CHARACTERS;
    private static GameVersion CURRENT_VERSION;
    
    public static boolean isAllowedChatCharacter(char character) {
        return character != '§' && character >= ' ' && character != '\u007f';
    }
    
    public static String filterText(String string) {
        StringBuilder stringBuilder2 = new StringBuilder();
        for (final char character6 : string.toCharArray()) {
            if (isAllowedChatCharacter(character6)) {
                stringBuilder2.append(character6);
            }
        }
        return stringBuilder2.toString();
    }
    
    public static String filterUnicodeSupplementary(String string) {
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int integer3 = 0; integer3 < string.length(); integer3 = string.offsetByCodePoints(integer3, 1)) {
            int integer4 = string.codePointAt(integer3);
            if (!Character.isSupplementaryCodePoint(integer4)) {
                stringBuilder2.appendCodePoint(integer4);
            }
            else {
                stringBuilder2.append('\ufffd');
            }
        }
        return stringBuilder2.toString();
    }
    
    public static GameVersion getCurrentVersion() {
        if (SharedConstants.CURRENT_VERSION == null) {
            SharedConstants.CURRENT_VERSION = DetectedVersion.tryDetectVersion();
        }
        return SharedConstants.CURRENT_VERSION;
    }
    
    static {
        NETTY_LEAK_DETECTION = ResourceLeakDetector.Level.DISABLED;
        ILLEGAL_FILE_CHARACTERS = new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
        ResourceLeakDetector.setLevel(SharedConstants.NETTY_LEAK_DETECTION);
    }
}
