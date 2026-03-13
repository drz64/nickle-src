package nickle;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public final class compile {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("usage: java -jar nickle.jar <file.iloc>");
            System.exit(1);
        }

        var input = CharStreams.fromFileName(args[0]);
        var lexer = new NickleILOCLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new NickleILOCParser(tokens);

        var tree = parser.program();

        var layout = new LayoutListener();
        ParseTreeWalker.DEFAULT.walk(layout, tree);

        var codegen = new CodeGenVisitor(layout.result());
        var c = codegen.visit(tree);

        System.out.print(c);
    }
}
