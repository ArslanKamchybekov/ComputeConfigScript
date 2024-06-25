public class AssignNode extends ASTNode{
    VarNode left;
    ASTNode right;

    public AssignNode(VarNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "={" + right + "}");
    }
}
