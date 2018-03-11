package engine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

import engine.log.LogUtil;

public class InputManager {
	
	private static InputManager instance = new InputManager();   

	private InputManager()
	{
		thread=new Thread(()-> this.Update());
	}
	
	public static  InputManager getInstance() {  
		          return instance;
	}  
	public void Start()
	{
		thread.start();
		bstart=true;
	}
	
	public void Stop()
	{
		thread.stop();
		bstart=false;
	}
	
	public boolean GetCmd(List<String > lst)
	{
		synchronized (cmds) {
			if (cmds.size()>0) {
				lst.clear();
				lst.addAll(cmds);
				cmds.clear();
				return true;
			}
			return false;
		}
	}
	
	private void Update()
	{
		while(bstart)
		{
			try {
				String string=s.nextLine();
				//System.out.println(string);
				synchronized (cmds) {
					cmds.add(string);
				}
			} catch (Exception ex) {
				// TODO: handle exception
				LogUtil.error("控制台获得输入异常" + ex.toString());
			}
		}
		
	}
	
	private boolean bstart=false;
	private Scanner s = new Scanner(System.in); 
	private List<String> cmds=new ArrayList<>();
	private Thread thread=null;
	

}
