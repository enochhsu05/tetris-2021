import java.util.TimerTask;
import java.awt.Point;

public class Mino extends TimerTask{
    
    public Point a = new Point(-1,0);
    public Point b = new Point(-1,0);
    public Point c = new Point(-1,0);
    public Point d = new Point(-1,0);
    public int color = 0;
    public int rotation = 0;
    public int special = 0; 
    int count = 0;
    float horizontal_start_delay = 7;
    float horizontal_move_delay = 2;
    
    public Mino(){ }
    
    Mino(int color, int x, int y){
        this.color = color;
        a.x = x;
        a.y = y;
    }
    
    public void run(){ //called by scheduled timer
        if (Frame.key.equals("left") && !Frame.isLeft(0)){ //checks if the current piece should move left
            if (count > horizontal_start_delay || count == 0){ //allows it to proceed if it's not held down, or if it's held down for more than .23 seconds
                if (count%horizontal_move_delay == 0){ //if it was held down, moveLeft() every 0.03 seconds
                    Frame.present.get(0).moveLeft();
                }
            }
            count++;
        }
        else if (Frame.key.equals("right") && !Frame.isRight(0)){ //same as left but for right
            if (count > horizontal_start_delay || count == 0){
                if (count%horizontal_move_delay == 0){
                    Frame.present.get(0).moveRight();
                }
            }
            count++;
        }else{
            count = 0;
        }
    }
    
    public void rotateUp(){ } //to let the individual tetraminos override it
    public void rotateDown(){ }
    
    protected void rotate(boolean up){ //keeps track of the rotation state of each tetramino
        if(up)
            rotation++;
        else
            rotation--;
        if (rotation == -1){
            rotation = 3;
        }
        if (rotation == 4){
            rotation = 0;
        }
    }
    
    public void moveDown(){ //moves down all points
        a.x++;
        b.x++;
        c.x++;
        d.x++;
    }
    
    public void moveRight(){
        a.y++;
        b.y++;
        c.y++;
        d.y++;
    }
    
    public void moveLeft(){
        a.y--;
        b.y--;
        c.y--;
        d.y--;
    }
}


