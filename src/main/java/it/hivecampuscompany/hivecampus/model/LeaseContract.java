package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.LeaseContractBean;

public class LeaseContract extends Lease {
    private final byte[] contract;
    private boolean signed;

    public LeaseContract(Ad ad, Account tenant, int starting, int duration, byte[] contract, boolean signed) {
        super(0, ad, tenant, Month.fromInt(starting), Permanence.fromInt(duration));
        this.contract = contract;
        setSigned(signed);
    }

    public LeaseContract(int id, Ad ad, int starting, int duration, byte[] contract, boolean signed) {
        super(id, ad, null, Month.fromInt(starting), Permanence.fromInt(duration));
        this.contract = contract;
        setSigned(signed);
    }

    public byte[] getContract() {
        return contract;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public LeaseContractBean toBean() {
        return new LeaseContractBean(ad.toBean(), getLeaseMonth().toString(), duration.toString(), contract);
    }
}
