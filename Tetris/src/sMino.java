public class sMino extends Mino{
       
    public sMino(){
        a.setLocation(1, 4);
        b.setLocation(1, 5);
        c.setLocation(0, 5);
        d.setLocation(0, 6);
        color = 5;
    }
    
    public void rotateUp(){
        if (rotation == 0){
            a.setLocation(a.x,a.y+2);
            b.setLocation(a.x-1,a.y);
            c.setLocation(a.x-1,a.y-1);
            d.setLocation(a.x-2,a.y-1);
        }
        else if (rotation == 1){
            a.setLocation(a.x-2,a.y);
            b.setLocation(a.x,a.y-1);
            c.setLocation(a.x+1,a.y-1);
            d.setLocation(a.x+1,a.y-2);
        }
        else if (rotation == 2){
            a.setLocation(a.x,a.y-2);
            b.setLocation(a.x+1,a.y);
            c.setLocation(a.x+1,a.y+1);
            d.setLocation(a.x+2,a.y+1);
        }
        else if (rotation == 3){
            a.setLocation(a.x+2,a.y);
            b.setLocation(a.x,a.y+1);
            c.setLocation(a.x-1,a.y+1);
            d.setLocation(a.x-1,a.y+2);
        }
        rotate(true);
    }

    public void rotateDown(){
        if (rotation == 0){
            a.setLocation(a.x-2,a.y);
            b.setLocation(a.x+1,a.y);
            c.setLocation(a.x+1,a.y+1);
            d.setLocation(a.x+2,a.y+1);
        }
        else if (rotation == 1){
            a.setLocation(a.x,a.y-2);
            b.setLocation(a.x,a.y+1);
            c.setLocation(a.x-1,a.y+1);
            d.setLocation(a.x-1,a.y+2);
        }
        else if (rotation == 2){
            a.setLocation(a.x+2,a.y);
            b.setLocation(a.x-1,a.y);
            c.setLocation(a.x-1,a.y-1);
            d.setLocation(a.x-2,a.y-1);
        }
        else if (rotation == 3){
            a.setLocation(a.x,a.y+2);
            b.setLocation(a.x,a.y-1);
            c.setLocation(a.x+1,a.y-1);
            d.setLocation(a.x+1,a.y-2);
        }
        rotate(false);
    }
}