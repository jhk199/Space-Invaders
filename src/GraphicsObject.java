import java.awt.*;
import java.util.Random;

public class GraphicsObject {

    double x;
    double y;
    double speed_x;
    double speed_y;
    int height;
    int width;


    public GraphicsObject(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.speed_x = 0;
        this.speed_y = 0;
        this.width = width;
        this.height = height;
    }

    /* Draw the object
     *
     * This function should never be called directly, but should be overridden
     * by subclasses.
     *
     * @param g The Graphics for the JPanel
     */
    public void draw(Graphics g) {
    }

    /* Update the object's location based on its speed
     *
     * @param pic_width   The width of the drawing window
     * @param pic_height  The height of the drawing window
     * @param frame       The number of frames since the start of the program
     */
    public void update(int pic_width, int pic_height, int frame) {
        this.x += this.speed_x;
        this.y += this.speed_y;

    }


    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public void setSpeedX(double speedX) {
        this.speed_x = speedX;
    }
}
