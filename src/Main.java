import ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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
        String input = """
                3 + 5 * (10 - 4)
                """;
        Lexer lexer = new Lexer(input);
        List<Lexer.Token> tokens = new ArrayList<>();
        for(Lexer.Token token: lexer){
            tokens.add(token);
        }
        Parser parser = new Parser(tokens);
        ASTNode root = parser.parse();
        root.print("  ");
    }
}
