package engine;

public class Engine implements Runnable {

    private static final int TARGET_FPS = 75;

    private static final int TARGET_UPS = 30;

    private final Window window;

    private final Thread loopThread;

    private final Timer timer;

    private final LogicInterface logicInterface;

    public Engine(String title, int width, int height, boolean vSync, LogicInterface logicInterface) throws Exception {
        loopThread = new Thread(this, "LOOP_THREAD");
        window = new Window(title, width, height, vSync);
        this.logicInterface = logicInterface;
        timer = new Timer();
    }

    public void start() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Mac")) {
            loopThread.run();
        } else {
            loopThread.start();
        }
    }

    @Override
    public void run() {
        try {
            init();
            loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        window.init();
        timer.init();
        logicInterface.init();
    }

    private void loop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        //boolean running = true;
        while (!window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if(!window.isvSync()) {
                sync();
            }
        }
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    private void input() {
        logicInterface.input(window);
    }

    private void update(float interval) {
        logicInterface.update(interval);
    }

    private void render() {
        logicInterface.render(window);
        window.update();
    }
}
