package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by chnpmy on 2014/12/2.
 * 指令执行的指挥进程
 */
public class InstrcutionHandler extends Task<Integer> {
    private Vector<String> vector = new Vector<String>();
    private Controller controller;

    public InstrcutionHandler(String instructions, Controller c) {
        this.controller = c;
        StringTokenizer stringTokenizer = new StringTokenizer(instructions, "\r\n", false);
        while (stringTokenizer.hasMoreTokens()) {
            vector.add(stringTokenizer.nextToken());
        }
    }

    @Override
    protected Integer call() throws Exception {
        Register.pc = 0;
        Statistics.dynamicInstructionCount = 0;
        new Memory(0, 20);
        Register.pc = 0;
        while (Register.pc < vector.size()) {
            new Instruction(vector.elementAt(Register.pc)).run();
            Statistics.dynamicInstructionCount++;
        }
        updateMemory();
        updateRegister();
        updateStat();
        return null;
    }

    private void updateStat(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println(Statistics.dynamicInstructionCount);
                    controller.lblDynamicInstruction.setText("" + Statistics.dynamicInstructionCount);
                    System.out.println(Statistics.dynamicInstructionCount);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateMemory(){
        //tbvMemory.getColumns().clear();
        ObservableList<MemoryMap> memData = FXCollections.observableArrayList();
        for (int i = Memory.startAddress; i <= Memory.endAddress; i++){
            memData.add(new MemoryMap(i, Memory.mem[i - Memory.startAddress]));
        }
        controller.tbvMemory.setItems(memData);
        controller.tbvMemoryAddress.setCellValueFactory(
                new PropertyValueFactory<MemoryMap, Integer>("address")
        );
        controller.tbvMemoryValue.setCellValueFactory(
                new PropertyValueFactory<MemoryMap, Integer>("value")
        );
    }

    private void updateRegister(){
        ObservableList<RegisterMap> regData = FXCollections.observableArrayList();

        regData.add(new RegisterMap("r0", Register.r0));
        regData.add(new RegisterMap("r1", Register.r1));
        regData.add(new RegisterMap("r2", Register.r2));
        regData.add(new RegisterMap("r3", Register.r3));
        regData.add(new RegisterMap("r4", Register.r4));
        regData.add(new RegisterMap("r5", Register.r5));
        regData.add(new RegisterMap("r6", Register.r6));
        regData.add(new RegisterMap("r7", Register.r7));

        regData.add(new RegisterMap("pc", Register.pc));

        controller.tbvRegister.setItems(regData);
        controller.tbvRegisterName.setCellValueFactory(
                new PropertyValueFactory<RegisterMap, String>("name")
        );
        controller.tbvRegisterValue.setCellValueFactory(
                new PropertyValueFactory<RegisterMap, Integer>("value")
        );
    }

    public static class MemoryMap{
        private final SimpleIntegerProperty address;
        private final SimpleIntegerProperty value;

        private MemoryMap(int address, int value){
            this.address = new SimpleIntegerProperty(address);
            this.value = new SimpleIntegerProperty(value);
        }

        public int getAddress(){
            return address.get();
        }

        public void setAddress(int address){
            this.address.set(address);
        }

        public int getValue() {
            return value.get();
        }

        public void setValue(int value){
            this.value.set(value);
        }
    }

    public static class RegisterMap{
        private final SimpleStringProperty name;
        private final SimpleIntegerProperty value;

        private RegisterMap(String name, int value){
            this.name = new SimpleStringProperty(name);
            this.value = new SimpleIntegerProperty(value);
        }

        public String getName(){
            return name.get();
        }

        public void setName(String name){
            this.name.set(name);
        }

        public int getValue(){
            return value.get();
        }

        public void setValue(int value){
            this.value.set(value);
        }
    }
}
