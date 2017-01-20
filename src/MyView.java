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
	
	public MyView()
	{
		super("MyView");
	    MediaTracker tracker = new MediaTracker(this);
	    addKeyListener(this);
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
		buffer = createImage(getSize().width, getSize().height);
		bufferG = buffer.getGraphics();

		/* 여기다 테트리스 관련 작업 그리기 */
		bufferG.setColor(Color.BLACK);
		bufferG.fillRect(10, 10, 30, 30);
		bufferG.drawLine(40, 40, 80, 80);
//		bufferG.drawImage(img, 30, 30, this);
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