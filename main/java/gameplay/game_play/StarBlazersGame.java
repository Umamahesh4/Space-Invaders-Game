package gameplay.game_play;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.*;


public class StarBlazersGame extends JFrame implements ActionListener, KeyListener{
    int temp_value;

    //pgadmin laptop = new pgadmin();

    int abcd = 0;
    private int fps = 30; // Frames per second
    private Timer gameTimer;
    private long lastTime;
    private ExecutorService executorService;
    private final int THREAD_POOL_SIZE = 8; // Number of threads in the pool
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 650;
    private static final int SPACESHIP_WIDTH = 60;
    private static final int SPACESHIP_HEIGHT = 40;
    private static final int ENEMY_WIDTH = 30;
    private static final int ENEMY_HEIGHT = 30;
    private static final int BULLET_WIDTH = 5;
    private static final int BULLET_HEIGHT = 10;
    private static final int ENEMY_BULLET_WIDTH = 3;
    private static final int ENEMY_BULLET_HEIGHT = 8;

    private int spaceshipX = WIDTH / 2 - SPACESHIP_WIDTH / 2;
    private final int spaceshipY = HEIGHT - 50;
    private final int spaceshipSpeed;

    {
        spaceshipSpeed = 20;
    }

    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<EnemyBullet> enemyBullets = new ArrayList<>();

    private int score = 0;
    private int level = 1;
    private int enemiesToDestroy = 5;
    private int lives = 3;

    private final Image backgroundImage;
    private final Image enemyImage;
    private final Image spaceshipImage;

    private boolean inGame = false;
    private boolean isPaused = false;

    private final StartMenu startMenu;
    private final PauseMenu pauseMenu;

    private Clip backgroundMusic;
    private Clip bulletSound;
    private Clip hitSound;

    public boolean deepak=true;

    private void playBackgroundMusic(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // Implementation can be empty if we don't need to handle key released events
    }


    private void playSound(String filePath, boolean loop) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private void playBulletSound() {
        playSound("C:/Users/umama/Desktop/temp python/demo5/src/main/java/gameplay/game_play/bullet_sound.wav", false); // Replace with your bullet sound file path
    }

    private void playHitSound() {
        playSound("C:/Users/umama/Desktop/temp python/demo5/src/main/java/gameplay/game_play/hit_sound.wav", false); // Replace with your hit sound file path
    }

    public StarBlazersGame() {
        lastTime = System.nanoTime();2
        gameTimer = new Timer(1000 / fps, this);
        gameTimer.setInitialDelay(0);
        gameTimer.start();
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        setSize(WIDTH, HEIGHT);
        setTitle("StarBlazers Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);

        Timer timer = new Timer(200, this);
        //timer.setDelay(1000/120);

        timer.start();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        startMenu = new StartMenu();
        startMenu.setVisible(true);
        add(startMenu);

        pauseMenu = new PauseMenu();
        pauseMenu.setVisible(false);
        add(pauseMenu);

        initializeEnemies();
        playBackgroundMusic("C:/Users/umama/Desktop/temp python/demo5/src/main/java/gameplay/game_play/1.wav"); // Replace with your audio file path



        // Load images
        backgroundImage = new ImageIcon("C:/Users/umama/Desktop/temp python/demo5/src/main/java/gameplay/game_play/background_image.jpg").getImage();
        enemyImage = new ImageIcon("C:/Users/umama/Desktop/temp python/demo5/src/main/java/gameplay/game_play/enemy_image.jpg").getImage();
        spaceshipImage = new ImageIcon("C:/Users/umama/Desktop/temp python/demo5/src/main/java/gameplay/game_play/spaceship_image.jpg").getImage();
    }

    private void initializeEnemies() {
        Random random = new Random();
        for (int i = 0; i < enemiesToDestroy; i++) {
            int x = random.nextInt(WIDTH - ENEMY_WIDTH);
            int y = random.nextInt(200);
            enemies.add(new Enemy(x, y));
        }
    }
    private void startGameLoop() {
        executorService.submit(this::gameLoop);
    }






    private void toggleStartMenu() {
        startMenu.setVisible(!startMenu.isVisible());
        inGame = true;
    }

    private void drawStartMenu(Graphics g) {
        g.setColor(new Color(255,165,0));
        g.setFont(new Font("Stencil", Font.BOLD, 45));
        g.drawString("StarBlazers Game", WIDTH / 2 - 200, HEIGHT / 2 - 60);
        g.setColor( Color.yellow);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Press S to start", WIDTH / 2 - 100, HEIGHT / 2);
        g.drawString("Press Q to quit", WIDTH / 2 - 100, HEIGHT / 2 + 30);
    }

    private void togglePause() {
        isPaused = !isPaused;
        pauseMenu.setVisible(isPaused);

        repaint();
    }

    private void drawPauseMenu(Graphics g) {
        g.setColor(new Color(255,165,0));
        g.setFont(new Font("Stencil", Font.BOLD, 30));
        g.drawString("Game Paused", WIDTH / 2 - 120, HEIGHT / 2 - 30);
        g.setColor(new Color(0,128,128));
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, WIDTH / 2 - 100, HEIGHT / 2 + 20);
        g.drawString("Press R to restart", WIDTH / 2 - 100, HEIGHT / 2 + 50);
        g.drawString("Press Q to quit", WIDTH / 2 - 100, HEIGHT / 2 + 80);
        g.drawString("Press E to resume", WIDTH / 2 - 120, HEIGHT / 2 + 110);
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, this);



        if (startMenu.isVisible()) {
            drawStartMenu(g);
        } else if (isPaused) {
            drawPauseMenu(g);
        } else if (inGame) {
//            g.setColor(Color.white);
//            g.fillRect(spaceshipX, spaceshipY, SPACESHIP_WIDTH, SPACESHIP_HEIGHT);
            g.drawImage(spaceshipImage, spaceshipX, spaceshipY, SPACESHIP_WIDTH, SPACESHIP_HEIGHT, this);


            for (Enemy enemy : enemies) {
//
                g.drawImage(enemyImage, enemy.getX(), enemy.getY(), ENEMY_WIDTH, ENEMY_HEIGHT, this);

            }

            for (Bullet bullet : bullets) {
                g.setColor(Color.blue);
                g.fillRect(bullet.getX(), bullet.getY(), BULLET_WIDTH, BULLET_HEIGHT);
            }

            for (EnemyBullet enemyBullet : enemyBullets) {
                g.setColor(Color.red);
                g.fillRect(enemyBullet.getX(), enemyBullet.getY(), ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT);
            }

            g.setColor(Color.white);
            g.drawString("Score: " + score, 10, 20);
            g.drawString("Level: " + level, 10, 40);
            g.drawString("Lives: " + lives, 10, 60);
            g.drawString("Enemies Left: " + enemies.size(), 10, 80);
            int heartSize = 20; // Size of heart symbol
            int padding = 5; // Padding between hearts and screen edges
            int heartsX = WIDTH - (padding + heartSize) * lives; // Adjust position based on number of lives
            int heartsY = padding + heartSize;
            for (int i = 0; i < lives; i++) {
                g.setColor(Color.red);
                g.fillOval(heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + padding;
            }
        } else {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", WIDTH / 2 - 100, HEIGHT / 2 - 30);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Final Score: " + score, WIDTH / 2 - 100, HEIGHT / 2 + 20);
            abcd = score;
//            System.out.println(abcd);
            while(deepak==true){
                temp_value=abcd;
                System.out.println(abcd);
                deepak=false;
            }
            g.drawString("Press R to restart", WIDTH / 2 - 100, HEIGHT / 2 + 50);
            g.drawString("Press Q to quit", WIDTH / 2 - 100, HEIGHT / 2 + 80);
        }
    }
    private volatile boolean isRunning = true;
    public int database(){
        return abcd;

    }
    private void gameLoop() {
        long now;
        double amountOfTicks = fps;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long lastTime = System.nanoTime();
        int updates = 0;

        while (isRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                if (inGame && !isPaused) {
                    moveSpaceship();
                    moveEnemies();
                    moveBullets();
                    moveEnemyBullets();
                    checkCollisions();
                    checkLevelCompletion();
                }
                updates++;

                repaint();
                delta--;
            }

//            if (System.currentTimeMillis() - lastTime >= 1000) {
////                System.out.println("FPS: " + updates);
//                updates = 0;
//                lastTime = System.currentTimeMillis();
//            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {

    }
    private void playPlayerBulletSound() {
        playSound("C:/Users/umama/Desktop/temp python/demo5/src/main/java/gameplay/game_play/player_bullet_sound.wav", false); // Replace with your player bullet sound file path
    }

    private void playEnemyBulletSound() {
        playSound("C:/Users/umama/Desktop/temp python/demo5/src/main/java/gameplay/game_play/enemy_bullet_sound.wav", false); // Replace with your enemy bullet sound file path
    }

    private void playLifeLostSound() {
        playSound("C:/Users/umama/Desktop/temp python/demo5/src/main/java/gameplay/game_play/life_lost_sound.wav", false); // Replace with your life lost sound file path
    }


    private void moveSpaceship() {
        if (spaceshipX < 0) {
            spaceshipX = 0;
        } else if (spaceshipX > WIDTH - SPACESHIP_WIDTH) {
            spaceshipX = WIDTH - SPACESHIP_WIDTH;
        }
    }

    private void moveEnemies() {
        for (Enemy enemy : enemies) {
            enemy.move();
        }
    }

    private void moveBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.move();
            if (bullet.getY() < 0) {
                iterator.remove();
            }
        }
    }

    private void moveEnemyBullets() {
        Iterator<EnemyBullet> iterator = enemyBullets.iterator();
        while (iterator.hasNext()) {
            EnemyBullet enemyBullet = iterator.next();
            enemyBullet.move();
            if (enemyBullet.getY() > HEIGHT) {
                iterator.remove();
            }
        }
    }

    private void checkCollisions() {
        // Collision check between player's bullets and enemies
        for (Bullet bullet : new ArrayList<>(bullets)) {
            for (Enemy enemy : new ArrayList<>(enemies)) {
                if (bullet.intersects(enemy)) {
                    bullets.remove(bullet);
                    enemies.remove(enemy);
                    score += 10;

                    playEnemyBulletSound(); 

                }
            }
        }

        // Collision check between enemy bullets and the player
        for (EnemyBullet enemyBullet : new ArrayList<>(enemyBullets)) {
            if (enemyBullet.intersects(spaceshipX, spaceshipY, SPACESHIP_WIDTH, SPACESHIP_HEIGHT)) {
                enemyBullets.remove(enemyBullet);
                lives--; // Reduce lives by 1 when hit
                playEnemyBulletSound(); // Play enemy bullet sound when hit by an enemy bullet

                if (lives <= 0) {
                    inGame = false;
                } else {
                    playLifeLostSound(); // Play sound for life lost
                    resetGame(); // Reset the game when a life is lost
                }
            }
        }

        // Collision check between enemies and the player
        for (Enemy enemy : new ArrayList<>(enemies)) {
            if (spaceshipIntersects(enemy)) {
                enemies.remove(enemy);
                lives--; // Reduce lives by 1 when hit by an enemy
                playEnemyBulletSound(); // Play enemy bullet sound when hit by an enemy

                if (lives <= 0) {
                    inGame = false;
                } else {
                    playLifeLostSound(); // Play sound for life lost
                    resetGame(); // Reset the game when a life is lost
                }
            }
        }
    }

    private void checkLevelCompletion() {
        if (enemies.isEmpty()) {
            level++;
            enemiesToDestroy += 2;
            initializeEnemies();
        }
    }

    private boolean spaceshipIntersects(Enemy enemy) {
        return spaceshipX < enemy.getX() + ENEMY_WIDTH &&
                spaceshipX + SPACESHIP_WIDTH > enemy.getX() &&
                spaceshipY < enemy.getY() + ENEMY_HEIGHT &&
                spaceshipY + SPACESHIP_HEIGHT > enemy.getY();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (startMenu.isVisible()) {
            if (key == KeyEvent.VK_S) {
                toggleStartMenu(); // Start the game
                startGameLoop();
            } else if (key == KeyEvent.VK_Q) {
                System.exit(0);
            }
        } else if (inGame && !isPaused) {
            if (key == KeyEvent.VK_LEFT) {
                spaceshipX -= spaceshipSpeed;
            } else if (key == KeyEvent.VK_RIGHT) {
                spaceshipX += spaceshipSpeed;
            } else if (key == KeyEvent.VK_SPACE) {
                bullets.add(new Bullet(spaceshipX + SPACESHIP_WIDTH / 2 - BULLET_WIDTH / 2, spaceshipY));
                playPlayerBulletSound(); // Play player bullet sound when shooting

            } else if (key == KeyEvent.VK_ESCAPE) {
                togglePause();
            }
        } else {
            if (key == KeyEvent.VK_R) {
                if (!inGame) {
                    restartGame(); // Restart the game directly when in game-over state
                } else {
                    togglePause();  // Hide the pause menu when in pause state
                    restartGame();  // Restart the game when in pause state
                }
            } else if (key == KeyEvent.VK_Q) {
                System.exit(0);
            } else if (key == KeyEvent.VK_E && isPaused) {
                togglePause(); // Resume the game
            }
        }
    }

    // ... (keyTyped and keyReleased methods remain unchanged)

    public void resetGame() {


        spaceshipX = WIDTH / 2 - SPACESHIP_WIDTH / 2;
        enemies.clear();
        bullets.clear();
        initializeEnemies();
    }

    private void restartGame() {
        inGame = true;
        level = 1;
        score = 0;
        lives = 3;
        resetGame();
//        togglePause();
    }

    private class Enemy {
        private int x;
        private int y;
        private final int speed = 2;
        private final int fireRate = 100; // Adjust fire rate as needed
        private int fireCounter = 0;

        public Enemy(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move() {
            y += speed;
            if (y > HEIGHT) {
                y = 0;
                x = new Random().nextInt(WIDTH - ENEMY_WIDTH);
            }
            fireCounter++;
            if (fireCounter >= fireRate) {
                fireBullet();
                fireCounter = 0;
            }
        }

        private void fireBullet() {
            enemyBullets.add(new EnemyBullet(x + ENEMY_WIDTH / 2 - ENEMY_BULLET_WIDTH / 2, y + ENEMY_HEIGHT / 2));
        }


    }

    private class Bullet {
        private final int x;
        private int y;
        private final int speed = 5;

        public Bullet(int x, int y) {
            this.x = x;
            this.y = y;
            playBulletSound();
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move() {
            y -= speed;
        }

        public boolean intersects(Enemy enemy) {
            return x < enemy.getX() + ENEMY_WIDTH &&
                    x + BULLET_WIDTH > enemy.getX() &&
                    y < enemy.getY() + ENEMY_HEIGHT &&
                    y + BULLET_HEIGHT > enemy.getY();
        }
    }

    private class EnemyBullet {
        private final int x;
        private int y;
        private final int speed = 10; // Adjust bullet speed as needed

        public EnemyBullet(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move() {
            y += speed;
        }

        public boolean intersects(int x, int y, int width, int height) {
            Rectangle playerRect = new Rectangle(x, y, width, height);
            return playerRect.contains(this.x, this.y);
        }
    }

    private class StartMenu extends JPanel {
        public StartMenu() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }
    }

    private class PauseMenu extends JPanel {
        private final JButton resumeButton;
        private final JButton restartButton;
        private final JButton quitButton;

        public PauseMenu() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            resumeButton = new JButton("Resume");
            restartButton = new JButton("Restart");
            quitButton = new JButton("Quit");

            resumeButton.addActionListener(e -> {
                restartGame();
            });

            restartButton.addActionListener(e -> {
                restartGame();
            });

            quitButton.addActionListener(e -> {
                System.exit(0);
            });

            add(resumeButton);
            add(restartButton);
            add(quitButton);
        }

        public int datarel(){
            return temp_value;
        }

    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            StarBlazersGame game = new StarBlazersGame();
            game.setVisible(true);

            int vale = game.temp_value;
            System.out.println("hello");
            System.out.println(vale);

            try{
                Class.forName("org.postgresql.Driver");
                Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/starblazers_game","postgres","umaabcd");
                String query="insert into game values(?)";
                PreparedStatement pst=con.prepareStatement(query);
                pst.setInt(1,vale);
                pst.executeUpdate();
                System.out.println("Records inserted sucessfully");
                con.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }




        });




    }
}
