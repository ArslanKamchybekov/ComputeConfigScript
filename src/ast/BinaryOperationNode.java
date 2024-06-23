package ast;

public class BinaryOperationNode extends ASTNode {
    ASTNode left, right;
    Lexer.Token operationToken;

    public BinaryOperationNode(ASTNode left, ASTNode right, Lexer.Token operationToken) {
        this.left = left;
        this.right = right;
        this.operationToken = operationToken;
    }

    public void print(String indent){
        System.out.println(indent + "Op{" + operationToken.value + '}');
        left.print(indent + indent);
        right.print(indent + indent);
    }
}
