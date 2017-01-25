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
	boolean startFlag = false;
	
	int size = 10;
	int startX = 50, startY = 50;
	
	WinEvent w = new WinEvent();
	public TetrisView()
	{
		super("MyTetris");
		board = new Board(); // ���� ���� ����
		setBounds(120, 120, 200, 300);
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
				board.addBlock();
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
	// ���� ���� �ǰ� �� �� �߰� �� �� �ٽ� �������� �� ���� �߰��Ǵ� ���
	public void KeyProcessing(int key)
	{
		board.removeBlockOnBoard(); // ���� �����̴� �� �� ���� ���� �ִ� �� ����(���� ������ ���� �״�� ���־���)
		
		if(key == KeyEvent.VK_SPACE)
			board.spaceMove();
		else
			board.block.move(key); // Ű���尡 � Ű�� ���ȴ����� ���� ���忡 �ִ� ���� �̵���Ŵ
		
		boolean needMakeNewBlock = board.needMakeNewBlock(); // �� ���� �����ؾ��ϴ��� �Ǻ�
		
		// ������ �����ؾ� �ϴ� ��Ȳ�̶��(=���� ���� �ִ� �ڸ��� �������ų� ��Ʈ���� ������ �� ���ٿ� �ٴ޾Ҵٸ�)
		if(needMakeNewBlock) 
		{
			board.block.restoreBlockToPreState(key); // �̵��� ��ġ ���
			board.addBlock(); // �� �� ���� ������ �������� �״�� ȭ�鿡 ����
			
			synchronized(this) // �ٸ� Ű�� ������ ���� ���� ����� ���� ���� ���� ����ȭ
			{
				board.makeBlock(); // �� �� ����	
			}
			
		}
		
		// �� ���� �����ؾ� �ϴ� ��Ȳ�� �ƴ�����, 
		//���� ���� ������ �ִٸ� ��� �� ���� Ű������ �������·� �ǵ�����.(���� ȭ���� ������ �ȵǴϱ�)
		else if(board.block.isItEdge()) 					
			board.block.restoreBlockToPreState(key);
	
		int removeLineNum = board.checkLine(); // �����ؾ� �Ǵ� ������ üũ
		if(removeLineNum != -1) // ������ üũ�� ������ ���� �� �����̶� ���� ����
			board.removeLine(removeLineNum);

		board.addBlock(); // �� �� �� �Ʒ� �����ӿ� ���� �� Ȥ�� ���� ���� �ش� ���� ȭ�鿡 ���������μ� ���� ������Ʈ
		
		repaint(); // ������Ʈ�� ���带 �ٽ� ȭ�鿡 �׷���
	}

	@Override
	public void update(Graphics g) // repaint()�� ȣ��� �� �ڵ����� ȣ��Ǵ� �޼ҵ�
	{
		paint(g);
	}
	
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