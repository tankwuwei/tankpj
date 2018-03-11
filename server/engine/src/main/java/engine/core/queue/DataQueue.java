package engine.core.queue;

/**
 * an object owns a queue.
 * 
 * @author sunnyawake
 *
 */
public abstract class DataQueue  {
	private DefaultQueue<Object> queue=new DefaultQueue<Object>(){
		public void handleEntry(Object o) {
			handle(o);
		}
	};
	public void queue(Object o){
		queue.queue(o);
	}
	public void dispose(){
		queue.dispose();
	}
	protected abstract void handle(Object o);
}
