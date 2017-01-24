import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class MyView extends Frame implements KeyListener
{
	Image buffer;
	Graphics bufferG;
	Board board;
	DownThread downThread;
	boolean startFlag;
	
	int size = 10;
	int startX = 50, startY = 50;
	
	WinEvent w = new WinEvent();
	public MyView()
	{
		super("MyTetris");
		board = new Board();
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
			if(!startFlag)
			{
				board.makeBlock();
				System.out.println("����Ű ������ ������ ����!");
				downThread.start();
				
				startFlag = true;
			}
			
			KeyProcessing(e.getKeyCode());
			
		}
	}

	public void KeyProcessing(int key)
	{
		board.removeBlockOnBoard(); // ���� ���� ���� �ִ� �� ����
		
		System.out.println("ȸ�� ���� �� ���� : " + board.block); // ���� �� ����
		
		switch(key)
		{
		case KeyEvent.VK_UP:
			
			board.block.rotateBlock(); // ���忡 �ִ� �ش� ���� rotation
			System.out.println("ȸ�� ���� �� ���� : " + board.block); // ���� �� ����
			board.addBlock(board.block); // ȸ���� ���� ������ �ش� ����  ȭ�鿡 ���������μ� ������Ʈ
			break;
			
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_LEFT:
			board.block.move(key); // Ű���尡 � Ű�� ���ȴ����� ���� ���忡 �ִ� ���� �̵���Ŵ
			board.addBlock(board.block); // �� or �� or �Ʒ� �����ӿ� ���� �ش� ����  ȭ�鿡 ���������μ� ������Ʈ
			break;
		}
		
		System.out.println("keyProcessing ȣ��");
		
		
		repaint(); // ������Ʈ�� ���� �ٽ� ȭ�鿡 �׷���
	}

	@Override
	public void update(Graphics g) // repaint()�� ȣ��� �� �ڵ����� ȣ��Ǵ� �޼ҵ�
	{
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) // ȭ�鿡 �׸��� �׷��ִ� �������̵��� �޼ҵ�
	{
		if(!startFlag)
			System.out.println("���� ����Ʈ ��ü ����");
		drawBuffer(); // ���߹��۸� ���
		g.drawImage(buffer, 0, 0, this); // �޸� ��ü�� �ִ� ���۸� ���� ȭ�鿡 �̹����� �׷���
	}
	
	private void drawBuffer()
	{
		int x, y;
		
		buffer = createImage(getSize().width, getSize().height); // �޸� ��ü�� ���� �̹��� ����
		bufferG = buffer.getGraphics(); // �޸� ��ü�� �����̹����� �׷��� ��ü ����
		
		/* ����� ��Ʈ���� ���� �۾� �׸��� */
		System.out.println("drawBuffer()����");
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