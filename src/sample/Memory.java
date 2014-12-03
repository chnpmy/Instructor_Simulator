package sample;

/**
 * Created by chnpmy on 2014/12/1.
 * 模拟内存
 * 实现了load和store
 */
public class Memory {
    static public int startAddress = 0;
    static public int endAddress = 0;
    static public int[] mem;
    public Memory(int startAddress, int endAddress){
        if (startAddress >= endAddress){
            System.out.println("Illegal Address Range!");
            return;
        }
        Memory.startAddress = startAddress;
        Memory.endAddress = endAddress;
        Memory.mem = new int[endAddress - startAddress + 1];
    }

    static int load(int address){
        if (address > endAddress){
            System.out.println("Load : Illegal Address Range!");
            return -1;
        }
        return mem[address - startAddress];
    }

    static void store(int address, int value){
        if (address > endAddress || address < startAddress){
            System.out.println("Store : Illegal Address Range!");
            return;
        }
        mem[address - startAddress] = value;
    }
}
