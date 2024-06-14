package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.exception.InvalidExtentionException;
import it.hivecampuscompany.hivecampus.model.Month;
import it.hivecampuscompany.hivecampus.model.Permanence;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.logging.Logger;

public class LeaseContractBean extends LeaseBean{
    private LeaseRequestBean leaseRequestBean;
    private final byte[] contract;


    private static final Logger LOGGER = Logger.getLogger(LeaseContractBean.class.getName());

    public LeaseContractBean(LeaseRequestBean leaseRequestBean, String path) throws IOException, InvalidExtentionException {
        super(leaseRequestBean.getAdBean(), leaseRequestBean.getMonth(), leaseRequestBean.getPermanence());
        this.leaseRequestBean = leaseRequestBean;
        this.contract = fromPathToBytes(path);
    }

    public LeaseContractBean(AdBean adBean, int starting, int duration, byte[] contract) {
        super(adBean, Month.fromInt(starting), Permanence.fromInt(duration));
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

    public static byte[] fromPathToBytes(String pathString) throws IOException, InvalidExtentionException {
        Path path = Paths.get(pathString);
        if (path.toString().endsWith(".pdf")) {
            return Files.readAllBytes(path);
        }
        throw new InvalidExtentionException("INVALID_EXTENSION_MSG");
    }

    @Override
    public String toString() {
        return adBean.toString() + ", " +  getMonth() + ", " + getPermanence();
    }
}
