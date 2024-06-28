import java.util.List;

public class BlockNode extends ASTNode {
    List<ASTNode> statements;

    public BlockNode(List<ASTNode> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "Block{" +
                "statements=" + statements +
                '}';
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Block{");
        String newIndent = indent + "    ";
        for (ASTNode node : statements) node.print(newIndent);
        System.out.println(indent + "}");
    }
}
