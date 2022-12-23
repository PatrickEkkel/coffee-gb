package eu.rekawek.coffeegb;

import eu.rekawek.coffeegb.cpu.Registers;
import eu.rekawek.coffeegb.cpu.opcode.Opcode;

public class TraceLine {
    public int pc;
    public Opcode currentOpcode;
    public Registers registers;
    public AddressSpace addressSpace;
    public int cycles;

    public boolean writeLine = false;

}
