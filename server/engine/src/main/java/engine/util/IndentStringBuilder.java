package engine.util;

public class IndentStringBuilder {

	private StringBuilder sb;
	
	private int indent = 0;
	private final String indentSpace = "    ";
	
	public IndentStringBuilder(){
		init(0);
	}
	
	public IndentStringBuilder(int defaultIndent){
		init(defaultIndent);
	}
	
	private void init(int defaultIndent){
		sb = new StringBuilder();
		indent = defaultIndent;
		if(indent<0) indent = 0;
	}
	
	public IndentStringBuilder appendln2(String v){
		append("\n");
		if(v.equals("}")) indent --;
		for (int i = 0; i < indent; i++)  sb.append(indentSpace);
		if(v.equals("{")) indent ++;
		append(v);
		return this;
	}
	
	public IndentStringBuilder appendln2(IndentStringBuilder v){
		append("\n");
		append(v);
		return this;
	}
	
	public IndentStringBuilder append(String v){
		sb.append(v);
		return this;
	}
	public IndentStringBuilder append(IndentStringBuilder v){
		sb.append(v);
		return this;
	}
	public IndentStringBuilder append(int v){
		sb.append(v);
		return this;
	}
	public IndentStringBuilder append(long v){
		sb.append(v);
		return this;
	}
	
	public void setLength(int len){
		sb.setLength(len);
	}
	
	@Override
	public String toString(){
		return sb.toString(); 
	}
}

