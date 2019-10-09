package coffeecatteam.theultimatetile.client.render.engine;

public interface IVAO extends IDisposable {

    void bind();

    void draw(int amount);

    void unbind();

    int getId();
}