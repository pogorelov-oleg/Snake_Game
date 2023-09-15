package Entity;

//import java.awt.Color;  использовал для временного прямоугольника
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 14; // прямоугольник сместился на 8 слева и справа и 16 сверху
        solidArea.y = 24; //
        solidArea.width = 20; // 48 отняли по 14 слева и справа
        solidArea.height = 20; //

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23; // начальная координата
        worldY = gp.tileSize * 21; //
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Media/Images/Player/Walking sprites/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Media/Images/Player/Walking sprites/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Media/Images/Player/Walking sprites/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Media/Images/Player/Walking sprites/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Media/Images/Player/Walking sprites/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Media/Images/Player/Walking sprites/boy_left_2.png"));
            right1 = ImageIO
                    .read(getClass().getResourceAsStream("/Media/Images/Player/Walking sprites/boy_right_1.png"));
            right2 = ImageIO
                    .read(getClass().getResourceAsStream("/Media/Images/Player/Walking sprites/boy_right_2.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
                || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // проверяем на коллизию
            collisionOn = false;
            gp.cChecker.checkTile(this);
            // если collisionOn = false, игрок не может двигаться
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            spriteCounter++; // меняет позицию каждые 10 обновлений счетчика (то есть каждые 10 кадров)
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white); // временный прямоугольник, вместо персонажа
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
