// utility
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

// graphics

// events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// swing
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener, Runnable {

    private final int canvasWidth;
    private final int canvasHeight;
    private final Color backgroundColor;

    private final int framesPerSecond = 25;
    private final int msPerFrame = 1000 / framesPerSecond;
    private Timer timer;
    private int frame = 0;
    private int AlienAmount;
    private Rectangle object1 = new Rectangle();
    private Rectangle object2 = new Rectangle();
    private long firingInterval = 500;
    private long lastFire = 0;
    private long firingInterval2 = 100;
    private long lastFire2 = 0;
    private boolean Lose = false;
    Random rand = new Random();
    private GraphicsObject ship;
    private GraphicsObject shot;
    private GraphicsObject alienShot;
    private GraphicsObject alien;
    private ArrayList<GraphicsObject> ObjectList = new ArrayList<>();
    private ArrayList<GraphicsObject> RemoveObjectList = new ArrayList<>();

    /* Constructor for a Space Invaders game
     */
    private SpaceInvaders() {
        // fix the window size and background color
        this.canvasWidth = 800;
        this.canvasHeight = 600;
        this.backgroundColor = Color.BLACK;
        setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));

        // set the drawing timer
        this.timer = new Timer(msPerFrame, this);
        ship = new Ship(370, 550, 20, 20, Color.ORANGE, 0);
        ObjectList.add(ship);
        AlienAmount = 0;

        for (int row = 0; row < 5; row++) {
            for (int x = 0; x < 12; x++) {
                alien = new Alien(100 + (x * 50), (50) + row * 30, 20, 20, Color.RED);
                ObjectList.add(alien);
                AlienAmount++;
            }
        }
    }

    /* Start the game
     */
    @Override
    public void run() {
        // show this window
        display();

        // set a timer to redraw the screen regularly
        this.timer.start();
    }

    /* Create the window and display it
     */
    private void display() {
        JFrame jframe = new JFrame();
        jframe.addKeyListener(this);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setContentPane(this);
        jframe.pack();
        jframe.setVisible(true);
    }

    /* Run all timer-based events
     *
     * @param e  An object describing the timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // update the game objects
        update();
        // draw every object (this calls paintComponent)
        repaint(0, 0, this.canvasWidth, this.canvasHeight);
        // increment the frame counter
        this.frame++;
    }

    /* Paint/Draw the canvas.
     *
     * This function overrides the paint function in JPanel. This function is
     * automatically called when the panel is made visible.
     *
     * @param g The Graphics for the JPanel
     */
    @Override
    protected void paintComponent(Graphics g) {
        // clear the canvas before painting
        clearCanvas(g);
        if (hasWonGame()) {
            paintWinScreen(g);
        } else if (hasLostGame()) {
            paintLoseScreen(g);
        } else {
            if (ObjectList.get(rand.nextInt(ObjectList.size())) instanceof Alien) {
                    alienTryToFire();
        }
            paintGameScreen(g);
        }
    }

    /* Clear the canvas
     *
     * @param g The Graphics representing the canvas
     */
    private void clearCanvas(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, this.canvasWidth, this.canvasHeight);
        g.setColor(oldColor);
    }

    /* Respond to key release events
     *
     * A key release is when you let go of a key
     *
     * @param e  An object describing what key was released
     */
    public void keyReleased(KeyEvent e) {
        if (!(e.getKeyCode() == KeyEvent.VK_SPACE))
            ship.setSpeedX(0);

        // you can leave this function empty
    }

    /* Respond to key type events
     *
     * A key type is when you press then let go of a key
     *
     * @param e  An object describing what key was typed
     */
    public void keyTyped(KeyEvent e) {
        // you can leave this function empty
    }

    /* Respond to key press events
     *
     * A key type is when you press then let go of a key
     *
     * @param e  An object describing what key was typed
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            ship.setSpeedX(-10);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            ship.setSpeedX(10);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println(ObjectList.size());
            Fire();
        }
    }

    /* Update the game objects
     */
    private void update() {
        for (int i = 0; i < ObjectList.size(); i++) {
            for (int j = i + 1; j < ObjectList.size(); j++) {
                GraphicsObject object1 = ObjectList.get(i);
                GraphicsObject object2 = ObjectList.get(j);
                if (isCollision(object1, object2)) {
                    collidedWith(object1, object2);
                    collidedWith(object2, object1);
                }
            }
        }
//        if (shot.getY() == -100) {
//            removeInvader(shot);
//        }
//        if (alienShot.getY() == -100) {
//            removeInvader(alienShot);
//        }
        ObjectList.removeAll(RemoveObjectList);
        RemoveObjectList.clear();
        for (int i = 0; i < ObjectList.size(); i++) {
            GraphicsObject object = ObjectList.get(i);
            object.update(canvasWidth, canvasHeight, frame);
        }
    }

    /* Check if the player has lost the game
     *
     * @returns  true if the player has lost, false otherwise
     */
    private boolean hasLostGame() {
        return Lose;
    }

    /* Check if the player has won the game
     *
     * @returns  true if the player has won, false otherwise
     */
    private boolean hasWonGame() {
        return (AlienAmount <= 0);
    }

    /* Paint the screen during normal game play
     *
     * @param g The Graphics for the JPanel
     */
    private void paintGameScreen(Graphics g) {
        Random randy = new Random();
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, this.canvasWidth, this.canvasHeight);
        int c = 1;
        while(c<randy.nextInt(100)) {
            g.setColor(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
            g.fillOval(randy.nextInt(800),randy.nextInt(600),5,5);
            c+=1;
        }
        for (int i=0;i<ObjectList.size();i++) {
            GraphicsObject object = ObjectList.get(i);
            object.draw(g);
        }
    }

    /* Paint the screen when the player has won
     *
     * @param g The Graphics for the JPanel
     */
    private void paintWinScreen(Graphics g) {
        if (hasWonGame()){
            g.setColor(Color.ORANGE);
            g.fillOval(0,0,this.canvasWidth, this.canvasHeight);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Bauhaus 93", Font.BOLD, 50));
            g.drawString("You Win!", canvasWidth/2 -120, canvasHeight/2);
        }
    }

    /* Paint the screen when the player has lost
     *
     * @param g The Graphics for the JPanel
     */
    private void paintLoseScreen(Graphics g) {
        if (hasLostGame()){
            g.setColor(Color.GRAY);
            g.fillOval(0,0,this.canvasWidth, this.canvasHeight);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Bauhaus 93", Font.BOLD, 50));
            g.drawString("You lose!", canvasWidth/2 -100, canvasHeight/2);
        }
    }

    private void removeInvader(GraphicsObject invader) {
        RemoveObjectList.add(invader);
    }

    private boolean isCollision(GraphicsObject One,GraphicsObject Two) {
        object1.setBounds((int) One.x,(int) One.y,One.width,One.height);
        object2.setBounds((int) Two.x,(int) Two.y,Two.width,Two.height);

        return object1.intersects(object2);
    }
    private void collidedWith(GraphicsObject One,GraphicsObject Two) {

        if (One instanceof Alien && Two instanceof  Projectile) {
            removeInvader(One);
            removeInvader(Two);
            AlienAmount--;
        }
        if (One instanceof Alien && Two instanceof Ship) {
            for (int i=0;i<ObjectList.size();i++) {
                GraphicsObject object = ObjectList.get(i);
                object.setSpeedX(0);
            }
            Lose = true;
        }
        if (One instanceof AlienShot && Two instanceof Ship) {
            for (int i=0;i<ObjectList.size();i++) {
                GraphicsObject object = ObjectList.get(i);
                object.setSpeedX(0);
            }
            Lose = true;
        }
    }
    private void Fire() {

        if (System.currentTimeMillis() - lastFire < firingInterval) {
            return;
        }
        lastFire = System.currentTimeMillis();
        shot = new Projectile(ship.getX()+5,ship.getY()-30,10,20, Color.WHITE, -10);
        ObjectList.add(shot);
    }
    private void alienTryToFire() {
        if (System.currentTimeMillis() - lastFire2 < firingInterval2) {
            return;
        }
        lastFire2 = System.currentTimeMillis();
        GraphicsObject abc = ObjectList.get(rand.nextInt(ObjectList.size()));
        if (abc instanceof Alien) {
            alienShot = new AlienShot(abc.getX(), abc.getY(), 10, 20, Color.GREEN, 10);
            ObjectList.add(alienShot);
        }
    }
    public static void main(String[] args) {
        SpaceInvaders invaders = new SpaceInvaders();
        EventQueue.invokeLater(invaders);
    }
}
