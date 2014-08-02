
import java.applet.Applet;
import java.awt.*;

/**
 * Hound class is used to give the content to the UI
 * @author Sahitya Pavurala
 */
public class Hounds extends Applet
{

	private static final long serialVersionUID = 1L;	
	
	public Hounds()
	    {
	        playerAnimal = 1;
	        computerAnimal = 2;
	        startSq = -1;
	    }

	

	@Override
	// Here we are over riding the getAppletInfo of Applet class
	public String getAppletInfo()
    {
        return "Hare and Hounds";
    }

	@Override
	// Here we are over riding the getParameterInfo method of Applet class
    public String[][] getParameterInfo()
    {
        return parameterInfo;
    }

	@Override
	// Here we are over riding the init method of Applet class
    @SuppressWarnings("deprecation")
	public void init()
    {
        String s = getParameter("bgColor");
        try
        {
            BG_COLOR = s != null ? new Color(Integer.parseInt(s, 16)) : DF_BG_COLOR;
        }
        catch(NumberFormatException _ex)
        {
            BG_COLOR = DF_BG_COLOR;
            System.out.println("ERROR: Invalid bgColor parameter.");
        }
        String s1 = getParameter("boardBgColor");
        try
        {
            BOARD_BG_COLOR = s1 != null ? new Color(Integer.parseInt(s1, 16)) : BG_COLOR;
        }
        catch(NumberFormatException _ex)
        {
            BOARD_BG_COLOR = BG_COLOR;
            System.out.println("ERROR: Invalid boardBgColor parameter.");
        }
        sTITLE = getParameter("title");
        if(sTITLE == null)
            sTITLE = new String("Hare and Hounds");
        sNEW_GAME = getParameter("newGame");
        if(sNEW_GAME == null)
            sNEW_GAME = new String("NEW GAME");
        sPLAYER = getParameter("player");
        if(sPLAYER == null)
            sPLAYER = new String("PLAYER");
        sHOUNDS = getParameter("hounds");
        if(sHOUNDS == null)
            sHOUNDS = new String("Hounds");
        sHARE = getParameter("hare");
        if(sHARE == null)
            sHARE = new String("Hare");
        sLEVEL = getParameter("level");
        if(sLEVEL == null)
            sLEVEL = new String("LEVEL");
        sBEGINNER = getParameter("beginner");
        if(sBEGINNER == null)
            sBEGINNER = new String("Beginner");
        sINTERMED = getParameter("intermediate");
        if(sINTERMED == null)
            sINTERMED = new String("Intermediate");
        sEXPERT = getParameter("expert");
        if(sEXPERT == null)
            sEXPERT = new String("Expert");
        sSCORE = getParameter("score");
        if(sSCORE == null)
            sSCORE = new String("SCORE");
        s2PLAYER = getParameter("player2");
        if(s2PLAYER == null)
            s2PLAYER = new String("Player");
        sCOMPUTER = getParameter("computer");
        if(sCOMPUTER == null)
            sCOMPUTER = new String("Computer");
        sFIRST_MOVE = getParameter("firstMove");
        if(sFIRST_MOVE == null)
            sFIRST_MOVE = new String(DF_sFIRST_MOVE);
        sSTALLING = getParameter("stalling");
        if(sSTALLING == null)
            sSTALLING = new String(DF_sSTALLING);
        sHARE_WINS = getParameter("hareWins");
        if(sHARE_WINS == null)
            sHARE_WINS = new String(DF_sHARE_WINS);
        sHOUNDS_WIN = getParameter("houndsWin");
        if(sHOUNDS_WIN == null)
            sHOUNDS_WIN = new String(DF_sHOUNDS_WIN);
        MediaTracker mediatracker = new MediaTracker(this);
        java.net.URL url = getCodeBase();
        boardImage = getImage(url, "board.jpg");
        gifletsImage = getImage(url, "giflets.gif");
        mediatracker.addImage(boardImage, 0);
        mediatracker.addImage(gifletsImage, 1);
        try
        {
            mediatracker.waitForAll();
        }
        catch(InterruptedException _ex) { }
        cp = new GamePanel(this);
        bc = new GameBoard(boardImage, gifletsImage, this);
        bc.resize(440, 276);
        Panel panel = new Panel();
        panel.setBackground(Color.white);
        panel.setLayout(new FlowLayout(1, 5, 10));
        panel.add(cp);
        Panel panel1 = new Panel();
        panel1.setLayout(new BorderLayout(10, 5));
        panel1.setBackground(Color.white);
        panel1.add("West", lTitle = new Label(sTITLE, 1));
        lTitle.setFont(GamePanel.titleFont);
        tfStatus = new TextField();
        tfStatus.setFont(GamePanel.statusFont);
        tfStatus.setForeground(Color.white);
        tfStatus.setBackground(Color.black);
        tfStatus.setEditable(false);
        panel1.add("Center", tfStatus);
        setBackground(BG_COLOR);
        Panel panel2 = new Panel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setBackground(BOARD_BG_COLOR);
        Panel panel3 = new Panel();
        panel3.setLayout(new FlowLayout(1, 10, 10));
        panel2.setLayout(new BorderLayout());
        panel2.add("Center", bc);
        panel2.add("East", panel);
        panel2.add("South", panel1);
        panel3.add(panel2);
        add(panel3);
        validate();
        new State();
        cp.setPlayerScore(playerScore);
        cp.setComputerScore(computerScore);
        newGame();
    }

	/**
	 * newGame() rearranges the game board and sets the
	 * the hounds and hare in the default positions 
	 */
    void newGame()
    {
        System.gc();
        if(playerAnimal == 1)
            setStatus(sFIRST_MOVE);
        else
            setStatus(" ");
        gameOver = false;
        bd = new Board(cp, this);
        hnh = new Game(bd, cp, this);
        bc.drawBoard(bd);
        setFocus();
        if(computerAnimal == 1)
            computerMove();
    }

    /**
	 * endGame() is called when there is a final result 
	 * in the game
	 * @param i integer value corresponding to human or computer  
	 */
    private void endGame(int i)
    {
        if(i == 1)
            cp.setComputerScore(++computerScore);
        else
            cp.setPlayerScore(++playerScore);
        gameOver = true;
        bc.drawBoard(bd);
    }

    /**
	 * computerMove() calls the corresponding animal move
	 * method from the Game class 
	 */
    private void computerMove()
    {
        hnh.move(computerAnimal);
        if(bd.isWin(computerAnimal))
        {
            endGame(1);
            return;
        } else
        {
            bc.drawBoard(bd);
            setStatus(" ");
            return;
        }
    }

    /**
   	 * selectPiece() selects the grid on the board
   	 * @param i index value in the grid 
   	 * @param j index value in the grid
   	 */
    void selectPiece(int i, int j)
    {
        if(!gameOver)
        {
            startSq = bc.pixelToSquare(i, j);
            if(bd.selectPiece(playerAnimal, startSq))
            {
                bc.drawBoard(bd, i, j);
                return;
            }
            startSq = -1;
        }
    }

    /**
   	 * dragPiece() is called when an animal is moved 
   	 * on the game board
   	 * @param i index value in the grid 
   	 * @param j index value in the grid
   	 */
    void dragPiece(int i, int j)
    {
        if(!gameOver && startSq != -1)
            bc.drawBoard(bd, i, j);
    }
    
    /**
   	 * dropPiece() is called when an animal is dropped 
   	 * on the game board
   	 * @param i index value in the grid 
   	 * @param j index value in the grid
   	 */
    void dropPiece(int i, int j)
    {
        if(!gameOver && startSq != -1)
        {
            endSq = bc.pixelToSquare(i, j);
            if(bd.movePlayerPiece(startSq, endSq))
            {
                if(bd.isWin(playerAnimal))
                {
                    endGame(0);
                } else
                {
                    bc.drawBoard(bd);
                    setStatus(" ");
                    computerMove();
                }
            } else
            {
                bc.drawBoard(bd);
            }
        }
        startSq = -1;
    }

    /**
   	 * setPlayerAnimal() is called when the human player
   	 * selects the animal 
   	 * @param i integer value corresponding to a certain animal
   	 */
    void setPlayerAnimal(int i)
    {
        if(i != playerAnimal)
        {
            if(i == 2)
            {
                playerAnimal = 2;
                computerAnimal = 1;
            } else
            {
                playerAnimal = 1;
                computerAnimal = 2;
            }
            setFocus();
            if(!gameOver)
                computerMove();
        }
    }
    
    /**
   	 * setStatus() is called to display the status of the game on the UI 
   	 * @param s string value to the displayed 
   	 */
    void setStatus(String s)
    {
        tfStatus.setText(" " + s);
    }

    /**
   	 * getPlayerAnimal() gives the value of the animal
   	 * which the user has selected
   	 * @return playerAnimal the animal which the user has selected
   	 */
    int getPlayerAnimal()
    {
        return playerAnimal;
    }

    /**
   	 * setFocus() is called to set the focus on an animal
   	 * calls the requestFocus in the GameBoard class
   	 */
    void setFocus()
    {
        bc.requestFocus();
    }

   
    String parameterInfo[][] = {
        {
            "bgColor", "integer", "Background color (24-bit RGB hex int)"
        }, {
            "boardBgColor", "integer", "Board background color (24-bit RGB hex int)"
        }, {
            "title", "string", "Applet title text"
        }, {
            "newGame", "string", "New Game button text"
        }, {
            "player", "string", "Player Side label text"
        }, {
            "hounds", "string", "Player Side Hounds checkbox text"
        }, {
            "hare", "string", "Player Side Hare checkbox text"
        }, {
            "level", "string", "Level label text"
        }, {
            "beginner", "string", "Beginner level text"
        }, {
            "intermediate", "string", "Intermediate level text"
        }, {
            "expert", "string", "Expert level text"
        }, {
            "score", "string", "Score label text"
        }, {
            "player2", "string", "Player Score label text"
        }, {
            "computer", "string", "Computer Score label text"
        }, {
            "firstMove", "string", "Hounds first status text"
        }, {
            "stalling", "string", "Hounds Stalling status text"
        }, {
            "hareWins", "string", "Hare Wins status text"
        }, {
            "houndsWin", "string", "Hounds Win status text"
        }
    };
    static final Color DF_BG_COLOR;
    static final String DF_sTITLE = "Hare and Hounds";
    static final String DF_sNEW_GAME = "NEW GAME";
    static final String DF_sPLAYER = "PLAYER";
    static final String DF_sHOUNDS = "Hounds";
    static final String DF_sHARE = "Hare";
    static final String DF_sLEVEL = "LEVEL";
    static final String DF_sBEGINNER = "Beginner";
    static final String DF_sINTERMED = "Intermediate";
    static final String DF_sEXPERT = "Expert";
    static final String DF_sSCORE = "SCORE";
    static final String DF_s2PLAYER = "Player";
    static final String DF_sCOMPUTER = "Computer";
    static final String DF_sFIRST_MOVE = "The Hounds always moves first";
    static final String DF_sSTALLING = "The Hounds are stalling !  The Hare wins!";
    static final String DF_sHARE_WINS = "The Hare has escaped the hounds! The Hare wins!";
    static final String DF_sHOUNDS_WIN = "The Hare is trapped ! The Hounds win!";
    static final int bcWidth = 440;
    static final int bcHeight = 276;
    static final int NULL = -1;
    static final int PLAYER = 0;
    static final int COMPUTER = 1;
    static final int HOUND = 1;
    static final int HARE = 2;
    protected Color BG_COLOR;
    protected Color BOARD_BG_COLOR;
    protected String sTITLE;
    protected String sNEW_GAME;
    protected String sPLAYER;
    protected String sHOUNDS;
    protected String sHARE;
    protected String sLEVEL;
    protected String sBEGINNER;
    protected String sINTERMED;
    protected String sEXPERT;
    protected String sSCORE;
    protected String s2PLAYER;
    protected String sCOMPUTER;
    protected String sFIRST_MOVE;
    protected String sSTALLING;
    protected String sHARE_WINS;
    protected String sHOUNDS_WIN;
    private int playerAnimal;
    private int computerAnimal;
    private int playerScore;
    private int computerScore;
    private int startSq;
    private int endSq;
    private boolean gameOver;
    private Label lTitle;
    private TextField tfStatus;
    private Board bd;
    private GameBoard bc;
    private GamePanel cp;
    private Image boardImage;
    private Image gifletsImage;
    private Game hnh;

    static
    {
        DF_BG_COLOR = Color.lightGray;
    }
}