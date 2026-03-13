
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

    @Override
    public String visitPlainInstruction(NickleILOCParser.PlainInstructionContext ctx) {
        // Arithmetic
        if (ctx.nop() != null) return visit(ctx.nop());
        if (ctx.add() != null) return visit(ctx.add());
        if (ctx.sub() != null) return visit(ctx.sub());
        if (ctx.mult() != null) return visit(ctx.mult());
        if (ctx.div() != null) return visit(ctx.div());
        if (ctx.addI() != null) return visit(ctx.addI());
        if (ctx.subI() != null) return visit(ctx.subI());
        if (ctx.rsubI() != null) return visit(ctx.rsubI());
        if (ctx.multI() != null) return visit(ctx.multI());
        if (ctx.divI() != null) return visit(ctx.divI());
        if (ctx.rdivI() != null) return visit(ctx.rdivI());
        if (ctx.lshift() != null) return visit(ctx.lshift());
        if (ctx.lshiftI() != null) return visit(ctx.lshiftI());
        if (ctx.rshift() != null) return visit(ctx.rshift());
        if (ctx.rshiftI() != null) return visit(ctx.rshiftI());
        if (ctx.andOp() != null) return visit(ctx.andOp());
        if (ctx.andI() != null) return visit(ctx.andI());
        if (ctx.orOp() != null) return visit(ctx.orOp());
        if (ctx.orI() != null) return visit(ctx.orI());
        if (ctx.xorOp() != null) return visit(ctx.xorOp());
        if (ctx.xorI() != null) return visit(ctx.xorI());

        // Memory
        if (ctx.load() != null) return visit(ctx.load());
        if (ctx.loadAI() != null) return visit(ctx.loadAI());
        if (ctx.loadAO() != null) return visit(ctx.loadAO());
        if (ctx.cload() != null) return visit(ctx.cload());
        if (ctx.cloadAI() != null) return visit(ctx.cloadAI());
        if (ctx.cloadAO() != null) return visit(ctx.cloadAO());
        if (ctx.loadI() != null) return visit(ctx.loadI());
        if (ctx.store() != null) return visit(ctx.store());
        if (ctx.storeAI() != null) return visit(ctx.storeAI());
        if (ctx.storeAO() != null) return visit(ctx.storeAO());
        if (ctx.cstore() != null) return visit(ctx.cstore());
        if (ctx.cstoreAI() != null) return visit(ctx.cstoreAI());
        if (ctx.cstoreAO() != null) return visit(ctx.cstoreAO());

        // Copies / conversions / conditional moves
        if (ctx.i2i() != null) return visit(ctx.i2i());
        if (ctx.c2c() != null) return visit(ctx.c2c());
        if (ctx.c2i() != null) return visit(ctx.c2i());
        if (ctx.i2c() != null) return visit(ctx.i2c());
        if (ctx.c_i2i() != null) return visit(ctx.c_i2i());
        if (ctx.c_c2c() != null) return visit(ctx.c_c2c());

        // Control
        if (ctx.cmpLT() != null) return visit(ctx.cmpLT());
        if (ctx.cmpLE() != null) return visit(ctx.cmpLE());
        if (ctx.cmpEQ() != null) return visit(ctx.cmpEQ());
        if (ctx.cmpGE() != null) return visit(ctx.cmpGE());
        if (ctx.cmpGT() != null) return visit(ctx.cmpGT());
        if (ctx.cmpNE() != null) return visit(ctx.cmpNE());
        if (ctx.cbr() != null) return visit(ctx.cbr());
        if (ctx.jumpI() != null) return visit(ctx.jumpI());
        if (ctx.jump() != null) return visit(ctx.jump());

        // IO / debug / misc
        if (ctx.pInt() != null) return visit(ctx.pInt());
        if (ctx.pChar() != null) return visit(ctx.pChar());
        if (ctx.pStr() != null) return visit(ctx.pStr());
        if (ctx.dReg() != null) return visit(ctx.dReg());
        if (ctx.dMem() != null) return visit(ctx.dMem());
        if (ctx.atoi() != null) return visit(ctx.atoi());
        if (ctx.haltInstr() != null) return visit(ctx.haltInstr());

        return null;
    }

    /* ---------------- concrete visits ---------------- */

    @Override public String visitNop(NickleILOCParser.NopContext ctx){ line("iloc_nop(cpu);"); return null; }

    @Override public String visitAdd(NickleILOCParser.AddContext ctx){
        line("iloc_add(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitSub(NickleILOCParser.SubContext ctx){
        line("iloc_sub(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitMult(NickleILOCParser.MultContext ctx){
        line("iloc_mult(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitDiv(NickleILOCParser.DivContext ctx){
        line("iloc_div(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }

    @Override public String visitAddI(NickleILOCParser.AddIContext ctx){
        line("iloc_addI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }
    @Override public String visitSubI(NickleILOCParser.SubIContext ctx){
        line("iloc_subI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }
    @Override public String visitRsubI(NickleILOCParser.RsubIContext ctx){
        line("iloc_rsubI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }
    @Override public String visitMultI(NickleILOCParser.MultIContext ctx){
        line("iloc_multI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }
    @Override public String visitDivI(NickleILOCParser.DivIContext ctx){
        line("iloc_divI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }
    @Override public String visitRdivI(NickleILOCParser.RdivIContext ctx){
        line("iloc_rdivI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }

    @Override public String visitLshift(NickleILOCParser.LshiftContext ctx){
        line("iloc_lshift(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitLshiftI(NickleILOCParser.LshiftIContext ctx){
        line("iloc_lshiftI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }
    @Override public String visitRshift(NickleILOCParser.RshiftContext ctx){
        line("iloc_rshift(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitRshiftI(NickleILOCParser.RshiftIContext ctx){
        line("iloc_rshiftI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }

    @Override public String visitAndOp(NickleILOCParser.AndOpContext ctx){
        line("iloc_and(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitAndI(NickleILOCParser.AndIContext ctx){
        line("iloc_andI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }
    @Override public String visitOrOp(NickleILOCParser.OrOpContext ctx){
        line("iloc_or(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitOrI(NickleILOCParser.OrIContext ctx){
        line("iloc_orI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }
    @Override public String visitXorOp(NickleILOCParser.XorOpContext ctx){
        line("iloc_xor(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitXorI(NickleILOCParser.XorIContext ctx){
        line("iloc_xorI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), numberToC(ctx.number().getText()), regExpr(ctx.reg(1))); return null; }

    @Override public String visitLoad(NickleILOCParser.LoadContext ctx){
        line("iloc_load(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); return null; }
    @Override public String visitLoadAI(NickleILOCParser.LoadAIContext ctx){
        line("iloc_loadAI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), offsetToC(ctx.offset()), regExpr(ctx.reg(1))); return null; }
    @Override public String visitLoadAO(NickleILOCParser.LoadAOContext ctx){
        line("iloc_loadAO(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitCload(NickleILOCParser.CloadContext ctx){
        line("iloc_cload(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); return null; }
    @Override public String visitCloadAI(NickleILOCParser.CloadAIContext ctx){
        line("iloc_cloadAI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), offsetToC(ctx.offset()), regExpr(ctx.reg(1))); return null; }
    @Override public String visitCloadAO(NickleILOCParser.CloadAOContext ctx){
        line("iloc_cloadAO(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }

    @Override public String visitLoadI(NickleILOCParser.LoadIContext ctx){
        String imm;
        if (ctx.number() != null) imm = numberToC(ctx.number().getText());
        else imm = Integer.toString(labelId(ctx.ID().getText())); // extension
        line("iloc_loadI(cpu, %s, %s);", imm, regExpr(ctx.reg()));
        return null;
    }

    @Override public String visitStore(NickleILOCParser.StoreContext ctx){
        line("iloc_store(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); return null; }
    @Override public String visitStoreAI(NickleILOCParser.StoreAIContext ctx){
        line("iloc_storeAI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), offsetToC(ctx.offset())); return null; }
    @Override public String visitStoreAO(NickleILOCParser.StoreAOContext ctx){
        line("iloc_storeAO(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }

    @Override public String visitCstore(NickleILOCParser.CstoreContext ctx){
        line("iloc_cstore(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); return null; }
    @Override public String visitCstoreAI(NickleILOCParser.CstoreAIContext ctx){
        line("iloc_cstoreAI(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), offsetToC(ctx.offset())); return null; }
    @Override public String visitCstoreAO(NickleILOCParser.CstoreAOContext ctx){
        line("iloc_cstoreAO(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }

    @Override public String visitI2i(NickleILOCParser.I2iContext ctx){
        line("iloc_i2i(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); return null; }
    @Override public String visitC2c(NickleILOCParser.C2cContext ctx){
        line("iloc_c2c(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); return null; }
    @Override public String visitC2i(NickleILOCParser.C2iContext ctx){
        line("iloc_c2i(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); return null; }
    @Override public String visitI2c(NickleILOCParser.I2cContext ctx){
        line("iloc_i2c(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); return null; }

    @Override public String visitC_i2i(NickleILOCParser.C_i2iContext ctx){
        line("iloc_c_i2i(cpu, %s, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2)), regExpr(ctx.reg(3))); return null; }
    @Override public String visitC_c2c(NickleILOCParser.C_c2cContext ctx){
        line("iloc_c_c2c(cpu, %s, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2)), regExpr(ctx.reg(3))); return null; }

    @Override public String visitCmpLT(NickleILOCParser.CmpLTContext ctx){
        line("iloc_cmp_LT(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitCmpLE(NickleILOCParser.CmpLEContext ctx){
        line("iloc_cmp_LE(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitCmpEQ(NickleILOCParser.CmpEQContext ctx){
        line("iloc_cmp_EQ(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitCmpGE(NickleILOCParser.CmpGEContext ctx){
        line("iloc_cmp_GE(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitCmpGT(NickleILOCParser.CmpGTContext ctx){
        line("iloc_cmp_GT(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }
    @Override public String visitCmpNE(NickleILOCParser.CmpNEContext ctx){
        line("iloc_cmp_NE(cpu, %s, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1)), regExpr(ctx.reg(2))); return null; }

    @Override public String visitCbr(NickleILOCParser.CbrContext ctx) {
        String cond = regExpr(ctx.reg());
        String t = ctx.ID(0).getText();
        String f = ctx.ID(1).getText();
        ensureLabel(t);
        ensureLabel(f);
        line("if (get_reg(cpu,%s)) goto %s; else goto %s;", cond, t, f);
        return null;
    }

    @Override public String visitJumpI(NickleILOCParser.JumpIContext ctx) {
        String l = ctx.ID().getText();
        ensureLabel(l);
        line("goto %s;", l);
        return null;
    }

    @Override public String visitJump(NickleILOCParser.JumpContext ctx) {
        String r = regExpr(ctx.reg());
        out.append("    switch (get_reg(cpu,").append(r).append(")) {\n");
        for (var e : model.labelIds().entrySet()) {
            out.append("        case ").append(e.getValue()).append(": goto ").append(e.getKey()).append(";\n");
        }
        out.append("        default: nickle_trap(cpu, \"invalid jump target\"); return;\n");
        out.append("    }\n");
        return null;
    }

    @Override public String visitPInt(NickleILOCParser.PIntContext ctx) { line("iloc_p_int(cpu, %s);", regExpr(ctx.reg())); return null; }
    @Override public String visitPChar(NickleILOCParser.PCharContext ctx){ line("iloc_p_char(cpu, %s);", regExpr(ctx.reg())); return null; }
    @Override public String visitPStr(NickleILOCParser.PStrContext ctx) { line("iloc_p_str(cpu, %s);", regExpr(ctx.reg())); return null; }
    @Override public String visitDReg(NickleILOCParser.DRegContext ctx) { line("iloc_d_reg(cpu);"); return null; }
    @Override public String visitDMem(NickleILOCParser.DMemContext ctx) { line("iloc_d_mem(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); return null; }

    @Override public String visitAtoi(NickleILOCParser.AtoiContext ctx){ line("iloc_atoi(cpu, %s, %s);", regExpr(ctx.reg(0)), regExpr(ctx.reg(1))); return null; }

    @Override public String visitHaltInstr(NickleILOCParser.HaltInstrContext ctx) { line("iloc_halt(cpu);"); return null; }

    /* ---------------- helpers ---------------- */

    private void emitPreamble() {
        out.append("#include \"iloc.h\"\n\n");
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

    private void line(String fmt, Object... args) {
        out.append("    ").append(String.format(Locale.ROOT, fmt, args)).append("\n");
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
