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
                    tokens.add(new Token(TokenType.ASSIGN, "="));
                    current++;
                    break;
                case '+':
                    tokens.add(new Token(TokenType.ADD, "+"));
                    current++;
                    break;
                case '-':
                    tokens.add(new Token(TokenType.SUBTRACT, "-"));
                    current++;
                    break;
                case '*':
                    tokens.add(new Token(TokenType.MULTIPLY, "*"));
                    current++;
                    break;
                case '/':
                    tokens.add(new Token(TokenType.DIVIDE, "/"));
                    current++;
                    break;
                case '>':
                case '<':
                    tokens.add(new Token(TokenType.OPERATOR, Character.toString(c)));
                    current++;
                    break;
                case '(':
                    tokens.add(new Token(TokenType.LPAREN, "("));
                    current++;
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RPAREN, ")"));
                    current++;
                    break;
                case '{':
                    tokens.add(new Token(TokenType.LBRACE, "{"));
                    current++;
                    break;
                case '}':
                    tokens.add(new Token(TokenType.RBRACE, "}"));
                    current++;
                    break;
                case ';':
                    tokens.add(new Token(TokenType.SEMICOLON, ";"));
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
                        break;
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
            case "var" -> TokenType.VAR;
            case "configs" -> TokenType.CONFIGS;
            case "compute" -> TokenType.COMPUTE;
            case "show" -> TokenType.SHOW;
            case "loop", "from" -> TokenType.LOOP;
            case "if", "else" -> TokenType.CONDITION;
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
    public Iterator<Lexer.Token> iterator() {
        return tokens.iterator();
    }

    public class Token{
        public final Lexer.TokenType tokenType;
        public final String value;

        public Token(Lexer.TokenType tokenType, String value) {
            this.tokenType = tokenType;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Token{" + "tokenType=" + tokenType + ", value='" + value + '\'' + '}';
        }
    }

    enum TokenType{
        CONFIG, UPDATE, COMPUTE, SHOW, CONFIGS, STRING, NUMBER, IDENTIFIER, REFERENCES, ASSIGN, LOOP, CONDITION,
        OPERATOR, ADD, SUBTRACT, MULTIPLY, DIVIDE, LPAREN, RPAREN, SEMICOLON, LBRACE, RBRACE, VAR
    }
}
