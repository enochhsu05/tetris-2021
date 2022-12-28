import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import javax.swing.*;
import java.util.ArrayList;

public class Frame extends JPanel implements KeyListener{
    
    public static final int gridLength = 20; //# of units high
    public static final int gridWidth = 10; //$ of units wide
    public static Color[] colors = {Color.BLACK, Color.WHITE, Color.magenta, Color.yellow, Color.cyan, Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE,Color.darkGray, Color.LIGHT_GRAY};
    public static ArrayList<Mino> past = new ArrayList<Mino>(); //stores all single blocks on the screen
    public static ArrayList<Mino> present = new ArrayList<Mino>(); //stores the piece the user is controlling
    public static ArrayList<Mino> future = new ArrayList<Mino>(); //stores the future pieces that will spawn
    public static Mino ghost = new Mino();
    public static Mino hold;
    public static Mino temp;
    public static boolean held = false;
    public static boolean doubleHeld = false;
    public static boolean start = true;
    public static boolean skip = false;
    public static boolean stop = false;
    public static boolean change = false;
    public static int lines; //lines cleared
    public static int level; //goes up based on lines cleared; determines the speed
    public static int score; //points awarded for soft/hard-dropping and clearing lines
    public static int choice = 0; //choice of mode
    public static String key = "";
    public static BigDecimal time = new BigDecimal("0"); //timer
    
    
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && key.equals("right")) //if the key is no longer held down
            key = "stop";
        if (e.getKeyCode() == KeyEvent.VK_LEFT && key.equals("left"))
            key = "stop";
    } 
    public void keyTyped(KeyEvent e){ }
    public void keyPressed(KeyEvent e) {
        if (choice == 0){ //to select which mode
            if (e.getKeyCode() == KeyEvent.VK_S)
                choice = 2; //survive the constant garbage lines
            else if (e.getKeyCode() == KeyEvent.VK_I)
                choice = 3; //challenge your memory since the minos turn invisible after placement
            else if (e.getKeyCode() == KeyEvent.VK_B)
                choice = 4; //survive the constant bomb lines
            else if (e.getKeyCode() == KeyEvent.VK_P)
                choice = 5; //have fun with the various effects possible in party mode
            else
                choice = 1; //clear lines and try to get to level 10
        }
        else if (!stop){ //if the current piece hard-dropped, the player can control it
            if (e.getKeyCode() == KeyEvent.VK_UP){
                if (rotateCheck(true)){ //it will rotate if it passes the rotate check
                    present.get(0).rotateUp();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_Z){
                if (rotateCheck(false)){
                    present.get(0).rotateDown();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                key = "right"; //changing the string 'key' causes the current piece to move right when it is called in the run() method in the Mino class
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                key = "left";
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN){
                if (!isBottom(0)){ //moves the current piece down if it's not already at the bottom
                    present.get(0).moveDown();
                    score++;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE){
                drop(); //hard-drops the piece
            }
            if (e.getKeyCode() == KeyEvent.VK_C){
                if (!doubleHeld){ //holds the current piece if the player didn't already use hold on the same turn
                    if (!held) //the first time is different from the subsequent uses since it doesn't switch the current piece with anything
                        hold();
                    else
                        release();
                }
            }
        }
    }
    
    public static boolean isOut(int x){ //checks if any part of the present piece is out of bounds
        if ((present.get(x)).a.getY() > 9
           || (present.get(x)).b.getY() > 9
           || (present.get(x)).c.getY() > 9
           || (present.get(x)).d.getY() > 9
           || (present.get(x)).a.getY() < 0
           || (present.get(x)).b.getY() < 0
           || (present.get(x)).c.getY() < 0
           || (present.get(x)).d.getY() < 0
           || (present.get(x)).a.getX() > 19 
           || (present.get(x)).b.getX() > 19
           || (present.get(x)).c.getX() > 19
           || (present.get(x)).d.getX() > 19){
           return true;
        }
        else if (overlap((present.get(x)).a.x,(present.get(x)).a.y) //checks if any part of the current piece overlaps with an already existing piece
            || overlap((present.get(x)).b.x,(present.get(x)).b.y)
            || overlap((present.get(x)).c.x,(present.get(x)).c.y)
            || overlap((present.get(x)).d.x,(present.get(x)).d.y)){
            return true;
        }
        return false;
        
    }
    
    public static boolean overlap(int x, int y){ //checks if the location given by the parameters is already occupied
        try{
            for (int k = 0; k < past.size(); k++){
                if (past.get(k).a.x == x && past.get(k).a.y == y){
                    return true;
                }
            }
        }catch(NullPointerException e){
            System.out.println("Nullpointer in overlap()");
        }
        return false;
    }
    
    public static boolean isBottom(int x){ //checks if there is anything below the current piece
        try{
            if ((present.get(x)).a.getX() == 19 
               || (present.get(x)).b.getX() == 19
               || (present.get(x)).c.getX() == 19
               || (present.get(x)).d.getX() == 19){
                return true;
            }
            else if (overlap((present.get(x)).a.x+1,(present.get(x)).a.y) //checks if any part of the current piece will overlap with anything were it to be moved down by 1
                || overlap((present.get(x)).b.x+1,(present.get(x)).b.y)
                || overlap((present.get(x)).c.x+1,(present.get(x)).c.y)
                || overlap((present.get(x)).d.x+1,(present.get(x)).d.y)){
                return true;
            }
        }catch(Exception e){
            System.out.println("Exception in isBottom()");
        }
        return false;
    }
    
    public static boolean belowBomb(int x, int y){ //checks if there is a bomb right below the current piece
        for (int k = 0; k < past.size(); k++){
            if (past.get(k).a.x == x+1 && past.get(k).a.y == y && past.get(k).special == 1){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isRight(int x){ //checks if there is anything to the right of the current piece
        try{
        if (present.size()==x+1
           && (present.get(x)).a.getY() == 9
           || (present.get(x)).b.getY() == 9
           || (present.get(x)).c.getY() == 9
           || (present.get(x)).d.getY() == 9){
            return true;
        }
        else if (present.size()==x+1
            &&  side((present.get(x)).a.x,(present.get(0)).a.y,true)
            || side((present.get(x)).b.x,(present.get(0)).b.y,true)
            || side((present.get(x)).c.x,(present.get(0)).c.y,true)
            || side((present.get(x)).d.x,(present.get(0)).d.y,true)){
            return true;
        }
        }catch(Exception e){
            System.out.println("Exception in isRight");
        }
        return false;
    }
    
    public static boolean side (int x, int y, boolean side){ //checks if there is anything to the left or right (determined by boolean side) of the given coordinate
        for (int k = 0; k < past.size(); k++){
            if (side && past.get(k).a.x == x && past.get(k).a.y == y+1){
                return true;
            }
            else if (!side && past.get(k).a.x == x && past.get(k).a.y == y-1){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isLeft(int x){ //same as isRight()
        try{
            if (present.size()==x+1
                && (present.get(x)).a.getY() == 0
               || (present.get(x)).b.getY() == 0
               || (present.get(x)).c.getY() == 0
               || (present.get(x)).d.getY() == 0){
                return true;
            }
            else if (present.size()==x+1
                && side((present.get(x)).a.x,(present.get(0)).a.y,false)
                || side((present.get(x)).b.x,(present.get(0)).b.y,false)
                || side((present.get(x)).c.x,(present.get(0)).c.y,false)
                || side((present.get(x)).d.x,(present.get(0)).d.y,false)){
                return true;
            }
        }catch(Exception e){
            System.out.println("Exception in isLeft");
        }
        return false;
    }

    public static void clearRows(){ //clears filled rows and assigns points
        int count = 0; //counts how many rows were cleared
        for (int k = 0; k < gridLength; k++){
            ArrayList<Integer> row = new ArrayList<Integer>(); //create an ArrayList for each row
            for (int v = 0; v < gridWidth; v++){
                for (int j = 0; j < past.size(); j++){ //aside from bombs, add all minos in the row to the ArrayList
                    try{
                        if (!(past.get(j).special == 1) && past.get(j).a.x == k && past.get(j).a.y == v){
                            row.add(j); 
                        }
                    }catch(NullPointerException e){ 
                        System.out.println("NullPointerException in ClearRows");
                    }
                }
            }
            if (row.size() == 10){ //if there are 10 elements in the ArrayList, it is full
                count++;
                lines++;
                for (int j = 0; j < 10; j++){
                    if (past.get((int)(row.get(j))).special == 2 && choice == 5){ //checks if any of the cleared minos were a special mino
                        change = true;
                    }
                    past.set((int)row.get(j), null); //set all minos in the to-be-cleared row to null
                }
                for (int j = 0; j < past.size(); j++){ //removes all minos set to null
                    if (past.get(j)==null){
                        past.remove(j);
                        j--;
                    }
                }
                for (int j = 0; j < past.size(); j++){ //makes all minos above the cleared row to move down
                    if (past.get(j).a.x < k){
                        past.get(j).a.x++;
                    }
                }
            }
        }
        if (count == 4){ //assigns points
            score+=level*1000;
        }
        else if (count == 3){
            score+=level*300;
        }
        else if (count == 2){
            score+=level*100;
        }
        else if (count == 1){
            score+=level*40;
        }
        if (change){
            effect(); //if one of the cleared minos were special, make an effect happen
        }
        change = false;
    }
    
    public static void explode(){ //checks if any of the bombs were detonated and clears the row
        int temp = 0;
        if (belowBomb((present.get(0)).a.x,(present.get(0)).a.y) //checks if any bombs were detonated
            || belowBomb((present.get(0)).b.x,(present.get(0)).b.y)
            || belowBomb((present.get(0)).c.x,(present.get(0)).c.y)
            || belowBomb((present.get(0)).d.x,(present.get(0)).d.y)){
            temp = Math.max(present.get(0).a.x, Math.max(present.get(0).b.x, Math.max(present.get(0).c.x, present.get(0).d.x))); //sets temp to the lowest point of the current piece, since ethat is what caused the detonation
            for (Mino m: past){ //sets all minos in the row to null
                if (m.a.x == temp+1){
                    past.set(past.indexOf(m),null);
                }
            }
            for (int j = 0; j < past.size(); j++){ //removes all minos set to null
                    if (past.get(j)==null){
                        past.remove(j);
                        j--;
                    }
                }
            for (Mino m: past){ //makes all minos above the cleared row move down
                if (m.a.x < temp+1){
                    m.a.x++;
                }
            }
            present.get(0).a.x++; //moves the current piece down
            present.get(0).b.x++;
            present.get(0).c.x++;
            present.get(0).d.x++;
        }
    }
    
    public static void effect(){
        int temp = (int)(Math.random()*5); //causes a random effect from the 5 options to trigger
        if (temp == 0){ //removes the bottom 4 rows
            for (int k = 0; k < past.size(); k++){
                if (past.get(k).a.x > 15){
                    past.set(k, null);
                }
            }
            for (int k = 0; k < past.size(); k++){
                if (past.get(k) == null){
                    past.remove(k);
                    k--;
                }else{
                    past.get(k).a.x+=4;
                }
            }
        }
        else if (temp == 1){ //pushes all minos to the bottom
            for (int k = 0; k < gridWidth; k++){
                ArrayList<Mino> col = new ArrayList<Mino>();
                int count = 0;
                for (int v = 0; v < gridLength; v++){
                    for (Mino m: past){
                        if (m.a.x == v && m.a.y == k){
                            col.add(m);
                            count++;
                        }
                    }
                }
                for (int v = 0; v < count; v++){
                    past.get(past.indexOf(col.get(count-v-1))).a.x = 19-v;
                }
            }
        }
        else if (temp == 2){ //creates 4 garbage lines at the bottom
            garbage();
            garbage();
            garbage();
            garbage();
        }
        else if (temp == 3){ //scrambles the top 4 rows with minos
            int max = 19;
            for (Mino m: past){
                max = Math.min(max, m.a.x);
            }
            for (int k = max; k < max+4; k++){
                ArrayList<Mino> row = new ArrayList<Mino>();
                for (Mino m: past){
                    if (m.a.x == k){
                        row.add(m);
                    }
                }
                ArrayList<Integer> ran = new ArrayList<Integer>();
                for (int v = 0; v < 10; v++){
                    ran.add(v);
                }
                for (int j = 0; j < row.size(); j++){
                    int rand = (int)(Math.random()*ran.size());
                    past.get(past.indexOf(row.get(j))).a.y = ran.get(rand);
                    ran.remove(rand);
                }
            }
        }
        else if (temp == 4){ //pushes all minos to the right
            for (int k = 0; k < gridLength; k++){
                ArrayList<Mino> col = new ArrayList<Mino>();
                int count = 0;
                for (int v = 0; v < gridWidth; v++){
                    for (Mino m: past){
                        if (m.a.x == k && m.a.y == v){
                            col.add(m);
                            count++;
                        }
                    }
                }
                for (int v = 0; v < count; v++){
                    past.get(past.indexOf(col.get(count-v-1))).a.y = 9-v;
                }
            }
        }
    }
    
    public static void drop(){ //hard-drops the current piece
        stop = true;
        while(!isBottom(0)){ //finds the lowest available space for the current piece to drop to
            present.get(0).moveDown();
            score+=2;
        }
    }
    
    public static void hold(){ //holds the current piece
        held = true;
        if (present.get(0) instanceof tMino) //changes 'hold' to the current piece
            hold = new tMino();
        else if (present.get(0) instanceof iMino)
            hold = new iMino();
        else if (present.get(0) instanceof sMino)
            hold = new sMino();
        else if (present.get(0) instanceof zMino)
            hold = new zMino();
        else if (present.get(0) instanceof jMino)
            hold = new jMino();
        else if (present.get(0) instanceof lMino)
            hold = new lMino();
        else if (present.get(0) instanceof oMino)
            hold = new oMino();
        hold.special = present.get(0).special;
        skip = true;
        doubleHeld = true;
        start = false;
    }
    
    public static void release(){ //switches the current piece with the held piece
        Mino temp = new Mino();
        if (present.get(0) instanceof tMino)
            temp = new tMino();
        else if (present.get(0) instanceof iMino)
            temp = new iMino();
        else if (present.get(0) instanceof sMino)
            temp = new sMino();
        else if (present.get(0) instanceof zMino)
            temp = new zMino();
        else if (present.get(0) instanceof jMino)
            temp = new jMino();
        else if (present.get(0) instanceof lMino)
            temp = new lMino();
        else if (present.get(0) instanceof oMino)
            temp = new oMino();
        temp.special = present.get(0).special;
        present.set(0, hold);
        hold = temp;
        doubleHeld = true;
    }
    
    public static void ghost(){
        if (present.size() == 1){ //copies all aspects of the current piece to the ghost
        ghost.a.x = present.get(0).a.x;
        ghost.b.x = present.get(0).b.x;
        ghost.c.x = present.get(0).c.x;
        ghost.d.x = present.get(0).d.x;
        ghost.a.y = present.get(0).a.y;
        ghost.b.y = present.get(0).b.y;
        ghost.c.y = present.get(0).c.y;
        ghost.d.y = present.get(0).d.y;
        present.add(ghost);
        while(!isBottom(1)){ //moves the ghost to the lowest possible location
            ghost.moveDown();
        }
        present.remove(ghost);
        }
    }
    
    public static void callUpDown(boolean up){ //makes 'temp' rotate either up or down
        if (up){
            temp.rotateUp();
        }else{
            temp.rotateDown();
        }
    }
    
    public static boolean rotateCheck(boolean up){ //checks where the current piece should rotate to
        if (present.get(0) instanceof tMino) //makes 'temp' an exact replica of the current piece
            temp = new tMino();
        else if (present.get(0) instanceof iMino)
            temp = new iMino();
        else if (present.get(0) instanceof sMino)
            temp = new sMino();
        else if (present.get(0) instanceof zMino)
            temp = new zMino();
        else if (present.get(0) instanceof jMino)
            temp = new jMino();
        else if (present.get(0) instanceof lMino)
            temp = new lMino();
        else
            temp = new oMino();
        temp.a.x = present.get(0).a.x;
        temp.b.x = present.get(0).b.x;
        temp.c.x = present.get(0).c.x;
        temp.d.x = present.get(0).d.x;
        temp.a.y = present.get(0).a.y;
        temp.b.y = present.get(0).b.y;
        temp.c.y = present.get(0).c.y;
        temp.d.y = present.get(0).d.y;
        temp.rotation = present.get(0).rotation;
        present.add(temp);
        callUpDown(up);
        if (isOut(1)){ //checks whether rotating 'temp' would cause it to be out of bounds, then slightly adjusts 'temp''s location to see which one works
            callUpDown(!up);
            temp.a.x++;
            callUpDown(up);
            if (isOut(1)){
                callUpDown(!up);
                temp.a.x++;
                callUpDown(up);
                if (isOut(1)){
                    callUpDown(!up);
                    temp.a.x--;
                    temp.a.y--;
                    callUpDown(up);
                    if (isOut(1)){
                        callUpDown(!up);
                        temp.a.y+=2;
                        callUpDown(up);
                        if (isOut(1)){
                            callUpDown(!up);
                            temp.a.x-=2;
                            temp.a.y--;
                            callUpDown(up);
                            if (isOut(1)){
                                callUpDown(!up);
                                temp.a.x--;
                                callUpDown(up);
                                if (isOut(1)){
                                    callUpDown(!up);
                                    temp.a.x+=2;
                                    temp.a.y++;
                                    callUpDown(up);
                                    if (isOut(1)){
                                        callUpDown(!up);
                                        temp.a.y++;
                                        callUpDown(up);
                                        if (isOut(1)){
                                            callUpDown(!up);
                                            temp.a.y++;
                                            callUpDown(up);
                                            if (isOut(1)){
                                                callUpDown(!up);
                                                temp.a.y-=4;
                                                callUpDown(up);
                                                if (isOut(1)){
                                                    callUpDown(!up);
                                                    temp.a.y--;
                                                    callUpDown(up);
                                                    if (isOut(1)){
                                                        callUpDown(!up);
                                                        temp.a.y--;
                                                        callUpDown(up);
                                                        if (isOut(1)){
                                                            present.remove(temp);
                                                            return false;
                                                        }else{
                                                            present.get(0).a.y-=3;
                                                        }
                                                    }else{
                                                        present.get(0).a.y-=2;
                                                    }
                                                }else{
                                                    present.get(0).a.y--;
                                                }
                                            }else{
                                                present.get(0).a.y+=3;
                                            }
                                        }else{
                                            present.get(0).a.y+=2;
                                        }
                                    }else{
                                        present.get(0).a.y++;
                                    }
                                }else{
                                    present.get(0).a.x-=2;
                                }
                            }else{
                                present.get(0).a.x--;
                            }
                        }else{
                            present.get(0).a.x++;
                            present.get(0).a.y++;
                        }
                    }else{
                        present.get(0).a.x++;
                        present.get(0).a.y--;
                    }
                }else{
                    present.get(0).a.x+=2;
                }
            }else{ //if it was able to rotate, change the current piece to have the adjustment 'temp' had
                present.get(0).a.x++;
            }
        }
        present.remove(temp);
        return true;
    }
    
    public static void garbage(){ //creates a garbage line
        if (isBottom(0)) { //if there is something below the current piece, move it up
            present.get(0).a.x--;
            present.get(0).b.x--;
            present.get(0).c.x--;
            present.get(0).d.x--;
        }
        for (Mino m: past){ //move all minos on the field upwards to give space for the garbage line
            m.a.x--;
        }
        for (int k = 0; k < gridWidth; k++){ //creates a garbage line at the bottom
            past.add(new Mino(colors.length-1,19,k));
        }
        past.remove((int)(Math.random()*10)+past.size()-10); //removes one mino of the row to enable it to be cleared
    }
    
    public static void bomb(){ //creates a bomb line
        if (isBottom(0)) { //if something is below, move the current piece up 
            present.get(0).a.x--;
            present.get(0).b.x--;
            present.get(0).c.x--;
            present.get(0).d.x--;
        }
        past.forEach(m -> { //move all minos up by one to give space for the new row
            m.a.x--;
        });
        for (int k = 0; k < gridWidth; k++){ //creates the new row
            past.add(new Mino(colors.length-1,19,k));
        }
        past.get((int)(Math.random()*10)+past.size()-10).special = 1; //makes one of the minos in the row the bomb
    }
    
    @Override
    public void paint(Graphics g) { //repaint() can only be called in this class or by using frame. in the runner
        //super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(colors[0]);
        g2d.fillRect(0+160,0,300,635);
        g2d.setColor(colors[9]);
        g2d.fillRect(300+160,0,160,635);
        g2d.fillRect(0,0,160,635);
        g2d.setColor(colors[1]);
        if (choice != 2 && choice != 4){
            g2d.drawString("Level: " + level, 10, 20);
            g2d.drawString("Score: " + score, 10, 40);
        }
        if (choice != 1 && choice != 3 && choice != 5){
            g2d.drawString("Time: " + time, 10, 60);
        }
        if (choice == 0){ //end/start screen
            g2d.drawString("Press 's' for survival, 'i' for invisible, 'b' for bombs,", 160+20 ,250);
            g2d.drawString(", 'p' for party, and anything else for classic", 160+33 ,270);
        }else{
            if (choice != 3){ //if it isn't invisible mode, draw all the minos on the field
                for (int k = 0; k < past.size(); k++){
                    g2d.setColor(colors[past.get(k).color]);
                    if ((past.get(k).special == 1 && choice == 4) || (past.get(k).special == 2 && choice == 5))
                        g2d.setColor(colors[1]);
                    g2d.fillRect(past.get(k).a.y*30+160,past.get(k).a.x*30,30,30);
                }
            }
            g2d.setColor(colors[1]);
            g2d.drawRect(310+160, 20, 140, 440);
            g2d.drawRect(10,70,140,80);
            for (int k = 0; k < future.size(); k++){ //draws all the future pieces
                g2d.setColor(colors[future.get(k).color]);
                g2d.fillRect((int)(future.get(k)).a.getY()*30+230+160,(int)(future.get(k)).a.getX()*30+30+k*90,30,30);
                g2d.fillRect((int)(future.get(k)).b.getY()*30+230+160,(int)(future.get(k)).b.getX()*30+30+k*90,30,30);
                g2d.fillRect((int)(future.get(k)).c.getY()*30+230+160,(int)(future.get(k)).c.getX()*30+30+k*90,30,30);
                if (future.get(k).special == 2 && choice == 5){
                    g2d.setColor(colors[1]);
                }
                g2d.fillRect((int)(future.get(k)).d.getY()*30+230+160,(int)(future.get(k)).d.getX()*30+30+k*90,30,30);
            }
            if (held){ //draws the held piece
                g2d.setColor(colors[hold.color]);
                g2d.fillRect((int)(hold).a.getY()*30-70,(int)(hold).a.getX()*30+80,30,30);
                g2d.fillRect((int)(hold).b.getY()*30-70,(int)(hold).b.getX()*30+80,30,30);
                g2d.fillRect((int)(hold).c.getY()*30-70,(int)(hold).c.getX()*30+80,30,30);
                if (hold.special == 2 && choice == 5){
                    g2d.setColor(colors[1]);
                }
                g2d.fillRect((int)(hold).d.getY()*30-70,(int)(hold).d.getX()*30+80,30,30);
            }
            g2d.setColor(new Color(255,255,255,100)); //draws the ghost
            g2d.fillRect((int)ghost.a.getY()*30+160,(int)ghost.a.getX()*30,30,30);
            g2d.fillRect((int)ghost.b.getY()*30+160,(int)ghost.b.getX()*30,30,30);
            g2d.fillRect((int)ghost.c.getY()*30+160,(int)ghost.c.getX()*30,30,30);
            g2d.fillRect((int)ghost.d.getY()*30+160,(int)ghost.d.getX()*30,30,30);
            
            try{
                g2d.setColor(colors[present.get(0).color]); //draws the current piece
                g2d.fillRect((int)(present.get(0)).a.getY()*30+160,(int)(present.get(0)).a.getX()*30,30,30);
                g2d.fillRect((int)(present.get(0)).b.getY()*30+160,(int)(present.get(0)).b.getX()*30,30,30);
                g2d.fillRect((int)(present.get(0)).c.getY()*30+160,(int)(present.get(0)).c.getX()*30,30,30);
                if (present.get(0).special == 2 && choice == 5){
                    g2d.setColor(colors[1]);
                }
                g2d.fillRect((int)(present.get(0)).d.getY()*30+160,(int)(present.get(0)).d.getX()*30,30,30);
            }catch(Exception e){
                System.out.println("Exception in paint");
            }
        }
    }
}