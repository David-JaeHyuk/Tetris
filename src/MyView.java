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
	int size = 10;
	int startX = 50, startY = 50;
	
	WinEvent w = new WinEvent();
	public MyView()
	{
		super("MyTetris");
		board = new Board();
//	    MediaTracker tracker = new MediaTracker(this);
		setBounds(50, 50, 300, 500);
	    addKeyListener(this);
	    setVisible(true);
	    addWindowListener(w);
	}
	
	@Override
	synchronized public void keyPressed(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		System.out.println("Ű ����");
		KeyProcessing(e.getKeyCode());
	}

	public void KeyProcessing(int key)
	{
		if(key == KeyEvent.VK_UP)
		{
			System.out.println("Up key ����");
			repaint();
		}
	}

	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void paint(Graphics g)
	{
		System.out.println("paint() ȣ��");
		drawBuffer(); // ���߹��۸� ���
		g.drawImage(buffer, 0, 0, this);
	}
	
	private void drawBuffer()
	{
		int x, y;
		
		buffer = createImage(getSize().width, getSize().height);
		bufferG = buffer.getGraphics();
		
		/* ����� ��Ʈ���� ���� �۾� �׸��� */
		
		bufferG.setColor(Color.BLACK);
		
		for(int i = 0; i < Board.row; i++) // ������ �Ʒ��� �׸���.
			for(int j = 0; j < Board.col; j++) // �¿��� ��� �׸���.
			{
				x = startX + j*size; // �������� x��ǥ ��� ������Ʈ
				y = startY + i*size; // �������� y��ǥ ��� ������Ʈ
				bufferG.fillRect(x, y, size, size);
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