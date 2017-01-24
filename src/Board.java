import java.awt.Color;
import java.awt.Point;

public class Board 
{ 	
	// 보드의 세로길이
	static int row = 40;
	
	// 보드의 가로길이
	static int col= 20;
	Color[][] gameBoard;
	Block block;
	Color bgColor = Color.BLACK;
	int beforeX, beforeY;
	
	public Board()
	{
		System.out.println("보드 객체 생성");
		initBoard();
	}
	
	void initBoard()
	{
		gameBoard = new Color[row][col];
		for(int i = 0; i < row; i++)
			for(int j = 0; j < col; j++)
				gameBoard[i][j] = bgColor;
	}
	
	// 블럭 생성
	void makeBlock()
	{
		int i = (int)(Math.random()*6+1);
		block = new Block(i);
		addBlock();
	}
	
	// 게임 보드에 새로 만들어진 블럭 혹은 밑으로 내려가는 블럭을 계속 그려줌
	void addBlock()
	{
		for(int i = 0; i < 4; i++)
			if(!block.isItEdge()) 
				gameBoard[block.absolute.y+block.relative[i].y][block.absolute.x+block.relative[i].x] = Color.white;
	}
	
	void removeBlockOnBoard()
	{
		Point ap = block.absolute;
//		System.out.println("remove 시 블럭의 정보");
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
	
	// 움직인 블럭이 유효한지 체크 및 새로운 블럭이 필요한 경우 새 블럭 생성
	boolean needMakeNewBlock()
	{
		// 가장 밑에 도달한 경우이거나
		// 움직이려는 자리가 이미 다른 블럭으로 차있는 경우 새 블럭 생성
		for(int i = 0; i < block.relative.length; i++)
		{
			if(block.absolute.y + block.relative[i].y >= row)
			{
				System.out.println("가장 밑단까지 왔으므로 새블럭을 생성");
				return true;
			}
			
//			System.out.print(block.absolute.y+block.relative[i].y + ", ");
//			System.out.println(block.absolute.x + block.relative[i].x);
			
			// array index를 엣지에 갖고갔을 때에는 예외처리 안뜨게 하기 위해 추가한 코드
			if(block.absolute.y + block.relative[i].y >= 0 
					&& block.absolute.x + block.relative[i].x >=0 
					&& block.absolute.x + block.relative[i].x < col)
			{
				if(gameBoard[block.absolute.y + block.relative[i].y][block.absolute.x + block.relative[i].x] != bgColor)
				{
					System.out.println("움직이려는 자리에 이미 블럭이 있으니 새블럭을 생성");
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
