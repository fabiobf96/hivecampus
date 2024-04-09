package it.hivecampuscompany.hivecampus.bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LeaseBean {
    private LeaseRequestBean leaseRequestBean;
    private byte[] contract;

    public LeaseBean(LeaseRequestBean leaseRequestBean, String path) throws IOException {
        this.leaseRequestBean = leaseRequestBean;
        this.contract = fromPathToBytes(path);
    }

    public LeaseRequestBean getLeaseRequestBean() {
        return leaseRequestBean;
    }

    public byte[] getContract() {
        return contract;
    }

    private byte [] fromPathToBytes (String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }
}
