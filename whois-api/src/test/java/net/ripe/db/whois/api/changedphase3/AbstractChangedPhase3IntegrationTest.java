package net.ripe.db.whois.api.changedphase3;

import net.ripe.db.whois.api.AbstractIntegrationTest;
import net.ripe.db.whois.api.changedphase3.util.Context;
import net.ripe.db.whois.api.rest.mapper.WhoisObjectMapper;
import net.ripe.db.whois.common.IntegrationTest;
import net.ripe.db.whois.common.MaintenanceMode;
import net.ripe.db.whois.common.rpsl.RpslObject;
import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

@Category(IntegrationTest.class)
public abstract class AbstractChangedPhase3IntegrationTest extends AbstractIntegrationTest {
    protected static final RpslObject TEST_PERSON = RpslObject.parse("" +
            "person:    Test Person\n" +
            "address:   Singel 258\n" +
            "phone:     +31 6 12345678\n" +
            "nic-hdl:   TP1-TEST\n" +
            "mnt-by:    OWNER-MNT\n" +
            "source:    TEST\n");
    protected static final RpslObject OWNER_MNTNER = RpslObject.parse("" +
            "mntner:        OWNER-MNT\n" +
            "descr:         Test maintainer\n" +
            "admin-c:       TP1-TEST\n" +
            "upd-to:        upd-to@ripe.net\n" +
            "mnt-nfy:       mnt-nfy@ripe.net\n" +
            "auth:          MD5-PW $1$EmukTVYX$Z6fWZT8EAzHoOJTQI6jFJ1  # 123\n" +
            "mnt-by:        OWNER-MNT\n" +
            "source:        TEST");
    @Autowired protected MaintenanceMode maintenanceMode;
    @Autowired private WhoisObjectMapper whoisObjectMapper;
    protected Context context;

    @Before
    public void setup() {
        databaseHelper.addObject("person: Test Person\nnic-hdl: TP1-TEST");
        databaseHelper.addObject(OWNER_MNTNER);
        databaseHelper.updateObject(TEST_PERSON);
        maintenanceMode.set("FULL,FULL");
        context = new Context(getPort(),getPort(),whoisObjectMapper );
    }
}
