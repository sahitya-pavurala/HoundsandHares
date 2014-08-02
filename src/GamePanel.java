
import java.awt.*;
/**
 * GamePanel class has the joy stick part of
 * the game board
 * @author Sahitya Pavurala
 */
final class GamePanel extends Panel
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GamePanel(Hounds hounds)
    {
        main = hounds;
        setLayout(new GridLayout(9, 1, 0, 4));
        setBackground(Color.white);
        setFont(textFont);
        add(bNewGame = new Button(hounds.sNEW_GAME));
        add(new Label(hounds.sPLAYER, 1));
        cbgAnimal = new CheckboxGroup();
        Panel panel = new Panel();
        panel.setLayout(new FlowLayout(0, 10, 0));
        panel.add(cbHounds = new Checkbox(hounds.sHOUNDS, cbgAnimal, true));
        add(panel);
        Panel panel1 = new Panel();
        panel1.setLayout(new FlowLayout(0, 10, 0));
        panel1.add(cbHare = new Checkbox(hounds.sHARE, cbgAnimal, false));
        add(panel1);
        add(new Label(hounds.sLEVEL, 1));
        add(cLevel = new Choice());
        cLevel.addItem(hounds.sBEGINNER);
        cLevel.addItem(hounds.sINTERMED);
        cLevel.addItem(hounds.sEXPERT);
        cLevel.select(hounds.sBEGINNER);
        add(new Label(hounds.sSCORE, 1));
        Panel panel2 = new Panel();
        panel2.setLayout(new FlowLayout(2, 3, 0));
        panel2.add(new Label(hounds.s2PLAYER, 1));
        panel2.add(tfPlayerScore = new TextField(2));
        tfPlayerScore.setFont(monoFont);
        add(panel2);
        Panel panel3 = new Panel();
        panel3.setLayout(new FlowLayout(2, 3, 0));
        panel3.add(new Label(hounds.sCOMPUTER, 1));
        panel3.add(tfComputerScore = new TextField(2));
        tfComputerScore.setFont(monoFont);
        add(panel3);
        validate();
    }

	@Override
	//Here we are over riding the action method of the Component class
    public boolean action(Event event, Object obj)
    {
        if(main.sNEW_GAME.equals(obj))
            main.newGame();
        else
        if(event.target == cbHounds)
            main.setPlayerAnimal(1);
        else
        if(event.target == cbHare)
            main.setPlayerAnimal(2);
        else
            main.setFocus();
        return true;
    }

	/**
	 * getLevel() gives the level of the game that
	 * the user selected
	 *@return level of the game selected
	 */
    int getLevel()
    {
        switch(cLevel.getSelectedIndex())
        {
        case 1: //level2
            return 2;

        case 2: // level3
            return 3;
        }
        return 1;//level1
    }

    /**
	 * setComputerScore() is called to set the computer 
	 * score in the text box in the game panel  
	 *@param i corresponds to the score value
	 */
    void setComputerScore(int i)
    {
        String s = Integer.toString(i);
        switch(s.length())
        {
        case 1: // '\001'
            s = "  " + s;
            break;

        case 2: // '\002'
            s = " " + s;
            break;
        }
        tfComputerScore.setText(s);
    }
    
    /**
	 * setPlayerScore() is called to set the player 
	 * score in the text box in the game panel  
	 *@param i corresponds to the score value
	 */
    void setPlayerScore(int i)
    {
        String s = Integer.toString(i);
        switch(s.length())
        {
        case 1: // '\001'
            s = "  " + s;
            break;

        case 2: // '\002'
            s = " " + s;
            break;
        }
        tfPlayerScore.setText(s);
    }

    static final int LEVEL1 = 1;
    static final int LEVEL2 = 2;
    static final int LEVEL3 = 3;
    static final Font titleFont = new Font("Dialog", 1, 15);
    static final Font textFont = new Font("Dialog", 0, 15);
    static final Font monoFont = new Font("Dialog", 1, 15);
    static final Font statusFont = new Font("Dialog", 0, 15);
    private Button bNewGame;
    private CheckboxGroup cbgAnimal;
    private Checkbox cbHounds;
    private Checkbox cbHare;
    private Choice cLevel;
    private TextField tfPlayerScore;
    private TextField tfComputerScore;
    private Hounds main;

}