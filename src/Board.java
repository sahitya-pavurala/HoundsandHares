/**
 * Board class records the content on the UI board
 * @author Sahitya Pavurala
 */
final class Board
{

    Board(GamePanel controlpanel, Hounds hounds)
    {
        b = new int[11];
        cp = controlpanel;
        main = hounds;
        for(int i = 0; i < 11; i++)
            b[i] = 0;

        b[0] = b[1] = b[3] = 1;
        b[10] = 2;
    }
    
    /**
    *isEmpty() check is a particular position
    *is empty
    *@param i integer value corresponding to a position
    *@return boolean value
    */
    boolean isEmpty(int i)
    {
        return b[i] == 0;
    }
    
    /**
     *setEmpty() makes a particular position
     *as empty
     *@param i integer value corresponding to a position
     */
    private void setEmpty(int i)
    {
        b[i] = 0;
    }
    
    /**
     *setSquare() marks the position with 
     *an animal
     *@param i integer value corresponding to a position
     */
    private void setSquare(int i, int j)
    {
        b[i] = j;
    }

    /**
     *getSquare() gets the animal in that
     *position
     *@param i integer value corresponding to a position
     *@return b[i] animal in that position
     */
    int getSquare(int i)
    {
        return b[i];
    }

    /**
     *getHare() checks if a Hare is present 
     * in that position
     *@return true if Hare is in that position
     */    
    int getHare()
    {
        for(int i = 10; i >= 0; i--)
            if(b[i] == 2)
                return i;

        return -1;
    }

    /**
     *isHound() checks if a Hound is present 
     *in that position
     *@param i integer value corresponding to a position
     *@return true if Hound is in that position
     */
    boolean isHound(int i)
    {
        return b[i] == 1;
    }

    /**
     *isValidMove() checks if a particular move is  
     *valid
     *@param i integer value corresponding to a position
     *@param j integer with the opponent positions
     *@param k integer value referring to an animal
     *@return true if the move is valid
     */
    private boolean isValidMove(int i, int j, int k)
    {
        if(i >= 0 && i <= 10 && j >= 0 && j <= 10 && b[j] == 0)
        {
            if(k == 1 && connect[i][j] > 1)
                return true;
            if(k == 2 && connect[i][j] > 0)
                return true;
        }
        return false;
    }

    /**
     *isReference() checks a move with the reference 
     *position using a call to the isRefMatch() method
     *@return true if the move matches
     */
    boolean isReference()
    {
        byte byte0 = 0;
        switch(cp.getLevel())
        {
        case 1: //level1
            byte0 = 42;
            break;

        case 2: // level2
            byte0 = 46;
            break;

        case 3: // level3
            byte0 = 64;
            break;
        }
        for(int i = 0; i < byte0; i++)
            if(isRefMatch(i))
                return true;

        return false;
    }

    /**
     *isRefMatch() checks the move with the reference
     *positions 
     *@param i integer value corresponding to a position 
     *@return true if the move matches
     */
    private boolean isRefMatch(int i)
    {
        for(int j = 0; j < 11; j++)
            if(b[j] != State.positions[i][j])
                return false;

        return true;
    }

    /**
     *isOpposition() checks if a opponent is present at
     *a position
     *@return true if a opponent is present
     */
    boolean isOpposition()
    {	
        int i = 0;
        i += traceSq[getHare()];
        for(int j = 0; j < 11; j++)
            if(isHound(j))
                i += traceSq[j];

        return i % 3 == 0;
    }

    /**
     *hareIsFree() checks if the Hare is free 
     *from the hounds
     *@return true if the Hare is free
     */
    boolean hareIsFree()
    {
        int i = getHare();
        int j = 0;
        if(i == 10)
            return false;
        if(i == 5 && (b[4] == 1 || b[6] == 1))
            return true;
        i--;
        for(int k = 10; k >= 0; k--)
            if(b[k] == 1 && (k - 1) / 3 > i / 3)
                j++;

        if(j == 1)
        {
            if(i != 3 && i != 5)
                return true;
            if(b[5] == 1 && (b[4] == 1 || b[6] == 1))
                return true;
        } else
        if(j > 1)
            return true;
        return false;
    }
    
    /**
     *isWin() checks if a move to a particular position
     *will give a final result
     *@param i integer value corresponding to a particular position
     *@return true if a there is a decision
     */
    boolean isWin(int i)
    {
        if(i == 2)
        {
            if(hareEscapes())
            {
                main.setStatus(main.sHARE_WINS);
                return true;
            }
            if(stallCount == 10)
            {
                main.setStatus(main.sSTALLING);
                return true;
            }
        } else
        if(hareIsTrapped())
        {
            main.setStatus(main.sHOUNDS_WIN);
            return true;
        }
        return false;
    }

    /**
     *hareIsTrapped() checks if the Hare is trapped
     *by the hounds
     *@return true if the Hare is trapped
     */
    private boolean hareIsTrapped()
    {
        switch(getHare())
        {
        default:
            break;

        case 4:
            if(b[1] == 1 && b[5] == 1 && b[7] == 1)
                return true;
            break;

        case 6: 
            if(b[3] == 1 && b[5] == 1 && b[9] == 1)
                return true;
            break;

        case 10: 
            if(b[7] == 1 && b[8] == 1 && b[9] == 1)
                return true;
            break;
        }
        return false;
    }

    /**
     *hareEscapes() checks if the Hare is free 
     *from the hounds
     *@return true if the Hare is free
     */
    private boolean hareEscapes()
    {
        int i = getHare();
        if(i == 0)
            return true;
        i--;
        for(int j = 0; j < 10; j++)
            if(b[j] == 1)
                return (j - 1) / 3 > i / 3;

        return false;
    }

    /**
     *selectPiece() checks if animal selected
     *on the board corresponds to a particular integer 
     *@param i integer value corresponding to a particular animal
     *@param j integer value corresponding to a particular animal
     *@return true if both i and j correspond to same animal
     */
    boolean selectPiece(int i, int j)
    {
        if(j >= 0 && j <= 10 && i == getSquare(j))
        {
            setEmpty(j);
            return true;
        } else
        {
            return false;
        }
    }

    /**
     *selectPiece() checks if animal selected
     *on the board corresponds to a particular integer 
     *@param i integer value corresponding to a particular animal
     *@param j integer value corresponding to a particular animal
     *@return true if both i and j correspond to same animal
     */
    boolean movePlayerPiece(int i, int j)
    {
        int k = main.getPlayerAnimal();
        if(isValidMove(i, j, k))
        {
            if(k == 1)
                if(connect[i][j] == 2)
                    stallCount++;
                else
                    stallCount = 0;
            setSquare(j, k);
            return true;
        } else
        {
            setSquare(i, k);
            return false;
        }
    }

    /**
     *moveHound() moves the Hounds to a given position 
     *@param i index value in the connect array
     *@param j index value in the connect array
     */
    void moveHound(int i, int j)
    {
        if(connect[i][j] == 2)
            stallCount++;
        else
            stallCount = 0;
        movePiece(i, j);
    }

    /**
     *movePiece() moves the animal image to a given position
     *also setting the previous position empty 
     *@param i integer value corresponding to a particular animal
     *@param j integer value corresponding to a particular animal
     */
    void movePiece(int i, int j)
    {
        b[j] = b[i];
        setEmpty(i);
    }

    /**
     *testMove() test is a move is valid
     *also setting the previous position empty 
     *@param i index value in the connect array
     *@param j index value in the connect array
     *@param k integer value corresponding to a particular animal
     *@return true is a move is valid
     */
    boolean testMove(int i, int j, int k)
    {
        if(b[j] == 0)
        {
            if(k == 1 && connect[i][j] > 1)
                return true;
            if(k == 2 && connect[i][j] > 0)
                return true;
        }
        return false;
    }

    static final int EMPTY = 0;
    static final int HOUND = 1;
    static final int HARE = 2;
    static final int connect[][] = {
        {
            0, 3, 3, 3, 0, 0, 0, 0, 0, 0,
            0
        }, {
            1, 0, 2, 0, 3, 3, 0, 0, 0, 0,
            0
        }, {
            1, 2, 0, 2, 0, 3, 0, 0, 0, 0,
            0
        }, {
            1, 0, 2, 0, 0, 3, 3, 0, 0, 0,
            0
        }, {
            0, 1, 0, 0, 0, 2, 0, 3, 0, 0,
            0
        }, {
            0, 1, 1, 1, 2, 0, 2, 3, 3, 3,
            0
        }, {
            0, 0, 0, 1, 0, 2, 0, 0, 0, 3,
            0
        }, {
            0, 0, 0, 0, 1, 1, 0, 0, 2, 0,
            3
        }, {
            0, 0, 0, 0, 0, 1, 0, 2, 0, 2,
            3
        }, {
            0, 0, 0, 0, 0, 1, 1, 0, 2, 0,
            3
        }, {
            0, 0, 0, 0, 0, 0, 0, 1, 1, 1,
            0
        }
    };
    private int b[];
    private int traceSq[] = {
        5, 3, 4, 3, 1, 2, 1, 0, 1, 0,
        -1
    };
    private int stallCount;
    private GamePanel cp;
    private Hounds main;

}