import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * 1) ���� ����
 * 2) �� ����(���� �����ÿ� ���� ���ڸ��� ã�ư��� ���� ����)
 * 3) KeyProcessing
 * 4) ���� ���� ��� �׿����� ����, �ƴ϶�� 2������ ���ư�
 * 
 */

class TetrisView extends Frame implements KeyListener
{
	Image buffer;
	Graphics bufferG;
	Board board;
	DownThread downThread;
	boolean startFlag;
	
	int size = 10;
	int startX = 50, startY = 50;
	
	WinEvent w = new WinEvent();
	public TetrisView()
	{
		super("MyTetris");
		board = new Board(); // ���� ���� ����
		setBounds(120, 120, 300, 500);
	    addKeyListener(this);
	    setVisible(true);
	    addWindowListener(w);
	}
	
	@Override
	synchronized public void keyPressed(KeyEvent e) 
	{
		int a = e.getKeyCode();
		if(a == e.VK_ENTER || startFlag) // ����Ű �Է½� ���� ����
		{
			downThread = new DownThread();
			if(!startFlag) // ���ʷ� ���� ����� ����̴�.
			{
				board.makeBlock();
				System.out.println("����Ű ������ ������ ����!");
				downThread.start();
				
				startFlag = true;
			}
			
			KeyProcessing(e.getKeyCode());
		}
	}

	/* KeyProcessing ����
	 * 1) ���� �������� �ִ� �� ���� 
	 * 2) Ű���� ������ �� �̵�
	 * 3) �������� ���� �� �Ʒ��� �����߰ų� �ƴϸ� �̹� �ٸ� ���� �ִ� �ڸ��� ��� �� ���� �����ϰ� ���� ���� ȭ�鿡 �׷��� 
	 * 4) ���� ���� ȭ�鿡 �׷���
	 * 		 
	 */
	
	public void KeyProcessing(int key)
	{
//		System.out.println("KeyProcessing �� key �� : " + key);
		board.removeBlockOnBoard(); // ���� �����̴� �� �� ���� ���� �ִ� �� ����(���� ������ ���� �״�� ���־���)
		
		board.block.move(key); // Ű���尡 � Ű�� ���ȴ����� ���� ���忡 �ִ� ���� �̵���Ŵ
		
		boolean needMakeNewBlock = board.needMakeNewBlock(); // �� ���� �����ؾ��ϴ��� �Ǻ�
		
		if(needMakeNewBlock) // ������ �����ؾ� �ϴ� ��Ȳ�̶��(=���� ���� �ִ� �ڸ��� �������ų� �� ���ٿ� �ٴ޾Ҵٸ�)
		{
			board.block.restoreBlockToPreState(key); // �̵��� ��ġ ���
			board.addBlock(); // �� �� ���� ������ �������� �״�� ȭ�鿡 ����
			board.makeBlock(); // �� �� ����
			System.out.println("������ �����߽��ϴ�");
		}
		
		else if(board.block.isItEdge()) // �� ���� �����ؾ� �ϴ� ��Ȳ�� �ƴϸ�, 
										// ���� ���� ������ �ִٸ� ��� �� ���� Ű������ �������·� �ǵ�����.
			board.block.restoreBlockToPreState(key);
	
		board.addBlock(); // �� �� �� �Ʒ� �����ӿ� ���� �� Ȥ�� ���� ���� �ش� ���� ȭ�鿡 ���������μ� ���� ������Ʈ
		
		repaint(); // ������Ʈ�� ���带 �ٽ� ȭ�鿡 �׷���
	}

	@Override
	public void update(Graphics g) // repaint()�� ȣ��� �� �ڵ����� ȣ��Ǵ� �޼ҵ�
	{
		paint(g);
	}
	
	// ������ �ִ� ���� ��� �״�� ���ܳ��� ���ο� ���� �����ϱ�? 
	
	@Override
	public void paint(Graphics g) // ȭ�鿡 �׸��� �׷��ִ� �������̵��� �޼ҵ�
	{
		drawBuffer(); // ���߹��۸� ���
		g.drawImage(buffer, 0, 0, this); // �޸� ��ü�� �ִ� ���۸� ���� ȭ�鿡 �̹����� �׷���
	}
	
	private void drawBuffer()
	{
		int x, y;
		
		buffer = createImage(getSize().width, getSize().height); // �޸� ��ü�� ���� �̹��� ����
		bufferG = buffer.getGraphics(); // �޸� ��ü�� �����̹����� �׷��� ��ü ����
		
		for(int i = 0; i < Board.row; i++) // ������ �Ʒ��� �׸���.
			for(int j = 0; j < Board.col; j++) // �¿��� ��� �׸���.
			{
				x = startX + j*size; // �簢���� �׸� �������� x��ǥ ��� ������Ʈ
				y = startY + i*size; // �簢���� �׸� �������� y��ǥ ��� ������Ʈ
				bufferG.setColor(board.gameBoard[i][j]); // ������Ʈ�� �������� ����(������ ���� ���� ������)�� �޸𸮹��ۿ� �׷��� ���� ����
				bufferG.fillRect(x, y, size, size); // ������Ʈ�� �������� ����(������ ���� ���� ������)�� �޸𸮹��� ȭ�鿡 �׷���
			}		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}