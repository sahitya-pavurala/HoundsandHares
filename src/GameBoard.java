
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.RepaintManager;

/**
 * GameBoard class is used to give the content to the UI
 * with images 
 * @author Sahitya Pavurala
 */
final class GameBoard extends Canvas
{

    GameBoard(Image image, Image image1, Hounds hounds)
    {
        boardImage = image;
        gifletsImage = image1;
        main = hounds;
        tracker = new MediaTracker(hounds);
        hareImage = extractImage(hareCoord);
        houndImage = extractImage(houndCoord);
        try
        {
            tracker.waitForAll();
            return;
        }
        catch(InterruptedException _ex)
        {
            return;
        }
    }

    /**
	 * drawBoard() calls the drawBoard method using the parameter
	 *@param Board gets the current content on the game board 
	 */
    void drawBoard(Board board)
    {
        drawBoard(board, -1, -1);
    }

    /**
	 * drawBoard() calls the drawBoard method using the parameter
	 *@param board gets the current content on the game board 
	 *@param i represents a certain animal
	 *@param j represents a certain animal
	 */
    void drawBoard(Board board, int i, int j)
    {
    	RepaintManager.currentManager(null).setDoubleBufferingEnabled(false);
        if(buffer == null)
        {
            bufferImage = createImage(440, 276);
            buffer = bufferImage.getGraphics();
        }
        buffer.drawImage(boardImage, 0, 0, main.BOARD_BG_COLOR, this);
        for(int k = 0; k < 11; k++)
            if(board.getSquare(k) == 2)
                buffer.drawImage(hareImage, squareToPixelX(k), squareToPixelY(k), this);
            else
            	if(board.getSquare(k) == 1)
                buffer.drawImage(houndImage, squareToPixelX(k), squareToPixelY(k), this);

        if(i != -1 && j != -1)
            if(main.getPlayerAnimal() == 1)
                buffer.drawImage(houndImage, i - 22, j - 22, this);
            else
                buffer.drawImage(hareImage, i - 22, j - 22, this);
        repaint();
    }
    @Override
	// Here we are over riding the paint method of Canvas class
    public void paint(Graphics g)
    {
        update(g);
    }
    @Override
	// Here we are over riding the update method of Canvas class
    public void update(Graphics g)
    {
        g.drawImage(bufferImage, 0, 0, this);
    }

    /**
	 * pixelToSquare() maps the images of hound and hare to
	 * a particular position on the board
	 * @param i coordinates of the image
	 * @param j coordinates of the image
	 */
    int pixelToSquare(int i, int j)
    {
        for(int k = 0; k < 11; k++)
            if(pixelCoord[k][0] + 20 <= i && i <= pixelCoord[k][0] + 20 + 45 && pixelCoord[k][1] + 20 <= j && j <= pixelCoord[k][1] + 20 + 45)
                return k;

        return -1;
    }

    /**
	 * SquareToPixel() maps a particular position to the 
	 * images of hounds and hare
	 * @param i coordinates of the image
	 */
    private int squareToPixelX(int i)
    {
        return pixelCoord[i][0] + 20;
    }

    /**
	 * SquareToPixel() maps a particular position to the 
	 * images of hounds and hare in the Y direction
	 * @param i coordinates of the image
	 */
    private int squareToPixelY(int i)
    {
        return pixelCoord[i][1] + 20;
    }

    @Override
	// Here we are over riding the mouseDrag method of Component class
    public boolean mouseDrag(Event event, int i, int j)
    {
        main.dragPiece(i, j);
        return true;
    }

    @Override
   	// Here we are over riding the mouseUp method of Component class
    public boolean mouseUp(Event event, int i, int j)
    {
        main.dropPiece(i, j);
        return true;
    }

    @Override
   	// Here we are over riding the mouseDown method of Component class
    public boolean mouseDown(Event event, int i, int j)
    {
        main.selectPiece(i, j);
        return true;
    }

    /**
   	 * extractImage() crops the giflets image 
   	 * @param ai coordinates of the image
   	 */
    private Image extractImage(int ai[])
    {
        CropImageFilter cropimagefilter = new CropImageFilter(ai[0], ai[1], ai[2], ai[3]);
        FilteredImageSource filteredimagesource = new FilteredImageSource(gifletsImage.getSource(), cropimagefilter);
        Image image = main.createImage(filteredimagesource);
        tracker.addImage(image, 0);
        return image;
    }

    static final int hareCoord[] = {
        0, 0, 45, 45
    };
    static final int houndCoord[] = {
        45, 0, 45, 45
    };
    static final int pixelCoord[][] = {
        {
            14, 96
        }, {
            96, 14
        }, {
            96, 96
        }, {
            96, 178
        }, {
            178, 14
        }, {
            178, 96
        }, {
            178, 178
        }, {
            260, 14
        }, {
            260, 96
        }, {
            260, 178
        }, {
            342, 96
        }
    };
    static final int SQUARE = 45;
    static final int OFFSET = 20;
    static final Color panelColor = new Color(255, 200, 0);
    private Image bufferImage;
    private Image boardImage;
    private Image gifletsImage;
    private Image hareImage;
    private Image houndImage;
    private MediaTracker tracker;
    private Graphics buffer;
    private Hounds main;

}