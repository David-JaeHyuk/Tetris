import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * 1) 보드 생성
 * 2) 블럭 생성(최초 생성시와 블럭이 제자리를 찾아갔을 경우로 나눔)
 * 3) KeyProcessing
 * 4) 만일 블럭이 모두 쌓였으면 종료, 아니라면 2번으로 돌아감
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
		board = new Board(); // 보드 최초 생성
		setBounds(120, 120, 200, 300);
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
			if(!startFlag) // 최초로 블럭을 만드는 경우이다.
			{
				board.makeBlock();
				board.addBlock();
				System.out.println("엔터키 눌려서 쓰레드 시작!");
				downThread.start();
				
				startFlag = true;
			}
			
			KeyProcessing(e.getKeyCode());
		}
	}

	/* KeyProcessing 역할
	 * 1) 기존 보드위에 있는 블럭 삭제 
	 * 2) 키보드 눌러서 블럭 이동
	 * 3) 움직여본 블럭이 맨 아래에 도달했거나 아니면 이미 다른 블럭이 있는 자리일 경우 새 블럭을 생성하고 기존 블럭을 화면에 그려줌 
	 * 4) 현재 블럭을 화면에 그려줌
	 * 		 
	 */
	// 라인 삭제 되고 새 블럭 추가 한 뒤 다시 연속으로 새 블럭이 추가되는 경우
	public void KeyProcessing(int key)
	{
		board.removeBlockOnBoard(); // 현재 움직이는 블럭 중 보드 위에 있는 블럭 삭제(기존 멈춰진 블럭은 그대로 냅둬야함)
		
		if(key == KeyEvent.VK_SPACE)
			board.spaceMove();
		else
			board.block.move(key); // 키보드가 어떤 키가 눌렸는지에 따라 보드에 있는 블럭을 이동시킴
		
		boolean needMakeNewBlock = board.needMakeNewBlock(); // 새 블럭을 생성해야하는지 판별
		
		// 새블럭을 생성해야 하는 상황이라면(=기존 블럭이 있는 자리에 놓으려거나 테트리스 보드의 맨 끝줄에 다달았다면)
		if(needMakeNewBlock) 
		{
			board.block.restoreBlockToPreState(key); // 이동한 위치 취소
			board.addBlock(); // 새 블럭 생성 이전에 기존블럭은 그대로 화면에 남김
			
			synchronized(this) // 다른 키를 눌러서 블럭을 새로 만드는 것을 막기 위해 동기화
			{
				board.makeBlock(); // 새 블럭 생성	
			}
			
		}
		
		// 새 블럭을 생성해야 하는 상황은 아니지만, 
		//현재 블럭이 엣지에 있다면 방금 전 눌린 키보드의 이전상태로 되돌린다.(블럭이 화면을 나가면 안되니까)
		else if(board.block.isItEdge()) 					
			board.block.restoreBlockToPreState(key);
	
		int removeLineNum = board.checkLine(); // 삭제해야 되는 라인을 체크
		if(removeLineNum != -1) // 위에서 체크한 라인은 전부 찬 라인이라서 라인 삭제
			board.removeLine(removeLineNum);

		board.addBlock(); // 좌 우 위 아래 움직임에 의한 블럭 혹은 새로 만든 해당 블럭을 화면에 더해줌으로서 보드 업데이트
		
		repaint(); // 업데이트된 보드를 다시 화면에 그려줌
	}

	@Override
	public void update(Graphics g) // repaint()가 호출될 때 자동으로 호출되는 메소드
	{
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) // 화면에 그림을 그려주는 오버라이딩한 메소드
	{
		drawBuffer(); // 이중버퍼링 사용
		g.drawImage(buffer, 0, 0, this); // 메모리 객체에 있는 버퍼를 실제 화면에 이미지로 그려줌
	}
	
	private void drawBuffer()
	{
		int x, y;
		
		buffer = createImage(getSize().width, getSize().height); // 메모리 객체에 가상 이미지 생성
		bufferG = buffer.getGraphics(); // 메모리 객체의 가상이미지의 그래픽 객체 얻어옴
		
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