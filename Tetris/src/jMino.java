public class jMino extends Mino{
        
    public jMino(){
        a.setLocation(0, 4);
        b.setLocation(1, 4);
        c.setLocation(1, 5);
        d.setLocation(1, 6);
        color = 7;
    }
    
    public void rotateUp(){
        if (rotation == 0){
            a.setLocation(a.x,a.y+2);
            b.setLocation(a.x,a.y-1);
            c.setLocation(a.x+1,a.y-1);
            d.setLocation(a.x+2,a.y-1);
        }
        else if (rotation == 1){
            a.setLocation(a.x+2,a.y);
            b.setLocation(a.x-1,a.y);
            c.setLocation(a.x-1,a.y-1);
            d.setLocation(a.x-1,a.y-2);
        }
        else if (rotation == 2){
            a.setLocation(a.x,a.y-2);
            b.setLocation(a.x,a.y+1);
            c.setLocation(a.x-1,a.y+1);
            d.setLocation(a.x-2,a.y+1);
        }
        else if (rotation == 3){
            a.setLocation(a.x-2,a.y);
            b.setLocation(a.x+1,a.y);
            c.setLocation(a.x+1,a.y+1);
            d.setLocation(a.x+1,a.y+2);
        }
        rotate(true);
    }
     
    public void rotateDown(){
        if (rotation == 0){
            a.setLocation(a.x+2,a.y);
            b.setLocation(a.x,a.y+1);
            c.setLocation(a.x-1,a.y+1);
            d.setLocation(a.x-2,a.y+1);
        }
        else if (rotation == 1){
            a.setLocation(a.x,a.y-2);
            b.setLocation(a.x+1,a.y);
            c.setLocation(a.x+1,a.y+1);
            d.setLocation(a.x+1,a.y+2);
        }
        else if (rotation == 2){
            a.setLocation(a.x-2,a.y);
            b.setLocation(a.x,a.y-1);
            c.setLocation(a.x+1,a.y-1);
            d.setLocation(a.x+2,a.y-1);
        }
        else if (rotation == 3){
            a.setLocation(a.x,a.y+2);
            b.setLocation(a.x-1,a.y);
            c.setLocation(a.x-1,a.y-1);
            d.setLocation(a.x-1,a.y-2);
        }
        rotate(false);
    }

}