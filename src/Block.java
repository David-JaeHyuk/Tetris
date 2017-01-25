import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;


public class Block implements MovingBlock
{
	KeyEvent keyEvent;
	private int type;
	
	static Color blockColor = Color.RED;
	
	// ���� ��ǥ���� ��Ÿ���� ����Ʈ
	
	Point absolute; // ���밪
	Point[] relative = new Point[4]; // ���밪���κ��� ������� ��ġ(�ϳ��� ���� 4ĭ�̴�)
	
	
	// ���� ������ ��Ÿ���� ���º���
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
		absolute = new Point(Board.col/2, 0); // �ʱ� ��ġ
		
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
	
	void rotateBlock() // ���� ������.
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
	public void move(int key) // ���� �ش� Ű�� �°� �����δ�.
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
	
	// ���� ���� ������ ��쿡�� �� �Լ��� ȣ��ȴ�.
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
	
	
	boolean isItEdge() // ���� ȭ���� edge�� �ִ��� �˻��ϱ�
	{
		
		// ȭ���� edge�� ��Ÿ���� �����ʹ�..
		
		for(int i = 0; i < relative.length; i++)
		{
			// �� �Ʒ� ���� �Ǻ�
			if(absolute.y+relative[i].y >= Board.row || absolute.y+relative[i].y < 0)
				return true;
				
			// �¿� ���� �Ǻ�
			if(absolute.x+relative[i].x >= Board.col || absolute.x+relative[i].x < 0)
				return true;
		}
		
		return false; // for���� �������Դٸ� edge�� �ƴ϶�� ����
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
//		sb.append("���� �� Type : " + type);
//		sb.append("\n���� relative[" + 0 + "] = " + relative[0]);
//		sb.append("\n���� relative[" + 1 + "] = " + relative[1]);
//		sb.append("\n���� relative[" + 2 + "] = " + relative[2]);
//		sb.append("\n���� relative[" + 3 + "] = " + relative[3]);
		for(int i = 0; i < relative.length; i++)
		{
			sb.append("����� ���� ����� ���� x : ");
			sb.append(relative[i].x+absolute.x);
			sb.append(", ����� ���� ����� ���� y : ");
			sb.append(relative[i].y+absolute.y);
			sb.append("\n");
		}
			
		return sb.toString();
	}
}
