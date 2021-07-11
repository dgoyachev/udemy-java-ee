package academy.learn.programming.sessionbeans;

import academy.learn.programming.annotations.PoliceQualifier;
import academy.learn.programming.annotations.ServiceManQualifier;
import academy.learn.programming.interfaces.Salute;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.text.MessageFormat;

@Stateful
@PoliceQualifier
@ServiceManQualifier(ServiceManQualifier.TYPE.POLICE)
public class Police implements Salute, Serializable {
    @Override
    public String salute(String name) {
        return MessageFormat.format("Yes sir! {0}", name);
    }
}
