package it.hivecampuscompany.hivecampus.manager;

import com.opencsv.CSVWriter;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.csv.CSVUtility;
import it.hivecampuscompany.hivecampus.dao.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.*;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
* @author Marina Sotiropoulos
*/

class LeaseRequestTest {

    private static final String USER_CSV_PATH = "db/csv/user-table.csv";
    private static final String ACCOUNT_CSV_PATH = "db/csv/account-table.csv";
    private static final String AD_CSV_PATH = "db/csv/ad-table.csv";
    private static final String LEASE_REQUEST_CSV_PATH = "db/csv/lease_request-table.csv";

    private static final String BACKUP_USER_CSV_PATH = USER_CSV_PATH + ".bak";
    private static final String BACKUP_ACCOUNT_CSV_PATH = ACCOUNT_CSV_PATH + ".bak";
    private static final String BACKUP_AD_CSV_PATH = AD_CSV_PATH + ".bak";
    private static final String BACKUP_LEASE_REQUEST_CSV_PATH = LEASE_REQUEST_CSV_PATH + ".bak";

    private static SessionManager sessionManager;
    private static LeaseRequestManager requestManager;

    private static SessionBean sessionBean;
    private static LeaseRequestBean reqBean1;
    private static LeaseRequestBean reqBean2;

    private static Properties properties;

    @BeforeAll
    static void setUp() throws IOException {
        backupCSVFiles();
        insertTestData();

        DAOFactoryFacade.getInstance().setPersistenceType(PersistenceType.CSV);

        sessionManager = SessionManager.getInstance();
        AccountDAO accountDAO = DAOFactoryFacade.getInstance().getAccountDAO();
        AdDAO adDAO = DAOFactoryFacade.getInstance().getAdDAO();
        requestManager = new LeaseRequestManager();
        properties = LanguageLoader.getLanguageProperties();

        // Create a new valid session
        User user = new User("user.example@gmail.com", "pippo", "tenant");
        Session session = sessionManager.createSession(user);
        sessionBean = new SessionBean(session);
        sessionBean.setClient(SessionBean.Client.CLI);

        Account tenant = accountDAO.retrieveAccountInformationByEmail(user.getEmail());

        Ad ad1 = adDAO.retrieveAdByID(CSVUtility.findLastRowIndex(new File(AD_CSV_PATH)), false);
        Ad ad2 = adDAO.retrieveAdByID(CSVUtility.findLastRowIndex(new File(AD_CSV_PATH)) - 1, false);

        reqBean1 = createLeaseRequestBean(ad1, tenant);
        reqBean2 = createLeaseRequestBean(ad2, tenant);
    }

    @AfterAll
    static void restoreData() throws IOException {
        restoreCSVFiles();
    }

    private static void backupCSVFiles() throws IOException {
        Files.copy(Path.of(USER_CSV_PATH), Path.of(BACKUP_USER_CSV_PATH), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Path.of(ACCOUNT_CSV_PATH), Path.of(BACKUP_ACCOUNT_CSV_PATH), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Path.of(AD_CSV_PATH), Path.of(BACKUP_AD_CSV_PATH), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Path.of(LEASE_REQUEST_CSV_PATH), Path.of(BACKUP_LEASE_REQUEST_CSV_PATH), StandardCopyOption.REPLACE_EXISTING);
    }

    private static void restoreCSVFiles() throws IOException {
        Path userCsvPath = Path.of(USER_CSV_PATH);
        Path backupUserCsvPath = Path.of(BACKUP_USER_CSV_PATH);

        Path accountCsvPath = Path.of(ACCOUNT_CSV_PATH);
        Path backupAccountCsvPath = Path.of(BACKUP_ACCOUNT_CSV_PATH);

        Path adCsvPath = Path.of(AD_CSV_PATH);
        Path backupAdCsvPath = Path.of(BACKUP_AD_CSV_PATH);

        Path leaseRequestCsvPath = Path.of(LEASE_REQUEST_CSV_PATH);
        Path backupLeaseRequestCsvPath = Path.of(BACKUP_LEASE_REQUEST_CSV_PATH);

        Files.copy(backupUserCsvPath, userCsvPath, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(backupAccountCsvPath, accountCsvPath, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(backupAdCsvPath, adCsvPath, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(backupLeaseRequestCsvPath, leaseRequestCsvPath, StandardCopyOption.REPLACE_EXISTING);

        Files.deleteIfExists(backupUserCsvPath);
        Files.deleteIfExists(backupAccountCsvPath);
        Files.deleteIfExists(backupAdCsvPath);
        Files.deleteIfExists(backupLeaseRequestCsvPath);
    }

    private static void insertTestData() throws IOException {
        try (CSVWriter userWriter = new CSVWriter(new FileWriter(USER_CSV_PATH, true));
             CSVWriter accountWriter = new CSVWriter(new FileWriter(ACCOUNT_CSV_PATH, true));
             CSVWriter adWriter = new CSVWriter(new FileWriter(AD_CSV_PATH, true));
             CSVWriter leaseRequestWriter = new CSVWriter(new FileWriter(LEASE_REQUEST_CSV_PATH, true))) {

            userWriter.writeNext(new String[]{"user.example@gmail.com", "password", "tenant"});
            accountWriter.writeNext(new String[]{"user.example@gmail.com", "User", "Example", "000 000 0000"});

            int adID = CSVUtility.findLastRowIndex(new File(AD_CSV_PATH)) + 1;
            adWriter.writeNext(new String[]{String.valueOf(adID), "user.example@gmail.com", "1", "1", "1", "9", "350"});

            int reqID = CSVUtility.findLastRowIndex(new File(LEASE_REQUEST_CSV_PATH)) + 1;
            leaseRequestWriter.writeNext(new String[]{String.valueOf(reqID), String.valueOf(adID), "user.example@gmail.com", "2", "10", "6", "I would like to rent this apartment"});
        }
    }

    private static LeaseRequestBean createLeaseRequestBean(Ad ad, Account tenant) {
        LeaseRequestBean reqBean = new LeaseRequestBean();
        reqBean.setAdBean(ad.toBean());
        reqBean.setTenant(tenant.toBean());
        reqBean.setDuration(6);
        reqBean.setLeaseMonth(9);
        reqBean.setMessage("I would like to rent this apartment");
        reqBean.setStatus(LeaseRequestStatus.PROCESSING);
        return reqBean;
    }

    @Test
    void testSendLeaseRequest_InvalidSession() {
        sessionManager.deleteSession(sessionBean);
        assertThrows(InvalidSessionException.class, () -> requestManager.sendLeaseRequest(sessionBean, reqBean1));
    }

    @Test
    void testSendLeaseRequest_Failure() {
        String result = assertDoesNotThrow(() -> requestManager.sendLeaseRequest(sessionBean, reqBean1));
        assertEquals(properties.getProperty("REQUEST_ALREADY_SENT_MSG"), result);
    }

    @Test
    void testSendLeaseRequest_Success() {
        String result = assertDoesNotThrow(() -> requestManager.sendLeaseRequest(sessionBean, reqBean2));
        assertEquals(properties.getProperty("SUCCESS_MSG_SEND_REQUEST"), result);
    }

    @Test
    void testDeleteLeaseRequest_Failure() {
        reqBean2.setStatus(LeaseRequestStatus.ACCEPTED);
        boolean result = assertDoesNotThrow(() -> requestManager.deleteLeaseRequest(reqBean2));
        assertFalse(result);
    }
}