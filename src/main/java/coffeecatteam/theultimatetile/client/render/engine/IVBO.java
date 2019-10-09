package coffeecatteam.theultimatetile.client.render.engine;

import java.nio.FloatBuffer;

public interface IVBO extends IDisposable {

    void bind();

    void data(long size);

    void subData(FloatBuffer vertices);

    int getDrawMode();

    void unbind();

    int getId();

    boolean isStatic();
}