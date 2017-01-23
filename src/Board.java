import java.awt.Color;
import java.awt.Point;

public class Board 
{ 	
	// ������ ���α���
	static int row = 40;
	
	// ������ ���α���
	static int col= 20;
	Color[][] gameBoard;
	Block block;
	Color bgColor = Color.BLACK;
	int beforeX, beforeY;
	
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
		int i = (int)(Math.random()*6) + 1;
		block = new Block(i);
		System.out.printf("��� ���� �� i �� : %d \n", i);
		addBlock(block);
	}
	
	// ���� ���忡 ���� ������� ���� �߰�
	void addBlock(Block block)
	{
		
		System.out.println("addBlock�Լ� ȣ��");
		Point relativeBlockPoint;
		for(int i = 0; i < 4; i++)
		{
			relativeBlockPoint = block.relative[i];
			
			if(block.absolute.y+relativeBlockPoint.y>=0) // ȸ���� �ƴ� �¿�Ʒ� �̵��� ���
				gameBoard[block.absolute.y+relativeBlockPoint.y][block.absolute.x+relativeBlockPoint.x] = Color.white;

		}
	}
	
	void removeBlockOnBoard()
	{
		for(int i = 0; i < row; i++)
			for(int j = 0; j < col; j++)
				if(gameBoard[i][j] != bgColor)
					gameBoard[i][j] = bgColor;
	}
	
}
