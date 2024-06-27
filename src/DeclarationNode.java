public class DeclarationNode extends ASTNode{
    String name;
    ASTNode expression;

    public DeclarationNode(String name, ASTNode expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "D{" + name + " = " + expression + '}';
    }

    @Override
    public void print(String indent) {
        System.out.println("D{" + name + " = " + expression + '}');
    }
}
