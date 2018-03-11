package engine.util;

public abstract class Node {
	public Node parent;
	public Node left;
	public Node right;
	public int childCount;
	public boolean checked;
	
	/**
	 * 只有大于和小于两种情况，没有等于。true 是我比node大，false是small。
	 * @param node
	 * @return
	 */
	public abstract boolean compare(Node node);

	protected void insert(Node node) {
		boolean flag = compare(node);
		if(flag){
			if(left == null){
				setLeft(node);
			}
			else{
				left.insert(node);
			}
		}
		else{
			if(right == null){
				setRight(node);
			}
			else{
				right.insert(node);
			}
		}
		childCount++;
	}
	
	protected int getIndex(){
		int index = right==null?0:right.childCount+1;
		Node node = this;
		Node parent = node.parent;
		while(parent != null){
			index += parent.isLeft(node)?(parent.right ==null?1:parent.right.childCount+2):0;
			node = parent;
			parent = node.parent;
		}
		return index;
//		Node node = this;
//		Node parent = node.parent;
//		while(parent != null){
//			index += parent.isLeft(node)?0:parent.childCount-(parent.right==null?0:parent.right.childCount);
//			node = parent;
//			parent = node.parent;
//		}
//		return index;
	}
	
	protected void setLeft(Node node){
		left = node;
		if(node == null)return;
		node.parent = this;
	}
	
	protected void setRight(Node node){
		right = node;
		if(node == null)return;
		node.parent = this;
	}
	
	protected boolean isLeft(Node node){
		return left == node;
	}
	
	protected void replace(Node origin, Node target){
		if(isLeft(origin)){
			setLeft(target);
		}
		else{
			setRight(target);
		}
		if(origin.left != null && origin.left != target && target != null)target.setLeft(origin.left);
		if(origin.right != null && origin.right != target && target != null) target.setRight(origin.right);
	}
	
	protected void clear(){
		parent = null;
		left = null;
		right = null;
		childCount = 0;
	}
}
