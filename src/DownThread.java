import java.awt.event.KeyEvent;

public class DownThread extends Thread{
	
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		while(true)
		{
			try
			{
				sleep(500);
			}
			catch(InterruptedException e) {};
			
			Main.view.KeyProcessing(KeyEvent.VK_DOWN); // 0.5초에 한번씩 내려옴
		}
	}



	
	

}
