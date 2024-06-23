public class VarNode extends ASTNode{
    Lexer.Token token;
    String name;

    public VarNode(Lexer.Token token) {
        this.token = token;
        this.name = token.value;
    }

    @Override
    public void print(String indent) {
        System.out.println("Var{" + name + "}");
    }
}
