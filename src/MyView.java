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
		System.out.println("키 눌림");
		KeyProcessing(e.getKeyCode());
	}

	public void KeyProcessing(int key)
	{
		if(key == KeyEvent.VK_UP)
		{
			System.out.println("Up key 눌림");
			repaint();
		}
	}

	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void paint(Graphics g)
	{
		System.out.println("paint() 호출");
		drawBuffer(); // 이중버퍼링 사용
		g.drawImage(buffer, 0, 0, this);
	}
	
	private void drawBuffer()
	{
		int x, y;
		
		buffer = createImage(getSize().width, getSize().height);
		bufferG = buffer.getGraphics();
		
		/* 여기다 테트리스 관련 작업 그리기 */
		
		bufferG.setColor(Color.BLACK);
		
		for(int i = 0; i < Board.row; i++) // 위에서 아래로 그린다.
			for(int j = 0; j < Board.col; j++) // 좌에서 우로 그린다.
			{
				x = startX + j*size; // 시작점의 x좌표 계속 업데이트
				y = startY + i*size; // 시작점의 y좌표 계속 업데이트
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