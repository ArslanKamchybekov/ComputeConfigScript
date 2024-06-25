public class DeclarationNode extends ASTNode{
    ASTNode var;
    ASTNode expression;

    public DeclarationNode(ASTNode var, ASTNode expression) {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "D{" + var + " = " + expression + '}';
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "D{" + var + " = " + expression + '}');
    }
}
