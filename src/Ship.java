import java.awt.*;

public class Ship extends GraphicsObject {

    int width;
    int height;
    Color color;

    public Ship(int x, int y, int width, int height, Color color, int speed_x) {
        super(x,y, width, height);
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed_x = speed_x;
    }
    public void draw(Graphics g) {
        // change the color of the pen

        g.setColor(this.color);
        // draw the rectangle
        g.fillRect(((int)this.x), ((int)this.y), this.width, this.height);
    }


    @Override
    public void update(int pic_width, int pic_height, int frame) {
        if ((speed_x < 0) && (x < 10)) {
            return;
        }

        if ((speed_x > 0) && (x > 770)) {
            return;
        }

        super.update(pic_width, pic_height, frame);
    }





}
