package BusinessLayer;

public class Validator {

    private String variable;

    public Validator(String variable){
        this.variable=variable;
    }

    public boolean isFloatValid() {//valideaza un numar real
        if(variable.matches("([1-9][0-9]*[.][0-9][0-9]*)|([0][.][0-9][0-9]*)|([1-9][0-9]*)|0")) {
            return true;
        }
        return false;
    }

    public boolean isNotEmpty(){//verifica sa nu fie gol
        if(variable.matches("[ ]+")){
            return false;
        }
        return true;
    }



}
