
package nickle;

import java.util.Locale;


public final class CodeGenVisitor extends NickleILOCBaseVisitor<String> {

    private final CompileModel model;
    private final StringBuilder out = new StringBuilder();

    public CodeGenVisitor(CompileModel model) {
        this.model = model;
    }

    @Override
    public String visitProgram(NickleILOCParser.ProgramContext ctx) {
        emitPreamble();
        emitProgramConfig();
        emitStaticData();
        emitIlocMainHeader();
        if (ctx.codeSection() != null) visit(ctx.codeSection());
        emitIlocMainFooter();
        return out.toString();
    }

    @Override
    public String visitCodeSection(NickleILOCParser.CodeSectionContext ctx) {
        for (var ins : ctx.instruction()) visit(ins);

        // implicit halt if last real instruction isn't halt
        if (!endsWithHalt(ctx)) {
            line("iloc_halt(cpu);");
        }
        return null;
    }

    private boolean endsWithHalt(NickleILOCParser.CodeSectionContext ctx) {
        for (int i = ctx.instruction().size() - 1; i >= 0; i--) {
            var ins = ctx.instruction(i);
            NickleILOCParser.PlainInstructionContext pi =
                    (ins.plainInstruction() != null) ? ins.plainInstruction()
                            : (ins.labeledInstruction() != null ? ins.labeledInstruction().plainInstruction() : null);
            if (pi == null) continue;
            return pi.haltInstr() != null;
        }
        return false;
    }

    @Override
    public String visitInstruction(NickleILOCParser.InstructionContext ctx) {
        if (ctx.labeledInstruction() != null) return visit(ctx.labeledInstruction());
        return visit(ctx.plainInstruction());
    }

    @Override
    public String visitLabeledInstruction(NickleILOCParser.LabeledInstructionContext ctx) {
        out.append("\n").append(ctx.ID().getText()).append(":\n");
        return visit(ctx.plainInstruction());
    }


    /* ---------------- concrete visits ---------------- */

    @Override public String visitNop(NickleILOCParser.NopContext ctx) { return line("iloc_nop(cpu);"); }

    @Override public String visitAdd(NickleILOCParser.AddContext ctx) { return line("iloc_add(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitSub(NickleILOCParser.SubContext ctx) { return line("iloc_sub(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitMult(NickleILOCParser.MultContext ctx) { return line("iloc_mult(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitDiv(NickleILOCParser.DivContext ctx) { return line("iloc_div(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitAddI(NickleILOCParser.AddIContext ctx) { return line("iloc_addI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitSubI(NickleILOCParser.SubIContext ctx) { return line("iloc_subI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitRsubI(NickleILOCParser.RsubIContext ctx) { return line("iloc_rsubI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitMultI(NickleILOCParser.MultIContext ctx) { return line("iloc_multI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitDivI(NickleILOCParser.DivIContext ctx) { return line("iloc_divI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitRdivI(NickleILOCParser.RdivIContext ctx) { return line("iloc_rdivI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitLshift(NickleILOCParser.LshiftContext ctx) { return line("iloc_lshift(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))) ; }

    @Override public String visitLshiftI(NickleILOCParser.LshiftIContext ctx) { return line("iloc_lshiftI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitRshift(NickleILOCParser.RshiftContext ctx) { return line("iloc_rshift(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitRshiftI(NickleILOCParser.RshiftIContext ctx) { return line("iloc_rshiftI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitAndOp(NickleILOCParser.AndOpContext ctx) { return line("iloc_and(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }
    
    @Override public String visitAndI(NickleILOCParser.AndIContext ctx) { return line("iloc_andI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitOrOp(NickleILOCParser.OrOpContext ctx) { return line("iloc_or(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }
    
    @Override public String visitOrI(NickleILOCParser.OrIContext ctx) { return line("iloc_orI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitXorOp(NickleILOCParser.XorOpContext ctx) { return line("iloc_xor(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitXorI(NickleILOCParser.XorIContext ctx) { return line("iloc_xorI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); }

    @Override public String visitLoad(NickleILOCParser.LoadContext ctx) { return line("iloc_load(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitLoadAI(NickleILOCParser.LoadAIContext ctx) { return line("iloc_loadAI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), offsetToC(ctx.offset()), regExpr(ctx.reg(1))); }

    @Override public String visitLoadAO(NickleILOCParser.LoadAOContext ctx) { return line("iloc_loadAO(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCload(NickleILOCParser.CloadContext ctx) { return line("iloc_cload(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitCloadAI(NickleILOCParser.CloadAIContext ctx) { return line("iloc_cloadAI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), offsetToC(ctx.offset()), regExpr(ctx.reg(1))); }

    @Override public String visitCloadAO(NickleILOCParser.CloadAOContext ctx) { return line("iloc_cloadAO(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitLoadI(NickleILOCParser.LoadIContext ctx) {
        String imm;
        if (ctx.number() != null) imm = numberToC(ctx.number().getText());
        else imm = Integer.toString(labelId(ctx.ID().getText())); // extension
        return line("iloc_loadI(cpu, %s, %s);", imm, regExpr(ctx.reg()));
    }

    @Override public String visitStore(NickleILOCParser.StoreContext ctx) { return line("iloc_store(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }
    
    @Override public String visitStoreAI(NickleILOCParser.StoreAIContext ctx) { return  line("iloc_storeAI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), offsetToC(ctx.offset())); }

    @Override public String visitStoreAO(NickleILOCParser.StoreAOContext ctx) { return line("iloc_storeAO(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCstore(NickleILOCParser.CstoreContext ctx) { return line("iloc_cstore(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitCstoreAI(NickleILOCParser.CstoreAIContext ctx) { return line("iloc_cstoreAI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), offsetToC(ctx.offset())); }

    @Override public String visitCstoreAO(NickleILOCParser.CstoreAOContext ctx) { return line("iloc_cstoreAO(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitI2i(NickleILOCParser.I2iContext ctx) { return line("iloc_i2i(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitC2c(NickleILOCParser.C2cContext ctx) { return line("iloc_c2c(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitC2i(NickleILOCParser.C2iContext ctx) { return line("iloc_c2i(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitI2c(NickleILOCParser.I2cContext ctx) { return line("iloc_i2c(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitC_i2i(NickleILOCParser.C_i2iContext ctx) { return line("iloc_c_i2i(cpu, %s, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2)), regExpr(ctx.reg(3))); }

    @Override public String visitC_c2c(NickleILOCParser.C_c2cContext ctx) { return line("iloc_c_c2c(cpu, %s, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2)), regExpr(ctx.reg(3))); }

    @Override public String visitCmpLT(NickleILOCParser.CmpLTContext ctx) { return line("iloc_cmp_LT(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCmpLE(NickleILOCParser.CmpLEContext ctx) { return line("iloc_cmp_LE(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCmpEQ(NickleILOCParser.CmpEQContext ctx) { return line("iloc_cmp_EQ(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }
    
    @Override public String visitCmpGE(NickleILOCParser.CmpGEContext ctx) { return line("iloc_cmp_GE(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCmpGT(NickleILOCParser.CmpGTContext ctx) { return line("iloc_cmp_GT(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCmpNE(NickleILOCParser.CmpNEContext ctx) { return line("iloc_cmp_NE(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); }

    @Override public String visitCbr(NickleILOCParser.CbrContext ctx) {
        String cond = regExpr(ctx.reg());
        String t = ctx.ID(0).getText();
        String f = ctx.ID(1).getText();
        ensureLabel(t);
        ensureLabel(f);
        return line("if (get_reg(cpu,%s)) goto %s; else goto %s;", cond, t, f);
    }

    @Override public String visitJumpI(NickleILOCParser.JumpIContext ctx) {
        String l = ctx.ID().getText();
        ensureLabel(l);
        return line("goto %s;", l);
    }

    @Override public String visitPInt(NickleILOCParser.PIntContext ctx) { return line("iloc_p_int(cpu, %s);", regExpr(ctx.reg())); }
    @Override public String visitPChar(NickleILOCParser.PCharContext ctx) { return line("iloc_p_char(cpu, %s);", regExpr(ctx.reg())); }
    @Override public String visitPStr(NickleILOCParser.PStrContext ctx) { return line("iloc_p_str(cpu, %s);", regExpr(ctx.reg())); }
    @Override public String visitPPrompt(NickleILOCParser.PPromptContext ctx) { return line("printf(%s);",ctx.stringLiteral().getText()) ; } 
    @Override public String visitDReg(NickleILOCParser.DRegContext ctx) { return line("iloc_d_reg(cpu);"); }
    @Override public String visitDMem(NickleILOCParser.DMemContext ctx) { return line("iloc_d_mem(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }
    @Override public String visitAtoi(NickleILOCParser.AtoiContext ctx){ return line("iloc_atoi(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }
	@Override public String visitIs_i(NickleILOCParser.Is_iContext ctx) { return line("iloc_is_i(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); }

    @Override public String visitHaltInstr(NickleILOCParser.HaltInstrContext ctx) { return line("iloc_halt(cpu);"); }

    /* ---------------- helpers ---------------- */

    private void emitPreamble() {
        out.append("#include \"iloc.h\"\n");
        out.append("#include \"stdio.h\"\n");
        out.append("\n");
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

    private void emitIlocMainHeader() {
        out.append("void iloc_main(cpu_t* cpu) {\n");
    }

    private void emitIlocMainFooter() {
        out.append("}\n");
    }

    private String line(String fmt, Object... args) {
        out.append("    ").append(String.format(Locale.ROOT, fmt, args)).append("\n");
        return null ; 
    }

    private void ensureLabel(String name) {
        if (!model.labelIds().containsKey(name))
            throw new IllegalArgumentException("unknown label: " + name);
    }

    private int labelId(String name) {
        Integer id = model.labelIds().get(name);
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
        if (r.R_STATIC() != null) return "get_r_static_idx(cpu)";
        if (r.R_ARGC() != null)   return "get_r_argc_idx(cpu)";
        if (r.R_ARGV() != null)   return "get_r_argv_idx(cpu)";
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
