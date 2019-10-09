package team.abnormals.tut_new.engine;

public interface Game {

    void init() throws Exception;
    
    void input(Window window);

    void update(float interval);
    
    void render(Window window);
    
    void cleanup();

}