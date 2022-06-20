import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;

/**
 * Test World to showcase and test the methods of TextBox
 * <p>
 * World displays a text box and allows text to be 
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class TestWorld extends World
{
    // Initialize variables and arrays
    private TextBox textBox; // Text box used
    private final int ANIMATION = 6; // Number of frames for typing animation
    private int animationCount; // Animation counter for the text box
    private boolean stopProgram; // A check to see whether the program should stop or not
    private boolean[] commands = new boolean[20]; // A list of booleans to track commands used on the text box
    /**
     * Constructor to initialize a Text Box and other variables
     */
    public TestWorld()
    {    
        // Create a new world with 800x560 cells with a cell size of 1x1 pixels.
        super(800, 560, 1);
        textBox = new TextBox(800, 560, true);
        addObject(textBox, 400, 560/2);
        animationCount = 0;
        stopProgram = false;
        Arrays.fill(commands, false);
    }
    
    /**
     * When program starts, start updating and displaying the output text
     */
    public void started(){
        // Add initial output text
        textBox.addToOutput("Welcome to the Door RPG!\n Type your name and press Enter to begin:\n");
    }
    
    /**
     * In the act method, run through each command that will update the text box accordingly
     */
    public void act(){
        // First command to display starting text
        if (!commands[0]){
            if (!textBox.stopTyping()){
                animateText();
            }
            else {
                commands[0] = true;
            }
        }
        // Second command to read in name
        if (commands[0] && !commands[2]){
            if (!commands[1]){
                textBox.setFinishedPress(false);
                commands[1] = true;
            }
            else {
                textBox.update("enter");
                if (textBox.getFinishedPress()){
                    commands[2] = true;
                }
            }
        }
        // Third command to ask for input
        if (commands[2] && !commands[3]){
            textBox.addToOutput("Hey " + textBox.getLastLine() + "!\n You enter a room with a door.\n A man in front of it."
            + "\nWhat do you say? Press control to finish.\n");
            textBox.update();
            commands[3] = true;
        }
        // Fourth command to get keyboard input
        if (commands[3] && !commands[5]){
            if (!commands[4]){
                textBox.setFinishedPress(false);
                commands[4] = true;
            }
            else {
                textBox.update("control");
                if (textBox.getFinishedPress()){
                    commands[5] = true;
                }
            }
        }
        // Fifth command to check for reaction
        if (commands[5] && !commands[6]){
            if (!textBox.getLastLine().endsWith("\n")){
                textBox.addToOutput("\n");
            }
            boolean win = Greenfoot.getRandomNumber(2) == 1;
            if (win){
                textBox.addToOutput("The man moves aside,\n but tells you to be careful.\n Do you enter the room (Type yes/no)?\n");
            }
            else {
                textBox.addToOutput("The man refuses to move.\n You lose!");
                stopProgram = true;
            }
            commands[6] = true;
        }
        // Sixth command to display reaction
        if (commands[6] && !commands[7]){
            if (!textBox.stopTyping()){
                animateText();
            }
            else {
                commands[7] = true;
            }
        }
        // Stop the program if possible
        if (stopProgram && commands[7]){
            Greenfoot.stop();
        }
        // Seventh command to get multi line keyboard input
        if (commands[7] && !commands[9]){
            if (!commands[8]){
                textBox.setFinishedPress(false);
                commands[8] = true;
            }
            else {
                textBox.update("enter");
                if (textBox.getFinishedPress()){
                    commands[9] = true;
                }
            }
        }
        // Eigth command to calculate result
        if (commands[9] && !commands[10]){
            if(textBox.getLastLine().toLowerCase().contains("yes")){
                textBox.addToOutput("You enter the room and get eaten by a lion. \nYou lose!");
            }
            else {
                textBox.addToOutput("You took the advice and survived. \nYou win!");
            }   
            commands[10] = true;
        }
        // Nineth command to display result
        if (commands[10] && !commands[11]){
            if (!textBox.stopTyping()){
                animateText();
            }
            else {
                commands[11] = true;
                Greenfoot.stop();
            }
        }
    }
    
    /**
     * Perform the typing animation by typing the text every few iterations
     * @param times   number of characters typed or shown at a time
     */
    public void animateText(int times){
        // Increment animation count and simulate user typing
        if (animationCount != 0){
            animationCount--;
        }
        else {
            for (int i = 0; i < times; i++){
                textBox.simulateType();
            }
            animationCount = ANIMATION;
        }
    }
    
    /**
     * Overloated animate function, to animate the text only one character at a time
     */
    public void animateText(){
        animateText(1);
    }
}
