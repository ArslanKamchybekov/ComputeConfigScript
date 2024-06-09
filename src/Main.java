public class Main {
    public static void main(String[] args) {
        String input = """
                config "num_users1" = 100
                update "num_users2" = 200
                compute "total" = %num_users1 + %num_users2
                """;
        Lexer lexer = new Lexer(input);
        for(Lexer.Token token: lexer){
            System.out.println(token);
        }
    }
}
