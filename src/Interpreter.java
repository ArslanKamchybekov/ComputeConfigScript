import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    private final Map<String, Integer> valueMap;

    public Interpreter(HashMap<String, Integer> valueMap) {
        this.valueMap = valueMap;
    }

    int visit(ASTNode node) {
        switch (node) {
            case AssignNode assignNode -> {
                int expression = visit(assignNode.expression);
                valueMap.put(assignNode.name, expression);
                return expression;
            }
            case BinaryOperationNode binaryOperationNode -> {
                int left = visit(binaryOperationNode.left);
                int right = visit(binaryOperationNode.right);
                switch (binaryOperationNode.operationToken.tokenType) {
                    case Lexer.TokenType.ADD -> {
                        return left + right;
                    }
                    case Lexer.TokenType.SUBTRACT -> {
                        return left - right;
                    }
                    case Lexer.TokenType.MULTIPLY -> {
                        return left * right;
                    }
                    case Lexer.TokenType.DIVIDE -> {
                        if (right == 0) throw new ParserException("Division by /0");
                        else return left / right;
                    }
                    default -> throw new ParserException("Unsupported token:" + binaryOperationNode.operationToken);
                }
            }
            case BlockNode blockNode -> {
                for (ASTNode statement : blockNode.statements) visit(statement);
            }
            case ConstNode constNode -> {
                //
            }
            case DeclarationNode declarationNode -> {
                int expression = visit(declarationNode.expression);
                valueMap.put(declarationNode.name, expression);
                return expression;
            }
            case VarNode varNode -> {
                if (!valueMap.containsKey(varNode.name)) throw new ParserException("No such var: " + varNode.token);
                else return valueMap.get(varNode.name);
            }
            case NumberNode numberNode -> {
                return numberNode.value;
            }
            case null, default -> {
                assert node != null;
                throw new ParserException("Unsupported node type: " + node.getClass().getCanonicalName());
            }
        }
        return -1;
    }
}
