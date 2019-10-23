import java.awt.*;

public class Alien extends GraphicsObject {
    private int Speed = 5;
    int width;
    int height;
    Color color;



    public Alien(double x, double y, int width, int height, Color color) {
        super(x,y,width, height);
        this.width = width;
        this.height = height;
        this.color = color;
        speed_x = -Speed;
    }

    public void draw(Graphics g) {
        // change the color of the pen

        g.setColor(this.color);
        // draw the rectangle
        g.fillRect(((int)this.x), ((int)this.y), this.width, this.height);


    }

    public void update(int pic_width, int pic_height, int frame) {
        if (this.x < 0 || this.x + this.width > pic_width) {
            speed_x = -1.05*speed_x;
            y += 10;
        }
        super.update(pic_width, pic_height, frame);
    }
}
