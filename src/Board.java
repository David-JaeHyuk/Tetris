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
	
	// �� ����
	void makeBlock()
	{
		int i = (int)(Math.random()*6+1);
		block = new Block(i);
		addBlock();
	}
	
	// ���� ���忡 ���� ������� �� Ȥ�� ������ �������� ���� ��� �׷���
	void addBlock()
	{
		for(int i = 0; i < 4; i++)
			if(!block.isItEdge()) 
				gameBoard[block.absolute.y+block.relative[i].y][block.absolute.x+block.relative[i].x] = Color.white;
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
	
	void removeLine()
	{
		
	}
	
	void printBoardInfo()
	{
		System.out.println(block);
	}
}
