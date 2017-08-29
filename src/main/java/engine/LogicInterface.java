package engine;

public interface LogicInterface {

    void init () throws Exception;

    void input(Window window);

    void update(float interval);

    void render(Window window);

}
