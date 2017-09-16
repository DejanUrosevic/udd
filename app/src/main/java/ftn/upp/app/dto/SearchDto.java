package ftn.upp.app.dto;

public class SearchDto {
	
	private String field;
	private String value;
	private String operator;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public SearchDto(String field, String value, String operator){
		this.field = field;
		this.value = value;
		this.operator = operator;
	}
	
	public SearchDto(){
		
	}
}
