import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;


public class Block implements MovingBlock
{
	KeyEvent keyEvent;
	private int type;
	
	static Color blockColor = Color.RED;
	
	// 블럭의 좌표값을 나타내는 포인트
	
	Point absolute; // 절대값
	Point[] relative = new Point[4]; // 절대값으로부터 상대적인 위치(하나의 블럭은 4칸이다)
	
	
	// 블럭의 종류를 나타내는 상태변수
	static final int BLOCK_SHAPE_I = 0;
	static final int BLOCK_SHAPE_J = 1;
	static final int BLOCK_SHAPE_L = 2;
	static final int BLOCK_SHAPE_O = 3;
	static final int BLOCK_SHAPE_S = 4;
	static final int BLOCK_SHAPE_T = 5;
	static final int BLOCK_SHAPE_Z = 6;
	
	public Block(int type)
	{
		this.type = type;
		initBlock(this.type);
	}
	
	void initBlock(int type)
	{
		absolute = new Point(Board.col/2, 0); // 초기 위치
		
		this.type = type;
		switch(type)
		{
		case BLOCK_SHAPE_I:
//			System.out.println("I");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(-1, 0);
			relative[2] = new Point(1, 0);
			relative[3] = new Point(2, 0);
			break;
			
		case BLOCK_SHAPE_J:
//			System.out.println("J");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(-1, 0);
			relative[2] = new Point(1, 0);
			relative[3] = new Point(1, -1);
			break;
			
		case BLOCK_SHAPE_L:
//			System.out.println("L");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(-1, 0);
			relative[2] = new Point(1, 0);
			relative[3] = new Point(1, -1);
			break;
			
		case BLOCK_SHAPE_O:
//			System.out.println("O");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(1, 0);
			relative[2] = new Point(1, 1);
			relative[3] = new Point(0, 1);
			break;
			
		case BLOCK_SHAPE_S: 
//			System.out.println("S");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(-1, 0);
			relative[2] = new Point(0, -1);
			relative[3] = new Point(1, -1);
			break;
			
		case BLOCK_SHAPE_T:
//			System.out.println("T");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(-1, 0);
			relative[2] = new Point(1, 0);
			relative[3] = new Point(0, -1);
			break;
			
		case BLOCK_SHAPE_Z:
//			System.out.println("Z");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(0, -1);
			relative[2] = new Point(1, 0);
			relative[3] = new Point(-1, -1);
		}
	}
	
	void rotateBlock() // 블럭을 돌린다.
	{
		for(int i = 0; i < 4; i++)
		{
			int temp = relative[i].x;
			relative[i].x = -relative[i].y;
			relative[i].y = temp;
		}
	}
	
	void backRotateBlock()
	{
		for(int i = 0; i < 4; i++)
		{
			int temp = relative[i].y;
			relative[i].y = -relative[i].x;
			relative[i].x = temp;
		}
	}
	
	@Override
	public void move(int key) // 블럭을 해당 키에 맞게 움직인다.
	{
		switch(key)
		{
			case KeyEvent.VK_UP:
				rotateBlock();
				break;
		
			case KeyEvent.VK_DOWN:
				absolute.y++;
				break;
				
			case KeyEvent.VK_LEFT:
				absolute.x--;
				break;
				
			case KeyEvent.VK_RIGHT:
				absolute.x++;
				break;
		}
	}
	
	// 현재 블럭이 엣지일 경우에만 이 함수가 호출된다.
	void restoreBlockToPreState(int key)
	{
		switch(key)
		{
			case KeyEvent.VK_RIGHT:
				absolute.x--;
				break;
				
			case KeyEvent.VK_LEFT:
				absolute.x++;
				break;
				
			case KeyEvent.VK_DOWN:
				absolute.y--;
				break;
				
			case KeyEvent.VK_UP:
				backRotateBlock();
				break;
				
			case KeyEvent.VK_SPACE:
				absolute.y--;
				break;
		}		
	}
	
	
	boolean isItEdge() // 블럭이 화면의 edge에 있는지 검사하기
	{
		
		// 화면의 edge를 나타내는 데이터는..
		
		for(int i = 0; i < relative.length; i++)
		{
			// 위 아래 엣지 판별
			if(absolute.y+relative[i].y >= Board.row || absolute.y+relative[i].y < 0)
				return true;
				
			// 좌우 엣지 판별
			if(absolute.x+relative[i].x >= Board.col || absolute.x+relative[i].x < 0)
				return true;
		}
		
		return false; // for문을 빠져나왔다면 edge가 아니라는 것임
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
//		sb.append("현재 블럭 Type : " + type);
//		sb.append("\n현재 relative[" + 0 + "] = " + relative[0]);
//		sb.append("\n현재 relative[" + 1 + "] = " + relative[1]);
//		sb.append("\n현재 relative[" + 2 + "] = " + relative[2]);
//		sb.append("\n현재 relative[" + 3 + "] = " + relative[3]);
		for(int i = 0; i < relative.length; i++)
		{
			sb.append("블록의 각각 요소의 현재 x : ");
			sb.append(relative[i].x+absolute.x);
			sb.append(", 블록의 각각 요소의 현재 y : ");
			sb.append(relative[i].y+absolute.y);
			sb.append("\n");
		}
			
		return sb.toString();
	}
}
