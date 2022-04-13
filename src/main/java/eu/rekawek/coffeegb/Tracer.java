package eu.rekawek.coffeegb;

import eu.rekawek.coffeegb.cpu.Registers;
import eu.rekawek.coffeegb.cpu.opcode.Opcode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Tracer {
    long opcodeCounter = 0;
    int clockCycles = 0;
    private BufferedWriter opcodeWriter;
    private BufferedWriter memoryTraceWriter;
    public Tracer(String opcodeTraceFilename, String memoryTraceFilename) throws IOException {
        this.opcodeWriter = new BufferedWriter(new FileWriter(opcodeTraceFilename));
        this.memoryTraceWriter = new BufferedWriter(new FileWriter(memoryTraceFilename));
    }

    private String toHex(int c) {
        return String.format("0x%X", c);
    }

    private void writeOpcode(Opcode opcode, Registers registers) throws IOException {
        String HL = toHex(registers.getHL());
        String AF = toHex(registers.getAF());
        String BC = toHex(registers.getBC());
        String DE = toHex(registers.getDE());
        String SP = toHex(registers.getSP());
        String registerState = String.format("HL:%s;AF:%s;BC:%s;DE:%s;SP:%s",HL, AF,BC, DE, SP);


        if(opcode != null) {
            this.opcodeWriter.write(String.format("PC:%s;OPCODE:%s;%s;\n", toHex(registers.getPC()), toHex(opcode.getOpcode()), registerState));
            //  this.writer.write(String.format("C:%s;PC:%s;OPCODE:%s;\n", opcodeCounter, toHex(registers.getPC()), toHex(opcode.getOpcode())));
        }
        opcodeCounter += 1;
    }

    private void writeMemoryTrace(Opcode opcode, Registers registers, AddressSpace addressSpace, int clockCycle) throws IOException {

        String PC = toHex(registers.getPC());
        String Clock = String.valueOf(clockCycle);
        String TIMA = toHex(addressSpace.getByte(0xFF05));
        String TAC =  toHex(addressSpace.getByte(0xFF07));
        String LY = toHex(addressSpace.getByte(0xFF44));
        //String DIV = toHex(addressSpace.getByte(0xFF04));
        String DIV = "0x0";
        String TMA = toHex(addressSpace.getByte(0xFF06));
        if(opcode != null) {
            String registerState = String.format("COUNT:%s;OPCODE:%s;PC:%s;TIMA:%s;TAC:%s;DIV:%s;TMA:%s;LY:%s;CLOCK:%s\n",opcodeCounter, toHex(opcode.getOpcode()), PC, TIMA, TAC, DIV, TMA,LY,clockCycle);
            this.memoryTraceWriter.write(registerState);
        }


    }

    public void write(Opcode opcode, Registers registers, AddressSpace addressSpace, int clockCycle) throws IOException {
        writeOpcode(opcode, registers);
        writeMemoryTrace(opcode, registers, addressSpace, clockCycle);
    }

    public void close() throws IOException {
        this.opcodeWriter.close();
    }

}
