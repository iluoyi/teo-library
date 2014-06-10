package edu.tmc.uth.teo.exceptions;

public class TEOException extends Exception {

	private static final long serialVersionUID = -6359085826804962230L;

	public TEOException() {
		super("TEOException");
	}

	public TEOException(String string) {
		super(string);
	}
}
