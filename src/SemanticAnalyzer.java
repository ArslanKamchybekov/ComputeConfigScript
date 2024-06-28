import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class SemanticAnalyzer {
    private final Stack<Set<String>> scopes = new Stack<>();

    void visit(ASTNode node) {
        switch (node) {
            case AssignNode assignNode -> {
                if (!isDeclared(assignNode.name))
                    throw new ParserException("Unexpected identifier name: " + assignNode.name);
                visit(assignNode.expression);
            }
            case BinaryOperationNode binaryOperationNode -> {
                visit(binaryOperationNode.left);
                visit(binaryOperationNode.right);
            }
            case BlockNode blockNode -> {
                scopes.push(new HashSet<>());
                for (ASTNode statement : blockNode.statements) visit(statement);
                scopes.pop();
            }
            case ConstNode constNode -> {
                //
            }
            case DeclarationNode declarationNode -> {
                if (isDeclared(declarationNode.name)) {
                    throw new ParserException("Identifier already exists: " + declarationNode.name);
                }
                visit(declarationNode.expression);
                scopes.peek().add(declarationNode.name);
            }
            case VarNode varNode -> {
                if (!isDeclared(varNode.name)) {
                    throw new ParserException("Unexpected identifier name: " + varNode.name);
                }
            }
            case NumberNode numberNode -> {
            }
            case null, default -> {
                assert node != null;
                throw new ParserException("Unsupported node type: " + node.getClass().getCanonicalName());
            }
        }
    }

    private boolean isDeclared(String name) {
        for (Set<String> scope : scopes) if (scope.contains(name)) return true;
        return false;
    }
}
