// Generated from /home/najem/nickle/v4/src/main/antlr/nickle/NickleILOC.g4 by ANTLR 4.13.1
 package nickle; 
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link NickleILOCParser}.
 */
public interface NickleILOCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(NickleILOCParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(NickleILOCParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#directive}.
	 * @param ctx the parse tree
	 */
	void enterDirective(NickleILOCParser.DirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#directive}.
	 * @param ctx the parse tree
	 */
	void exitDirective(NickleILOCParser.DirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#memoryDirective}.
	 * @param ctx the parse tree
	 */
	void enterMemoryDirective(NickleILOCParser.MemoryDirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#memoryDirective}.
	 * @param ctx the parse tree
	 */
	void exitMemoryDirective(NickleILOCParser.MemoryDirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#registersDirective}.
	 * @param ctx the parse tree
	 */
	void enterRegistersDirective(NickleILOCParser.RegistersDirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#registersDirective}.
	 * @param ctx the parse tree
	 */
	void exitRegistersDirective(NickleILOCParser.RegistersDirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#dataSection}.
	 * @param ctx the parse tree
	 */
	void enterDataSection(NickleILOCParser.DataSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#dataSection}.
	 * @param ctx the parse tree
	 */
	void exitDataSection(NickleILOCParser.DataSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#dataDecl}.
	 * @param ctx the parse tree
	 */
	void enterDataDecl(NickleILOCParser.DataDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#dataDecl}.
	 * @param ctx the parse tree
	 */
	void exitDataDecl(NickleILOCParser.DataDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#dataValue}.
	 * @param ctx the parse tree
	 */
	void enterDataValue(NickleILOCParser.DataValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#dataValue}.
	 * @param ctx the parse tree
	 */
	void exitDataValue(NickleILOCParser.DataValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#codeSection}.
	 * @param ctx the parse tree
	 */
	void enterCodeSection(NickleILOCParser.CodeSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#codeSection}.
	 * @param ctx the parse tree
	 */
	void exitCodeSection(NickleILOCParser.CodeSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(NickleILOCParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(NickleILOCParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#labeledInstruction}.
	 * @param ctx the parse tree
	 */
	void enterLabeledInstruction(NickleILOCParser.LabeledInstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#labeledInstruction}.
	 * @param ctx the parse tree
	 */
	void exitLabeledInstruction(NickleILOCParser.LabeledInstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#plainInstruction}.
	 * @param ctx the parse tree
	 */
	void enterPlainInstruction(NickleILOCParser.PlainInstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#plainInstruction}.
	 * @param ctx the parse tree
	 */
	void exitPlainInstruction(NickleILOCParser.PlainInstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#nop}.
	 * @param ctx the parse tree
	 */
	void enterNop(NickleILOCParser.NopContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#nop}.
	 * @param ctx the parse tree
	 */
	void exitNop(NickleILOCParser.NopContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#add}.
	 * @param ctx the parse tree
	 */
	void enterAdd(NickleILOCParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#add}.
	 * @param ctx the parse tree
	 */
	void exitAdd(NickleILOCParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#sub}.
	 * @param ctx the parse tree
	 */
	void enterSub(NickleILOCParser.SubContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#sub}.
	 * @param ctx the parse tree
	 */
	void exitSub(NickleILOCParser.SubContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#mult}.
	 * @param ctx the parse tree
	 */
	void enterMult(NickleILOCParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#mult}.
	 * @param ctx the parse tree
	 */
	void exitMult(NickleILOCParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#div}.
	 * @param ctx the parse tree
	 */
	void enterDiv(NickleILOCParser.DivContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#div}.
	 * @param ctx the parse tree
	 */
	void exitDiv(NickleILOCParser.DivContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#addI}.
	 * @param ctx the parse tree
	 */
	void enterAddI(NickleILOCParser.AddIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#addI}.
	 * @param ctx the parse tree
	 */
	void exitAddI(NickleILOCParser.AddIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#subI}.
	 * @param ctx the parse tree
	 */
	void enterSubI(NickleILOCParser.SubIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#subI}.
	 * @param ctx the parse tree
	 */
	void exitSubI(NickleILOCParser.SubIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#rsubI}.
	 * @param ctx the parse tree
	 */
	void enterRsubI(NickleILOCParser.RsubIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#rsubI}.
	 * @param ctx the parse tree
	 */
	void exitRsubI(NickleILOCParser.RsubIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#multI}.
	 * @param ctx the parse tree
	 */
	void enterMultI(NickleILOCParser.MultIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#multI}.
	 * @param ctx the parse tree
	 */
	void exitMultI(NickleILOCParser.MultIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#divI}.
	 * @param ctx the parse tree
	 */
	void enterDivI(NickleILOCParser.DivIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#divI}.
	 * @param ctx the parse tree
	 */
	void exitDivI(NickleILOCParser.DivIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#rdivI}.
	 * @param ctx the parse tree
	 */
	void enterRdivI(NickleILOCParser.RdivIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#rdivI}.
	 * @param ctx the parse tree
	 */
	void exitRdivI(NickleILOCParser.RdivIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#lshift}.
	 * @param ctx the parse tree
	 */
	void enterLshift(NickleILOCParser.LshiftContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#lshift}.
	 * @param ctx the parse tree
	 */
	void exitLshift(NickleILOCParser.LshiftContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#lshiftI}.
	 * @param ctx the parse tree
	 */
	void enterLshiftI(NickleILOCParser.LshiftIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#lshiftI}.
	 * @param ctx the parse tree
	 */
	void exitLshiftI(NickleILOCParser.LshiftIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#rshift}.
	 * @param ctx the parse tree
	 */
	void enterRshift(NickleILOCParser.RshiftContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#rshift}.
	 * @param ctx the parse tree
	 */
	void exitRshift(NickleILOCParser.RshiftContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#rshiftI}.
	 * @param ctx the parse tree
	 */
	void enterRshiftI(NickleILOCParser.RshiftIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#rshiftI}.
	 * @param ctx the parse tree
	 */
	void exitRshiftI(NickleILOCParser.RshiftIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#andOp}.
	 * @param ctx the parse tree
	 */
	void enterAndOp(NickleILOCParser.AndOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#andOp}.
	 * @param ctx the parse tree
	 */
	void exitAndOp(NickleILOCParser.AndOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#andI}.
	 * @param ctx the parse tree
	 */
	void enterAndI(NickleILOCParser.AndIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#andI}.
	 * @param ctx the parse tree
	 */
	void exitAndI(NickleILOCParser.AndIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#orOp}.
	 * @param ctx the parse tree
	 */
	void enterOrOp(NickleILOCParser.OrOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#orOp}.
	 * @param ctx the parse tree
	 */
	void exitOrOp(NickleILOCParser.OrOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#orI}.
	 * @param ctx the parse tree
	 */
	void enterOrI(NickleILOCParser.OrIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#orI}.
	 * @param ctx the parse tree
	 */
	void exitOrI(NickleILOCParser.OrIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#xorOp}.
	 * @param ctx the parse tree
	 */
	void enterXorOp(NickleILOCParser.XorOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#xorOp}.
	 * @param ctx the parse tree
	 */
	void exitXorOp(NickleILOCParser.XorOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#xorI}.
	 * @param ctx the parse tree
	 */
	void enterXorI(NickleILOCParser.XorIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#xorI}.
	 * @param ctx the parse tree
	 */
	void exitXorI(NickleILOCParser.XorIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#load}.
	 * @param ctx the parse tree
	 */
	void enterLoad(NickleILOCParser.LoadContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#load}.
	 * @param ctx the parse tree
	 */
	void exitLoad(NickleILOCParser.LoadContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#loadAI}.
	 * @param ctx the parse tree
	 */
	void enterLoadAI(NickleILOCParser.LoadAIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#loadAI}.
	 * @param ctx the parse tree
	 */
	void exitLoadAI(NickleILOCParser.LoadAIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#loadAO}.
	 * @param ctx the parse tree
	 */
	void enterLoadAO(NickleILOCParser.LoadAOContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#loadAO}.
	 * @param ctx the parse tree
	 */
	void exitLoadAO(NickleILOCParser.LoadAOContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cload}.
	 * @param ctx the parse tree
	 */
	void enterCload(NickleILOCParser.CloadContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cload}.
	 * @param ctx the parse tree
	 */
	void exitCload(NickleILOCParser.CloadContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cloadAI}.
	 * @param ctx the parse tree
	 */
	void enterCloadAI(NickleILOCParser.CloadAIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cloadAI}.
	 * @param ctx the parse tree
	 */
	void exitCloadAI(NickleILOCParser.CloadAIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cloadAO}.
	 * @param ctx the parse tree
	 */
	void enterCloadAO(NickleILOCParser.CloadAOContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cloadAO}.
	 * @param ctx the parse tree
	 */
	void exitCloadAO(NickleILOCParser.CloadAOContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#loadI}.
	 * @param ctx the parse tree
	 */
	void enterLoadI(NickleILOCParser.LoadIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#loadI}.
	 * @param ctx the parse tree
	 */
	void exitLoadI(NickleILOCParser.LoadIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#store}.
	 * @param ctx the parse tree
	 */
	void enterStore(NickleILOCParser.StoreContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#store}.
	 * @param ctx the parse tree
	 */
	void exitStore(NickleILOCParser.StoreContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#storeAI}.
	 * @param ctx the parse tree
	 */
	void enterStoreAI(NickleILOCParser.StoreAIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#storeAI}.
	 * @param ctx the parse tree
	 */
	void exitStoreAI(NickleILOCParser.StoreAIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#storeAO}.
	 * @param ctx the parse tree
	 */
	void enterStoreAO(NickleILOCParser.StoreAOContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#storeAO}.
	 * @param ctx the parse tree
	 */
	void exitStoreAO(NickleILOCParser.StoreAOContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cstore}.
	 * @param ctx the parse tree
	 */
	void enterCstore(NickleILOCParser.CstoreContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cstore}.
	 * @param ctx the parse tree
	 */
	void exitCstore(NickleILOCParser.CstoreContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cstoreAI}.
	 * @param ctx the parse tree
	 */
	void enterCstoreAI(NickleILOCParser.CstoreAIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cstoreAI}.
	 * @param ctx the parse tree
	 */
	void exitCstoreAI(NickleILOCParser.CstoreAIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cstoreAO}.
	 * @param ctx the parse tree
	 */
	void enterCstoreAO(NickleILOCParser.CstoreAOContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cstoreAO}.
	 * @param ctx the parse tree
	 */
	void exitCstoreAO(NickleILOCParser.CstoreAOContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#i2i}.
	 * @param ctx the parse tree
	 */
	void enterI2i(NickleILOCParser.I2iContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#i2i}.
	 * @param ctx the parse tree
	 */
	void exitI2i(NickleILOCParser.I2iContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#c2c}.
	 * @param ctx the parse tree
	 */
	void enterC2c(NickleILOCParser.C2cContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#c2c}.
	 * @param ctx the parse tree
	 */
	void exitC2c(NickleILOCParser.C2cContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#c2i}.
	 * @param ctx the parse tree
	 */
	void enterC2i(NickleILOCParser.C2iContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#c2i}.
	 * @param ctx the parse tree
	 */
	void exitC2i(NickleILOCParser.C2iContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#i2c}.
	 * @param ctx the parse tree
	 */
	void enterI2c(NickleILOCParser.I2cContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#i2c}.
	 * @param ctx the parse tree
	 */
	void exitI2c(NickleILOCParser.I2cContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#c_i2i}.
	 * @param ctx the parse tree
	 */
	void enterC_i2i(NickleILOCParser.C_i2iContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#c_i2i}.
	 * @param ctx the parse tree
	 */
	void exitC_i2i(NickleILOCParser.C_i2iContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#c_c2c}.
	 * @param ctx the parse tree
	 */
	void enterC_c2c(NickleILOCParser.C_c2cContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#c_c2c}.
	 * @param ctx the parse tree
	 */
	void exitC_c2c(NickleILOCParser.C_c2cContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cmpLT}.
	 * @param ctx the parse tree
	 */
	void enterCmpLT(NickleILOCParser.CmpLTContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cmpLT}.
	 * @param ctx the parse tree
	 */
	void exitCmpLT(NickleILOCParser.CmpLTContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cmpLE}.
	 * @param ctx the parse tree
	 */
	void enterCmpLE(NickleILOCParser.CmpLEContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cmpLE}.
	 * @param ctx the parse tree
	 */
	void exitCmpLE(NickleILOCParser.CmpLEContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cmpEQ}.
	 * @param ctx the parse tree
	 */
	void enterCmpEQ(NickleILOCParser.CmpEQContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cmpEQ}.
	 * @param ctx the parse tree
	 */
	void exitCmpEQ(NickleILOCParser.CmpEQContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cmpGE}.
	 * @param ctx the parse tree
	 */
	void enterCmpGE(NickleILOCParser.CmpGEContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cmpGE}.
	 * @param ctx the parse tree
	 */
	void exitCmpGE(NickleILOCParser.CmpGEContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cmpGT}.
	 * @param ctx the parse tree
	 */
	void enterCmpGT(NickleILOCParser.CmpGTContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cmpGT}.
	 * @param ctx the parse tree
	 */
	void exitCmpGT(NickleILOCParser.CmpGTContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cmpNE}.
	 * @param ctx the parse tree
	 */
	void enterCmpNE(NickleILOCParser.CmpNEContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cmpNE}.
	 * @param ctx the parse tree
	 */
	void exitCmpNE(NickleILOCParser.CmpNEContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#cbr}.
	 * @param ctx the parse tree
	 */
	void enterCbr(NickleILOCParser.CbrContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#cbr}.
	 * @param ctx the parse tree
	 */
	void exitCbr(NickleILOCParser.CbrContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#jumpI}.
	 * @param ctx the parse tree
	 */
	void enterJumpI(NickleILOCParser.JumpIContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#jumpI}.
	 * @param ctx the parse tree
	 */
	void exitJumpI(NickleILOCParser.JumpIContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#jump}.
	 * @param ctx the parse tree
	 */
	void enterJump(NickleILOCParser.JumpContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#jump}.
	 * @param ctx the parse tree
	 */
	void exitJump(NickleILOCParser.JumpContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#pInt}.
	 * @param ctx the parse tree
	 */
	void enterPInt(NickleILOCParser.PIntContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#pInt}.
	 * @param ctx the parse tree
	 */
	void exitPInt(NickleILOCParser.PIntContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#pChar}.
	 * @param ctx the parse tree
	 */
	void enterPChar(NickleILOCParser.PCharContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#pChar}.
	 * @param ctx the parse tree
	 */
	void exitPChar(NickleILOCParser.PCharContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#pStr}.
	 * @param ctx the parse tree
	 */
	void enterPStr(NickleILOCParser.PStrContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#pStr}.
	 * @param ctx the parse tree
	 */
	void exitPStr(NickleILOCParser.PStrContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#dReg}.
	 * @param ctx the parse tree
	 */
	void enterDReg(NickleILOCParser.DRegContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#dReg}.
	 * @param ctx the parse tree
	 */
	void exitDReg(NickleILOCParser.DRegContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#dMem}.
	 * @param ctx the parse tree
	 */
	void enterDMem(NickleILOCParser.DMemContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#dMem}.
	 * @param ctx the parse tree
	 */
	void exitDMem(NickleILOCParser.DMemContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#atoi}.
	 * @param ctx the parse tree
	 */
	void enterAtoi(NickleILOCParser.AtoiContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#atoi}.
	 * @param ctx the parse tree
	 */
	void exitAtoi(NickleILOCParser.AtoiContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#haltInstr}.
	 * @param ctx the parse tree
	 */
	void enterHaltInstr(NickleILOCParser.HaltInstrContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#haltInstr}.
	 * @param ctx the parse tree
	 */
	void exitHaltInstr(NickleILOCParser.HaltInstrContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#offset}.
	 * @param ctx the parse tree
	 */
	void enterOffset(NickleILOCParser.OffsetContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#offset}.
	 * @param ctx the parse tree
	 */
	void exitOffset(NickleILOCParser.OffsetContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#atSymbol}.
	 * @param ctx the parse tree
	 */
	void enterAtSymbol(NickleILOCParser.AtSymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#atSymbol}.
	 * @param ctx the parse tree
	 */
	void exitAtSymbol(NickleILOCParser.AtSymbolContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#reg}.
	 * @param ctx the parse tree
	 */
	void enterReg(NickleILOCParser.RegContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#reg}.
	 * @param ctx the parse tree
	 */
	void exitReg(NickleILOCParser.RegContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(NickleILOCParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(NickleILOCParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link NickleILOCParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(NickleILOCParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link NickleILOCParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(NickleILOCParser.StringLiteralContext ctx);
}