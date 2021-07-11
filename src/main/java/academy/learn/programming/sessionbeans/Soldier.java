package academy.learn.programming.sessionbeans;

import academy.learn.programming.annotations.ServiceManQualifier;
import academy.learn.programming.annotations.SoldierQualifier;
import academy.learn.programming.interfaces.Salute;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.text.MessageFormat;

@Stateful
@SoldierQualifier
@ServiceManQualifier(ServiceManQualifier.TYPE.SOLDIER)
public class Soldier implements Salute, Serializable {
    @Override
    public String salute(String name) {
        return MessageFormat.format("Aye Aye Capt'n {0}", name);
    }
}
