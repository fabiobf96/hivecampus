package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.Account;
import it.hivecampuscompany.hivecampus.model.Ad;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LeaseBean {
    private LeaseRequestBean leaseRequestBean;
    private AdBean adBean;
    private String starting;
    private String duration;
    private byte[] contract;
    private boolean signed;
    private Instant timeStamp;

    public LeaseBean(LeaseRequestBean leaseRequestBean, String path) throws IOException {
        this.leaseRequestBean = leaseRequestBean;
        this.contract = fromPathToBytes(path);
    }

    public LeaseBean(AdBean adBean, String starting, String duration, byte[] contract, boolean signed, Instant timeStamp) {
        this.adBean = adBean;
        this.starting = starting;
        this.duration = duration;
        this.contract = contract;
        this.signed = signed;
        this.timeStamp = timeStamp;
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
        return starting + ", " + duration;
    }
}
