package sample;

import java.util.StringTokenizer;

/**
 * Created by chnpmy on 2014/12/1.
 * 指令级模拟器
 */
public class Instruction{
    private String option;
    private int rs;
    private int rd;
    private int rdNum;
    private int imm;

    private boolean valid = true;

    public Instruction(String instruction){
        System.out.println(instruction);
        StringTokenizer stringTokenizer = new StringTokenizer(instruction, " \t,", false);
        option = stringTokenizer.nextToken();

        /**
         * 注意此时传过来的rd只有store和blt有效
         */
        rdNum = Integer.parseInt(stringTokenizer.nextToken().substring(1, 2));
        rd = getRegValue(rdNum);

        rs = getRegValue(Integer.parseInt(stringTokenizer.nextToken().substring(1, 2)));
        imm = Integer.parseInt(stringTokenizer.nextToken());
        if (stringTokenizer.hasMoreTokens() && !stringTokenizer.nextToken().substring(0, 1).equals(";")){
            valid = false;
            System.out.println("Instruction: Invalid!");
        }
    }

    public boolean run(){
        if (!valid)
            return false;
        if (option.toLowerCase().equals("load"))
            load();
        else if (option.toLowerCase().equals("store"))
            store();
        else if (option.toLowerCase().equals("add"))
            add();
        else if (option.toLowerCase().equals("blt"))
            blt();
        else return false;
        return true;
    }

    private void load(){
        setRegValue(rdNum, Memory.load(rs + imm));
        Register.pc++;
    }

    private void store(){
        Memory.store(rs + imm, rd);
        Register.pc++;
    }

    private void add(){
        System.out.println("add");
        setRegValue(rdNum, rs + imm);
        Register.pc++;
    }

    private void blt(){
        if (rd < rs)
            Register.pc += imm;
        else
            Register.pc++;
    }

    private int getRegValue(int RNum){
        int res = -1;
        switch (RNum){
            case 0: res = Register.r0;break;
            case 1: res = Register.r1;break;
            case 2: res = Register.r2;break;
            case 3: res = Register.r3;break;
            case 4: res = Register.r4;break;
            case 5: res = Register.r5;break;
            case 6: res = Register.r6;break;
            case 7: res = Register.r7;break;
        }
        return res;
    }

    private void setRegValue(int RNum, int value){
        switch (RNum){
            case 1: Register.r1 = value;break;
            case 2: Register.r2 = value;break;
            case 3: Register.r3 = value;break;
            case 4: Register.r4 = value;break;
            case 5: Register.r5 = value;break;
            case 6: Register.r6 = value;break;
            case 7: Register.r7 = value;break;
        }
    }
}
