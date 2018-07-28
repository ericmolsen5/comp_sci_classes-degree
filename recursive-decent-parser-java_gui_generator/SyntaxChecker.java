//package com.olsen;
/*
 * File Name: SyntaxChecker.java
 * Author: Eric Olsen
 * UMUC, CMSC330, Project 1
 * Due  17 JUN 2018
 */

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class SyntaxChecker {

    private String inputString;
    private Lexer lexer;
    private Token token;
    private String currentLexeme;


    public SyntaxChecker(Lexer lexer) {
        this.lexer = lexer;
        this.inputString = inputString;
    }

    //this prints the tokens and lexemes in order to assist in program testing
	//This helped me identify if issues existed in the Token generation or GUI instantiation
    public void printTokensAndLexemes() {
        token = lexer.getNextToken();
        currentLexeme = lexer.getCurrentLexeme();
        System.out.println(token + " \t|\t " + currentLexeme);

        while (token != Token.END_OF_FILE) {
            token = lexer.getNextToken();
            System.out.println(token + " \t|\t " + lexer.getCurrentLexeme());

        }
    }

	//This launches the series of parse operations which utilize Java's object constructors as
	//program gateways
    public void buildGUI() {
        try{
            GUI parsedGUI = new GUI();
        } catch (ImproperSyntaxException e){
            e.printStackTrace();
        }
    }


    /**
     * The GUI definition language is implemented below. I will keep all grammar rules as an inner-classes
     * to the syntax checker since it only has relevance to this class.
     * This will ensure that inner classes  have access to the lexer's nextToken() method, but be hidden from 
	 * external classes. In a larger project, it would make a lot more sense to make this its own accessible package.
     */

//GUI grammar: <GUI> --> WINDOW STRING '(' NUMBER_DATA ')' <layout> <widgets> END '.'
    private class GUI {

        private JFrame frame;
        private LayoutManager lastLayout;
        private String title;
        private int width;
        private int height;

        //using inner class constructors as the program flow gateway is how I've approached this assignment
        private GUI() throws ImproperSyntaxException{

            //This first series of checks validates our needed inputs for the JFrame object
            token = lexer.getNextToken();
            if (token.equals(token.WINDOW)) {

                //check for proper GUI name
                token = lexer.getNextToken();
                if (token.equals(token.DOUBLE_QUOTE)) { //checks that double-quote opens a declared String
                    token = lexer.getNextToken();
                    if (token.equals(token.STRING_DATA)) { //verifies that internal data is String
                        title = lexer.getCurrentLexeme(); //fetches token's String literal as JFrame title
                        token = lexer.getNextToken();
                        if (token.equals(token.DOUBLE_QUOTE)) { //validates proper closing syntax
                            frame = new JFrame(title);  //all checks pass, create JFrame
                        } else {
                            throw new ImproperSyntaxException(token, token.DOUBLE_QUOTE, "Window",
                                    lexer.getCurrentLexeme());
                        }
                    } else {
                        throw new ImproperSyntaxException(token, token.STRING_DATA, "Window",
                                lexer.getCurrentLexeme());
                    }
                }
                else {
                    throw new ImproperSyntaxException(token, token.CLOSING_PARENTHESIS, "Window",
                            lexer.getCurrentLexeme());
                }

                //check for GUI dimensions, most inline comments dropped because token identifiers are self-explanatory
                token = lexer.getNextToken();
                if (token.equals(token.OPENING_PARENTHESIS)) {
                    token = lexer.getNextToken();
                    if (token.equals(Token.NUMBER_DATA)) {
                        width = Integer.valueOf(lexer.getCurrentLexeme()); //extract integer width value
                        token = lexer.getNextToken();
                        if (token.equals(Token.COMMA_SEPARATOR)) {
                            token = lexer.getNextToken();
                            if (token.equals(Token.NUMBER_DATA)) {
                                height = Integer.valueOf(lexer.getCurrentLexeme()); //extract integer height value
                                token = lexer.getNextToken();
                                if (token.equals(Token.CLOSING_PARENTHESIS)) { //width and height validated
                                    frame.setSize(width, height);				//set dimensions of the frame
                                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //set default close operation

                                } else {
                                    throw new ImproperSyntaxException(token, token.CLOSING_PARENTHESIS, "Window",
                                            lexer.getCurrentLexeme());
                                }
                            } else {
                                throw new ImproperSyntaxException(token, token.NUMBER_DATA, "Window",
                                        lexer.getCurrentLexeme());
                            }
                        } else {
                            throw new ImproperSyntaxException(token, token.COMMA_SEPARATOR, "Window",
                                    lexer.getCurrentLexeme());
                        }
                    } else {
                        throw new ImproperSyntaxException(token, token.NUMBER_DATA, "Window",
                                lexer.getCurrentLexeme());
                    }
                } else {
                    throw new ImproperSyntaxException(token, token.OPENING_PARENTHESIS, "Window",
                            lexer.getCurrentLexeme());
                }
            }
			
			//frame is now created. The LayoutManager and Widgets is where the recursive features begin
            
            token = lexer.getNextToken();
			
            if ((token.equals(Token.LAYOUT))) {
                //we generate the needed LayoutManager by using Layout's default constructor
                Layout layout = new Layout();        //result of instantiating the Layout class is Grid or Flow layouts
                lastLayout = layout.layoutType.type; //this is a class field for the sake of the JFrame
                frame.setLayout(lastLayout);         //assign the JFrame Layout Manager
            } else {
                throw new ImproperSyntaxException(token, token.LAYOUT, "Layout", lexer.getCurrentLexeme());
            }

            //<widgets> non-terminal syntax checker - triggered by the ':' separator
            //the colon token is our indicator that we have some nested widgets to insert
            if (token.equals(Token.COLON)) {
                //Widgets will recursively generate all GUI objects until encountering the END token
                Widgets widgets = new Widgets(); //most of the recursive work begins here

                //Since the GUI class is the parse tree root node, we'll use iteration to fill the JFrame
                //the for-each loop below takes and adds JPanels to the JFrame
                for (JPanel panels : widgets.panelList) {
                    frame.add(panels);
                }
            } else {
                throw new ImproperSyntaxException(token, token.COLON, "Widgets", lexer.getCurrentLexeme());
            }

            //process the End and '.' token
            if (token.equals(Token.END)) {

                System.out.println("End of Window detected.");
                token = lexer.getNextToken();
                if (token.equals(Token.PERIOD)) {
                    System.out.println("End of file detected");
                } else {
                    throw new ImproperSyntaxException(token, token.PERIOD, "Program end", lexer.getCurrentLexeme());
                }
            } else {
                throw new ImproperSyntaxException(token, token.END, "Program end", lexer.getCurrentLexeme());
            }

            frame.setVisible(true);
			//program complete
        }

    }

    //Layout Grammar: <Layout> --> Layout <LayoutType> ':'
    private class Layout {

        private LayoutType layoutType;

        private Layout() throws ImproperSyntaxException {
            //validate that the next token is Either Flow or Grid. Then we'll instantiate the LayoutType class
            token = lexer.getNextToken();
            if ((token.equals(Token.FLOW)) || token.equals(Token.GRID)) {
                //proper syntax detected, continue program flow into Layout object
				layoutType = new LayoutType();
                token = lexer.getNextToken();
                if (!token.equals(Token.COLON)) {
                    throw new ImproperSyntaxException(token, token.COLON, "Layout Type", lexer.getCurrentLexeme());
                }
            } else {
                throw new ImproperSyntaxException(token, token.FLOW, token.GRID, "Layout Type", lexer.getCurrentLexeme());
            }
        }

    }

    //LayoutType Grammar: <LayoutType> --> Flow | Grid '(' NUMBER_DATA ',' NUMBER_DATA [ ',' NUMBER_DATA ',' NUMBER_DATA ] ')'
    private class LayoutType {

        private LayoutManager type;
        private int rows, cols, hgap, vgap;

        private LayoutType() throws ImproperSyntaxException {

            //The next checks determine whether it's Grid or Flow layout
            if (token.equals(Token.FLOW)) {
                type = new FlowLayout();
            } else if (token.equals(Token.GRID)) {

                //check for integer inputs
                token = lexer.getNextToken();
                if (token.equals(Token.OPENING_PARENTHESIS)) {
                    token = lexer.getNextToken();

                    if (token.equals(Token.NUMBER_DATA)) {
                        rows = Integer.valueOf(lexer.getCurrentLexeme()); //set rows value
                        token = lexer.getNextToken();

                        if (token.equals(Token.COMMA_SEPARATOR)) {
                            token = lexer.getNextToken();

                            if (token.equals(Token.NUMBER_DATA)) {
                                cols = Integer.valueOf(lexer.getCurrentLexeme()); //set cols value
                                token = lexer.getNextToken();

                                //here we check for the 2-input or 4-input constructor
                                //2-input
                                if (token.equals(Token.CLOSING_PARENTHESIS)) { //complete the 2-value constructor
                                    type = new GridLayout(rows, cols);		//instantiate the LayoutManager
                                //4-input constructor detected
                                } else if (token.equals(Token.COMMA_SEPARATOR)) {
                                    token = lexer.getNextToken();

                                    if (token.equals(Token.NUMBER_DATA)) {
                                        hgap = Integer.valueOf(lexer.getCurrentLexeme()); //set horizontal spacing
                                        token = lexer.getNextToken();

                                        if (token.equals(Token.COMMA_SEPARATOR)) {
                                            token = lexer.getNextToken();

                                            if (token.equals(Token.NUMBER_DATA)) {
                                                vgap = Integer.valueOf(lexer.getCurrentLexeme()); //set vertical spacing
                                                token = lexer.getNextToken();
                                                if (token.equals(Token.CLOSING_PARENTHESIS)) {
                                                    type = new GridLayout(rows, cols, hgap, vgap); //instantiate the LayoutManager
                                                } else {
                                                    throw new ImproperSyntaxException(token, token.CLOSING_PARENTHESIS,
                                                            "Grid Layout", lexer.getCurrentLexeme());
                                                }
                                            } else {
                                                throw new ImproperSyntaxException(token, token.NUMBER_DATA, "Grid Layout",
                                                        lexer.getCurrentLexeme());
                                            }
                                        } else {
                                            throw new ImproperSyntaxException(token, token.COMMA_SEPARATOR, "Grid Layout",
                                                    lexer.getCurrentLexeme());
                                        }
                                    } else {
                                        throw new ImproperSyntaxException(token, token.NUMBER_DATA, "Grid Layout",
                                                lexer.getCurrentLexeme());
                                    }
                                } else {
                                    throw new ImproperSyntaxException(token, token.COMMA_SEPARATOR,
                                            token.CLOSING_PARENTHESIS, "Grid Layout", lexer.getCurrentLexeme());
                                }
                            } else {
                                throw new ImproperSyntaxException(token, token.NUMBER_DATA, "Grid Layout",
                                        lexer.getCurrentLexeme());
                            }
                        }else {
                            throw new ImproperSyntaxException(token, token.COMMA_SEPARATOR, "Grid Layout",
                                    lexer.getCurrentLexeme());
                        }
                    }else {
                        throw new ImproperSyntaxException(token, token.NUMBER_DATA, "Grid Layout",
                                lexer.getCurrentLexeme());
                    }
                } else {
                    throw new ImproperSyntaxException(token, token.OPENING_PARENTHESIS, "Grid Layout",
                            lexer.getCurrentLexeme());
                }
            }
        }
    }

    // Widgets Grammar: <Widgets> --> <Widget> <Widgets> | <Widget>
    private class Widgets {

		//Design decision: every Widget gets its own JPanel. This greatly assists when adding to the JFrame
        private ArrayList<JPanel> panelList = new ArrayList<JPanel>();
        private boolean isPanel = false;  //this boolean assists in running recursive JPanel methods

        private Widgets() {

            token = lexer.getNextToken();

            try{
                checkAndAddWidgets();  //recursive helper method
            } catch (ImproperSyntaxException e) {
                e.printStackTrace();
            }
        }


        private void checkAndAddWidgets() throws ImproperSyntaxException {

			//check for all widgets except for JPanels
            if ((token.equals(Token.BUTTON)) || (token.equals(Token.GROUP)) || (token.equals(Token.LABEL)) ||
                    (token.equals(Token.TEXTFIELD))) {

                Widget newWidget = new Widget(); //this will create the widget and move the token to the next item
                JPanel tempPanel = new JPanel(); //tempPanel will hold the generated widget
                tempPanel.add(newWidget.widgetType); //add the generated widget to the tempPanel
                panelList.add(tempPanel); //tempPanel added to the ArrayList of JPanels which will eventually be pulled onto the frame

            }
            //panel gets some special treatment since it also needs its own LayoutManager
            else if (token.equals(Token.PANEL)) {

                isPanel = true;
                token = lexer.getNextToken();

                if (token.equals(Token.LAYOUT)) {

                    //generate a panel first so widgets can be added later
                    JPanel panel = new JPanel();
                    //assign the panel its own layout manager
                    try{
                        Layout panelLayoutObj = new Layout();
                        LayoutManager panelLayout = panelLayoutObj.layoutType.type;
                        panel.setLayout(panelLayout);
                    } catch (ImproperSyntaxException e) {
                        e.printStackTrace();
                    }


                    //now recursively instantiate widgets
                    Widgets nextWidgets = new Widgets();

                    //here's our recursive break criteria
                    if ((token.equals(Token.END)) && isPanel) {
                        isPanel = false; //this allows us to exit the recursive method call
                        token = lexer.getNextToken();
						
						//this is used to bundle our JPanels together for a single JPanel to add to the frame 
                        if (token.equals(Token.SEMI_COLON)) {
                            //here we use an iterative helper method to extract frame contents into a single frame
                            LayoutManager tmpLayout = panel.getLayout();
                            panel = panelExtractor(nextWidgets.panelList); //this was defaulting to flow layout for some reason
                            panel.setLayout(tmpLayout);
                            panelList.add(panel); //nested frame should now be created



                            token = lexer.getNextToken();
                        }
                    }
                }

            } else {
                throw new ImproperSyntaxException(token, token.NUMBER_DATA, "JPanel", lexer.getCurrentLexeme());
            }

			//Recursive looping feature:
            if (!token.equals(Token.END) && !isPanel) {  //recursive exit criteria
                checkAndAddWidgets();
            } else if (isPanel) {		//recursive exit criteria for nested panels
                checkAndAddWidgets();
            }

        }

        //helper method for adding single JPanel
		//this was needed in order to collect all JPanels in one single place for evantual frame adding
        private JPanel panelExtractor(ArrayList<JPanel> panelList) {
            JPanel extractedPanel = new JPanel();
            for (JPanel listItem : panelList) {
                extractedPanel.add(listItem);
            }
            return extractedPanel;
        }

    }

	//Widget Grammar: <Widget> -->  Button STRING ‘;’ | <Group radio_Buttons> End ; | Label STRING ‘;’ | Panel <layout> <widgets> End ‘;’ | Textfield NUMBER ‘;’
    private class Widget {

        private Component widgetType; //this is the first time I've ever used a Component Object as a field
		
        //Lesson Learned: My Label objects were over-writing my Button objects because I wasn't using a boolean 
		//to control the if Statements. That took me about 2 hours on a Sunday to figure out.
		private boolean componentSelected = false;  

        private Widget() throws ImproperSyntaxException {

            //This program supports empty buttons and labels, but not empty radio buttons or default textfields
			
            //Button definition
            if ((token.equals(Token.BUTTON)) && !componentSelected) {
                token = lexer.getNextToken();
                if (token.equals(Token.DOUBLE_QUOTE)) {
                    token = lexer.getNextToken();
                    //string or number input
                    if (token.equals(Token.NUMBER_DATA) || token.equals(Token.STRING_DATA)) {
                        widgetType = new JButton(lexer.getCurrentLexeme());
                        componentSelected = true;
                        token = lexer.getNextToken();
                        if (token.equals(Token.DOUBLE_QUOTE)) {
                            token = lexer.getNextToken();
                            if (token.equals(Token.SEMI_COLON)) {
                                token = lexer.getNextToken();
                            } else {
                                throw new ImproperSyntaxException(token, token.SEMI_COLON, "Widget Button",
                                        lexer.getCurrentLexeme());
                            }
                        } else {
                            throw new ImproperSyntaxException(token, token.DOUBLE_QUOTE, "Widget Button",
                                    lexer.getCurrentLexeme());
                        }
                    } else if (token.equals(Token.DOUBLE_QUOTE)){
                        widgetType = new JButton((""));
                        componentSelected = true;
                        token = lexer.getNextToken();
                        if (token.equals(Token.SEMI_COLON)) {
                            token = lexer.getNextToken();
                        } else {
                            throw new ImproperSyntaxException(token, token.SEMI_COLON, "Widget Button",
                                    lexer.getCurrentLexeme());
                        }
                    }
                    else {
                        throw new ImproperSyntaxException(token, token.NUMBER_DATA, token.STRING_DATA, "Widget Layout",
                                lexer.getCurrentLexeme());
                    }
                } else {
                    throw new ImproperSyntaxException(token, token.DOUBLE_QUOTE, "Widget Button",
                            lexer.getCurrentLexeme());
                }
            } //Button should have been validated in widgets and is not checked here

            //Label definition
            if ((token.equals(Token.LABEL)) && !componentSelected) {
                token = lexer.getNextToken();
                if (token.equals(Token.DOUBLE_QUOTE)) {
                    token = lexer.getNextToken();
                    //String or Number input
                    if ((token.equals(Token.NUMBER_DATA)) || (token.equals(Token.STRING_DATA))) {
                        widgetType = new JLabel(lexer.getCurrentLexeme());
                        componentSelected = true;
                        token = lexer.getNextToken();
                        if (token.equals(Token.DOUBLE_QUOTE)) {
                            token = lexer.getNextToken();
                            if (token.equals(Token.SEMI_COLON)) {
                                token = lexer.getNextToken();
                            } else {
                                throw new ImproperSyntaxException(token, token.SEMI_COLON, "Widget Button",
                                        lexer.getCurrentLexeme());
                            }
                        } else {
                            throw new ImproperSyntaxException(token, token.DOUBLE_QUOTE, "Widget Label",
                                    lexer.getCurrentLexeme());
                        }
                        //null data
                    } else if (token.equals(Token.DOUBLE_QUOTE)){
                        widgetType = new JLabel("");
                        componentSelected = true;
                        token = lexer.getNextToken();
                        if (token.equals(Token.SEMI_COLON)){
                            token = lexer.getNextToken();
                        } else{
                            throw new ImproperSyntaxException(token, token.SEMI_COLON, "Widget Label",
                                    lexer.getCurrentLexeme());
                        }
                    } else {
                        throw new ImproperSyntaxException(token, token.NUMBER_DATA, token.STRING_DATA, "Widget Layout",
                                lexer.getCurrentLexeme());
                    }
                }
                else {
                    throw new ImproperSyntaxException(token, token.DOUBLE_QUOTE, "Widget Label",
                            lexer.getCurrentLexeme());
                }
            } //Label was previously validated

            //Textfield definition
            if ((token.equals(Token.TEXTFIELD)) && !componentSelected) {
                token = lexer.getNextToken();
                if (token.equals((Token.NUMBER_DATA))) {
                    int columnWidth = Integer.valueOf(lexer.getCurrentLexeme());
                    widgetType = new JTextField(columnWidth);
                    componentSelected = true;
                    token = lexer.getNextToken();
                    if (token.equals(Token.SEMI_COLON)) {    //encountered our expected end of line separator
                        token = lexer.getNextToken();       //this should either be the next widget or the END token
                    } else {
                        throw new ImproperSyntaxException(token, token.SEMI_COLON, "Textfield Widget",
                                lexer.getCurrentLexeme());
                    }
                } else {
                    throw new ImproperSyntaxException(token, token.NUMBER_DATA, "Textfield Widget",
                            lexer.getCurrentLexeme());
                }
            } //Textfield widget tested in the Widgets class


            //Group definition
            if ((token.equals(Token.GROUP)) && !componentSelected) {
                token = lexer.getNextToken();
                if (token.equals(Token.COLON)) {
                    //We have to allow for button groups inside of button groups, so we'll declare the ButtonGroups
                    //and container panels in the RadioButtons class upon initial calling of the class
                    try {
                        RadioButtons radioButtons = new RadioButtons();
                        componentSelected = true;
                        //at this point there should be no more Radio or Group Tokens detected
                        widgetType = radioButtons.radioButtonPanel;
                    } catch (ImproperSyntaxException e) {
                        e.printStackTrace();
                    }

                    //closing syntax checked in the RadioButtons class
                } //error check occurs in the Radio class
            }

        }

    }


    //this class primarily exists to create a JPanel with logically grouped buttons
    //RadioButtons Grammar: <radio_buttons> --> <radio_button> <radio_buttons> | <radio_button>
    private class RadioButtons {

        private JPanel radioButtonPanel = new JPanel(); //everything gets its own JPanel
        private ButtonGroup group = new ButtonGroup();

        private RadioButtons() throws ImproperSyntaxException{
            token = lexer.getNextToken();
            //Design decision: this program does not support nesting button groups inside button groups. Just make an adjacent group
			if (token.equals(Token.GROUP)) {
                //special case, we don't want a group within a group
                System.out.println("Please don't nest Radio buttons inside of other Radio buttons. Create a separate" +
                        " panel to create this option");
            }
            if (token.equals(Token.RADIO)) {

                addRadioButtons();  //recurseive helper method which generates the buttons
                if (token.equals(Token.END)){
                    //cycle through the End and ';' then return program flow back to calling method
                    token = lexer.getNextToken(); //should be the Group's 'End'
                    token = lexer.getNextToken(); //should be the Group's closing ';'
                }
            } else {
                throw new ImproperSyntaxException(token, token.DOUBLE_QUOTE, "Radio Button",
                        lexer.getCurrentLexeme());
            }
        }

        //recursive helper method
        private void addRadioButtons(){
            try{
                RadioButton nestedButton = new RadioButton();
                //pull frame from nestedButtonGroup onto this buttonFrame
                radioButtonPanel.add(nestedButton.button);
                group.add(nestedButton.button);
                if (token.equals(Token.RADIO)){
                    addRadioButtons();
                }
            } catch (ImproperSyntaxException e){
                e.printStackTrace();
            }


        }

    }

    //this is a pretty simple class that just creates the button after validating the expression syntax
	//RadioButton Grammar: <radio_button> --> Radio STRING ‘;’
    private class RadioButton {

        private JRadioButton button;
		
        private RadioButton() throws ImproperSyntaxException{
            token = lexer.getNextToken();
            if (token.equals(Token.DOUBLE_QUOTE)) {
                token = lexer.getNextToken();

                if ((token.equals(Token.STRING_DATA)) || (token.equals(Token.NUMBER_DATA))) {
                    button = new JRadioButton(lexer.getCurrentLexeme());
                    token = lexer.getNextToken();

                    if (token.equals(Token.DOUBLE_QUOTE)) {
                        token = lexer.getNextToken();

                        if (token.equals(Token.SEMI_COLON)) {
                            token = lexer.getNextToken();
                        } else {
                            throw new ImproperSyntaxException(token, token.SEMI_COLON, "Radio Button",
                                    lexer.getCurrentLexeme());
                        }
                    } else {
                        throw new ImproperSyntaxException(token, token.DOUBLE_QUOTE, "Radio Button",
                                lexer.getCurrentLexeme());
                    }
                } else {
                    throw new ImproperSyntaxException(token, token.STRING_DATA, token.NUMBER_DATA, "Radio Button",
                            lexer.getCurrentLexeme());
                }
            } else {
                throw new ImproperSyntaxException(token, token.DOUBLE_QUOTE, "Radio Button",
                        lexer.getCurrentLexeme());
            }

        }

    }


}
