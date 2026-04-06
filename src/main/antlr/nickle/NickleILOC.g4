
grammar NickleILOC;

@header { package nickle; }

/* =========================
   Parser rules
   ========================= */

program
    : directive* dataSection? codeSection EOF
    ;

directive
    : memoryDirective
    | registersDirective
    ;

memoryDirective
    : DOT_MEMORY number
    ;

registersDirective
    : DOT_REGISTERS number
    ;

dataSection
    : DOT_DATA dataDecl+
    ;

dataDecl
    : ID COLON dataValue
    ;

dataValue
    : DOT_INT number
    | DOT_CHAR number
    | DOT_STRING stringLiteral
    ;

codeSection
    : DOT_CODE instruction*
    ;

instruction
    : labeledInstruction
    | plainInstruction
    ;

labeledInstruction
    : ID COLON plainInstruction
    ;

plainInstruction
    : instruction0 
    | instruction1 
    | instruction2 
    | instruction3 
    ;

instruction0
    : nop | halt
    | dReg 
    ; 

instruction1
    : jump | jumpI
    | pInt | pChar | pStr | pPrompt 
    ;

instruction2
    : loadI | load 
    | cload 
    | store 
    | cstore 
    | i2i | c2c | c2i | i2c
    | dMem
    | atoi | is_i 
    ;

instruction3
    : add | sub | mult | div
    | addI | subI | rsubI | multI | divI | rdivI
    | lshift | lshiftI | rshift | rshiftI
    | andOp | andI | orOp | orI | xorOp | xorI
    | loadAI | loadAO 
    | cloadAI | cloadAO 
    | storeAI | storeAO 
    | cstoreAI | cstoreAO
    | cmpLT | cmpLE | cmpEQ | cmpGE | cmpGT | cmpNE
    | cbr
    ;

halt : HALT ;
nop : NOP ;

add   : ADD   reg COMMA reg ARROW reg ;
sub   : SUB   reg COMMA reg ARROW reg ;
mult  : MULT  reg COMMA reg ARROW reg ;
div   : DIV   reg COMMA reg ARROW reg ;

addI  : ADDI  reg COMMA number ARROW reg ;
subI  : SUBI  reg COMMA number ARROW reg ;
rsubI : RSUBI reg COMMA number ARROW reg ;
multI : MULTI reg COMMA number ARROW reg ;
divI  : DIVI  reg COMMA number ARROW reg ;
rdivI : RDIVI reg COMMA number ARROW reg ;

lshift  : LSHIFT  reg COMMA reg ARROW reg ;
lshiftI : LSHIFTI reg COMMA number ARROW reg ;
rshift  : RSHIFT  reg COMMA reg ARROW reg ;
rshiftI : RSHIFTI reg COMMA number ARROW reg ;

andOp : AND reg COMMA reg ARROW reg ;
andI  : ANDI reg COMMA number ARROW reg ;
orOp  : OR reg COMMA reg ARROW reg ;
orI   : ORI reg COMMA number ARROW reg ;
xorOp : XOR reg COMMA reg ARROW reg ;
xorI  : XORI reg COMMA number ARROW reg ;

load    : LOAD reg ARROW reg ;
loadAI  : LOADAI reg COMMA offset ARROW reg ;
loadAO  : LOADAO reg COMMA reg ARROW reg ;
cload   : CLOAD reg ARROW reg ;
cloadAI : CLOADAI reg COMMA offset ARROW reg ;
cloadAO : CLOADAO reg COMMA reg ARROW reg ;
loadI   : LOADI (number | ID) ARROW reg ; // ID is Nickle extension: label-constant

store   : STORE reg ARROW reg ;
storeAI : STOREAI reg ARROW reg COMMA offset ;
storeAO : STOREAO reg ARROW reg COMMA reg ;
cstore  : CSTORE reg ARROW reg ;
cstoreAI: CSTOREAI reg ARROW reg COMMA offset ;
cstoreAO: CSTOREAO reg ARROW reg COMMA reg ;

i2i : I2I reg ARROW reg ;
c2c : C2C reg ARROW reg ;
c2i : C2I reg ARROW reg ;
i2c : I2C reg ARROW reg ;

cmpLT : CMP_LT reg COMMA reg ARROW reg ;
cmpLE : CMP_LE reg COMMA reg ARROW reg ;
cmpEQ : CMP_EQ reg COMMA reg ARROW reg ;
cmpGE : CMP_GE reg COMMA reg ARROW reg ;
cmpGT : CMP_GT reg COMMA reg ARROW reg ;
cmpNE : CMP_NE reg COMMA reg ARROW reg ;

cbr : CBR reg ARROW ID COMMA ID ;

jump  : JUMP  ARROW ID ;
jumpI : JUMPI ARROW ID ;

pInt : P_INT reg ;
pChar: P_CHAR reg ;
pStr : P_STR reg ;
pPrompt : P_PROMPT stringLiteral ;
dReg : D_REG ;
dMem : D_MEM reg COMMA reg ;

atoi : ATOI reg ARROW reg ;
is_i : IS_I reg ARROW reg ; 


offset : atSymbol | number ;
atSymbol : AT ID ;

reg
    : REGISTER_NUM
    | R_PC
    | R_STATIC
    | R_ARGC
    | R_ARGV
    ;

number : NUMBER ;
stringLiteral : STRING ;

/* =========================
   Lexer rules
   ========================= */

DOT_MEMORY    : '.memory' ;
DOT_REGISTERS : '.registers' ;
DOT_DATA      : '.data' ;
DOT_CODE      : '.code' ;

DOT_INT       : '.int' ;
DOT_CHAR      : '.char' ;
DOT_STRING    : '.string' ;

HALT          : 'halt' ;
NOP           : 'nop' ;

ADD           : 'add' ;
SUB           : 'sub' ;
MULT          : 'mult' ;
DIV           : 'div' ;

ADDI          : 'addI' ;
SUBI          : 'subI' ;
RSUBI         : 'rsubI' ;
MULTI         : 'multI' ;
DIVI          : 'divI' ;
RDIVI         : 'rdivI' ;

LSHIFT        : 'lshift' ;
LSHIFTI       : 'lshiftI' ;
RSHIFT        : 'rshift' ;
RSHIFTI       : 'rshiftI' ;

AND           : 'and' ;
ANDI          : 'andI' ;
OR            : 'or' ;
ORI           : 'orI' ;
XOR           : 'xor' ;
XORI          : 'xorI' ;

LOADI         : 'loadI' ;
LOAD          : 'load' ;
LOADAI        : 'loadAI' ;
LOADAO        : 'loadAO' ;

CLOAD         : 'cload' ;
CLOADAI       : 'cloadAI' ;
CLOADAO       : 'cloadAO' ;

STORE         : 'store' ;
STOREAI       : 'storeAI' ;
STOREAO       : 'storeAO' ;

CSTORE        : 'cstore' ;
CSTOREAI      : 'cstoreAI' ;
CSTOREAO      : 'cstoreAO' ;

I2I           : 'i2i' ;
C2C           : 'c2c' ;
C2I           : 'c2i' ;
I2C           : 'i2c' ;

JUMP          : 'jump' ;
JUMPI         : 'jumpI' ;

CMP_LT        : 'cmp_LT' ;
CMP_LE        : 'cmp_LE' ;
CMP_EQ        : 'cmp_EQ' ;
CMP_GE        : 'cmp_GE' ;
CMP_GT        : 'cmp_GT' ;
CMP_NE        : 'cmp_NE' ;

CBR           : 'cbr' ;

P_INT         : 'p_int' ;
P_CHAR        : 'p_char' ;
P_STR         : 'p_str' ;
P_PROMPT      : 'p_prompt' ;

D_REG         : 'd_reg' ;
D_MEM         : 'd_mem' ;

ATOI          : 'atoi' ;
IS_I          : 'is_i' ;

R_PC          : 'pc' ; 
R_STATIC      : 'r_static' ;
R_ARGC        : 'r_argc' ;
R_ARGV        : 'r_argv' ;

ARROW         : '=>' ;
COMMA         : ',' ;
COLON         : ':' ;
AT            : '@' ;

REGISTER_NUM  : 'r' [0-9]+ ;

NUMBER
    : '0' [xX] [0-9a-fA-F]+
    | '0' [bB] [01]+
    | '0' [0-7]+
    | '0'
    | '-'? [1-9] [0-9]*
    ;

ID  : [a-zA-Z_] [a-zA-Z0-9_]* ;

STRING
    : '"' (~["\\] | '\\' .)* '"'
    ;

WS            : [ \t\r\n]+ -> skip ;
COMMENT       : '//' ~[\r\n]* -> skip ;
