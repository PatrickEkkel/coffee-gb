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
    private BufferedWriter registerTraceWriter;
    public Tracer(String opcodeTraceFilename, String memoryTraceFilename, String registerTraceFilename) throws IOException {
        this.opcodeWriter = new BufferedWriter(new FileWriter(opcodeTraceFilename));
        this.memoryTraceWriter = new BufferedWriter(new FileWriter(memoryTraceFilename));
        this.registerTraceWriter = new BufferedWriter(new FileWriter(registerTraceFilename));
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
            this.opcodeWriter.write(String.format("PC:%s;OPCODE:%s;%s;\n",toHex(registers.getPC()), toHex(opcode.getOpcode()), registerState));
            //  this.opcodeWriter.write(String.format("C:%s;PC:%s;OPCODE:%s;\n", opcodeCounter, toHex(registers.getPC()), toHex(opcode.getOpcode())));
        }
        opcodeCounter += 1;
    }

    private void writeMemoryTrace(Opcode opcode, Registers registers, AddressSpace addressSpace, int clockCycle) throws IOException {
        if(opcode != null && opcode.getOpcode()  == 0x38 && clockCycle == 0 && opcodeCounter == 44046) {
            System.out.println("bla");
        }
        this.clockCycles = clockCycle;
        String PC = toHex(registers.getPC());
        String Clock = String.valueOf(clockCycle);
        String TIMA = toHex(addressSpace.getByte(0xFF05));
        String TAC =  toHex(addressSpace.getByte(0xFF07));
        String LY = toHex(addressSpace.getByte(0xFF44));

       // String LY = "0x0";
        //String DIV = toHex(addressSpace.getByte(0xFF04));
        String DIV = "0x0";
        String TMA = toHex(addressSpace.getByte(0xFF06));
        if(opcode != null) {
            //String registerState = String.format("OPCODE:%s;PC:%s;LY:%s;CLOCK:%s\n",toHex(opcode.getOpcode()),PC, LY,clockCycle);
            String registerState = String.format("OPCODE:%s;PC:%s;TIMA:%s;TAC:%s;DIV:%s;TMA:%s;LY:%s;CLOCK:%s\n",toHex(opcode.getOpcode()), PC, TIMA, TAC, DIV, TMA,LY,clockCycle);
            this.registerTraceWriter.write(registerState);
        }


    }

    public void writeMemoryInteraction(String action,int address, int value) {
        String formattedString = "";
        if(action.equals("READ")) {
            formattedString = String.format("A:%s;ADDR:%s;VAL:%s\n",action,toHex(address),toHex(value));
        } else if(action.equals("WRITE")) {
            formattedString = String.format("A:%s;ADDR:%s;VAL:%s\n",action,toHex(address), toHex(value));
        }


        try {
            this.memoryTraceWriter.write(formattedString);
        }
        catch (Exception e) {
           System.out.printf("error: %s%n",e);
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
