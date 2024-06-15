public class StatementNode extends ASTNode{
    private Lexer.Token statementToken;
    String statement;

    public StatementNode(Lexer.Token statementToken) {
        this.statementToken = statementToken;
        this.statement = statementToken.value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "S{" + statement + '}');
    }
}
