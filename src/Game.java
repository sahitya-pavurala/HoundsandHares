/**
 * Game class record the action player or AI agent take
 * @author Sahitya Pavurala
 */
public class Game
{
	public static int MAXVALUE= 65535;
	public static int MINVALUE= -65535;
	private Hounds main;
	
    Game(Board board, GamePanel controlpanel, Hounds hounds)
    {
    	
    	
        houndList = new int[3];
        moveList = new int[8];
        refList = new int[10][2];
        oppList = new int[10][2];
        bd = board;
        cp = controlpanel;
        main = hounds;
    }
    /**
     *move() method calls the appropriate
     *method to call for the next AI move
     *@param i integer value corresponding to a animal
     */
    void move(int i)
    {
        level = cp.getLevel();
        if(i == 2)
        {
            moveHare();
            return;
        } else
        {
            moveHound();
            return;
        }
    }
    
    /**
	 * moveHare() method gets the move of
	 * the Hare as a computer based on the user level
	 */
    private void moveHare()
    {
        oppPtr = 0;
        source = bd.getHare();//get the current position of the hare
        choices = makeMoveList(source, 2);//gets the number of possible moves
        for(int i = 0; i < choices; i++)
        {
            bd.movePiece(source, moveList[i]);
            if(bd.isWin(2))//to check if there is a winner
                return;
            switch(level)
            {
            default:
                break;

            case 3: // level3
                if(bd.hareIsFree())
                    return;

            case 2: // level2
                if(moveList[i] != 4 && moveList[i] != 6 && bd.isOpposition())
                {
                    oppList[oppPtr][0] = source;
                    oppList[oppPtr++][1] = moveList[i];
                }
                break;
            }
            bd.movePiece(moveList[i], source);//level1
        }

        if(oppPtr != 0)
        {
            choices = makeDecision(oppPtr);// call the heuristic function
            bd.movePiece(oppList[choices][0], oppList[choices][1]);
            return;
        }
        if(level == 3)
        {
            for(int j = 0; j < 10; j++)
                if(bd.testMove(source, harePref[j], 2))// to test if it is a valis move
                {
                    bd.movePiece(source, harePref[j]);
                    return;
                }

        }
        bd.movePiece(source, moveList[makeDecision(choices)]);
    }
    
    /**
	 * moveHound() method gets the move of
	 * the Hound as a computer based on the user level
	 */
    private void moveHound()
    {
        refPtr = oppPtr = 0;
        makeHoundList();// gets the positions where the hounds are
        for(int i = 0; i < 3; i++)
        {
            source = houndList[i];
            choices = makeMoveList(source, 1);//gets the next possible set of moves
            for(int j = 0; j < choices; j++)
            {
                bd.movePiece(source, moveList[j]);
                if(bd.isWin(1))
                    return;
                if(bd.isReference())
                {
                    refList[refPtr][0] = source;
                    refList[refPtr++][1] = moveList[j];
                } else
                if(level != 1 && bd.isOpposition())
                {
                    oppList[oppPtr][0] = source;
                    oppList[oppPtr++][1] = moveList[j];
                }
                bd.movePiece(moveList[j], source);
            }

        }

        if(refPtr != 0)
        {
            choices = makeDecision(refPtr);
            bd.moveHound(refList[choices][0], refList[choices][1]);
            return;
        }
        if(oppPtr != 0)
        {
            choices = makeDecision(oppPtr);
            bd.moveHound(oppList[choices][0], oppList[choices][1]);
            return;
        }
        do
        {
            source = houndList[makeDecision(3)];
            choices = makeMoveList(source, 1);
        } while(choices <= 0);
        bd.moveHound(source, moveList[makeDecision(choices)]);
    }

   
    /**
	 * moveMoveList() method gets the next possible based
	 * on the current positions
	 * @param i the current position
	 * @param j integer corresponding to animal
	 * @return k number of moves
	 */
    private int makeMoveList(int i, int j)
    {
        int k = 0;
        for(int l = 0; l < 11; l++)
            if(bd.testMove(i, l, j))
                moveList[k++] = l;

        return k;
    }

    /**
	 * moveHoundList() method gets the current positions
	 * of the hound
	 */
    private void makeHoundList()
    {
        int i = 0;
        for(int j = 0; j < 11; j++)
            if(bd.isHound(j))
                houndList[i++] = j;

    }

    /**
	 * moveDecision() method is the heuristic function
	 * which is called when alpha beta is used 
	 * @param i the current position
	 * @return best move possible
	 */
    private int makeDecision(int i)
    {
    	int depth=0;
    	int v = MINVALUE;
    	int value = minValue(MINVALUE,MAXVALUE,depth+1);
    	if(value < v)
    	{
    		return value;
    	}
        return (int)((double)i * Math.random());
    }
    
    /**
	 * minValue() method gets the min value based
	 * on the alpha value
	 * @param alpha alpha value used in function
	 * @param beta beta value used in function
	 * @param depth depth of the tree
	 * @return v minimum Value
	 */
    public int minValue( int alpha,int beta,int depth)
    {
    	int v = MAXVALUE;
    	return v;
    }
    
    /**
	 * maxValue() method gets the max value based
	 * on the alpha value
	 * @param alpha alpha value used in function
	 * @param beta beta value used in function
	 * @param depth depth of the tree
	 * @return v maximum Value
	 */
    public int maxValue( int alpha,int beta,int depth)
    {
    	int v = MAXVALUE;
    	return v;
    }

    static final int PLAYER = 0;
    static final int COMPUTER = 1;
    static final int HOUND = 1;
    static final int HARE = 2;
    private int houndList[];
    private int moveList[];
    private int refList[][];
    private int oppList[][];
    private int harePref[] = {
        2, 5, 8, 10, 1, 3, 7, 9, 4, 6
    };
    private int refPtr;
    private int oppPtr;
    private int source;
    private int choices;
    private int level;
    private Board bd;
    private GamePanel cp;
    
}