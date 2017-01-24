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
		int i = (int)(Math.random()*6) + 1;
		block = new Block(i);
		System.out.printf("블록 생성 시 i 값 : %d \n", i);
		addBlock(block);
	}
	
	// 게임 보드에 새로 만들어진 블럭을 추가
	void addBlock(Block block)
	{
		
		System.out.println("addBlock함수 호출");
		Point relativeBlockPoint;
		for(int i = 0; i < 4; i++)
		{
			relativeBlockPoint = block.relative[i];
			
			if(block.absolute.y+relativeBlockPoint.y>=0) // 회전이 아닌 좌우아래 이동일 경우
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
