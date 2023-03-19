package JSCP.RANDROBIN;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

import java.util.Arrays;
import java.util.Random;

public class Consumer implements CSProcess {
    private One2OneChannelInt[] res;
    private One2OneChannelInt[] req;
    private int index;
    private int consumed = 0;

    public Consumer(One2OneChannelInt[] res, One2OneChannelInt[] req, int index) {
        this.res = res;
        this.req = req;
        this.index = index;
    }

    @Override
    public void run() {
        Random random=new Random();
        int x = random.nextInt(res.length);
        while (true) {
            req[x].out().write(0);
            int respond = res[x].in().read();

            if (respond == 1) {
                req[x].out().write(1);

                consumed+=res[x].in().read();
            }
            x = random.nextInt(res.length);

        }
    }

    public One2OneChannelInt getRes(int i) {
        return res[i];
    }

    public One2OneChannelInt getReq(int i) {
        return req[i];
    }
}
