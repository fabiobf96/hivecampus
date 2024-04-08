package it.hivecampuscompany.hivecampus.bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LeaseBean {
    private AdBean adBean;
    private byte[] contract;

    public LeaseBean(AdBean adBean, String path) throws IOException {
        this.adBean = adBean;
        this.contract = fromPathToBytes(path);
    }

    public AdBean getAdBean() {
        return adBean;
    }

    public byte[] getContract() {
        return contract;
    }

    private byte [] fromPathToBytes (String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }
}
