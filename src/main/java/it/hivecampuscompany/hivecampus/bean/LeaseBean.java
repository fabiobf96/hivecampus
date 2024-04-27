package it.hivecampuscompany.hivecampus.bean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.logging.Logger;

public class LeaseBean {
    private LeaseRequestBean leaseRequestBean;
    private AdBean adBean;
    private String starting;
    private String duration;
    private byte[] contract;
    private static final Logger LOGGER = Logger.getLogger(LeaseBean.class.getName());
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
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
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
