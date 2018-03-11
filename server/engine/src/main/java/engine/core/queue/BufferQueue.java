package engine.core.queue;



public abstract class BufferQueue  {
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
