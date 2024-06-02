package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.Month;
import it.hivecampuscompany.hivecampus.model.Permanence;

public abstract class LeaseBean {
    protected AdBean adBean;
    protected Month month;
    protected Permanence permanence;

    public AdBean getAdBean() {
        return adBean;
    }

    public void setAdBean(AdBean adBean) {
        this.adBean = adBean;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Permanence getPermanence() {
        return permanence;
    }

    public void setPermanence(Permanence permanence) {
        this.permanence = permanence;
    }

    LeaseBean(AdBean adBean, Month month, Permanence permanence) {
        setAdBean(adBean);
        setMonth(month);
        setPermanence(permanence);
    }
}
