public class ConstNode extends ASTNode{
    Lexer.Token token;
    String name;

    public ConstNode(Lexer.Token token) {
        this.token = token;
        this.name = token.value;
    }

    @Override
    public void print(String indent) {
        System.out.println("Const{" + name + "}");
    }
}
