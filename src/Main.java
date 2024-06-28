import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = """
                var x = 17;
                {
                  x = 5-4;
                  var y = 12;
                  var value = y + x;
                  var multiply = x * y;
                }
                var z = x + 1;
                """;

        //Lexer
        Lexer lexer = new Lexer(input);
        List<Lexer.Token> tokens = new ArrayList<>();
        for (Lexer.Token token : lexer) {
            tokens.add(token);
            System.out.println(token);
        }

        System.out.println("----------------------------");

        //Parser
        Parser parser = new Parser(tokens);
        ASTNode root = parser.parse();
        root.print("  ");

        //Semantic Analyzer
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        analyzer.visit(root);

        System.out.println("----------------------------");

        //Interpreter
        HashMap<String, Integer> valueMap = new HashMap<>();
        Interpreter interpreter = new Interpreter(valueMap);
        interpreter.visit(root);
        System.out.println(List.of(valueMap));
    }
}

//add indents for blocks
//
//        String input1 = """
//                config "num_users1" = 100
//                update "num_users2" = 200
//                compute "total" = %num_users1 + %num_users2
//                if %num_users > 1000
//                    update "status" = "high load"
//                else
//                    update "status" = "normal load"
//                end
//                loop "i" from 1 to 5
//                    compute "iteration_result_%i" = %num_users*%i
//                end
//                """;
//        Lexer lexer = new Lexer(input);
//        for(Lexer.Token token: lexer){
//            System.out.println(token);
//        }