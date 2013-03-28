package at.jku.sea.modelanalyzer.parse.ocl.type;


public class GenericType<T> extends Type<T> {
	
	private Object genericID;

	public GenericType(Object genericID) {
		super(OclType.GENERIC, null);
		this.genericID = genericID;
	}
		
	public Object getGenericID() {
		return genericID;
	}

}
