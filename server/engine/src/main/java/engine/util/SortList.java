package engine.util;

import engine.string.StringUtil;

/**
 * 排行榜类，用来对一系列的数据进行排队
 * @author xjf
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class SortList<T extends Node> {
	private T root;
	/**
	 * 插入一个节点
	 * @param node
	 */
	public synchronized void insert(T node){
		if(root == null){
			root = node;
		}
		else{
			root.insert(node);
		}
	}

	public void clear() {
		root = null;
	}
	/**
	 * 删除一个节点
	 * @param node
	 */
	public synchronized void remove(T node){
		boolean replaceRoot = false;
		if(node == root){
			replaceRoot = true;
		}
		Node less = findLess(node);
		if(less == null){
			if(node.parent != null){
				node.parent.replace(node, node.right);
				dec(node.parent);
			}
			else{
				less = node.right;
			}
		}
		else{
			dec(less.parent);
			if(less.parent == node){
				if(node.parent != null)
					node.parent.replace(node, less);
				else
					less.setRight(node.right);
			}
			else{
				less.parent.replace(less, less.left);
				if(node.parent != null){
					node.parent.replace(node, less);
				}
				else{
					less.setLeft(node.left);
					less.setRight(node.right);
				}
			}
		}
		if(less != null)less.childCount = (less.left != null?less.left.childCount:0) + (less.right != null?less.right.childCount:0);
		if(replaceRoot){
			root = (T)less;
			if(root != null)root.parent = null;
		}
		node.clear();
		//System.out.println(root);
	}
	/**
	 * 得到节点对应的序号
	 * @param node
	 * @return
	 */
	public synchronized int getIndex(Node node){
		if(node==null) return -1;
		return node.getIndex();
	}
	
	/**
	 * 给出排序队列，总是从第一名开始
	 * @param list
	 */
	public synchronized void getSortList(T[] list){
		Node node = getHead();
		int index = 0;
		while(index < list.length){
			if(node==null) break;
			if(node.checked){
				node = node.parent;
				if(node == null)break;
			}
			else if(node.right != null && !node.right.checked){
				node = node.right;
				continue;
			}
			else{
				list[index++] = (T)node;
				node.checked = true;
				if(node.left != null){
					node = node.left;
					continue;
				}
				else{
					node = node.parent;
					continue;
				}
			}
		}
		for (T t:list){
			if(t != null)t.checked=false; //状态必须恢复
		}
	}
	/**
	 * 队列总大小
	 * @return
	 */
	public int size() {
		// TODO Auto-generated method stub
		return root==null?0:root.childCount+1;
	}
	
	private Node findLess(Node node){
		Node less = node.left;
		if(less == null)return null;
		while(less.right != null){
			less = less.right;
		}
		return less;
	}
	
	private void dec(Node node){
		while(node != null){
			node.childCount--;
			node = node.parent;
		}
	}
	
	public T getHead(){
		Node node = root;
		if(node == null)return null;
		while(node.right != null){
			node = node.right;
		}
		return (T)node;
	}
	
	public T getFoot(){
		Node node = root;
		if(node == null)return null;
		while(node.left != null){
			node = node.left;
		}
		return (T)node;
	}
	
	public T removeFoot(){
		T t = getFoot();
		remove(t);
		return t;
	}
	
	public T removeHead(){
		T t = getHead();
		remove(t);
		return t;
	}
	
	//TODO 重排，这个是针对多次增减以后，会发生层次太深，效率降低，所以要全局重排成完全二叉树
	public void resort(){
		
	}
	
	public static void main(String[] args){
		int N = 100;
		int[] v = new int[N];
		for (int i = 0; i< N; i++){
			v[i] = i;
		}
		for (int i = 0; i< N; i++){
			int index = Utility.random(0, N, false, false);
			int value = v[index];
			v[index] = v[0];
			v[0] = value;
		}
		SortList<TNode> list = new SortList<TNode>();
		TNode[] origin = new TNode[100];
		for (int i = 0; i< 100; i++){
			origin[i] = new TNode(v[i]);
			list.insert(origin[i]);
		}
		long t = System.currentTimeMillis();
//		for (int i = 0; i< 100000; i++){
//			int index = Utility.random(0, N, true, false);
//			//System.out.println("remove " + origin[index].value);
//			list.remove(origin[index]);
//			list.insert(origin[index]);
//		}
		System.out.println((System.currentTimeMillis()-t)/1000.0);
		TNode[] end = new TNode[30];
		list.getSortList(end);
		System.out.println(list.size());
		//int index = Utility.random(0, N, true, false);
		//System.out.println(origin[index].value + "  index=" + list.getIndex(origin[index]));
//		System.out.println(list.root);
		for (int i = 0; i< end.length; i++){
			System.out.print(end[i].value);
			System.out.print(",");
//			System.out.print(list.getIndex(end[i]));
//			System.out.print(",");
		}
		System.out.print("\n"+(System.currentTimeMillis()-t)/1000.0);
	}
	

	static class TNode extends Node{
		int value;
		
		public TNode(int v){
			value = v;
		}
		@Override
		public boolean compare(Node node) {
			// TODO Auto-generated method stub
			return value >= ((TNode)node).value;
		}
		
		public int getLevel(){
			return parent==null?0:((TNode)parent).getLevel()+1;
		}
		
		public String toString(){
			String tab = StringUtil.repeat("\t", getLevel());
			return "value=" + value + ", childCount="+childCount +"\n" + tab+"left:[" + left + "\n"+tab+"right:["+ right;
		}
	}

}
