import java.awt.Point;
import java.awt.event.KeyEvent;

public class Block
{
	KeyEvent keyEvent;
	private int type;
	
	// 블럭의 좌표값을 나타내는 포인트
	Point absolute; // 절대값
	
	Point[] relative = new Point[4]; // 절대값으로부터 상대적인 위치
	
	
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
			System.out.println("I");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(-1, 0);
			relative[2] = new Point(1, 0);
			relative[3] = new Point(2, 0);
			break;
			
		case BLOCK_SHAPE_J:
			System.out.println("J");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(0, -1);
			relative[2] = new Point(1, -1);
			relative[3] = new Point(1, -2);
			break;
			
		case BLOCK_SHAPE_L:
			System.out.println("L");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(0, -1);
			relative[2] = new Point(1, -1);
			relative[3] = new Point(1, -2);
			break;
			
		case BLOCK_SHAPE_O:
			System.out.println("L");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(1, 0);
			relative[2] = new Point(1, 1);
			relative[3] = new Point(0, 1);
			break;
			
		case BLOCK_SHAPE_S: 
			System.out.println("S");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(1, 0);
			relative[2] = new Point(1, 1);
			relative[3] = new Point(2, 1);
			break;
			
		case BLOCK_SHAPE_T:
			System.out.println("T");
			relative[0] = new Point(0, 0);
			relative[1] = new Point(0, 1);
			relative[2] = new Point(0, 2);
			relative[3] = new Point(1, 1);
			break;
			
		case BLOCK_SHAPE_Z:
			System.out.println("Z");
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
	
	void move(int key) // 블럭을 해당 키에 맞게 움직인다.
	{
		switch(key)
		{
			case KeyEvent.VK_DOWN:
				absolute.y += 1;
				break;
				
			case KeyEvent.VK_LEFT:
				absolute.x -= 1;
				break;
				
			case KeyEvent.VK_RIGHT:
				absolute.x += 1;
				break;
		}
	}
	
	void isItEdgeRotation() // 블럭이 화면의 edge에 있는지 검사하기
	{
		
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("현재 블럭 Type : " + type);
		sb.append("\n현재 relative[" + 0 + "] = " + relative[0]);
		sb.append("\n현재 relative[" + 1 + "] = " + relative[1]);
		sb.append("\n현재 relative[" + 2 + "] = " + relative[2]);
		sb.append("\n현재 relative[" + 3 + "] = " + relative[3]);
		
		return sb.toString();
	}
}
