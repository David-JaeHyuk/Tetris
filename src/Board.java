import java.awt.Color;
import java.awt.Point;

public class Board 
{ 	
	// ������ ���α���
	static int row = 40;
	
	// ������ ���α���
	static int col= 20;
	static Color[][] gameBoard;
	Block block;
	Color bgColor = Color.BLACK;
	
	public Board()
	{
		initBoard();
	}
	
	void initBoard()
	{
		gameBoard = new Color[row][col];
		for(int i = 0; i < row; i++)
			for(int j = 0; j < col; j++)
				gameBoard[i][j] = bgColor;
	}
	
	// ���� �����
	void makeBlock()
	{
		int i = (int)(Math.random()*6+1);
		block = new Block(i);
		
		addBlock(block);
	}
	
	// ���� ���忡 �� �߰�
	void addBlock(Block block)
	{
		Point p;
		for(int i = 0; i < 4; i++)
		{
			p = block.relative[i];
			if(block.absolute.y+p.y>=0)
				gameBoard[block.absolute.y+p.y][block.absolute.x+p.x] = Color.white;
		}
	}
	
}
