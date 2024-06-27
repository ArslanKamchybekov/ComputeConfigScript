public class AssignNode extends ASTNode{
    String name;
    ASTNode expression;

    public AssignNode(String name, ASTNode expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "={" + name + " = " + expression + "}";
    }

    @Override
    public void print(String indent) {
        System.out.println("={" + name + " = " + expression + "}");
    }
}
