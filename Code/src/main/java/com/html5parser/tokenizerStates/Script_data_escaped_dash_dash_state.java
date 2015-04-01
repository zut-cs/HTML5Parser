package com.html5parser.tokenizerStates;

import com.html5parser.classes.ASCIICharacter;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.TokenizerContext;
import com.html5parser.classes.TokenizerState;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.ITokenizerState;
import com.html5parser.parseError.ParseErrorType;

public class Script_data_escaped_dash_dash_state implements ITokenizerState{

	@Override
	public ParserContext process(ParserContext context) {
		TokenizerStateFactory factory = TokenizerStateFactory.getInstance();
		TokenizerContext tokenizerContext = context.getTokenizerContext();
		int currentChar = tokenizerContext.getCurrentInputCharacter();
		ASCIICharacter asciiCharacter = tokenizerContext.getCurrentASCIICharacter();
		
		switch (asciiCharacter) {
		case HYPHEN_MINUS:
			/* 
			 * Emit a U+002D HYPHEN-MINUS character token.
			 */
			tokenizerContext.emitCurrentToken(new Token(TokenType.character, String
					.valueOf(Character.toChars(0x002D))));
			break;
		case LESS_THAN_SIGN:
			/*
			 * Switch to the script data escaped less-than sign state.
			 */
			tokenizerContext.setNextState(factory.getState(TokenizerState.Script_data_escaped_less_than_sign_state));
			break; 
		case GREATER_THAN_SIGN:
			/*
			 * Switch to the script data state. Emit a U+003E GREATER-THAN SIGN character token.
			 */
			tokenizerContext.setNextState(factory.getState(TokenizerState.Script_data_state)); 
			tokenizerContext.emitCurrentToken(new Token(TokenType.character, String
					.valueOf(Character.toChars(0x003E))));
			break;
		case NULL:
			/*
			 * Parse error. 
			 * Switch to the script data escaped state. 
			 * Emit a U+FFFD REPLACEMENT CHARACTER character token.
			 */
			context.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			tokenizerContext.setNextState(factory.getState(TokenizerState.Script_data_escaped_state)); 
			tokenizerContext.emitCurrentToken(new Token(TokenType.character, String
					.valueOf(Character.toChars(0xFFFD))));
			break;
		case EOF:
			/*
			 * Parse error. 
			 * Switch to the data state. 
			 * Reconsume the EOF character.
			 */
			context.addParseErrors(ParseErrorType.UnexpectedInputCharacter); 
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Data_state));
			tokenizerContext.setFlagReconsumeCurrentInputCharacter(true);
			break;
		default:
			/*
			 * Switch to the script data escaped state. 
			 * Emit the current input character as a character token.
			 */
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Script_data_escaped_state));
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					currentChar));
			break;
		}
		
		context.setTokenizerContext(tokenizerContext);
		return context;
	}

}
