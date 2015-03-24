package com.html5parser.parseError;

import com.html5parser.classes.ParserContext;

public class ParseError {

	private String message = "";

	public ParseError(ParseErrorType type, ParserContext context) {

		switch (type) {
		case UnexpectedInputCharacter:
			this.message = "Unexpected character: "
					+ String.valueOf(Character.toChars(context
							.getTokenizerContext().getCurrentInputCharacter()))
					+ " ("
					+ context.getTokenizerContext().getCurrentInputCharacter()
					+ ") at " + context.getTokenizerContext().getNextState();
			break;
		case UnexpectedToken:
			this.message = "Unexpected token: "
					+ context.getTokenizerContext().getCurrentToken().getType()
					+ " value: "
					+ context.getTokenizerContext().getCurrentToken()
							.getValue() + " at " + context.getInsertionMode();
			break;
		case EndTagWithAttributes:
			this.message = "End tag token emitted with attributes.";
			break;
		case EndTagWithSelfClosingFlag:
			this.message = "End tag token emitted with its self-closing flag set.";
			break;
		default:
			break;

		}
	}

	public ParseError(ParseErrorType type, String error) {
		switch (type) {
		case DuplicatedAttributeName:
			this.message = "Duplicated attribute name :" + error;
			break;
		default:
			this.message = error;
			break;
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}