package coffeecatteam.theultimatetile.client.render.engine;

import org.lwjgl.opengl.GL13;

/**
 * Texture banks as defined by {@link GL13#GL_TEXTURE0} through {@link
 * GL13#GL_TEXTURE31}. As these texture banks are not necessarily all supported
 * by graphics cards (as documented in {@link GL13#glActiveTexture(int)}, please
 * use these sparingly and try to stay within the first eight or so banks.
 */
public enum TextureBank {
    BANK_1,
    BANK_2,
    BANK_3,
    BANK_4,
    BANK_5,
    BANK_6,
    BANK_7,
    BANK_8,
    BANK_9,
    BANK_10,
    BANK_11,
    BANK_12,
    BANK_13,
    BANK_14,
    BANK_15,
    BANK_16,
    BANK_17,
    BANK_18,
    BANK_19,
    BANK_20,
    BANK_21,
    BANK_22,
    BANK_23,
    BANK_24,
    BANK_25,
    BANK_26,
    BANK_27,
    BANK_28,
    BANK_29,
    BANK_30,
    BANK_31,
    BANK_32;

    public static final TextureBank[] BANKS = values();
}