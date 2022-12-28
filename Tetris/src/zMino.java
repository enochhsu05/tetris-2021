public class zMino extends Mino{
       
    public zMino(){
        a.setLocation(0, 4);
        b.setLocation(0, 5);
        c.setLocation(1, 5);
        d.setLocation(1, 6);
        color = 6;
    }
    
    public void rotateUp(){
        if (rotation == 0){
            a.setLocation(a.x+1,a.y+1);
            b.setLocation(a.x-1,a.y);
            c.setLocation(a.x-1,a.y+1);
            d.setLocation(a.x-2,a.y+1);
        }
        else if (rotation == 1){
            a.setLocation(a.x-1,a.y+1);
            b.setLocation(a.x,a.y-1);
            c.setLocation(a.x-1,a.y-1);
            d.setLocation(a.x-1,a.y-2);
        }
        else if (rotation == 2){
            a.setLocation(a.x-1,a.y-1);
            b.setLocation(a.x+1,a.y);
            c.setLocation(a.x+1,a.y-1);
            d.setLocation(a.x+2,a.y-1);
        }
        else if (rotation == 3){
            a.setLocation(a.x+1,a.y-1);
            b.setLocation(a.x,a.y+1);
            c.setLocation(a.x+1,a.y+1);
            d.setLocation(a.x+1,a.y+2);
        }
        rotate(true);
    }
    
     public void rotateDown(){
        if (rotation == 0){
            a.setLocation(a.x-1,a.y+1);
            b.setLocation(a.x+1,a.y);
            c.setLocation(a.x+1,a.y-1);
            d.setLocation(a.x+2,a.y-1);
        }
        else if (rotation == 1){
            a.setLocation(a.x-1,a.y-1);
            b.setLocation(a.x,a.y+1);
            c.setLocation(a.x+1,a.y+1);
            d.setLocation(a.x+1,a.y+2);
        }
        else if (rotation == 2){
            a.setLocation(a.x+1,a.y-1);
            b.setLocation(a.x-1,a.y);
            c.setLocation(a.x-1,a.y+1);
            d.setLocation(a.x-2,a.y+1);
        }
        else if (rotation == 3){
            a.setLocation(a.x+1,a.y+1);
            b.setLocation(a.x,a.y-1);
            c.setLocation(a.x-1,a.y-1);
            d.setLocation(a.x-1,a.y-2);
        }
        rotate(false);
    }

}