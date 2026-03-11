#include "iloc.h"
#include "cpu.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <stddef.h>

const int EXTRA_REGS = 3 ; 
const int R_STATIC_OFFSET = 0 ; 
const int R_ARGC_OFFSET = 1 ; 
const int R_ARGV_OFFSET = 2 ; 


int get_r_static_idx(cpu_t *cpu)    {return cpu->reg_count+R_STATIC_OFFSET;   }
int get_r_argc_idx(cpu_t *cpu)      {return cpu->reg_count+R_ARGC_OFFSET; }
int get_r_argv_idx(cpu_t *cpu)      {return cpu->reg_count+R_ARGV_OFFSET; }
int get_reg(cpu_t *cpu, int idx)    {return cpu->regs[idx] ;}

static void die(const char* msg) {
    fprintf(stderr, "%s\n", msg);
    exit(1);
}

void nickle_trap(cpu_t* cpu, const char* msg) {
    fprintf(stderr, "Nickle trap: %s\n", msg);
    exit(1);
}

void nickle_check_mem(cpu_t* cpu, uint64_t addr, uint64_t size) {
    if (addr < 0 || size < 0 || addr + size > cpu->mem_size) nickle_trap(cpu, "memory access out of range");
}

void nickle_init(cpu_t* cpu, size_t user_regs, size_t mem_size) {
    cpu->reg_count  = user_regs;

    cpu->regs = calloc(cpu->reg_count+EXTRA_REGS, sizeof(uint64_t));
    cpu->mem  = calloc(mem_size, 1);
    cpu->mem_size = mem_size;

    if (!cpu->regs || !cpu->mem) die("allocation failed");
    cpu->halted = false;
}

void nickle_free(cpu_t* cpu) {
    free(cpu->regs);
    free(cpu->mem);
}

void nickle_build_static(cpu_t* cpu) {
    uint64_t inline_size = (uint64_t)STATIC_COUNT * 8u;
    uint64_t string_size = 0;

    for (size_t i = 0; i < STATIC_COUNT; i++)
        if (STATIC_DATA[i].kind == D_STRING)
            string_size += (uint64_t)strlen(STATIC_DATA[i].v.s) + 1u;

    uint64_t total = inline_size + string_size;
    if (total > cpu->mem_size) nickle_trap(cpu, "static preload does not fit in memory");

    uint64_t base  = (uint64_t)cpu->mem_size - total;

    cpu->regs[get_r_static_idx(cpu)] = base;

    uint64_t inline_ptr = base;
    uint64_t string_ptr = base + inline_size;

    for (size_t i = 0; i < STATIC_COUNT; i++) {
        nickle_check_mem(cpu, inline_ptr, 8);
        switch (STATIC_DATA[i].kind) {
            case D_INT:
                memcpy(cpu->mem + inline_ptr, &STATIC_DATA[i].v.i, 8);
                break;
            case D_CHAR: {
                uint64_t v = STATIC_DATA[i].v.c;
                memcpy(cpu->mem + inline_ptr, &v, 8);
                break;
            }
            case D_STRING: {
                const char* s = STATIC_DATA[i].v.s;
                size_t n = strlen(s) + 1;
                nickle_check_mem(cpu, string_ptr, (uint64_t)n);
                memcpy(cpu->mem + inline_ptr, &string_ptr, 8);
                memcpy(cpu->mem + string_ptr, s, n);
                string_ptr += (uint64_t)n;
                break;
            }
        }
        inline_ptr += 8;
    }
}

void nickle_build_args(cpu_t* cpu, int argc, char** argv) {
    int count = argc > 1 ? argc - 1 : 0;
    cpu->regs[get_r_argc_idx(cpu)] = (uint64_t)count;

    if (count == 0) {
        cpu->regs[get_r_argv_idx(cpu)] = 0;
        return;
    }

    uint64_t inline_size = (uint64_t)count * 8u;
    uint64_t string_size = 0;

    for (int i = 1; i < argc; i++)
        string_size += (uint64_t)strlen(argv[i]) + 1u;

    uint64_t total = inline_size + string_size;

    uint64_t static_base = cpu->regs[get_r_static_idx(cpu)];
    if (total > static_base) nickle_trap(cpu, "CLI preload does not fit below static block");

    uint64_t cli_base = static_base - total;
    cpu->regs[get_r_argv_idx(cpu)] = cli_base;

    uint64_t inline_ptr = cli_base;
    uint64_t string_ptr = cli_base + inline_size;

    for (int i = 1; i < argc; i++) {
        size_t n = strlen(argv[i]) + 1;
        nickle_check_mem(cpu, inline_ptr, 8);
        nickle_check_mem(cpu, string_ptr, (uint64_t)n);
        memcpy(cpu->mem + inline_ptr, &string_ptr, 8);
        memcpy(cpu->mem + string_ptr, argv[i], n);
        string_ptr += (uint64_t)n;
        inline_ptr += 8;
    }
}

int main(int argc, char** argv) {
    cpu_t cpu;
    nickle_init(&cpu, PROGRAM_USER_REGS, PROGRAM_MEM_SIZE);
    nickle_build_static(&cpu);
    nickle_build_args(&cpu, argc, argv);
    iloc_main(&cpu);
    nickle_free(&cpu);
    return 0;
}
