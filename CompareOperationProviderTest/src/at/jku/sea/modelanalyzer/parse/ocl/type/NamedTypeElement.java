package at.jku.sea.modelanalyzer.parse.ocl.type;

public class NamedTypeElement<T> {
	
	private final String name;
	private final Type<T> type;

	public NamedTypeElement(String name, Type<T> type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Type<T> getType() {
		return type;
	}

}
