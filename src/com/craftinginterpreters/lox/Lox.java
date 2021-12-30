package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox
{
    private static final Interpreter interpreter = new Interpreter();

    static boolean hadError = false;
    static boolean hadRuntimeError = false;

    public static void main(String[] args) throws IOException
    {
        if(args.length > 1){
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        }
        else if(args.length == 1){
            runFile(args[0]);
        }
        else{
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException{
        //read source from file and execute it
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        //Indicate error in exit code
        if(hadError) System.exit(65);
        if(hadRuntimeError) System.exit(70);
    }

    private static void runPrompt() throws IOException{
        //read from standard input
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        //infinite prompt
        for(;;){
            System.out.print("> ");
            String line = reader.readLine();
            if(line == null) break;
            run(line);
            hadError = false;
        }
    }

    private static void run(String source){
        Lexer scanner = new Lexer(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        //stop if there was a syntax error
        if(hadError) return;

        interpreter.interpret(expression);
    }

    static void error(int line, String message){
        report(line, "", message);
    }

    private static void report(int line, String where, String message){
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

    static void error(Token token, String message){
        if(token.type == TokenType.EOF){
            report(token.line, " at end", message);
        }
        else{
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    static void runtimeError(RuntimeError error){
        System.err.println(error.getMessage() + "\n[Line " + error.token.line + "]");
        hadRuntimeError = true;
    }
}
