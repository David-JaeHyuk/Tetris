import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

public class WinEvent extends WindowAdapter
{
	@Override
	public void windowClosing(WindowEvent e) 
	{
		// TODO Auto-generated method stub
		System.exit(0);
	}

	
}
