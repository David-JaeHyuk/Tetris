import java.awt.Color;
import java.awt.Point;

public class Board 
{ 	
	// 보드의 세로길이
	static int row = 40;
	
	// 보드의 가로길이
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
	
	// 블럭을 만든다
	void makeBlock()
	{
		int i = (int)(Math.random()*6+1);
		block = new Block(i);
		
		addBlock(block);
	}
	
	// 게임 보드에 블럭 추가
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
