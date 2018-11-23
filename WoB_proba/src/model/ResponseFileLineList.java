package model;

import java.util.ArrayList;
import java.util.List;

public class ResponseFileLineList {
	
	private List<ResponseFileLine> list;
	
	public ResponseFileLineList(){
		list = new ArrayList<ResponseFileLine>();
	}

	public List<ResponseFileLine> getList() {
		return list;
	}

	public void setList(List<ResponseFileLine> list) {
		this.list = list;
	}
	
	public void addResponseFileLine(InputFileLine inputFileLine, String message, Status error) {
		ResponseFileLine line = new ResponseFileLine();
		line.setLineNumber(Integer.parseInt(inputFileLine.getLineNumber()));
		line.setMessage(message);
		line.setStatus(error.name());
		
		list.add(line);		
	}	

}
