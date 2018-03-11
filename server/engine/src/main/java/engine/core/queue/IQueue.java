package engine.core.queue;



/**
 * a module is responsible for processing those actions that all
 * act on the same shared resource,so all incoming actions are queued
 * and executed one by one.
 * 
 * each module can only execute buffer.code that it recognizes.
 * 
 * if inserting a entry buffer to module,always call queue(buffer),
 * don't call logic(buffer).
 * 
 * @author sunnyawake
 *
 */
public interface IQueue<T> {
	void queue(T o);
}
