package ast;

public class NumberNode extends ASTNode {
    private final Lexer.Token numberToken;
    int value;

    public NumberNode(Lexer.Token numberToken) {
        this.numberToken = numberToken;
        this.value = Integer.parseInt(numberToken.value);
    }

    @Override
    public String toString() {
        return "ast.NumberNode{" + value + '}';
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "N{" + value + '}');
    }
}
