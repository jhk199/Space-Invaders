import java.awt.*;

public class AlienShot extends GraphicsObject {
    private int Speed;
    int width;
    int height;
    Color color;




    public AlienShot(int x, int y, int width, int height, Color color, int Speed) {
        super(x, y, width, height);
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed_y = Speed;
    }

    public void draw(Graphics g) {
        // change the color of the pen

        g.setColor(this.color);
        // draw the rectangle
        g.fillRect(((int)this.x), ((int)this.y), this.width, this.height);
    }

    public void update(int pic_width, int pic_height, int frame) {
        super.update(pic_width, pic_height, frame);
    }
}


