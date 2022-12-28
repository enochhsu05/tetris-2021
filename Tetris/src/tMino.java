public class tMino extends Mino{
       
    public tMino(){
        a.setLocation(0, 5);
        b.setLocation(1, 4);
        c.setLocation(1, 5);
        d.setLocation(1, 6);
        color = 2;
    }
    
 
    public void rotateUp(){
        if (rotation == 0){
            a.setLocation(a.x+1,a.y+1);
            b.setLocation(a.x-1,a.y-1);
            c.setLocation(a.x,a.y-1);
            d.setLocation(a.x+1,a.y-1);
        }
        else if (rotation == 1){
            a.setLocation(a.x+1,a.y-1);
            b.setLocation(a.x-1,a.y-1);
            c.setLocation(a.x-1,a.y);
            d.setLocation(a.x-1,a.y+1);
        }
        else if (rotation == 2){
            a.setLocation(a.x-1,a.y-1);
            b.setLocation(a.x+1,a.y+1);
            c.setLocation(a.x,a.y+1);
            d.setLocation(a.x-1,a.y+1);
        }
        else if (rotation == 3){
            a.setLocation(a.x-1,a.y+1);
            b.setLocation(a.x+1,a.y-1);
            c.setLocation(a.x+1,a.y);
            d.setLocation(a.x+1,a.y+1);
        }
        rotate(true);
    }
    
    public void rotateDown(){
        if (rotation == 0){
            a.setLocation(a.x+1,a.y-1);
            b.setLocation(a.x+1,a.y+1);
            c.setLocation(a.x,a.y+1);
            d.setLocation(a.x-1,a.y+1);
        }
        else if (rotation == 1){
            a.setLocation(a.x-1,a.y-1);
            b.setLocation(a.x+1,a.y-1);
            c.setLocation(a.x+1,a.y);
            d.setLocation(a.x+1,a.y+1);
        }
        else if (rotation == 2){
            a.setLocation(a.x-1,a.y+1);
            b.setLocation(a.x-1,a.y-1);
            c.setLocation(a.x,a.y-1);
            d.setLocation(a.x+1,a.y-1);
        }
        else if (rotation == 3){
            a.setLocation(a.x+1,a.y+1);
            b.setLocation(a.x-1,a.y-1);
            c.setLocation(a.x-1,a.y);
            d.setLocation(a.x-1,a.y+1);
        }
        rotate(false);
    }
}
