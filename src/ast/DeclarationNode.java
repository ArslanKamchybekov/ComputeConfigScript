package ast;

public class DeclarationNode extends ASTNode{
    VarNode var;
    ASTNode expression;

    public DeclarationNode(VarNode var, ASTNode expression) {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "D{" + expression + "}");
    }
}
