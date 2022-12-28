
import java.math.BigDecimal;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

public class Runner extends TimerTask{
    
    private static JFrame frame = new JFrame("Tetris");
    private static ArrayList<Mino> bag = new ArrayList<Mino>();
    
    public void run(){ //called by a scheduled timer
        if (Frame.choice != 0){ //has the timer running when a game is going on
            Frame.time = Frame.time.add(new BigDecimal("0.01"));
            Frame.clearRows();
            Frame.ghost();
        }
        frame.repaint();
    }
    
    public static void main(String[] args) throws InterruptedException{
        Frame tFrame = new Frame();
        frame.add(tFrame); 
        frame.setBounds(10,-10,Frame.gridWidth*30+15+160+160, Frame.gridLength*30+35);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to stop the run when the frame is closed
        frame.setVisible(true);
        frame.setFocusable(true); //this and the line below are needed for the arrows keys to work... probably?
        frame.addKeyListener(tFrame);
        
        Timer t = new Timer();
        t.schedule(new Runner(), 0, 10); //goes into the run method of "new Runner()" and runs it every "10" millisecond with "0" initial
        t.schedule(new Mino(), 0, 10);
        t.schedule(new iMino(), 0, 3000);
        
        for (int k = 0; k < 5; k++){ //adding the initial 5 minos into the arrayList "future"
            addMino(); 
        }
        
        while(true){
            Frame.present.clear(); 
            Frame.hold = null; //resets the held piece
            Frame.held = false;
            waiting(); //waits for the player to choose a mode
            Frame.lines = 0; //resets lines, score, and time
            Frame.score = 0;
            Frame.time = new BigDecimal("0");
            do{
                Frame.level = 1+Frame.lines/10; //starts at 1, increases by 1 every 10 lines cleared
                Frame.present.clear(); //clears present to create space for the new one
                Frame.present.add(Frame.future.get(0)); //adds the first Mino in future to present
                Frame.future.remove(0); //removes the Mino in present from future
                addMino(); //adds a new mino to future
                Frame.stop = false;
                Frame.skip = false;
                if (Frame.start) //to prevent doubleheld from deactivating the first time a piece is held
                    Frame.doubleHeld = false;
                else
                    Frame.start = true;
                while (!Frame.isBottom(0) && !Frame.skip){ //added outer loop for the event that a piece is held after it already got to the bottom
                    while (!Frame.isBottom(0) && !Frame.skip){ //continuously moves the present piece down while it's not at the bottom, with a delay in between
                        Frame.present.get(0).moveDown();
                        for (int k = 0; k < 10; k++){
                            if (!Frame.stop && !Frame.skip)
                                java.util.concurrent.TimeUnit.MILLISECONDS.sleep((400/10)+2-(4*Frame.level)); //the delay is less the higher the level
                        }
                    }
                    if (!Frame.stop && !Frame.skip){ //added delay for pieces that weren't held or hard-dropped
                        java.util.concurrent.TimeUnit.MILLISECONDS.sleep(500); 
                    }
                }
                if (Frame.choice == 4) //if the mode is bombs, check if something detonated
                    Frame.explode();
                if (!Frame.skip){ //convert present to past as long as it wasn't skipped
                    changeMino(Frame.present.get(0));
                }
                if (Frame.past.isEmpty() && Frame.score != 0) //award points for perfoect clears
                    Frame.score+=3000*Frame.level;
            }while(!Up()); //while it's not topped out
            System.out.println((400/10)+2-(4*Frame.level));
            java.util.concurrent.TimeUnit.SECONDS.sleep(1);
            Frame.choice = 0; //resets the score and clears the board
            Frame.past.clear();
        }
    }
    
    private static void waiting() throws InterruptedException{ //stalls until the player chooses a mode
        while (Frame.choice == 0){
            java.util.concurrent.TimeUnit.MILLISECONDS.sleep(100); //delays until the player presses "start"
        }
    }
    
    private static boolean Up(){ //checks if the current mino is at the very top
        if (Frame.present.get(0).a.x <= 0
            || Frame.present.get(0).b.x <= 0
            || Frame.present.get(0).c.x <= 0
            || Frame.present.get(0).d.x <= 0){
            return true;
        }
        return false;
    }
    
    private static void fillBag(){ //fills the bag with 2 of every type of mino
        while (bag.size() < 8){
            bag.add(new tMino());
            bag.add(new oMino());
            bag.add(new iMino());
            bag.add(new sMino());
            bag.add(new zMino());
            bag.add(new lMino());
            bag.add(new jMino());
        }
    }
    
    private static void addMino(){ //adds a new mino to future
        if (bag.isEmpty()){ //fills the bag if it's empty
            fillBag();
        }
        int x = (int)(Math.random()*bag.size());
        Frame.future.add(bag.get(x)); //adds a random mino from the bag to future
        if ((int)(Math.random()*10)==0 && Frame.choice == 5){ //has a chance to make the new mino special if it's in party mode
            Frame.future.get(Frame.future.size()-1).special = 2;
        }
        bag.remove(x); //remove the added mino from the bag
    }
    
    private static void changeMino(Mino m){ //converts the tetramino to 4 individual minos and adds them to 'past'
        Frame.past.add(new Mino(m.color,m.a.x,m.a.y));
        Frame.past.add(new Mino(m.color,m.b.x,m.b.y));
        Frame.past.add(new Mino(m.color,m.c.x,m.c.y));
        Frame.past.add(new Mino(m.color,m.d.x,m.d.y));
        if (Frame.present.get(0).special == 2){
            Frame.past.get(Frame.past.size()-1).special = 2;
        }
    }
    
}
