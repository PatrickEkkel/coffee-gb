package eu.rekawek.coffeegb;

import eu.rekawek.coffeegb.cpu.Registers;
import eu.rekawek.coffeegb.cpu.op.Op;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Tracer {

    private BufferedWriter writer;
    public Tracer(String filename) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filename));
    }

    public void write(Op op, Registers registers) throws IOException {
        this.writer.write(String.format("Opcode: %s Registers: %s", op.toString(),registers.toString()));

    }

    public void close() throws IOException {
        this.writer.close();
    }

}
