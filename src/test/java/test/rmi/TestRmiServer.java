package test.rmi;

import org.junit.Test;
import test.base.TestBase;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class TestRmiServer extends TestBase {

    @Test
    public void test() throws RemoteException, AlreadyBoundException, MalformedURLException, InterruptedException {
        LocateRegistry.createRegistry(1099);
        Naming.bind("//127.0.0.1:1099/remote", new TestRmiRemote());

//        while (true) {
//            Thread.sleep(1000L);
//        }
    }
}
