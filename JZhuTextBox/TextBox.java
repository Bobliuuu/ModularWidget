import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList; // Import ArrayLists
import java.util.Arrays; // Import Arrays methods

/**
 * A modular widget that can get input from user (if needed) or take string output and display it to the World using a text box.
 * This widget can be customized to show text of any width and height, with any specified background and text colour. 
 * It can be of any font size and font type, and be centered or left aligned, and can have any amount of initial text. 
 * Displayed text can be either added on all in one go, or slowly updated using the curOutput variable. 
 * MaxLines is initialized so that the text lines do not exceed the borders of the text box. 
 * Note: Some values must be experimented with, meaning there is no definite value for them. 
 * Certain values indicated in the constructors (character size and height) will change when the font changes. 
 * <p>
 * Constructors: 
 * This class aims to be as flexible as possible, allowing it to have simple constructors (0 or 2 parameters), 
 * all the way to a fully customizable text box with 12 parameters
 * However, more complex constructors will require a more advanced knowledge of the text displayed in the text box and parameters
 * By allowing customizable changes, it allows the class to become modular
 * <p>
 * Implementation: 
 * If wanting to implement a simple textbox, it is highly recommended to use the simple constructors. 
 * Otherwise, use the longer customizable constructors. 
 * <p>
 * Methods: 
 * Methods can be used to update text, parse text, display text, get lines of text, etc. 
 * See each method for its corresponding documentation. 
 * Update method is overloaded depending on the parameters used (see method for details). 
 * GetStringWidth method is very CPU intensive, so use the centering text methods sparingly. 
 * <p>
 * Notes: 
 * When using typing animation methods of text box, curOutput changes while lastOutput is stored and outputText remains the same. 
 * Centering text may be memory intensive, so use that method sparingly. 
 * For simulating typing commands, make sure the current output and text output are the same before continuing to the next command 
 * (use the stopTyping method).
 * 
 * @author Jerry Zhu, Jordan Cohen
 * @version 1
 */

// Class definition, extends Actor methods
public class TextBox extends Actor
{
    // Initialize objects
    private GreenfootImage textBox;
    private Color backgroundColor;
    private Color textColor;
    private Font textFont;
    // Initialize ArrayLists
    private ArrayList <String> textC;
    private ArrayList <String> textO;
    // Initialize variables
    private int width;
    private int centeredY;
    private int maxLines;
    private int fontSize;
    private boolean finishedPress;
    private boolean centered;
    private String font;
    private String rawInput;
    private String outputText;
    private String curOutput;
    private String lastOutput;
    
    /**
     * Main constructor - A basic constructor that sets default values; easy to use but not very flexible.
     * <p>
     * Creates a TextBox of white text on a black background that takes up an 800 by 560 pixels screen, 
     * with size 25 Times New Roman font. 
     */
    public TextBox(){
        this(800, 560, 0, 0, 0, 255, 255, 255, 25, "Times New Roman", false, "");
    }
    
    /**
     * Similar to above, but with the ability to customize width and height. 
     * @param width     the width of the text box
     * @param height    the height of the text box
     */
    public TextBox(int width, int height){
        this(width, height, 0, 0, 0, 255, 255, 255, 25, "Times New Roman", false, "");
    }
    
    /**
     * Similar to above, but with the ability to customize left or center alignment. 
     * <p>
     * Note: Use this constructor sparingly, as centering text is very CPU intensive. 
     * @param width     the width of the text box
     * @param height    the height of the text box
     * @param centered  whether the text is center aligned
     */
    public TextBox(int width, int height, boolean centered){
        this(width, height, 0, 0, 0, 255, 255, 255, 25, "Times New Roman", true, "");
    }
    
    /**
     * Similar to above, but with the ability to customize background and text colour, with left alignment. 
     * <p>
     * Note: Make sure that the background colour complements the text colour
     * @param width     the width of the text box
     * @param height    the height of the text box
     * @param bgR       red parameter of the background colour
     * @param bgG       green parameter of the background colour
     * @param bgB       blue parameter of the background colour
     * @param txtR      red parameter of the text colour
     * @param txtG      green parameter of the text colour
     * @param txtB      blue parameter of the text colour
     */
    public TextBox(int width, int height, int bgR, int bgG, int bgB, int txtR, int txtG, int txtB){
        this(width, height, bgR, bgG, bgB, txtR, txtG, txtB, 25, "Times New Roman", false, "");
    }
    
    /**
     * Similar to above, but with the ability to customize font type and font size. 
     * <p>
     * Note: Make sure that the background colour complements the text colour
     * @param width     the width of the text box
     * @param height    the height of the text box
     * @param bgR       red parameter of the background colour
     * @param bgG       green parameter of the background colour
     * @param bgB       blue parameter of the background colour
     * @param txtR      red parameter of the text colour
     * @param txtG      green parameter of the text colour
     * @param txtB      blue parameter of the text colour
     * @param fontSize  the font size of the text
     * @param Font      the specified font of the text box
     */
    public TextBox(int width, int height, int bgR, int bgG, int bgB, int txtR, int txtG, int txtB, int fontSize, String Font){
        this(width, height, bgR, bgG, bgB, txtR, txtG, txtB, fontSize, Font, false, "");
    }
    
    /**
     * Similar to above, but with the ability to customize center or left alignment. 
     * <p>
     * Note: Use this constructor sparingly, as centering text is very CPU intensive. 
     * <p>
     * Note: Make sure that the background colour complements the text colour
     * @param width     the width of the text box
     * @param height    the height of the text box
     * @param bgR       red parameter of the background colour
     * @param bgG       green parameter of the background colour
     * @param bgB       blue parameter of the background colour
     * @param txtR      red parameter of the text colour
     * @param txtG      green parameter of the text colour
     * @param txtB      blue parameter of the text colour
     * @param fontSize  the font size of the text
     * @param Font      the specified font of the text box
     * @param centered  whether the text is center aligned
     */
    public TextBox(int width, int height, int bgR, int bgG, int bgB, int txtR, int txtG, int txtB, int fontSize, String Font, 
    boolean centered){
        this(width, height, bgR, bgG, bgB, txtR, txtG, txtB, fontSize, Font, centered, "");
    }
    
    /**
     * The most detailed constructor, adding the ability to initialize initial text, as well as incorporating 
     * all the other parameters. 
     * <p>
     * Note: Make sure that the background colour complements the text colour
     * <p>
     * Note: Use this constructor sparingly, as centering text is very CPU intensive. 
     * @param width     the width of the text box
     * @param height    the height of the text box
     * @param bgR       red parameter of the background colour
     * @param bgG       green parameter of the background colour
     * @param bgB       blue parameter of the background colour
     * @param txtR      red parameter of the text colour
     * @param txtG      green parameter of the text colour
     * @param txtB      blue parameter of the text colour
     * @param fontSize  the font size of the text
     * @param Font      the specified font type of the text box
     * @param centered  whether the text is center aligned
     * @param startText the initial text for the text box
     */
    public TextBox(int width, int height, int bgR, int bgG, int bgB, int txtR, int txtG, int txtB, int fontSize, String font, 
    boolean centered, String startText){
        // Create a textbox and initialize the instane variables, as well as setting the string output
        textBox = new GreenfootImage(width, height);
        backgroundColor = new Color(bgR, bgG, bgB);
        textColor = new Color (txtR, txtG, txtB);
        this.font = font;
        this.fontSize = fontSize;
        textFont = new Font (font, fontSize);
        maxLines = (int) Math.floor(height / fontSize); // Calculate maximum lines as total height divided by font size
        textBox.setColor(backgroundColor);
        textBox.fill();
        setImage(textBox);
        textBox.setFont(textFont);
        this.width = width;
        this.centered = centered;
        outputText = startText;
        curOutput = outputText;
        lastOutput = "";
    }
    
    /**
     * Displays text to the screen, assuming that text has changed
     */
    public void display()
    {
        // Set the textbox parameters 
        textBox.setColor(backgroundColor);
        textBox.fill();
        textBox.setColor(textColor);
        // Check if the text box needs to be centered or not
        if (centered){
            // Check and set the centered position for each line of text
            for (int i = 0; i < textC.size(); i++){
                centeredY = (width/2) - getStringWidth(textFont, textC.get(i))/2;
                textBox.drawString(textC.get(i), centeredY, fontSize*(i+1)); 
            }
        }
        else {
            // Print the text left aligned
            for (int i = 0; i < textC.size(); i++){
                textBox.drawString(textC.get(i), 0, fontSize*(i+1));
            }
        }
    }
    
    /**
     * Updates the current output immediately to match the entire output text. 
     * Make sure that this is not more than maxLines, as some lines may be cut out if it exceeds the size of the window
     */
    public void update(){
        curOutput = outputText;
        checkLines(); // Make sure total lines don't overflow past max lines of the image
        display();
    }
    
    /**
     * Overloaded method that updates the current output to match the entire output text while taking in keyboard input. 
     * This method takes in Greenfoot user input rather than regular String input
     * @param stop    Delimiter to stop the user input
     */
    public void update(String stop){
        rawInput = Greenfoot.getKey(); // Get the key pressed by the user
        if(rawInput != null){
            // Append the key to the output string, checking for delimiters or backspaces
            if(rawInput.length() == 1){
                if (Greenfoot.isKeyDown("shift") && Character.isDigit(rawInput.charAt(0))){
                    outputText += rawInput.toUpperCase();
                }
                else {
                    outputText += rawInput;
                }
            }
            if(rawInput == "space"){
                outputText += " ";
            }
            if(rawInput == "enter"){
                outputText += "\n";
            }
            if (rawInput == stop){
                finishedPress = true;
            }
            if(rawInput == "backspace"){
                // If key is backspace, make sure last character can be deleted
                if (outputText.length() != 0){
                    if (lastOutput.length() < outputText.length()){
                        outputText = outputText.substring(0, outputText.length() - 1);
                    }
                } 
                else {
                    outputText = "";
                }
            }
        }
        // Update and display the text
        curOutput = outputText;
        checkLines();
        display();
    }
    
    /**
     * <p>Get the width of a String, if it was printed out using the drawString command in a particular
     * Font. This is not a cheap method, and should not be called from an act method. It is appropriate
     * to call this in the constructor.</p>
     * 
     * <p>In advanced cases, you may want to cache the results during a loading method. You could also
     * call it manually while coding, not the results, and use literal values to avoid having this
     * code called at all.</p>
     * 
     * <h3>Performance & Compatibility:</h3>
     * <ul>
     *  <li> Locally, performance should be sufficient on any moderate computer (average call 0.4ms on my laptop)</li>
     *  <li> To be compatible with Greenfoot Gallery, removed use of getAwtImage() and replaced with getColorAt() on a GreenfootImage</li>
     *  <li> On Gallery, performance is about 10x slower than locally (4ms on Gallery via Computer). For reference, an act() should be
     *       less than 16.6ms to maintain 60 frames/acts per second. </li>
     *  <li> HUGE performance drop on Gallery via Mobile devices - not sure why, going to ignore for now </li>
     * </ul>
     * 
     * @param font the GreenFoot.Font which is being used to draw text
     * @param text the actual text to be drawn
     * @return int  the width of the String text as draw in Font font, in pixels.
     * 
     * @author Jordan Cohen, Jerry Zhu
     * @since June 2021
     * @version November 2021 - Edited Mr.Cohen's version for my text box class
     */
    public static int getStringWidth (Font font, String text){
        
        // Dividing font size by 1.2 should work for even the widest fonts, as fonts are
        // taller than wide. For example, a 24 point font is usually 24 points tall 
        // height varies by character but even a w or m should be less than 20 wide
        // 24 / 1.2 = 20
        final int MAX_WIDTH = (int)(text.length() * (font.getSize()/1.20));//1000; 
        int fontSize = font.getSize();
        if (MAX_WIDTH == 0){
            return 0;
        }
        GreenfootImage temp = new GreenfootImage (MAX_WIDTH, fontSize);
        temp.setFont(font);
        temp.drawString (text, 0, fontSize);
        int checkX;
        int lastFound = 0;
        boolean running = true;
        int marginOfError = fontSize / 4; // how many pixels can be skipped scanning vertically for pixels?
        checkX = MAX_WIDTH - 1;
        while(running){
            boolean found = false;
            for (int i = 0; i < fontSize && !found; i+=marginOfError){
                Color c = temp.getColorAt(checkX, i);
                if (!( c.getAlpha() == 0 && c.getRed() == 0 && c.getBlue() == 0 && c.getGreen() == 0 )){
                    found = true;
                }
            }
            if (found){
                return checkX;
            }
            checkX--;
            if (checkX < 0){
                running = false;
            }
        }
        return 0;
    }
    
    /**
     * Simulate typing of text in the text box on the screen. 
     * Called in the World whenever the text should be updated by a character. 
     * Includes the ability to incorporate animation frames. 
     */
    public void simulateType(){
        if (curOutput != outputText){
            // Take an extra character from outputText into curOutput
            curOutput = outputText.substring(0, curOutput.length() + 1);
            checkLines();
            display();
        }
    }
    
    /**
     * Setter method to set the finishedPress value of whether the enter key is pressed or not.
     * @param finishedPress   whether the enter key has been pressed yet
     */
    public void setFinishedPress(boolean finishedPress){
        this.finishedPress = finishedPress;
    }
    
    /**
     * Getter method to check whether the delimiter key is pressed or not. 
     * @return boolean   the result of whether the enter key has been pressed or not
     */
    public boolean getFinishedPress(){
        return finishedPress;
    }
    
    /**
     * Method that checks to see if the text displayed matches the entire text.
     * @return boolean   if the current output equals the output text
     */
    public boolean stopTyping(){
        return curOutput.length() == outputText.length();
    }
    
    /**
     * Get the total output text. 
     * @return String    the corresponding text output
     */
    public String getOutput(){
        return outputText;
    }
    
    /**
     * Set the total output text. 
     * @param outputText   the corresponding output text
     */
    public void setOutput(String outputText){
        this.outputText = outputText;
    }
    
    /**
     * Add to the total output text. 
     * @param outputText   the text needed to be added
     */
    public void addToOutput(String outputText){
        this.outputText += outputText;
        keepLastOutput();
    }
    
    /**
     * Get the current text displayed. 
     * @return String     the current output text
     */
    public String getCurrent(){
        return curOutput; 
    }
    
    /**
     * Set the current text displayed. 
     * @param curOutput  the current output text
     */
    public void setCurrent(String curOutput){
        this.curOutput = curOutput;
    }
    
    /**
     * Check if a lines needs to be deleted, and delete that line if neccesary. Update the arrayLists accordingly. 
     */
    public void checkLines(){
        // Make new array lists for curOutput and outputText
        textC = new ArrayList <String> (Arrays.asList(curOutput.split("\n")));
        textO = new ArrayList <String> (Arrays.asList(outputText.split("\n")));
        while (textC.size() > maxLines || (textC.size() == maxLines && outputText.endsWith("\n"))){
            // While there are still lines to be deleted, delete the lines and change the variables and lists
            textC.remove(0);
            textO.remove(0);
            boolean newLine = curOutput.endsWith("\n");
            curOutput = "";
            for (String line : textC){
                curOutput += line + "\n";
            }
            outputText = "";
            for (String line : textO){
                outputText += line + "\n";
            }
            if (!newLine){
                curOutput = curOutput.substring(0, curOutput.length() - 1);
                outputText = outputText.substring(0, outputText.length() - 1);
            }
        }
    }
    
    /**
     * Get the lines in an ArrayList from the indices [start, end] inclusive. 
     * @param start                start point to get lines from the ArrayList 
     * @param end                  end point to get lines from the ArrayList
     * @return ArrayList <String>  an ArrayList of the required list of sentences
     */
    public ArrayList <String> getLines(int start, int end){
        // Get a arraylist subset by appending all valid entries in the arraylist to another arraylist
        ArrayList <String> listOfSentences = new ArrayList <String> ();
        for (int i = start; i <= end; i++){
            listOfSentences.add(textC.get(i));
        }
        return listOfSentences;
    }
    
    /**
     * Get the last n lines in the text output returned in an ArrayList. 
     * @param numOfLines            the number of lines
     * @return ArrayList <String>   an arraylist of the last numOfLines lines
     */
    public ArrayList <String> getLastLines(int numOfLines){
        return getLines(textC.size() - 1 - numOfLines, textC.size() - 1);
    }
    
    /**
     * Get the last line in the text output
     * @return String     the last line of the text output. 
     */
    public String getLastLine(){
        ArrayList <String> listOfSentences = new ArrayList <String> (Arrays.asList(curOutput.split("\n")));
        return listOfSentences.get(listOfSentences.size() - 1);
    }
    
    /**
     * Save the last output in a variable called lastOutput
     * Used so that the backspace does not delete text that should not be deleted. 
     */
    public void keepLastOutput(){
        lastOutput = outputText;
    }
}
