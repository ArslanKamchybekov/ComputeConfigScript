import java.util.List;

public class Parser {
    private final List<Lexer.Token> tokens;
    private int current;
    private Lexer.Token currentToken;

    public Parser(List<Lexer.Token> tokens) {
        this.tokens = tokens;
        current = 0;
        currentToken = tokens.get(current);
    }

    public ASTNode parse(){
        return expression();
    }

    private ASTNode expression() {
        ASTNode node = term();
        while (currentToken != null && (currentToken.tokenType == Lexer.TokenType.ADD || currentToken.tokenType == Lexer.TokenType.SUBTRACT)) {
            Lexer.Token token = currentToken;
//            if (currentToken.tokenType == Lexer.TokenType.ADD || currentToken.tokenType == Lexer.TokenType.SUBTRACT) {
//                consume(currentToken.tokenType);
//                node = new BinaryOperationNode(node, term(), token);
//            }
//            if (currentToken.tokenType == Lexer.TokenType.SHOW) {
//                consume(currentToken.tokenType);
//                node = new StatementNode(currentToken);
//            }
                consume(currentToken.tokenType);
                node = new BinaryOperationNode(node, term(), token);
        }
        return node;
    }

    private ASTNode term() {
        ASTNode node = factor();
        while(currentToken != null && (currentToken.tokenType == Lexer.TokenType.MULTIPLY || currentToken.tokenType == Lexer.TokenType.DIVIDE)){
            Lexer.Token token = currentToken;
            consume(currentToken.tokenType);
            node = new BinaryOperationNode(node, factor(), token);
        }
        return node;
    }

    private void consume(Lexer.TokenType type) {
        if(currentToken.tokenType == type) {
            current++;
            if(current < tokens.size())currentToken = tokens.get(current);
            else currentToken = null;
        } else throw new ParserException("Unsupported token: " + currentToken);
    }

    private ASTNode factor() {
        Lexer.Token token = currentToken;
        if (token.tokenType == Lexer.TokenType.NUMBER) {
            consume(Lexer.TokenType.NUMBER);
            return new NumberNode(token);
        }
        if (token.tokenType == Lexer.TokenType.LPAREN){
            consume(Lexer.TokenType.LPAREN);
            ASTNode node = expression();
            consume(Lexer.TokenType.RPAREN);
            return node;
        }
        throw new ParserException("Unsupported token: " + currentToken);
    }
}
