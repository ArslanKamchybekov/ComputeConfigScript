public class BinaryOperationNode extends ASTNode {
    ASTNode left, right;
    Lexer.Token operationToken;

    public BinaryOperationNode(ASTNode left, ASTNode right, Lexer.Token operationToken) {
        this.left = left;
        this.right = right;
        this.operationToken = operationToken;
    }

    @Override
    public String toString() {
        return "Op{" + left + " " + operationToken.value + " " + right + '}';
    }

    public void print(String indent){
        System.out.println(indent + "Op{" + left + " " + operationToken.value + " " + right + '}');
        left.print(indent + indent);
        right.print(indent + indent);
    }
}
