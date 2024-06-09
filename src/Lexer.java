import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lexer implements Iterable<Lexer.Token> {

    private final  List<Token> tokens;
    private final String input;
    private int current;

    public Lexer(String input){
        this.tokens = new ArrayList<>();
        this.input = input;
        this.current = 0;
        tokenize();
    }

    private void tokenize() throws LexerException {
        while(current < input.length()){
            char c = input.charAt(current);
            switch (c){
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    current++;
                    break;
                case '=':
                    tokens.add(new Token(TokenType.ASSIGNMENT, "="));
                    current++;
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    tokens.add(new Token(TokenType.OPERATOR, Character.toString(c)));
                    current++;
                    break;
                case '"':
                    tokens.add(new Token(TokenType.STRING, readString()));
                    current++;
                    break;
                case '%':
                    tokens.add(new Token(TokenType.REFERENCES, readReference()));
                    break;
                default:
                    if(isDigit(c)) tokens.add(new Token(TokenType.NUMBER, readNumber()));
                    else if (isAlpha(c)) {
                        String identifier = readIdentifier();
                        tokens.add(new Token(deriveTokenType(identifier), identifier));
                    }else{
                        throw new LexerException("Unsupported character!");
                    }
            }
        }
    }

    private TokenType deriveTokenType(String identifier) {
        return switch (identifier) {
            case "config" -> TokenType.CONFIG;
            case "update" -> TokenType.UPDATE;
            case "configs" -> TokenType.CONFIGS;
            case "compute" -> TokenType.COMPUTE;
            case "show" -> TokenType.SHOW;
            default -> TokenType.IDENTIFIER;
        };
    }

    private String readIdentifier() {
        StringBuilder builder = new StringBuilder();
        while(current < input.length() && isAlphaNumeric(input.charAt(current))){
            builder.append(input.charAt(current));
            current++;
        }
        return builder.toString();
    }

    private String readNumber() {
        StringBuilder builder = new StringBuilder();
        while(current < input.length() && isDigit(input.charAt(current))){
            builder.append(input.charAt(current));
            current++;
        }
        return builder.toString();
    }

    private String readString() {
        current++;
        StringBuilder builder = new StringBuilder();
        while(current < input.length() && input.charAt(current) != '"'){
            builder.append(input.charAt(current));
            current++;
        }
        return builder.toString();
    }

    private String readReference() {
        current++;
        StringBuilder builder = new StringBuilder();
        while(current < input.length() && isAlphaNumeric(input.charAt(current))){
            builder.append(input.charAt(current));
            current++;
        }
        return builder.toString();
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) | isDigit(c);
    }

    private boolean isAlpha(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || c == '_';
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

    class Token{
        final TokenType tokenType;
        final String value;

        public Token(TokenType tokenType, String value) {
            this.tokenType = tokenType;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Token{" +
                    "tokenType=" + tokenType +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
    enum TokenType{
        CONFIG, UPDATE, COMPUTE, SHOW, CONFIGS, STRING, NUMBER, IDENTIFIER, REFERENCES, OPERATOR, ASSIGNMENT
    }
}
