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
		if(a == e.VK_ENTER || startFlag) // 엔터키 입력시 게임 시작
		{
			
			downThread = new DownThread();
			if(!startFlag)
			{
				board.makeBlock();
				System.out.println("엔터키 눌려서 쓰레드 시작!");
				downThread.start();
				
				startFlag = true;
			}
			
			KeyProcessing(e.getKeyCode());
			
		}
	}

	public void KeyProcessing(int key)
	{
		board.removeBlockOnBoard(); // 기존 보드 위에 있는 블럭 삭제
		
		System.out.println("회전 이전 블럭 정보 : " + board.block); // 현재 블럭 정보
		
		switch(key)
		{
		case KeyEvent.VK_UP:
			
			board.block.rotateBlock(); // 보드에 있는 해당 블럭을 rotation
			System.out.println("회전 이후 블럭 정보 : " + board.block); // 현재 블럭 정보
			board.addBlock(board.block); // 회전에 의해 움직인 해당 블럭을  화면에 더해줌으로서 업데이트
			break;
			
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_LEFT:
			board.block.move(key); // 키보드가 어떤 키가 눌렸는지에 따라 보드에 있는 블럭을 이동시킴
			board.addBlock(board.block); // 좌 or 우 or 아래 움직임에 의한 해당 블럭을  화면에 더해줌으로서 업데이트
			break;
		}
		
		System.out.println("keyProcessing 호출");
		
		
		repaint(); // 업데이트된 블럭을 다시 화면에 그려줌
	}

	@Override
	public void update(Graphics g) // repaint()가 호출될 때 자동으로 호출되는 메소드
	{
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) // 화면에 그림을 그려주는 오버라이딩한 메소드
	{
		if(!startFlag)
			System.out.println("최초 페인트 객체 실행");
		drawBuffer(); // 이중버퍼링 사용
		g.drawImage(buffer, 0, 0, this); // 메모리 객체에 있는 버퍼를 실제 화면에 이미지로 그려줌
	}
	
	private void drawBuffer()
	{
		int x, y;
		
		buffer = createImage(getSize().width, getSize().height); // 메모리 객체에 가상 이미지 생성
		bufferG = buffer.getGraphics(); // 메모리 객체의 가상이미지의 그래픽 객체 얻어옴
		
		/* 여기다 테트리스 관련 작업 그리기 */
		System.out.println("drawBuffer()실행");
		for(int i = 0; i < Board.row; i++) // 위에서 아래로 그린다.
			for(int j = 0; j < Board.col; j++) // 좌에서 우로 그린다.
			{
				x = startX + j*size; // 사각형을 그릴 시작점의 x좌표 계속 업데이트
				y = startY + i*size; // 사각형을 그릴 시작점의 y좌표 계속 업데이트
				bufferG.setColor(board.gameBoard[i][j]); // 업데이트된 보드판의 색깔(보드판 위에 블럭이 씌워진)로 메모리버퍼에 그려줄 색을 변경
				bufferG.fillRect(x, y, size, size); // 업데이트된 보드판의 색깔(보드판 위에 블럭이 씌워진)로 메모리버퍼 화면에 그려줌
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