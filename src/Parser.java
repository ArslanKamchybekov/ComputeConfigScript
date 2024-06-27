import java.util.ArrayList;
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
        List<ASTNode> statements = new ArrayList<>();
        while(currentToken != null){
            statements.add(statement());
            if(currentToken != null && currentToken.tokenType == Lexer.TokenType.SEMICOLON) consume(currentToken.tokenType);
        }
        return new BlockNode(statements);
    }

    private ASTNode statement() {
        if(currentToken.tokenType == Lexer.TokenType.LBRACE) return block();
        if(currentToken.tokenType == Lexer.TokenType.VAR || currentToken.tokenType == Lexer.TokenType.CONST) return declaration();
        if(currentToken.tokenType == Lexer.TokenType.IDENTIFIER) return assignment();
        return expression();
    }

    private ASTNode assignment() {
        VarNode var = var();
        consume(Lexer.TokenType.ASSIGN);
        return new AssignNode(var.name, expression());
    }

    private ASTNode declaration() {
        if(currentToken != null && currentToken.tokenType == Lexer.TokenType.VAR) {
            consume(Lexer.TokenType.VAR);
            VarNode var = var();
            consume(Lexer.TokenType.ASSIGN);
            return new DeclarationNode(var.name, expression());
        }
        if(currentToken != null && currentToken.tokenType == Lexer.TokenType.CONST) {
            consume(Lexer.TokenType.CONST);
            ConstNode constant = constant();
            consume(Lexer.TokenType.ASSIGN);
            return new DeclarationNode(constant.name, expression());
        }
        return null;
    }

    private ConstNode constant(){
        Lexer.Token token = currentToken;
        consume(Lexer.TokenType.IDENTIFIER);
        return new ConstNode(token);
    }

    private VarNode var() {
        Lexer.Token token = currentToken;
        consume(Lexer.TokenType.IDENTIFIER);
        return new VarNode(token);
    }

    private ASTNode block() {
        consume(Lexer.TokenType.LBRACE);
        ArrayList<ASTNode> statements = new ArrayList<>();
        while(currentToken != null && currentToken.tokenType != Lexer.TokenType.RBRACE){
            statements.add(statement());
            if(currentToken != null && currentToken.tokenType == Lexer.TokenType.SEMICOLON) consume(currentToken.tokenType);
        }
        consume(Lexer.TokenType.RBRACE);
        return new BlockNode(statements);
    }

    private ASTNode expression() {
        ASTNode node = term();
        while (currentToken != null && (currentToken.tokenType == Lexer.TokenType.ADD || currentToken.tokenType == Lexer.TokenType.SUBTRACT)) {
            Lexer.Token token = currentToken;
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
            if(current < tokens.size()) currentToken = tokens.get(current);
            else currentToken = null;
        } else throw new ParserException("Unsupported token: " + currentToken);
    }

    private ASTNode factor() {
        Lexer.Token token = currentToken;
        if (token.tokenType == Lexer.TokenType.NUMBER) {
            consume(Lexer.TokenType.NUMBER);
            return new NumberNode(token);
        }
        if(token.tokenType == Lexer.TokenType.IDENTIFIER){
            consume(Lexer.TokenType.IDENTIFIER);
            return new VarNode(token);
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
