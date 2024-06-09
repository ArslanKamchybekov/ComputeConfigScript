public class Main {
    public static void main(String[] args) {
        String input = """
                config "num_users1" = 100
                update "num_users2" = 200
                compute "total" = %num_users1 + %num_users2
                if %num_users > 1000
                    update "status" = "high load"
                else
                    update "status" = "normal load"
                end
                loop "i" from 1 to 5
                    compute "iteration_result_%i" = %num_users*%i
                end
                """;
        Lexer lexer = new Lexer(input);
        for(Lexer.Token token: lexer){
            System.out.println(token);
        }
    }
}
