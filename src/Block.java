import java.awt.Point;
import java.awt.event.KeyEvent;

public class Block
{
	KeyEvent keyEvent;
	// ���� ��ǥ���� ��Ÿ���� ����Ʈ
	Point absolute;
	private int type;
	
	Point[] relative = new Point[4];
	
	
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
		initBlock(type);
	}
	
	void initBlock(int type)
	{
		absolute = new Point(Board.col/2, 0); // �ʱ� ��ġ
		
		this.type = type;
		switch(type)
		{
		case BLOCK_SHAPE_I:
			relative[0] = new Point(0, 0);
			relative[1] = new Point(-1, 0);
			relative[2] = new Point(1, 0);
			relative[3] = new Point(2, 0);
			break;
			
		case BLOCK_SHAPE_J:
			relative[0] = new Point(0, 0);
			relative[1] = new Point(0, -1);
			relative[2] = new Point(1, -1);
			relative[3] = new Point(1, -2);
			break;
			
		case BLOCK_SHAPE_L:
			relative[0] = new Point(0, 0);
			relative[1] = new Point(0, -1);
			relative[2] = new Point(1, -1);
			relative[3] = new Point(1, -2);
			break;
			
		case BLOCK_SHAPE_S: 
			relative[0] = new Point(0, 0);
			relative[1] = new Point(1, 0);
			relative[2] = new Point(1, 1);
			relative[3] = new Point(2, 1);
			break;
			
		case BLOCK_SHAPE_T:
			relative[0] = new Point(0, 0);
			relative[1] = new Point(0, 1);
			relative[2] = new Point(0, 2);
			relative[3] = new Point(1, 1);
			break;
			
		case BLOCK_SHAPE_Z:
			relative[0] = new Point(0, 0);
			relative[1] = new Point(0, -1);
			relative[2] = new Point(1, 0);
			relative[3] = new Point(-1, -1);
		}
	}
	
	void rotateBlock() // ���� ������.
	{
		for(int count = 0; count < 4; count++)
		{
			int temp = relative[count].x;
			
			relative[count].x = -relative[count].y;
			relative[count].y = temp;
		}
	}
	
	void isItEdgeRotation()
	{
		
	}
}
