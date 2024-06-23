import java.util.List;

public class BlockNode extends ASTNode{
    List<ASTNode> statements;

    public BlockNode(List<ASTNode> statements) {
        this.statements = statements;
    }

    @Override
    public void print(String indent) {
        for (ASTNode node: statements) node.print("  ");
    }
}
