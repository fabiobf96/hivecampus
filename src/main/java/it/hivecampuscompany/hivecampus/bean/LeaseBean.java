package it.hivecampuscompany.hivecampus.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;

public class LeaseBean {
    private LeaseRequestBean leaseRequestBean;
    private AdBean adBean;
    private String starting;
    private String duration;
    private byte[] contract;

    public LeaseBean(LeaseRequestBean leaseRequestBean, String path) throws IOException {
        this.leaseRequestBean = leaseRequestBean;
        this.contract = fromPathToBytes(path);
    }

    public LeaseBean(AdBean adBean, String starting, String duration, byte[] contract) {
        this.adBean = adBean;
        this.starting = starting;
        this.duration = duration;
        this.contract = contract;
    }

    public LeaseRequestBean getLeaseRequestBean() {
        return leaseRequestBean;
    }

    public byte[] getContract() {
        return contract;
    }
    public void getContract(String path) {
        path += "/contract-" + LocalDate.now() + ".pdf";
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(contract);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte [] fromPathToBytes (String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    @Override
    public String toString() {
        return adBean.toString() + ", " + starting + ", " + duration;
    }
}
