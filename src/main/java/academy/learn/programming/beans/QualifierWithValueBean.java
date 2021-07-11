package academy.learn.programming.beans;

import academy.learn.programming.annotations.ServiceManQualifier;
import academy.learn.programming.annotations.Web;
import academy.learn.programming.interfaces.Salute;

import javax.inject.Inject;

@Web
public class QualifierWithValueBean {

    @Inject
    @ServiceManQualifier(ServiceManQualifier.TYPE.POLICE)
    private Salute policeSalute;

    @Inject
    @ServiceManQualifier(ServiceManQualifier.TYPE.SOLDIER)
    private Salute soldierSalute;

    private String police;
    private String soldier;
    private String name;

    public void policeSalutation() {
        police = policeSalute.salute(name);
    }

    public void soldierSalutation() {
        soldier = soldierSalute.salute(name);
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public String getSoldier() {
        return soldier;
    }

    public void setSoldier(String soldier) {
        this.soldier = soldier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
