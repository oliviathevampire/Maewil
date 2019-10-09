package coffeecatteam.theultimatetile.gfx.assets.texture;

import coffeecatteam.theultimatetile.client.render.engine.TextureBank;
import coffeecatteam.theultimatetile.gfx.assets.IAsset;
import coffeecatteam.theultimatetile.utils.ApiInternal;
import coffeecatteam.theultimatetile.utils.Identifier;
import com.google.gson.JsonElement;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Random;

public interface ITexture extends IAsset {

    Identifier ID = new Identifier("tex");
    int TOP_LEFT = 0;
    int BOTTOM_LEFT = 1;
    int BOTTOM_RIGHT = 2;
    int TOP_RIGHT = 3;

    void bind(TextureBank bank, boolean revertAfterBind);

    void bind();

    void param(int param, int value);

    int getId();

    int getTextureWidth();

    int getTextureHeight();

    ByteBuffer getPixelData();

    void unbind(TextureBank bank, boolean revertAfterUnbind);

    void unbind();

    void draw(float x, float y);

    void draw(float x, float y, float scale);

    void draw(float x, float y, float width, float height);

    void draw(float x, float y, float width, float height, int[] light);

    void draw(float x, float y, float width, float height, int filter);

    void draw(float x, float y, float width, float height, int[] light, int filter);

    void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2);

    void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light);

    void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int filter);

    void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light, int filter);

    void draw(float x, float y, float x2, float y2, float x3, float y3, float x4, float y4, float srcX, float srcY, float srcX2, float srcY2, int[] light, int filter);

    @ApiInternal
    void setAdditionalData(Map<String, JsonElement> data);

    @ApiInternal
    void setVariations(List<ITexture> variations);

    JsonElement getAdditionalData(String name);

    ITexture getVariation(Random random);

    ITexture getPositionalVariation(int x, int y);

    ITexture getSubTexture(int x, int y, int width, int height);

    ITexture getSubTexture(int x, int y, int width, int height, boolean inheritVariations, boolean inheritData);

    int getTextureColor(int x, int y);

    int getRenderWidth();

    int getRenderHeight();

    int getRenderOffsetX();

    int getRenderOffsetY();
}