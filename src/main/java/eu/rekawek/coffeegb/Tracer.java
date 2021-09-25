package eu.rekawek.coffeegb;

import eu.rekawek.coffeegb.cpu.Registers;
import eu.rekawek.coffeegb.cpu.op.Op;
import eu.rekawek.coffeegb.cpu.opcode.Opcode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Tracer {
    long opcodeCounter = 0;
    private BufferedWriter writer;
    public Tracer(String filename) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filename));
    }

    private String toHex(int c) {
        return String.format("0x%X", c);
    }

    public void write(Opcode opcode, Registers registers) throws IOException {
        String HL =  toHex(registers.getHL());
        String AF = toHex(registers.getAF());
        String BC = toHex(registers.getBC());
        String DE = toHex(registers.getDE());
        String SP = toHex(registers.getSP());
        String registerState = String.format("HL:%s;AF:%s;BC:%s;DE:%s;SP:%s",HL, AF,BC, DE, SP);

        if(opcode != null) {
            this.writer.write(String.format("C:%s;PC:%s;OPCODE:%s;%s; \n", opcodeCounter, toHex(registers.getPC()), toHex(opcode.getOpcode()), registerState));
        }
        opcodeCounter += 1;
    }

    public void close() throws IOException {
        this.writer.close();
    }

}
