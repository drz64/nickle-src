
package nickle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class LayoutListener extends NickleILOCBaseListener {

    private long memorySize = 0x10000; // 64kb
    private int userRegisters = 16;
    private int runLimit = 0; 
    private long opOffset = 0 ; 

    private final List<DataDesc> staticData = new ArrayList<>();
    private final Map<String, Long> staticOffsets = new LinkedHashMap<>();

    // label -> stable integer id (0..L-1), insertion order is definition order
    private final Map<String, Long> labelIds = new LinkedHashMap<>();

    @Override
    public void exitMemoryDirective(NickleILOCParser.MemoryDirectiveContext ctx) {
        memorySize = (int) parseNumber(ctx.NUMBER().getText());
    }

    @Override
    public void exitRegistersDirective(NickleILOCParser.RegistersDirectiveContext ctx) {
        userRegisters = (int) parseNumber(ctx.NUMBER().getText());
    }

    @Override
    public void exitLimitDirective(NickleILOCParser.LimitDirectiveContext ctx) {
        runLimit = (int) parseNumber(ctx.NUMBER().getText());
    }


    @Override public void exitInstruction0(NickleILOCParser.Instruction0Context ctx) { opOffset += 1 ;}
    @Override public void exitInstruction1(NickleILOCParser.Instruction1Context ctx) { opOffset += 2 ;}
    @Override public void exitInstruction2(NickleILOCParser.Instruction2Context ctx) { opOffset += 3 ;}
    @Override public void exitInstruction3(NickleILOCParser.Instruction3Context ctx) { opOffset += 4 ;}

    @Override
    public void enterLabeledInstruction(NickleILOCParser.LabeledInstructionContext ctx) {
        String lab = ctx.ID().getText();
        if (labelIds.containsKey(lab)) {
            throw new IllegalArgumentException("duplicate label: " + lab);
        }
        labelIds.put(lab, opOffset);
    }

    private long staticDataOffset = 0L;

    @Override
    public void exitDataDecl(NickleILOCParser.DataDeclContext ctx) {
        String name = ctx.ID().getText();

        // @symbol offsets: index * 8
        staticOffsets.put(name, staticDataOffset);

        var dv = ctx.dataValue();
        if (dv.DOT_INT() != null) {
            long v = parseNumber(dv.number().getText());
            staticData.add(DataDesc.ofInt(v));
            staticDataOffset += 8 ; 
        } else if (dv.DOT_CHAR() != null) {
            long v = parseNumber(dv.number().getText());
            if (v < 0 || v > 255) throw new IllegalArgumentException(".char out of range (0..255): " + v);
            staticData.add(DataDesc.ofChar((int) v));
            staticDataOffset += 1 ; 
        } else if (dv.DOT_STRING() != null) {
            String raw = dv.stringLiteral().getText();
            String s = unquote(raw);
            staticData.add(DataDesc.ofString(s));
            staticDataOffset += 8 ; 
        } else {
            throw new IllegalStateException("unknown data value");
        }
    }

    public CompileModel result() {
        return new CompileModel(
                memorySize,
                userRegisters,
                runLimit,
                List.copyOf(staticData),
                Map.copyOf(staticOffsets),
                Map.copyOf(labelIds)
        );
    }

    static long parseNumber(String text) {
        // Supports: decimal, 0x.., 0b.., 0... (octal)
        if (text.startsWith("0x") || text.startsWith("0X")) return Long.parseUnsignedLong(text.substring(2), 16);
        if (text.startsWith("0b") || text.startsWith("0B")) return Long.parseUnsignedLong(text.substring(2), 2);
        if (text.length() > 1 && text.startsWith("0")) return Long.parseUnsignedLong(text.substring(1), 8);
        return Long.parseLong(text, 10);
    }

    static String unquote(String s) {
        // Input is ANTLR STRING token including quotes
        String body = s.substring(1, s.length() - 1);

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < body.length(); i++) {
            char c = body.charAt(i);
            if (c == '\\' && i + 1 < body.length()) {
                char n = body.charAt(++i);
                switch (n) {
                    case '"':  out.append('"');  break;
                    case '\\': out.append('\\'); break;
                    case 'n':  out.append('\n'); break;
                    case 't':  out.append('\t'); break;
                    case 'r':  out.append('\r'); break;
                    default:   out.append(n);    break;
                }
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
