package dev.ufuk.bakan;
public class Snake extends Obstacle {

    public Snake() {
     
        super("Yılan", (int) Math.round((Math.random() * 5) + 1), 12, 0, 5);
    }

}
