import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class Board
{ 	
	// ������ ���α���
	static int row = 22;
	
	// ������ ���α���
	static int col= 12;
	Color[][] gameBoard;
	Color[][] nextBlockBoard;
	
	Block block;
	Block nextBlock;
	
	Color bgColor = Color.BLACK;
	Color nextBlockBoardColor = Color.white;
	int beforeX, beforeY;
	
	public Board()
	{
		System.out.println("���� ��ü ����");
		initBoard();
	}
	
	void initBoard()
	{
		gameBoard = new Color[row][col];
		for(int i = 0; i < row; i++)
			for(int j = 0; j < col; j++)
				gameBoard[i][j] = bgColor;
	}
	
	void makeNextBlock()
	{
		int i = (int)(Math.random()*7);
		nextBlock = new Block(i);
	}
	
	// �� ����
	void makeBlock()
	{
		int i = (int)(Math.random()*7);
		block = new Block(i);
		nextBlockBoard = new Color[4][4];
	}
	
	// ���� ���忡 ���� ������� �� Ȥ�� ������ �������� ���� ��� �׷���
	void addBlock()
	{
		for(int i = 0; i < 4; i++)
			if(!block.isItEdge()) 
				gameBoard[block.absolute.y+block.relative[i].y][block.absolute.x+block.relative[i].x] = Block.blockColor;
	}
	
	void removeBlockOnBoard()
	{
		Point ap = block.absolute;
//		System.out.println("remove �� ���� ����");
//		printBoardInfo();
		if(!block.isItEdge())
		{
			for(int i = 0; i < 4; i++)
			{
				Point p = block.relative[i];
				gameBoard[ap.y+p.y][ap.x+p.x] = bgColor;
			}
		}
	}
	
	// ������ ���� ��ȿ���� üũ �� ���ο� ���� �ʿ��� ��� �� �� ����
	boolean needMakeNewBlock()
	{
		// ���� �ؿ� ������ ����̰ų�
		// �����̷��� �ڸ��� �̹� �ٸ� ������ ���ִ� ��� �� �� ����
		for(int i = 0; i < block.relative.length; i++)
		{
			if(block.absolute.y + block.relative[i].y >= row)
			{
				System.out.println("���� �شܱ��� �����Ƿ� ������ ����");
				return true;
			}
			
//			System.out.print(block.absolute.y+block.relative[i].y + ", ");
//			System.out.println(block.absolute.x + block.relative[i].x);
			
			// array index�� ������ ������ ������ ����ó�� �ȶ߰� �ϱ� ���� �߰��� �ڵ�
			if(block.absolute.y + block.relative[i].y >= 0  
					&& block.absolute.x + block.relative[i].x >=0 
					&& block.absolute.x + block.relative[i].x < col)
			{
				if(gameBoard[block.absolute.y + block.relative[i].y][block.absolute.x + block.relative[i].x] != bgColor)
				{
					System.out.println("�����̷��� �ڸ��� �̹� ���� ������ ������ ����");
					return true;
				}
			}	
		}
		return false;
	}
	
	int checkLine()
	{
		boolean removeFlag = false;
		for(int i = 0; i < gameBoard.length; i++)
		{
			for(int j = 0; j < gameBoard[i].length; j++)
			{
				if(gameBoard[i][j] == Block.blockColor) // ������ Ž���ϵ�, �ش� ���� i�� ���� j for���� ��� ��ĥ ��� �ش� ���� �ѹ��� ����
				{
					removeFlag = true;
					continue;
				}
				else
				{
					removeFlag = false;
					break;
				}		
			}
			if(removeFlag) // �ش� ������ ����
				return i;
		}
		
		return -1; // for���� �׳� ��� ���������� ��� remove�� ������ ����
			
	}
	
	/* 
	 * ������ �����ؾ� �Ѵٸ� �ش� ������ �� ���������� ĥ�ϰ�,
	 * �ش� ���� ���� ���θ� ��� ��ĭ�� ������ ������, �� �������� ������ ������� ĥ��
	 */
	void removeLine(int removeLineNum)
	{
		for(int k = 0; k < col; k++)
			gameBoard[removeLineNum][k] = bgColor; // �ش� ������ ���� ���������� ĥ��
		
		for(int i = 0; i < removeLineNum; i++)
		{
			for(int j = 0; j < col; j++)
				gameBoard[removeLineNum-i][j] = gameBoard[removeLineNum-i-1][j];
		}
	}
	
	void printBoardInfo()
	{
		System.out.println(block);
	}
	
	void spaceMove() // �����̽����� ��� ���� �ִ� ��ġ���� ���� ���� ������.
	{
		for(int i = 0; i < row; i++)
			for(int j = 0; j < col; j++)
			{
				if(!block.isItEdge())
					if(gameBoard[block.absolute.y + block.relative[0].y][block.absolute.x + block.relative[0].x] != Block.blockColor
					&& gameBoard[block.absolute.y + block.relative[1].y][block.absolute.x + block.relative[1].x] != Block.blockColor
					&& gameBoard[block.absolute.y + block.relative[2].y][block.absolute.x + block.relative[2].x] != Block.blockColor
					&& gameBoard[block.absolute.y + block.relative[3].y][block.absolute.x + block.relative[3].x] != Block.blockColor)
						block.absolute.y++;
					else
						return;	
			
				
				
			}

	}
}
