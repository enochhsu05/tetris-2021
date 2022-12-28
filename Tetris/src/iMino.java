public class iMino extends Mino{
    
    public iMino(){
        a.setLocation(0, 3);
        b.setLocation(0, 4);
        c.setLocation(0, 5);
        d.setLocation(0, 6);
        color = 4;
    }
    
    public void rotateUp(){ //rotates based on the position of point a
        if (rotation == 0){
            a.setLocation(a.x-1,a.y+2);
            b.setLocation(a.x+1,a.y);
            c.setLocation(a.x+2,a.y);
            d.setLocation(a.x+3,a.y);
        }
        else if (rotation == 1){
            a.setLocation(a.x+3,a.y+1);
            b.setLocation(a.x,a.y-1);
            c.setLocation(a.x,a.y-2);
            d.setLocation(a.x,a.y-3);
        }
        else if (rotation == 2){
            a.setLocation(a.x+1,a.y-2);
            b.setLocation(a.x-1,a.y);
            c.setLocation(a.x-2,a.y);
            d.setLocation(a.x-3,a.y);
        }
        else if (rotation == 3){
            a.setLocation(a.x-3,a.y-1);
            b.setLocation(a.x,a.y+1);
            c.setLocation(a.x,a.y+2);
            d.setLocation(a.x,a.y+3);
        }
        rotate(true);
    }
    
     public void rotateDown(){
        if (rotation == 0){
            a.setLocation(a.x+2,a.y+1);
            b.setLocation(a.x-1,a.y);
            c.setLocation(a.x-2,a.y);
            d.setLocation(a.x-3,a.y);
        }
        else if (rotation == 1){
            a.setLocation(a.x+1,a.y-2);
            b.setLocation(a.x,a.y+1);
            c.setLocation(a.x,a.y+2);
            d.setLocation(a.x,a.y+3);
        }
        else if (rotation == 2){
            a.setLocation(a.x-2,a.y-1);
            b.setLocation(a.x+1,a.y);
            c.setLocation(a.x+2,a.y);
            d.setLocation(a.x+3,a.y);
        }
        else if (rotation == 3){
            a.setLocation(a.x-1,a.y+2);
            b.setLocation(a.x,a.y-1);
            c.setLocation(a.x,a.y-2);
            d.setLocation(a.x,a.y-3);
        }
        rotate(false);
    }
     
    public void run(){ //using the iMino class just because it's available
        if (Frame.choice == 2){ //calls either garbage or bomb if the mode is right
            Frame.garbage();
        }
        else if (Frame.choice == 4){
            Frame.bomb();
        }
    }
}