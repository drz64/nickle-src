
package nickle;

import java.util.ArrayList;
import java.util.List;

public final class CodeGenVisitor extends NickleILOCBaseVisitor<String> {

    private final CompileModel model;
    private final List<String> prompts = new ArrayList<String>() ; 
    private final StringBuilder out = new StringBuilder();

    public CodeGenVisitor(CompileModel model) {
        this.model = model;
    }

    @Override
    public String visitProgram(NickleILOCParser.ProgramContext ctx) {
        emitPreamble();
        emitProgramConfig();
        emitStaticData();
        emitProgramHeader();
        if (ctx.codeSection() != null) visit(ctx.codeSection());
        emitProgramFooter();
        emitPrompts() ; 
        return out.toString();
    }

    /* ---------------- concrete visits ---------------- */

    @Override public String visitNop(NickleILOCParser.NopContext ctx) { return line("op_nop"); }

    @Override public String visitAdd(NickleILOCParser.AddContext ctx) { return line("op_add", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitSub(NickleILOCParser.SubContext ctx) { return line("op_sub", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitMult(NickleILOCParser.MultContext ctx) { return line("op_mult", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitDiv(NickleILOCParser.DivContext ctx) { return line("op_div", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitAddI(NickleILOCParser.AddIContext ctx) { return line("op_addI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitSubI(NickleILOCParser.SubIContext ctx) { return line("op_subI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitRsubI(NickleILOCParser.RsubIContext ctx) { return line("op_rsubI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitMultI(NickleILOCParser.MultIContext ctx) { return line("op_multI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitDivI(NickleILOCParser.DivIContext ctx) { return line("op_divI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitRdivI(NickleILOCParser.RdivIContext ctx) { return line("op_rdivI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitLshift(NickleILOCParser.LshiftContext ctx) { return line("op_lshift", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))) ; }

    @Override public String visitLshiftI(NickleILOCParser.LshiftIContext ctx) { return line("op_lshiftI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitRshift(NickleILOCParser.RshiftContext ctx) { return line("op_rshift", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitRshiftI(NickleILOCParser.RshiftIContext ctx) { return line("op_rshiftI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitAndOp(NickleILOCParser.AndOpContext ctx) { return line("op_and", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }
    
    @Override public String visitAndI(NickleILOCParser.AndIContext ctx) { return line("op_andI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitOrOp(NickleILOCParser.OrOpContext ctx) { return line("op_or", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }
    
    @Override public String visitOrI(NickleILOCParser.OrIContext ctx) { return line("op_orI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitXorOp(NickleILOCParser.XorOpContext ctx) { return line("op_xor", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitXorI(NickleILOCParser.XorIContext ctx) { return line("op_xorI", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitLoad(NickleILOCParser.LoadContext ctx) { return line("op_load", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitLoadAI(NickleILOCParser.LoadAIContext ctx) { return line("op_loadAI", regExpr(ctx.reg(0)), offsetToC(ctx.offset()), regExpr(ctx.reg(1))); }

    @Override public String visitLoadAO(NickleILOCParser.LoadAOContext ctx) { return line("op_loadAO", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCload(NickleILOCParser.CloadContext ctx) { return line("op_cload", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitCloadAI(NickleILOCParser.CloadAIContext ctx) { return line("op_cloadAI", regExpr(ctx.reg(0)), offsetToC(ctx.offset()), regExpr(ctx.reg(1))); }

    @Override public String visitCloadAO(NickleILOCParser.CloadAOContext ctx) { return line("op_cloadAO", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitLoadI(NickleILOCParser.LoadIContext ctx) {
        String imm;
        if (ctx.number() != null) imm = numberToC(ctx.number().getText());
        else imm = Long.toString(labelId(ctx.ID().getText())); // extension
        return line("op_loadI", imm, regExpr(ctx.reg()));
    }

    @Override public String visitStore(NickleILOCParser.StoreContext ctx) { return line("op_store", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }
    
    @Override public String visitStoreAI(NickleILOCParser.StoreAIContext ctx) { return  line("op_storeAI", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), offsetToC(ctx.offset())); }

    @Override public String visitStoreAO(NickleILOCParser.StoreAOContext ctx) { return line("op_storeAO", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCstore(NickleILOCParser.CstoreContext ctx) { return line("op_cstore", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitCstoreAI(NickleILOCParser.CstoreAIContext ctx) { return line("op_cstoreAI", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), offsetToC(ctx.offset())); }

    @Override public String visitCstoreAO(NickleILOCParser.CstoreAOContext ctx) { return line("op_cstoreAO", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitI2i(NickleILOCParser.I2iContext ctx) { return line("op_i2i", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitC2c(NickleILOCParser.C2cContext ctx) { return line("op_c2c", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitC2i(NickleILOCParser.C2iContext ctx) { return line("op_c2i", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitI2c(NickleILOCParser.I2cContext ctx) { return line("op_i2c", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitCmpLT(NickleILOCParser.CmpLTContext ctx) { return line("op_cmp_LT", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCmpLE(NickleILOCParser.CmpLEContext ctx) { return line("op_cmp_LE", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCmpEQ(NickleILOCParser.CmpEQContext ctx) { return line("op_cmp_EQ", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }
    
    @Override public String visitCmpGE(NickleILOCParser.CmpGEContext ctx) { return line("op_cmp_GE", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCmpGT(NickleILOCParser.CmpGTContext ctx) { return line("op_cmp_GT", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCmpNE(NickleILOCParser.CmpNEContext ctx) { return line("op_cmp_NE", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCbr(NickleILOCParser.CbrContext ctx) {
        String cond = regExpr(ctx.reg());
        String t = ctx.ID(0).getText();
        String f = ctx.ID(1).getText();
        ensureLabel(t);
        ensureLabel(f);
        return line("op_cbr", cond, model.labelIds().get(t), model.labelIds().get(f));
    }

    @Override public String visitRet(NickleILOCParser.RetContext ctx) {
        String r = regExpr(ctx.reg());
        return line("op_ret", r);
    }

    @Override public String visitJumpI(NickleILOCParser.JumpIContext ctx) {
        String l = ctx.ID().getText();
        ensureLabel(l);
        return line("op_jumpI", model.labelIds().get(l));
    }

    @Override public String visitPInt(NickleILOCParser.PIntContext ctx) { return line("op_p_int", regExpr(ctx.reg())); }
    @Override public String visitPChar(NickleILOCParser.PCharContext ctx) { return line("op_p_char", regExpr(ctx.reg())); }
    @Override public String visitPStr(NickleILOCParser.PStrContext ctx) { return line("op_p_str", regExpr(ctx.reg())); }
    @Override public String visitPPrompt(NickleILOCParser.PPromptContext ctx) { 
        int k = prompts.size() ; 
        prompts.add(ctx.stringLiteral().getText()) ; 
        return line("op_p_prompt",k) ; 
    } 
    @Override public String visitDReg(NickleILOCParser.DRegContext ctx) { return line("op_d_reg"); }
    @Override public String visitDMem(NickleILOCParser.DMemContext ctx) { return line("op_d_mem", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }
    @Override public String visitAtoi(NickleILOCParser.AtoiContext ctx){ return line("op_atoi", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }
	@Override public String visitIs_i(NickleILOCParser.Is_iContext ctx) { return line("op_is_i", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitHalt(NickleILOCParser.HaltContext ctx) { return line("op_halt"); }

    /* ---------------- helpers ---------------- */

    private void emitPreamble() {
        out.append("#include \"nickle.h\"\n\n");
    }

    private void emitProgramConfig() {
        out.append("const size_t PROGRAM_USER_REGS = ").append(model.userRegisters()).append(";\n");
        out.append("const size_t PROGRAM_MEM_SIZE  = ").append(model.memorySize()).append(";\n\n");
    }

    private void emitStaticData() {
        out.append("const data_desc_t STATIC_DATA[] = {\n");
        for (var d : model.staticData()) {
            switch (d.kind()) {
                case INT -> out.append("    { .kind = D_INT,    .v.i = ").append(d.iVal()).append(" },\n");
                case CHAR -> out.append("    { .kind = D_CHAR,   .v.c = ").append(d.cVal()).append(" },\n");
                case STRING -> {
                    out.append("    { .kind = D_STRING, .v.s = ");
                    out.append(cString(d.sVal()));
                    out.append(" },\n");
                }
            }
        }
        out.append("};\n\n");
        out.append("const size_t STATIC_COUNT = sizeof(STATIC_DATA) / sizeof(STATIC_DATA[0]);\n\n");
    }

    private void emitPrompts() {
      out.append("const char* PROMPTS[] = {\n");
      if (prompts.size()>0) {
        out.append("    " + prompts.get(0) + "\n") ;
        for (int k=1; k < prompts.size() ; k++) {
            out.append("  , " + prompts.get(k) + "\n") ;
        }
      }
      out.append("};\n\n");
    }

    private void emitProgramHeader() {
        out.append("const int64_t PROGRAM[] = {\n");
    }

    private void emitProgramFooter() {
        out.append("  op_halt\n}; \n\n");
        out.append("const size_t PROGRAM_COUNT = sizeof(PROGRAM) / sizeof(PROGRAM[0]);\n\n") ;
    }

    private String line(String opcode, Object... args) {
        out.append("  ").append(opcode).append(", ") ;
        for (Object a : args) out.append(a).append(", ") ; 
        out.append("\n") ; 
        return null ; 
    }

    private void ensureLabel(String name) {
        if (!model.labelIds().containsKey(name))
            throw new IllegalArgumentException("unknown label: " + name);
    }

    private long labelId(String name) {
        Long id = model.labelIds().get(name);
        if (id == null) throw new IllegalArgumentException("unknown label: " + name);
        return id;
    }

    private String regExpr(NickleILOCParser.RegContext r) {
        if (r.REGISTER_NUM() != null) {
            String t = r.REGISTER_NUM().getText().substring(1);
            int idx = Integer.parseInt(t);
            if (idx < 0 || idx >= model.userRegisters())
                throw new IllegalArgumentException("register out of range r" + idx + " (N=" + model.userRegisters() + ")");
            return Integer.toString(idx);
        }
        if (r.R_RA() != null)     return "PROGRAM_USER_REGS+R_RA_OFFSET";
        if (r.R_STATIC() != null) return "PROGRAM_USER_REGS+R_STATIC_OFFSET";
        if (r.R_ARGC() != null)   return "PROGRAM_USER_REGS+R_ARGC_OFFSET";
        if (r.R_ARGV() != null)   return "PROGRAM_USER_REGS+R_ARGV_OFFSET";
        throw new IllegalStateException("unknown reg");
    }

    private String offsetToC(NickleILOCParser.OffsetContext o) {
        if (o.atSymbol() != null) {
            String sym = o.atSymbol().ID().getText();
            Long off = model.staticOffsets().get(sym);
            if (off == null) throw new IllegalArgumentException("unknown @symbol: " + sym);
            return off.toString();
        }
        return numberToC(o.number().getText());
    }

    static String numberToC(String text) {
        long v;
        if (text.startsWith("0x") || text.startsWith("0X")) v = Long.parseUnsignedLong(text.substring(2), 16);
        else if (text.startsWith("0b") || text.startsWith("0B")) v = Long.parseUnsignedLong(text.substring(2), 2);
        else if (text.length() > 1 && text.startsWith("0")) v = Long.parseUnsignedLong(text.substring(1), 8);
        else v = Long.parseLong(text, 10);
        return Long.toUnsignedString(v);
    }

    static String cString(String s) {
        String esc = s
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
        return "\"" + esc + "\"";
    }
}
